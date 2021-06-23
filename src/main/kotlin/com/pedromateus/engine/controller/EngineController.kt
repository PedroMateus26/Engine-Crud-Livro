package com.pedromateus.engine.controller

import com.pedromateus.engine.controller.dto.LivroEvent
import com.pedromateus.engine.service.EngineService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/busca.livros")
class EngineController(private val engineService: EngineService) {

    private val logger=LoggerFactory.getLogger(this::class.java)

    @Get("/{id}")
    fun findById(id:UUID): HttpResponse<LivroEvent> {
        logger.info("Recebendo id: $id")
        return HttpResponse.ok(engineService.findById(id))
    }

    @Get
    fun findAllLivro():HttpResponse<List<LivroEvent>> {
        logger.info("Retornando lista")
        return HttpResponse.ok(engineService.findAllLivros())
    }

}