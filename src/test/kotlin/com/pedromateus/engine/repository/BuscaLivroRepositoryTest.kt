package com.pedromateus.engine.repository

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import com.pedromateus.engine.database.entity.LivroEntity
import com.pedromateus.engine.database.scylla.BuscaLivroRepositoryImpl
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.util.UUID

@MicronautTest
class BuscaLivroRepositoryTest : AnnotationSpec() {

    val cqlSession = mockk<CqlSession>(relaxed = true)
    val row = mockk<Row>()
    val buscaLivroRepositoryImpl = BuscaLivroRepositoryImpl(cqlSession)

    lateinit var livroEntityBuscado: LivroEntity
    lateinit var list: List<LivroEntity>

    @BeforeEach
    fun setUp() {
        livroEntityBuscado = LivroEntity(UUID.randomUUID(), "titulo", "autor")
        list = listOf()
    }

    @Test
    fun `deve buscar quando o id for fornecido`() {

        every {
            cqlSession.execute(
                QueryBuilder.selectFrom("prateleira")
                    .all()
                    .whereColumn("id")
                    .isEqualTo(QueryBuilder.literal(livroEntityBuscado.id))
                    .build()
            ).one()
        } answers { row }

        testaRow()
        val result = buscaLivroRepositoryImpl.findById(livroEntityBuscado.id!!)
        result shouldBe livroEntityBuscado
    }

    @Test
    fun `deve buscar todos os elementos e armazenar em uma lista`() {

        every {
            cqlSession.execute(QueryBuilder.selectFrom("prateleira").all().build()).one()

        } answers { row }

        testaRow()

        val result = buscaLivroRepositoryImpl.findAllLivros()
        result shouldBe list

    }

    fun testaRow() {
        every { row.getUuid("id") } answers { livroEntityBuscado.id }
        every { row.getString("titulo") } answers { livroEntityBuscado.titulo }
        every { row.getString("autor") } answers { livroEntityBuscado.autor }
    }

}
