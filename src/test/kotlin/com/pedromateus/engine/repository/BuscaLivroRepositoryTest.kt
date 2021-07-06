package com.pedromateus.engine.repository

import com.datastax.oss.driver.api.core.CqlSession
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

            every { buscaLivroRepositoryImpl.converteRowParaLivroEvent(
                cqlSession.execute(
                    QueryBuilder.selectFrom("prateleira")
                        .all()
                        .whereColumn("id")
                        .isEqualTo(QueryBuilder.literal(livroEntityBuscado.id))
                        .build()
                ).first()!!
            )} answers {livroEntityBuscado}

        val result = buscaLivroRepositoryImpl.findById(livroEntityBuscado.id!!)
        result shouldBe livroEntityBuscado

    }

    @Test
    fun `deve buscar todos os elementos e armazenar em uma lista`() {

        every {
            cqlSession.execute(QueryBuilder.selectFrom("prateleira").all().build())
                .map {
                    LivroEntity(
                        id = it.getUuid("id"),
                        titulo = it.getString("titulo")!!,
                        autor = it.getString("autor")!!
                    )
                }.toList()
        } answers { list }

        val result = buscaLivroRepositoryImpl.findAllLivros()
        result shouldBe list


    }

}