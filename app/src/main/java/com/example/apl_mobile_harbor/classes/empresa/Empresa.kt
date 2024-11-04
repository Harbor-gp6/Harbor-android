package com.example.apl_mobile_harbor.classes.empresa

data class Empresa(
    val id: Int,
    val razaoSocial: String,
    val nomeFantasia: String,
    val cnpj: String,
    val endereco: Endereco
)

data class Endereco(
    val id: Int,
    val logradouro: String,
    val cidade: String,
    val estado: String,
    val numero: String,
    val cep: String,
    val complemento: String
)
