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
    // Construtor vazio necessário para a desserialização do Firestore
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
        // Métodos de conversão personalizados, se necessário
    }
}

data class Usuario(
    val id: String = "",
    val nome: String = ""
) {
    // Construtor vazio necessário para a desserialização do Firestore
    constructor() : this(id = "", nome = "")
}
