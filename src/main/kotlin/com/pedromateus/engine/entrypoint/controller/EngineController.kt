package com.pedromateus.engine.entrypoint.controller

import com.pedromateus.engine.core.mapper.LivroConverter
import com.pedromateus.engine.entrypoint.controller.dto.LivroEvent
import com.pedromateus.engine.core.ports.EngineServicePort
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/busca/livros")
class EngineController(private val engineServicePort: EngineServicePort) {

    private val logger=LoggerFactory.getLogger(this::class.java)

    @Get("/{id}")
    fun findById(id:UUID): HttpResponse<LivroEvent> {
        logger.info("Recebendo id: $id")
        val livro=engineServicePort.findById(id)
        val livroEvent=LivroConverter.converteLivroParaLivroEvent(livro)
        return HttpResponse.ok(livroEvent)
    }

    @Get
    fun findAllLivro():HttpResponse<List<LivroEvent>> {
        logger.info("Retornando lista")
        val livros=engineServicePort.findAllLivros()
        val livrosEvent=livros.map { LivroConverter.converteLivroParaLivroEvent(it) }
        return HttpResponse.ok(livrosEvent)
    }

}