package com.pedromateus.engine.controller.dto

import io.micronaut.core.annotation.Introspected

@Introspected
data class LivroRequest(
    val titulo: String,
    val autor: String
)