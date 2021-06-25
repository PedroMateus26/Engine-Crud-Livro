package com.pedromateus.engine.database.scylla

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import com.pedromateus.engine.core.ports.BuscaLivroRepositoryPort
import com.pedromateus.engine.database.entity.LivroEntity
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Singleton

@Singleton
class BuscaLivroRepositoryImpl(private val cqlSession: CqlSession) : BuscaLivroRepositoryPort {

    private val logger=LoggerFactory.getLogger(this::class.java)

    override fun findById(id: UUID): LivroEntity = converteRowParaLivroEvent(
        cqlSession.execute(
            QueryBuilder.selectFrom("prateleira")
                .all()
                .whereColumn("id")
                .isEqualTo(QueryBuilder.literal(id))
                .build()
        ).one()!!
    )

    override fun findAllLivros(): List<LivroEntity> {
        val rs=cqlSession.execute(QueryBuilder.selectFrom("prateleira").all().build())
        val list=rs.map {
            converteRowParaLivroEvent(it)
        }.toList()
        list.forEach {
            logger.info("$it")
        }
        return list
    }


    private fun converteRowParaLivroEvent(row: Row) =
        LivroEntity(id = row.getUuid("id"),titulo = row.getString("titulo")!!, autor = row.getString("autor")!!)


}