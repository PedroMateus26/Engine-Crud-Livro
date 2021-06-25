package com.pedromateus.engine.core.ports

import com.pedromateus.engine.database.entity.LivroEntity
import com.pedromateus.engine.entrypoint.controller.dto.LivroEvent
import java.util.*

interface BuscaLivroRepositoryPort {

    fun findById(id:UUID): LivroEntity
    fun findAllLivros():List<LivroEntity>
}