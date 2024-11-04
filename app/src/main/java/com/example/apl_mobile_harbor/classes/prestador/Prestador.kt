package com.example.apl_mobile_harbor.classes.prestador

import com.example.apl_mobile_harbor.classes.empresa.Empresa

data class Prestador(
    val id: Int,
    val empresa: Empresa,
    val foto: String,
    val nome: String,
    val sobrenome: String,
    val telefone: String,
    val cpf: String,
    val email: String,
    val cargo: String
)
