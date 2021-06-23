package com.pedromateus.engine.service

import com.pedromateus.engine.controller.dto.LivroEvent
import com.pedromateus.engine.repository.BuscaLivroRepository
import java.util.*
import javax.inject.Singleton

@Singleton
class EngineServiceImpl(private val buscaLivroRepository: BuscaLivroRepository):EngineService {
    override fun findById(id: UUID): LivroEvent=buscaLivroRepository.findById(id)
    override fun findAllLivros():List<LivroEvent> = buscaLivroRepository.findAllLivros()
}