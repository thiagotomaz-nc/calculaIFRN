package com.thiago.tomaz.calculaifrn

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thiago.tomaz.calculaifrn.databinding.FragmentCalculadoraBimestralBinding
import com.thiago.tomaz.calculaifrn.databinding.FragmentCalculadoraSemestralBinding

class CalculadoraBimestral : Fragment() {

    private lateinit var bindingCalculadoraBimestral: FragmentCalculadoraBimestralBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingCalculadoraBimestral = FragmentCalculadoraBimestralBinding.inflate(layoutInflater, container, false)

        bindingCalculadoraBimestral.btnLimparCampos.setOnClickListener {
            limparCampos()
        }

        bindingCalculadoraBimestral.btnCalcularNotas.setOnClickListener {
            calcularNota(bindingCalculadoraBimestral.edtNotaE1.text.toString(),bindingCalculadoraBimestral.edtNotaE2.text.toString(),bindingCalculadoraBimestral.edtNotaE3.text.toString(),bindingCalculadoraBimestral.edtNotaE4.text.toString() )
        }

        return bindingCalculadoraBimestral.root
    }

    private fun limparCampos() {
        bindingCalculadoraBimestral.edtNotaE1.text.clear()
        bindingCalculadoraBimestral.edtNotaE2.text.clear()
        bindingCalculadoraBimestral.edtNotaE3.text.clear()
        bindingCalculadoraBimestral.edtNotaE4.text.clear()
        bindingCalculadoraBimestral.edtNotaE1.requestFocus()
    }

    private fun calcularNota(nota1: String, nota2: String, nota3: String, nota4: String) {
        //aqui eu verifico se a primeira nota em branco
        if (nota1.isNullOrBlank()) {
            bindingCalculadoraBimestral.edtNotaE1.error="informar a nota entre 0 e 100"
            return
        }

        if (nota1.toInt()<0 || nota1.toInt()>100){
            bindingCalculadoraBimestral.edtNotaE1.error="informar a nota entre 0 e 100"
            return
        }
        if(!nota2.isNullOrBlank()){
                if (nota2.toInt()<0 || nota2.toInt()>100){
                    bindingCalculadoraBimestral.edtNotaE2.error="informar valores entre 0 e 100"
                    return
                }
        }else{
            if (!nota3.isNullOrBlank() || !nota4.isNullOrBlank()){
                bindingCalculadoraBimestral.edtNotaE2.error="informar valor do campo nota 2º bimestre"
                return
            }
        }
        if(!nota3.isNullOrBlank()){
            if (nota3.toInt()<0 || nota3.toInt()>100){
                bindingCalculadoraBimestral.edtNotaE3.error="informar valores entre 0 e 100"
                return
            }
        }else{
            if (!nota4.isNullOrBlank()){
                bindingCalculadoraBimestral.edtNotaE3.error="informar valor do campo nota 3º bimestre"
                return
            }
        }

        if(!nota4.isNullOrBlank()){
            if (nota4.toInt()<0 || nota4.toInt()>100){
                bindingCalculadoraBimestral.edtNotaE4.error="informar valores entre 0 e 100"
                return
            }
        }

        val tela = Intent(context, BimestralExibirResultado::class.java)

        val bundle = Bundle()

        bundle.putString("nota1", nota1)
        bundle.putString("nota2", nota2.ifBlank { "-1" })
        bundle.putString("nota3", nota3.ifBlank { "-1" })
        bundle.putString("nota4", nota4.ifBlank { "-1" })

        tela.putExtras(bundle)

        startActivity(tela)
    }


}