package com.pedromateus.engine
import com.pedromateus.engine.core.ports.EngineService
import com.pedromateus.engine.entrypoint.controller.EngineController
import com.pedromateus.engine.entrypoint.controller.dto.LivroEvent
import com.pedromateus.engine.entrypoint.controller.dto.LivroRequest
import io.kotest.core.spec.style.AnnotationSpec
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

@MicronautTest
class EngineCrudeLivroTest(private val application: EmbeddedApplication<*>): StringSpec({

    "test the server is running" {
        assert(application.isRunning)
    }

    var service= mockk<EngineService>()
    val controller= EngineController(service)

    lateinit var livroEvent: LivroEvent

    @AnnotationSpec.BeforeEach
    fun setup(){
        livroEvent= LivroEvent(UUID.randomUUID(), LivroRequest("titulo","autor"))
    }

    @AnnotationSpec.Test
    fun `deve realizar um consulta pelo id`(id: UUID){
        every { service.findById(any()) } answers { livroEvent }
        val result=controller.findById(id)

        result.body() shouldBe livroEvent

    }
})
