package com.example.appassignment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appassignment.databinding.ActivityMainBinding
import com.example.appassignment.dto.Todo
import com.example.appassignment.dto.TodosList
import com.example.appassignment.mvvm.repo.Repo
import com.example.appassignment.mvvm.vm.MyViewModel
import com.example.appassignment.mvvm.vm.MyViewModelFactory
import com.example.appassignment.rv.Adapter


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var vm : MyViewModel
    lateinit var repo : Repo
    lateinit var adapter: Adapter
    var filteredList = emptyList<Todo>()
    lateinit var result: TodosList
    var isFisrtTime:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!isMobileDataEnabled()) {

            Toast.makeText(this, "Mobile data is off. Please turn it on. And Restart App", Toast.LENGTH_SHORT).show()
        }


        getTodos()

        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                if(isFisrtTime){
                    return
                }
                filter(s.toString())

            }
        })
        val spinnerAdp = ArrayAdapter.createFromResource(this, R.array.filter_options, android.R.layout.simple_spinner_item)
        spinnerAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinnerAdp


        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {

                val selectedItem = parent?.getItemAtPosition(position).toString()
                when (selectedItem) {
                    "All" -> {
                        if(isFisrtTime){
                            return
                        }
                        filteredList = emptyList()
                        filteredList = result.todos
                        Toast.makeText(this@MainActivity, "Show All", Toast.LENGTH_SHORT).show()
                        adapter.updateList(filteredList)
                    }
                    "Completed" -> {
                        filteredList = emptyList()
                        filteredList = result.todos.filter {
                            it.completed
                        }
                        adapter.updateList(filteredList)
                    }
                    "Uncompleted" -> {
                        filteredList = emptyList()
                        filteredList = result.todos.filter {
                            it.completed.not()
                        }
                        adapter.updateList(filteredList)
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun getTodos() {
        adapter = Adapter(this, ArrayList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        repo = Repo()
        vm = ViewModelProvider(this,MyViewModelFactory(repo))[MyViewModel::class.java]
        vm.getTodos()
        vm.getTodosLiveData.observe(this){
            binding.progressBar.visibility = View.GONE
            isFisrtTime = false
            filteredList = it.todos
            result = it
            adapter.updateList(it.todos)
        }
    }

    private fun isMobileDataEnabled(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.isConnected ?: false
    }

    fun filter(text: String?) {
        val temp: MutableList<Todo> = ArrayList<Todo>()
        for (d in filteredList) {

            if (d.todo.toLowerCase().contains(text.toString().toLowerCase())) {
                temp.add(d)
            }
        }

        adapter.updateList(temp.toList())
    }
}