package com.example.apl_mobile_harbor.modulos

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.apl_mobile_harbor.classes.auth.AuthInterceptor
import com.example.apl_mobile_harbor.classes.auth.TokenManager
import com.example.apl_mobile_harbor.classes.auth.Usuario
import com.example.apl_mobile_harbor.interfaces.ApiHarbor
import com.example.apl_mobile_harbor.view_models.atividade.AtividadeViewModel
import com.example.apl_mobile_harbor.view_models.avaliacao.AvaliacaoViewModel
import com.example.apl_mobile_harbor.view_models.login.LoginViewModel
import com.example.apl_mobile_harbor.view_models.pedidos.PedidosViewModel
import com.example.apl_mobile_harbor.view_models.prestador.PrestadorViewModel
import com.example.apl_mobile_harbor.view_models.produto.ProdutoViewModel
import com.example.apl_mobile_harbor.view_models.servico.ServicoViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val Context.dataStore by preferencesDataStore(name = "user_preferences")

val appModule = module {

    single<DataStore<Preferences>> { androidContext().dataStore }

    // TokenManager que depende do TokenProvider
    single { TokenManager(get(), get()) }

    // Interceptor de autenticação que depende do TokenManager
    single { AuthInterceptor(get()) }

    single<Usuario> { Usuario() }

    // OkHttpClient com o AuthInterceptor
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .build()
    }

    // Configuração do Retrofit com o baseUrl e GsonConverterFactory
    single {
        Retrofit.Builder()
            .baseUrl("http://192.168.0.107:8080/api/") // Altere para a URL da sua API
            .client(get<OkHttpClient>()) // Adiciona o OkHttpClient configurado
            .addConverterFactory(GsonConverterFactory.create()) // Converter JSON
            .build()
    }

    // Cria a interface ApiHarbor a partir do Retrofit
    single { get<Retrofit>().create(ApiHarbor::class.java) }

    // ViewModel LoginViewModel com injeção de ApiHarbor e TokenManager

    viewModelOf(::AvaliacaoViewModel)

    viewModelOf(::AtividadeViewModel)

    viewModelOf(::ProdutoViewModel)

    viewModelOf(::ServicoViewModel)

    viewModelOf(::LoginViewModel)

    viewModelOf(::PedidosViewModel)

    viewModelOf(::PrestadorViewModel)
}
