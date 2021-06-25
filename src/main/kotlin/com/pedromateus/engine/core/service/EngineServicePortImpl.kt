package com.pedromateus.engine.core.service

import com.pedromateus.engine.core.mapper.LivroConverter
import com.pedromateus.engine.core.model.Livro
import com.pedromateus.engine.core.ports.EngineServicePort
import com.pedromateus.engine.core.ports.BuscaLivroRepositoryPort
import java.util.*
import javax.inject.Singleton

@Singleton
class EngineServicePortImpl(private val buscaLivroRepositoryPort: BuscaLivroRepositoryPort): EngineServicePort {
    override fun findById(id: UUID): Livro {
        val livroEntity = buscaLivroRepositoryPort.findById(id)
        return LivroConverter.converteLivroEntityParaLivro(livroEntity)
    }
    override fun findAllLivros(): List<Livro> {
        val listEntity = buscaLivroRepositoryPort.findAllLivros()
        return listEntity.map {LivroConverter.converteLivroEntityParaLivro(it)}
    }
}