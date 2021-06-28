package com.pedromateus.engine.controller

import com.pedromateus.engine.core.mapper.LivroConverter
import com.pedromateus.engine.core.model.Livro
import com.pedromateus.engine.core.ports.EngineServicePort
import com.pedromateus.engine.entrypoint.controller.EngineController
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.util.*

@MicronautTest
class EngineControllerTest:AnnotationSpec() {
    var service= mockk<EngineServicePort>()
    val controller= EngineController(service)

    lateinit var livro: Livro
    lateinit var list: List<Livro>

    @BeforeEach
    fun setup(){
        livro= Livro(UUID.randomUUID(), "titulo","autor")
        list= listOf(livro)
    }

    @Test
    fun `deve realizar um consulta pelo id`(){
        every { service.findById(any()) } answers { livro }
        val livroEvent=LivroConverter.converteLivroParaLivroDTO(livro)
        val result=controller.findById(livro.id!!)

        result.body().id shouldBe livroEvent.id
        result.body().titulo shouldBe livroEvent.titulo
        result.body().autor shouldBe livroEvent.autor

    }

    @Test
    fun `deve realizar um consulta e buscat todos os livros cadastrados`(){
        every { service.findAllLivros() } answers { list }
        val listEvent=list.map { LivroConverter.converteLivroParaLivroDTO(it) }
        val result=controller.findAllLivro()

        result.body().get(0).id shouldBe listEvent.get(0).id
        result.body().get(0).titulo shouldBe listEvent.get(0).titulo
        result.body().get(0).autor shouldBe listEvent.get(0).autor
    }
}