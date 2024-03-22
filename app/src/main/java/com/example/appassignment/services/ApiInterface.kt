package com.example.appassignment.services

import com.example.appassignment.dto.TodosList
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("todos")
   suspend fun getTodos(): Response<TodosList>
}