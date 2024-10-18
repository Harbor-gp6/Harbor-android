package com.example.apl_mobile_harbor.view_models.pedidos

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apl_mobile_harbor.classes.auth.TokenManager
import com.example.apl_mobile_harbor.classes.pedido.Pedido
import com.example.apl_mobile_harbor.interfaces.ApiHarbor
import kotlinx.coroutines.launch

class PedidosViewModel(private val tokenManager: TokenManager, private val apiHarbor: ApiHarbor): ViewModel() {
    val pedidos = mutableStateListOf<Pedido>()

    val usuario = tokenManager.getUsuario()

    fun atualizarListaDePedidos() {
        viewModelScope.launch {
            try {
                if (usuario != null) {
                    val retorno = apiHarbor.getPedidos()
                    if (retorno.isSuccessful) {
                        pedidos.clear()
                        pedidos.addAll(retorno.body() ?: emptyList())
                    } else {
                        Log.i("erro", "Erro")
                    }
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }

        fun getPedidos(): List<Pedido> {
            atualizarListaDePedidos()
            return pedidos
        }
    }
}