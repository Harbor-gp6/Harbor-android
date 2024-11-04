package com.example.apl_mobile_harbor.view_models.produto

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apl_mobile_harbor.classes.produto.Produto
import com.example.apl_mobile_harbor.interfaces.ApiHarbor
import kotlinx.coroutines.launch

class ProdutoViewModel(val apiHarbor: ApiHarbor): ViewModel() {

    val produtos = mutableStateListOf<Produto>()

    fun getProdutos() {
        viewModelScope.launch {
            try {
                val response = apiHarbor.getProdutos()
                if (response.isSuccessful) {
                    produtos.clear()
                    response.body()?.let { produtos.addAll(it) }
                } else {
                    Log.e("api", "Erro ao buscar produtos")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }
}