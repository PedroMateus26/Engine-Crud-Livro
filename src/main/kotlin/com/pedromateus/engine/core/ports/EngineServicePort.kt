package com.pedromateus.engine.core.ports

import com.pedromateus.engine.core.model.Livro
import java.util.UUID

interface EngineServicePort {
    fun findById(id:UUID): Livro
    fun findAllLivros():List<Livro>
}