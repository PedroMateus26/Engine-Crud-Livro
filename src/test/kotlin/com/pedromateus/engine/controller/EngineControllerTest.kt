package com.pedromateus.engine.controller

import com.pedromateus.engine.controller.dto.LivroEvent
import com.pedromateus.engine.controller.dto.LivroRequest
import com.pedromateus.engine.service.EngineService
import io.kotest.core.spec.style.AnnotationSpec.BeforeEach
import io.kotest.core.spec.style.AnnotationSpec.Test
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.util.*

@MicronautTest
class EngineControllerTest {

    var service= mockk<EngineService>()
    val controller=EngineController(service)

    lateinit var livroEvent: LivroEvent

    @BeforeEach
    fun setup(){
        livroEvent= LivroEvent(UUID.randomUUID(), LivroRequest("titulo","autor"))
    }

    @Test
    fun `deve realizar um consulta pelo id`(id:UUID){
        every { service.findById(any()) } answers { livroEvent }
        val result=controller.findById(id)

        result.body() shouldBe livroEvent

    }
}