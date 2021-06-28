package com.pedromateus.engine.core.ports

import com.pedromateus.engine.database.entity.LivroEntity
import java.util.UUID

interface BuscaLivroRepositoryPort {

    fun findById(id:UUID): LivroEntity
    fun findAllLivros():List<LivroEntity>
}