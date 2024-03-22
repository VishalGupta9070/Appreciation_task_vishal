package com.example.appassignment.mvvm.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appassignment.mvvm.repo.Repo

class MyViewModelFactory( val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyViewModel(repo) as T
    }
        }
