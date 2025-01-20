package br.edu.ifsp.dmo1.exemplosqlite.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.dmo1.exemplosqlite.databinding.ActivityMainBinding
import br.edu.ifsp.dmo1.exemplosqlite.ui.details.DetailsActivity

class MainActivity : AppCompatActivity(), ItemListDadoClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MeuDadoAdapter
    private lateinit var addResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var updateResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupRecyclerView()
        setupObservers()
        setupListeners()
        setupLauncher()
        viewModel.load()
    }

    override fun clickEditItemList(id: Int, texto: String) {
        val mIntent = Intent(this, DetailsActivity::class.java)
        mIntent.putExtra("id", id)
        mIntent.putExtra("texto", texto)
        updateResultLauncher.launch(mIntent)
    }

    private fun setupLauncher() {
        addResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if (it.resultCode == RESULT_OK) {
                    val texto = it.data?.getStringExtra("texto") ?: ""
                    viewModel.addDado(texto)
                }
            }
        )

        updateResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if (it.resultCode == RESULT_OK) {
                    val texto = it.data?.getStringExtra("texto") ?: ""
                    val id = it.data?.getIntExtra("id", -1) ?: -1
                    viewModel.updateDado(id, texto)
                }
            }
        )
    }

    private fun setupListeners() {
        binding.buttonAdd.setOnClickListener {
            val mIntent = Intent(this, DetailsActivity::class.java)
            addResultLauncher.launch(mIntent)
        }
    }

    private fun setupObservers() {
        viewModel.dados.observe(this, Observer {
            adapter.updateDados(it)
        })
    }

    private fun setupRecyclerView() {
        adapter = MeuDadoAdapter(mutableListOf(), this)
        binding.listDados.adapter = adapter
        binding.listDados.layoutManager = LinearLayoutManager(this)
    }


}