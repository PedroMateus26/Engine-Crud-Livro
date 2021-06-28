package com.pedromateus.engine.service

import com.pedromateus.engine.core.mapper.LivroConverter
import com.pedromateus.engine.core.ports.BuscaLivroRepositoryPort
import com.pedromateus.engine.core.service.EngineServicePortImpl
import com.pedromateus.engine.database.entity.LivroEntity
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.util.*

@MicronautTest
class EngineServiceTest:AnnotationSpec() {

    val repository= mockk<BuscaLivroRepositoryPort>()
    val service=EngineServicePortImpl(repository)

    lateinit var livroEntity: LivroEntity
    lateinit var list: List<LivroEntity>

    @BeforeEach
    fun setup(){
        livroEntity= LivroEntity(UUID.randomUUID(), "titulo","autor")
        list= listOf(livroEntity)
    }

    @Test
    fun `deve realizar um consulta pelo id`(){
        every { repository.findById(any()) } answers { livroEntity }
        val livro = LivroConverter.converteLivroEntityParaLivro(livroEntity)
        val result=service.findById(livroEntity.id!!)


        result.id shouldBe livro.id
        result.titulo shouldBe livro.titulo
        result.autor shouldBe livro.autor



    }

    @Test
    fun `deve realizar um consulta e buscat todos os livros cadastrados`(){
        every { repository.findAllLivros() } answers { list }
        val listLivro=list.map { LivroConverter.converteLivroEntityParaLivro(it) }
        val result=service.findAllLivros()

        result.get(0).id shouldBe listLivro.get(0).id
        result.get(0).titulo shouldBe listLivro.get(0).titulo
        result.get(0).autor shouldBe listLivro.get(0).autor

    }
}