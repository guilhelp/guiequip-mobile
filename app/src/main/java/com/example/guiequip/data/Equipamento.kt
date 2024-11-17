package com.example.guiequip.data

data class Equipamento(
    val id: String? = null,
    val departamentoId: String = "",
    val tipoEquipamento: String = "",
    val marca: String = "",
    val modelo: String = "",
    val processador: String? = null,
    val ram: String? = null,
    val hdSsd: String? = null,
    val usuario: Usuario = Usuario()
) {

    constructor() : this(
        id = null,
        departamentoId = "",
        tipoEquipamento = "",
        marca = "",
        modelo = "",
        processador = null,
        ram = null,
        hdSsd = null,
        usuario = Usuario()
    )

    companion object {

    }
}

data class Usuario(
    val id: String = "",
    val nome: String = ""
) {

    constructor() : this(id = "", nome = "")
}
