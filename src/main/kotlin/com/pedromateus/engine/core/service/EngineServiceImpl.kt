package com.pedromateus.engine.core.service

import com.pedromateus.engine.core.ports.EngineService
import com.pedromateus.engine.entrypoint.controller.dto.LivroEvent
import com.pedromateus.engine.core.ports.BuscaLivroRepository
import java.util.*
import javax.inject.Singleton

@Singleton
class EngineServiceImpl(private val buscaLivroRepository: BuscaLivroRepository): EngineService {
    override fun findById(id: UUID): LivroEvent =buscaLivroRepository.findById(id)
    override fun findAllLivros():List<LivroEvent> = buscaLivroRepository.findAllLivros()
}