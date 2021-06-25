package com.pedromateus.engine.database.entity

import io.micronaut.core.annotation.Introspected
import java.util.*

@Introspected
class LivroEntity (
    val id: UUID?,
    val titulo:String?,
    val autor:String?

    )