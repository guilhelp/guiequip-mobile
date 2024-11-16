package com.example.guiequip.data

import com.google.firebase.firestore.PropertyName

data class Departamento(
    val nome: String = "",
    val descricao: String = "",
    var id: String = ""
)