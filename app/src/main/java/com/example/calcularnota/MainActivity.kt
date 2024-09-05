package com.example.calcularnota

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.calcularnota.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.btnCalcularNota.isEnabled = false

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val isNombreEstudianteFilled = !binding.NombreEstudiante.text.isNullOrEmpty()
                val isNombreCursoFilled = !binding.NombreCurso.text.isNullOrEmpty()
                val isNota1Filled = !binding.Nota1.text.isNullOrEmpty()
                val isNota2Filled = !binding.Nota2.text.isNullOrEmpty()

                binding.btnCalcularNota.isEnabled =
                    isNombreEstudianteFilled && isNombreCursoFilled && isNota1Filled && isNota2Filled
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.NombreEstudiante.addTextChangedListener(textWatcher)
        binding.NombreCurso.addTextChangedListener(textWatcher)
        binding.Nota1.addTextChangedListener(textWatcher)
        binding.Nota2.addTextChangedListener(textWatcher)

        binding.btnCalcularNota.setOnClickListener {
            val nota1 = binding.Nota1.text.toString().toFloat()
            val nota2 = binding.Nota2.text.toString().toFloat()

            //aunque no estaba en el taller especificado, lo puse jeje
            if (nota1 > 5.0 || nota2 > 5.0) {
                Toast.makeText(this, "Las notas no deben ser mayores a 5.0", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val notaFinal = calcularNota()
            val nombreE = binding.NombreEstudiante.text.toString()
            val nombreC = binding.NombreCurso.text.toString()

            val resultado = if (notaFinal >= 3) "Felicitaciones,Aprobado" else "Reprobado, sigue esforzandote"
            Toast.makeText(this, resultado, Toast.LENGTH_LONG).show()


            val resultadoTexto = "Nombre: %s\nCurso: %s\nNota 1: %.2f\nNota 2: %.2f\nNota Final: %.2f"
                .format(nombreE, nombreC, nota1, nota2, notaFinal)

            binding.txtResultado.text = resultadoTexto


            limpiarCampos()
        }
    }


    private fun calcularNota(): Float {
        val nota1 = binding.Nota1.text.toString().toFloat()
        val nota2 = binding.Nota2.text.toString().toFloat()
        var notaFinal = (nota1 + nota2) / 2

        if (binding.cbxRemitente.isChecked) {
            notaFinal -= 0.5f
        }

        return notaFinal
    }


    private fun limpiarCampos() {
        binding.NombreEstudiante.text.clear()
        binding.NombreCurso.text.clear()
        binding.Nota1.text.clear()
        binding.Nota2.text.clear()
        binding.cbxRemitente.isChecked = false
    }
}
