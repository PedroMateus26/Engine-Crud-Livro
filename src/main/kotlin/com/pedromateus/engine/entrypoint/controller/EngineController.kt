package com.pedromateus.engine.entrypoint.controller

import com.pedromateus.engine.core.mapper.LivroConverter
import com.pedromateus.engine.entrypoint.controller.dto.LivroDTO
import com.pedromateus.engine.core.ports.EngineServicePort
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory
import java.util.UUID

@Controller("/busca/livros")
class EngineController(private val engineServicePort: EngineServicePort) {

    private val logger=LoggerFactory.getLogger(this::class.java)

    @Get("/{id}")
    fun findById(id:UUID): HttpResponse<LivroDTO> {
        logger.info("Recebendo id: $id")
        val livro=engineServicePort.findById(id)
        val livroDTO=LivroConverter.converteLivroParaLivroDTO(livro)
        return HttpResponse.ok(livroDTO)
    }

    @Get
    fun findAllLivro():HttpResponse<List<LivroDTO>> {
        logger.info("Retornando lista")
        val livros=engineServicePort.findAllLivros()
        val livrosDTO=livros.map { LivroConverter.converteLivroParaLivroDTO(it) }
        return HttpResponse.ok(livrosDTO)
    }

}