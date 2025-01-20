package br.edu.ifsp.dmo1.exemplosqlite.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo1.exemplosqlite.R
import br.edu.ifsp.dmo1.exemplosqlite.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        verifyBundle()

        binding.buttonSave.setOnClickListener(this)
    }



    override fun onClick(v: View) {
        if (v.id == binding.buttonSave.id) {
            val string = binding.textTexto.text.toString()
            val numero = binding.textNumero.text.toString().toInt()
            if (string.isNotEmpty()) {
                val mIntent = Intent()
                mIntent.putExtra("texto", string)
                mIntent.putExtra("numero", numero)

                if (binding.buttonSave.text.toString().equals("Atualizar")){
                    val id = binding.textId.text.toString().toInt()
                    mIntent.putExtra("id", id)
                }

                setResult(RESULT_OK, mIntent)
            } else {
                Toast.makeText(this, "Não foi possível salvar os dados.", Toast.LENGTH_SHORT).show()
                setResult(RESULT_CANCELED)
            }
            finish()
        }
    }

    private fun verifyBundle() {
        if (intent.extras != null) {
            val id = intent.getIntExtra("id", -1)
            val texto = intent.getStringExtra("texto")
            val numero = intent.getIntExtra("numero", -2)

            binding.buttonSave.setText("Atualizar")
            binding.textlayoutId.visibility = VISIBLE
            binding.textId.setText(id.toString())
            binding.textTexto.setText(texto)
            binding.textNumero.setText(numero.toString())
        }
    }
}