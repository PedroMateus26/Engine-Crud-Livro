package com.pedromateus.engine.entrypoint.controller.dto

import io.micronaut.core.annotation.Introspected

@Introspected
data class LivroRequest(
    val titulo: String,
    val autor: String
)