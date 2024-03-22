package com.example.appassignment.dto

data class TodosList(
    val limit: Int,
    val skip: Int,
    val todos: List<Todo>,
    val total: Int
)