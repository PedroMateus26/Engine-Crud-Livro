package com.pedromateus.engine.client

import com.pedromateus.engine.controller.dto.LivroEvent
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import java.util.*

@Client( "http://localhost:8081")
interface BuscandoDadosCadastrados {

    @Get("/{id}")
    fun findById(id: UUID): LivroEvent
    @Get
    fun findAll():List<LivroEvent>
}