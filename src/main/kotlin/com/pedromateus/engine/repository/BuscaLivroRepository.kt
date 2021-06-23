package com.pedromateus.engine.repository

import com.pedromateus.engine.controller.dto.LivroEvent
import java.util.*

interface BuscaLivroRepository {

    fun findById(id:UUID): LivroEvent
    fun findAllLivros():List<LivroEvent>
}