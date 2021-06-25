package com.pedromateus.engine.core.ports

import com.pedromateus.engine.entrypoint.controller.dto.LivroEvent
import java.util.*

interface BuscaLivroRepository {

    fun findById(id:UUID): LivroEvent
    fun findAllLivros():List<LivroEvent>
}