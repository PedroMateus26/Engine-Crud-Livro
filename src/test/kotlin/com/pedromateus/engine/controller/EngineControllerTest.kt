package com.pedromateus.engine.controller

import com.pedromateus.engine.core.ports.EngineService
import com.pedromateus.engine.entrypoint.controller.EngineController
import com.pedromateus.engine.entrypoint.controller.dto.LivroEvent
import com.pedromateus.engine.entrypoint.controller.dto.LivroRequest
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.AnnotationSpec.*
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.util.*

@MicronautTest
class EngineControllerTest:AnnotationSpec() {
    var service= mockk<EngineService>()
    val controller= EngineController(service)

    lateinit var livroEvent: LivroEvent
    lateinit var list: List<LivroEvent>

    @BeforeEach
    fun setup(){
        livroEvent= LivroEvent(UUID.randomUUID(), LivroRequest("titulo","autor"))
        list= listOf(livroEvent)
    }

    @Test
    fun `deve realizar um consulta pelo id`(){
        every { service.findById(any()) } answers { livroEvent }
        val result=controller.findById(livroEvent.id!!)

        result.body() shouldBe livroEvent

    }

    @Test
    fun `deve realizar um consulta e buscat todos os livros cadastrados`(){
        every { service.findAllLivros() } answers { list }
        val result=controller.findAllLivro()

        result.body() shouldBe list

    }
}