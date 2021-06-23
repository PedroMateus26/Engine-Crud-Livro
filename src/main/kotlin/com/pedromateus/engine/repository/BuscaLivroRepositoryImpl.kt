package com.pedromateus.engine.repository

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import com.pedromateus.engine.controller.dto.LivroEvent
import com.pedromateus.engine.controller.dto.LivroRequest
import io.micronaut.http.annotation.Controller
import io.micronaut.http.client.annotation.Client
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Singleton

@Singleton
class BuscaLivroRepositoryImpl(private val cqlSession: CqlSession) : BuscaLivroRepository {

    private val logger=LoggerFactory.getLogger(this::class.java)

    override fun findById(id: UUID): LivroEvent = converteRowParaLivroEvent(
        cqlSession.execute(
            QueryBuilder.selectFrom("prateleira")
                .all()
                .whereColumn("id")
                .isEqualTo(QueryBuilder.literal(id))
                .build()
        ).one()!!
    )

    override fun findAllLivros(): List<LivroEvent> {
        val rs=cqlSession.execute(QueryBuilder.selectFrom("prateleira").all().build())
        val list=rs.map {
            converteRowParaLivroEvent(it)
        }.toList()
        list.forEach {
            logger.info("$it")
        }
        return list
    }


    private fun converteRowParaLivroEvent(row: Row) = LivroEvent(
        id = row.getUuid("id"),
        LivroRequest(titulo = row.getString("titulo")!!, autor = row.getString("autor")!!)
    )

}