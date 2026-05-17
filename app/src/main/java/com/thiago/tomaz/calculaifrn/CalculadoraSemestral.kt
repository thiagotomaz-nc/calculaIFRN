package com.thiago.tomaz.calculaifrn

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thiago.tomaz.calculaifrn.databinding.FragmentCalculadoraSemestralBinding


class CalculadoraSemestral : Fragment() {

    private lateinit var bindingCalculadoraSemestral: FragmentCalculadoraSemestralBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingCalculadoraSemestral = FragmentCalculadoraSemestralBinding.inflate(layoutInflater, container, false)

        bindingCalculadoraSemestral.btnLimparCampos.setOnClickListener {
            limparCampos()
        }

        bindingCalculadoraSemestral.btnCalcularNotas.setOnClickListener {
            calcularNota(bindingCalculadoraSemestral.edtNotaE1.text.toString(),bindingCalculadoraSemestral.edtNotaE2.text.toString() )
        }

        return bindingCalculadoraSemestral.root
    }

    private fun limparCampos() {
        bindingCalculadoraSemestral.edtNotaE1.text.clear()
        bindingCalculadoraSemestral.edtNotaE2.text.clear()
        bindingCalculadoraSemestral.edtNotaE1.requestFocus()

    }

    private fun calcularNota(nota1: String, nota2: String) {
        //aqui eu verifico se a primeira nota em branco
        if (nota1.isNullOrBlank()) {
            bindingCalculadoraSemestral.edtNotaE1.error="informar a nota entre 0 e 100"
            return
        }

        if (nota1.toInt()<0 || nota1.toInt()>100){
            bindingCalculadoraSemestral.edtNotaE1.error="informar a nota entre 0 e 100"
            return
        }
        if(!nota2.isNullOrBlank()){
            if (nota2.toInt()<0 || nota2.toInt()>100){
                bindingCalculadoraSemestral.edtNotaE2.error="informar valores entre 0 e 100"
                return
            }
        }

        val tela = Intent(context, SemestralExibirResultado::class.java)

        val bundle = Bundle()

        bundle.putString("nota1", nota1)
        bundle.putString("nota2", nota2.ifBlank { "-1" })

        tela.putExtras(bundle)

        startActivity(tela)


    }

}