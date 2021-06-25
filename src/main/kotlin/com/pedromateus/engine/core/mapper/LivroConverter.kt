package com.pedromateus.engine.core.mapper

import com.pedromateus.engine.core.model.Livro
import com.pedromateus.engine.database.entity.LivroEntity
import com.pedromateus.engine.entrypoint.controller.dto.LivroEvent

class LivroConverter {
    
    companion object{
        fun converteLivroEntityParaLivro(livroEntity:LivroEntity)=Livro(livroEntity.id,livroEntity.titulo,livroEntity.autor)
        fun converteLivroParaLivroEvent(livro:Livro)= LivroEvent(livro.id,livro.titulo,livro.autor)
    }
}