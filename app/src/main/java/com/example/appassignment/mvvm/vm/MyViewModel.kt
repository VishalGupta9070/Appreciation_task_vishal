package com.example.appassignment.mvvm.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appassignment.dto.TodosList
import com.example.appassignment.mvvm.repo.Repo
import kotlinx.coroutines.launch

class MyViewModel(val repo: Repo) : ViewModel(){

    val getTodosLiveData : LiveData<TodosList> = repo.getTodosLiveData

    fun getTodos(){
        viewModelScope.launch {
            repo.getTodos()
        }
    }

}