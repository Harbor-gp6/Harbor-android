package com.example.apl_mobile_harbor.modulos

import com.example.apl_mobile_harbor.view_models.login.LoginViewModel
import com.example.apl_mobile_harbor.classes.auth.AuthInterceptor
import com.example.apl_mobile_harbor.classes.auth.TokenManager
import com.example.apl_mobile_harbor.classes.auth.TokenProvider
import com.example.apl_mobile_harbor.interfaces.ApiHarbor
import com.example.apl_mobile_harbor.view_models.pedidos.PedidosViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // TokenProvider como um singleton
    single { TokenProvider }

    // TokenManager que depende do TokenProvider
    single { TokenManager(get()) }

    // Interceptor de autenticação que depende do TokenManager
    single { AuthInterceptor(get()) }

    // OkHttpClient com o AuthInterceptor
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .build()
    }

    // Configuração do Retrofit com o baseUrl e GsonConverterFactory
    single {
        Retrofit.Builder()
            .baseUrl("http://192.168.0.108:8080/") // Altere para a URL da sua API
            .client(get<OkHttpClient>()) // Adiciona o OkHttpClient configurado
            .addConverterFactory(GsonConverterFactory.create()) // Converter JSON
            .build()
    }

    // Cria a interface ApiHarbor a partir do Retrofit
    single { get<Retrofit>().create(ApiHarbor::class.java) }

    // ViewModel LoginViewModel com injeção de ApiHarbor e TokenManager
    viewModel { LoginViewModel(get(), get()) }

    viewModel { PedidosViewModel(get(), get()) }
}
