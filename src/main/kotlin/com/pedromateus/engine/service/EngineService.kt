package com.pedromateus.engine.service

import com.pedromateus.engine.controller.dto.LivroEvent
import java.util.*

interface EngineService {
    fun findById(id:UUID):LivroEvent
    fun findAllLivros():List<LivroEvent>
}