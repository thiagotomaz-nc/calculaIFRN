package com.thiago.tomaz.calculaifrn

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.thiago.tomaz.calculaifrn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var biddingMainActivity: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biddingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(biddingMainActivity.root)

        biddingMainActivity.btnCalcularNotas.setOnClickListener {
            calcularNota(
                biddingMainActivity.edtNotaE1.text.toString(),
                biddingMainActivity.edtNotaE2.text.toString()
            )
        }

        biddingMainActivity.btnLimparCampos.setOnClickListener {
            limparCampos()
        }
    }

    private fun limparCampos() {
        biddingMainActivity.edtNotaE1.text.clear()
        biddingMainActivity.edtNotaE2.text.clear()
    }

    private fun calcularNota(nota1: String, nota2: String) {
        //aqui eu verifico se a primeira nota em branco
        if (nota1.isNullOrBlank()) {
            biddingMainActivity.edtNotaE1.error="informar a nota entre 0 e 100"
            return
        }

        if (nota1.toInt()<0 || nota1.toInt()>100){
            biddingMainActivity.edtNotaE1.error="informar a nota entre 0 e 100"
            return
        }
        if(!nota2.isNullOrBlank()){
            if (nota2.toInt()<0 || nota2.toInt()>100){
                biddingMainActivity.edtNotaE2.error="informar valores entre 0 e 100"
                return
            }
        }
        val tela = Intent(this, ExibirResultado::class.java)

        val bundle = Bundle()

        bundle.putString("nota1", nota1)
        bundle.putString("nota2", nota2?.ifBlank { "0" } ?: "0")

        tela.putExtras(bundle)

        startActivity(tela)


    }


}