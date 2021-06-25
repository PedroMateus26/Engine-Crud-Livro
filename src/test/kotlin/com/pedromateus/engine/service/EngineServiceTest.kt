package com.pedromateus.engine.service

import com.pedromateus.engine.core.ports.BuscaLivroRepository
import com.pedromateus.engine.core.service.EngineServiceImpl
import com.pedromateus.engine.entrypoint.controller.dto.LivroEvent
import com.pedromateus.engine.entrypoint.controller.dto.LivroRequest
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.util.*

@MicronautTest
class EngineServiceTest:AnnotationSpec() {

    val repository= mockk<BuscaLivroRepository>()
    val service=EngineServiceImpl(repository)

    lateinit var livroEvent: LivroEvent
    lateinit var list: List<LivroEvent>

    @BeforeEach
    fun setup(){
        livroEvent= LivroEvent(UUID.randomUUID(), LivroRequest("titulo","autor"))
        list= listOf(livroEvent)
    }

    @Test
    fun `deve realizar um consulta pelo id`(){
        every { repository.findById(any()) } answers { livroEvent }
        val result=service.findById(livroEvent.id!!)

        result shouldBe livroEvent

    }

    @Test
    fun `deve realizar um consulta e buscat todos os livros cadastrados`(){
        every { repository.findAllLivros() } answers { list }
        val result=service.findAllLivros()

        result shouldBe list

    }
}