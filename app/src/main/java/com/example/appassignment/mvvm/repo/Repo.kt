package com.example.appassignment.mvvm.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appassignment.dto.TodosList
import com.example.appassignment.services.ApiInterface
import com.example.appassignment.services.RetrofitClient
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException

class Repo() {

    private val getTodos = MutableLiveData<TodosList>()

     val getTodosLiveData : LiveData<TodosList>
    get() = getTodos

    suspend fun getTodos(){
        val response = try {
            RetrofitClient.getApiServices().getTodos()
        }catch (e: IOException) {
            Log.e("errorE", "IOException, you might not have internet connections")
            return

        } catch (e: HttpException) {
            Log.e("errorHttp", "HttpException, unexpected response")
            return
        }

        if (response.isSuccessful && response.body() != null){
            getTodos.postValue(response.body())
            Log.d("TAG", "getGalleryItems: ${response.body()}")
        }
    }

}