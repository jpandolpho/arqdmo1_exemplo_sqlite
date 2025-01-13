package br.edu.ifsp.dmo1.exemplosqlite.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.dmo1.exemplosqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MeuDadoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonAdd.setOnClickListener {
            viewModel.addDado("Novo texto")
        }
    }

    private fun setupObservers() {
        viewModel.dados.observe(this, Observer {
            adapter.updateDados(it)
        })
    }

    private fun setupRecyclerView() {
        adapter = MeuDadoAdapter(mutableListOf())
        binding.listDados.adapter = adapter
        binding.listDados.layoutManager = LinearLayoutManager(this)
    }


}