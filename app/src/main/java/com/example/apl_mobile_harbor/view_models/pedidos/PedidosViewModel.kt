package com.example.apl_mobile_harbor.view_models.pedidos

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apl_mobile_harbor.classes.auth.TokenManager
import com.example.apl_mobile_harbor.classes.auth.Usuario
import com.example.apl_mobile_harbor.classes.empresa.Empresa
import com.example.apl_mobile_harbor.classes.pedido.Cliente
import com.example.apl_mobile_harbor.classes.pedido.Pedido
import com.example.apl_mobile_harbor.classes.pedido.PedidoCriacao
import com.example.apl_mobile_harbor.classes.pedido.PedidoPrestadorCriacao
import com.example.apl_mobile_harbor.classes.pedido.PedidoProdutoCriacao
import com.example.apl_mobile_harbor.classes.pedido.convertToDate
import com.example.apl_mobile_harbor.classes.prestador.Prestador
import com.example.apl_mobile_harbor.classes.servico.Servico
import com.example.apl_mobile_harbor.componentes.separarNomeCompleto
import com.example.apl_mobile_harbor.interfaces.ApiHarbor
import kotlinx.coroutines.launch

class PedidosViewModel(private val tokenManager: TokenManager, private val apiHarbor: ApiHarbor, private val usuario: Usuario): ViewModel() {
    val pedidos = mutableStateListOf<Pedido>()

    val pedidosPorData = mutableStateListOf<Pedido>()

    var selectedDate = mutableStateOf("")

    val _pedidoAtual = MutableLiveData<Pedido?>()

    val empresaPrestador = mutableStateOf<Empresa?>(null)

    val pedidoAtual: LiveData<Pedido?> get() = _pedidoAtual

    private val _servicoSelectionState = MutableLiveData<Map<Int, Boolean>>(emptyMap())
    val servicoSelectionState: LiveData<Map<Int, Boolean>> get() = _servicoSelectionState

    private val _prestadorSelectionState = MutableLiveData<Map<Int, Prestador?>>()
    val prestadorSelectionState: LiveData<Map<Int, Prestador?>> get() = _prestadorSelectionState

    private val _servicoPrestadorState = MutableLiveData<Map<Int, Int?>>()
    val servicoPrestadorState: LiveData<Map<Int, Int?>> get() = _servicoPrestadorState

    private val _pedidoCriacao = mutableStateOf<PedidoCriacao?>(null)
    val pedidoCriacao: State<PedidoCriacao?> = _pedidoCriacao

