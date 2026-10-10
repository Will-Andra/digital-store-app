package com.example.digitalstore.core.domain

sealed class AppError: Exception(){
    object NetworkError : AppError()
    object Unauthorized : AppError()
    object Forbidden : AppError()
    object NotFound : AppError()
    object TimeoutError: AppError()
    data class ServerError(val code: Int) : AppError()
    data class Unknown(val exception: Throwable) : AppError()

    fun toMessageError(): String{
        return when(this){
            is NetworkError -> "Sem conexão com a internet"
            is Unauthorized -> "Acesso não autorizado"
            is Forbidden -> "Você não tem permissão para acessar este recurso"
            is NotFound -> "Recurso não encontrado"
            is TimeoutError -> "Tempo de conexão excedido. Verifique sua internet"
            is ServerError -> "Erro interno no servidor (Código: $code). Tente novamente"
            is Unknown -> "Ocorreu um erro desconhecido"
        }
    }
}