package com.pedromateus.engine.repository

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import com.pedromateus.engine.database.entity.LivroEntity
import com.pedromateus.engine.database.scylla.BuscaLivroRepositoryImpl
import io.kotest.core.spec.style.AnnotationSpec
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@MicronautTest
@ExtendWith(MockKExtension::class)
class BuscaLivroRepositoryTest:AnnotationSpec() {

    @InjectMockKs
    lateinit var buscaLivroRepositoryImpl: BuscaLivroRepositoryImpl

    @RelaxedMockK
    lateinit var cqlSession: CqlSession
    lateinit var livroEntityBuscado: LivroEntity

    @BeforeEach
    fun setUp(){
        MockKAnnotations.init(this)
        livroEntityBuscado=LivroEntity(UUID.randomUUID(),"titulo","autor")
    }

//    @Test
//    fun `deve buscar quando o id for fornecido`(){
//
//            cqlSession.execute(
//                QueryBuilder.selectFrom("prateleira")
//                    .all()
//                    .whereColumn("id")
//                    .isEqualTo(QueryBuilder.literal(livroEntityBuscado.id))
//                    .build()
//            ).one()!!
//
//        val result=buscaLivroRepositoryImpl.findById(livroEntityBuscado.id!!)
//        assertEquals(result, livroEntityBuscado)
//    }

    @Test
    fun `deve buscar todos os elementos e armazenar em uma lista`(){
        val rs=cqlSession.execute(QueryBuilder.selectFrom("prateleira").all().build())
        val list=rs.map {
            converteRowParaLivroEvent(it)
        }.toList()

        val result=buscaLivroRepositoryImpl.findAllLivros()
        assertEquals(result, list)
    }

    private fun converteRowParaLivroEvent(row: Row) =
        LivroEntity(id = row.getUuid("id"),titulo = row.getString("titulo")!!, autor = row.getString("autor")!!)
}