    fun atualizarListaDePedidos() {
        viewModelScope.launch {
            try {
                val retorno = apiHarbor.getPedidos()
                if (retorno.isSuccessful) {
                    pedidos.clear()
                    pedidos.addAll(retorno.body()?.sortedBy { convertToDate(it.dataAgendamento) } ?: emptyList())
                } else {
                    Log.i("erro", "Erro")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }

    fun finalizarPedido(pedido: Pedido) {
        viewModelScope.launch {
            try {
                val response = apiHarbor.finalizarPedido(pedido.codigoPedido)
                if (response.isSuccessful) {
                    pedidos.remove(pedido)
                } else {
                    Log.e("erro", "Erro ao finalizar")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }

    fun cancelarPedido(pedido: Pedido) {
        viewModelScope.launch {
            try {
                val response = apiHarbor.cancelarPedido(pedido.codigoPedido)
                if (response.isSuccessful) {
                    pedidos.remove(pedido)
                } else {
                    Log.e("erro", "Erro ao cancelar")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }

    fun getPedidosPorData() {
        viewModelScope.launch {
            try {
                val response = apiHarbor.getPedidosPorData(selectedDate.value)
                if (response.isSuccessful) {
                    pedidosPorData.clear()
                    response.body()?.let { pedidosPorData.addAll(it) }
                } else {
                    Log.e("erro", "Erro ao buscar por data")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }

    fun getPedidoPorCodigo(codigo: String) {
        viewModelScope.launch {
            try {
                val response = apiHarbor.getPedidoPorCodigo(codigo)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _pedidoAtual.value = it
                        carregarPedido(it)
                    }
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }

    fun Pedido.toPedidoCriacao(): PedidoCriacao {
        val (nome, sobrenome) = separarNomeCompleto(this.nomeCliente)
        return PedidoCriacao(
            cliente = Cliente(
                nome = nome,
                sobrenome = sobrenome, // Se você tiver sobrenome no Pedido, inclua aqui.
                telefone = this.telefoneCliente,
                cpf = this.cpfCliente,
                email = this.emailCliente
            ),
            cnpjEmpresa = (empresaPrestador.value?.cnpj ?: ""), // Preencha com o valor adequado, se disponível
            pedidoPrestador = this.pedidoPrestador.map {
                PedidoPrestadorCriacao(it.idPrestador, it.idServico)
            },
            pedidoProdutos = this.pedidoProdutos.map {
                PedidoProdutoCriacao(it.idProduto, it.quantidade)
            },
            dataAgentamento = this.dataAgendamento,
            formaPagamentoEnum = this.formaPagamentoEnum
        )
    }

    fun getEmpresa() {
        viewModelScope.launch {
            try {
                val response = usuario.idEmpresa?.let { apiHarbor.getEmpresaPorId(it) }
                if (response != null) {
                    if (response.isSuccessful) {
                        response.body().let { empresaPrestador.value = it }
                    } else {
                        Log.e("api", "Erro ao buscar empresa")
                    }
                } else {
                    Log.e("api", "Empresa nula")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }

    fun carregarPedido(pedido: Pedido) {
        _pedidoCriacao.value = pedido.toPedidoCriacao()
    }

    // Atualizadores dos atributos de cliente
    fun atualizarAtributoCliente(campo: (Cliente) -> Cliente) {
        _pedidoCriacao.value = _pedidoCriacao.value?.copy(cliente = campo(_pedidoCriacao.value?.cliente ?: Cliente("", "", "", "", "")))
    }

    fun atualizarFormaPagamento(novaForma: String) {
        _pedidoCriacao.value = _pedidoCriacao.value?.copy(formaPagamentoEnum = novaForma)
    }

    fun atualizarPedidoProduto(produtoId: Int, quantidade: Int) {
        _pedidoCriacao.value = _pedidoCriacao.value?.let { pedidoCriacao ->
            val novosPedidoProdutos = pedidoCriacao.pedidoProdutos.toMutableList().apply {
                // Remove o produto se a quantidade for zero
                removeIf { it.id == produtoId }
                // Adiciona ou atualiza o produto se a quantidade for maior que zero
                if (quantidade > 0) {
                    add(PedidoProdutoCriacao(id = produtoId, quantidade = quantidade))
                }
            }
            pedidoCriacao.copy(pedidoProdutos = novosPedidoProdutos)
        }
    }


    fun atualizarPedidoCriacao(id: Int) {
        val pedidoCriacao = _pedidoCriacao.value
        if (pedidoCriacao != null) {
            viewModelScope.launch {
                try {
                    val response = apiHarbor.atualizarPedido(pedidoCriacao, id) // Chame sua função de API para atualização
                    if (response.isSuccessful) {
                        Log.i("sucesso", "Pedido atualizado com sucesso")
                        // Aqui, você pode atualizar o estado da UI ou realizar outras ações após o sucesso
                    } else {
                        Log.e("erro", "Erro ao atualizar pedido")
                    }
                } catch (err: Exception) {
                    Log.e("api", err.toString())
                }
            }
        } else {
            Log.e("erro", "PedidoCriacao é nulo")
        }
    }

    fun carregarEstadosDeServico(pedido: Pedido?, servicos: List<Servico>) {
        pedido?.let {
            val estadoInicial = servicos.associate { servico ->
                servico.id to it.pedidoPrestador.any { prestador ->
                    prestador.idServico == servico.id
                }
            }
            _servicoSelectionState.value = estadoInicial
        }
    }

    fun carregarEstadosDePrestador(pedido: Pedido?, prestadores: List<Prestador>) {
        pedido?.let {
            // Mapeia o ID do prestador para o prestador real
            val estadoInicial = prestadores.associate { prestador ->
                // Aqui verificamos se o prestador está associado ao pedido
                val prestadorAssociado = it.pedidoPrestador
                    .firstOrNull { pedidoPrestador ->
                        pedidoPrestador.idPrestador == prestador.id
                    }

                // Associamos o prestador real no mapa
                prestador.id to if (prestadorAssociado != null) prestador else null
            }

            // Atualiza o estado com o mapa de prestadores associados
            _prestadorSelectionState.value = estadoInicial
        }
    }




    fun atualizarServicoSelecionado(servicoId: Int, isSelected: Boolean) {
        val updatedState = _servicoSelectionState.value?.toMutableMap() ?: mutableMapOf()
        updatedState[servicoId] = isSelected
        _servicoSelectionState.value = updatedState
    }

    fun atualizarPrestadorParaServico(servicoId: Int, prestadorId: Int?) {
        // Atualiza o estado do prestador para o serviço no estado compartilhado
        val prestadorStateAtualizado = _servicoPrestadorState.value?.toMutableMap() ?: mutableMapOf()
        prestadorStateAtualizado[servicoId] = prestadorId
        _servicoPrestadorState.value = prestadorStateAtualizado
    }

    fun atualizarPedidoPrestador(servicoId: Int, prestadorId: Int?) {
        // Atualiza o pedido de criação com a seleção de prestador
        _pedidoCriacao.value = _pedidoCriacao.value?.let { pedidoCriacao ->
            val novosPedidoPrestador = pedidoCriacao.pedidoPrestador.toMutableList().apply {
                // Remove qualquer pedido já existente para o serviço atual
                removeIf { it.servicoId == servicoId }
                // Se o prestadorId for fornecido, associa o prestador ao serviço
                prestadorId?.let {
                    add(PedidoPrestadorCriacao(prestadorId = it, servicoId = servicoId))
                }
            }
            // Retorna o pedido com a lista de prestadores atualizada
            pedidoCriacao.copy(pedidoPrestador = novosPedidoPrestador)
        }
    }



    fun salvarSelecoesDeServicos() {
        val selecoesAtuais = _servicoSelectionState.value ?: return
        val prestadorSelecoes = _servicoPrestadorState.value ?: return

        // Filtrar apenas os serviços selecionados
        val selecionados = selecoesAtuais.filterValues { it }.keys

        _pedidoCriacao.value = _pedidoCriacao.value?.copy(
            pedidoPrestador = selecionados.mapNotNull { servicoId ->
                val prestadorId = prestadorSelecoes[servicoId]
                if (prestadorId != null) {
                    PedidoPrestadorCriacao(prestadorId = prestadorId, servicoId = servicoId)
                } else {
                    null // Ignorar se o prestador não foi definido
                }
            }
        )
    }


}
