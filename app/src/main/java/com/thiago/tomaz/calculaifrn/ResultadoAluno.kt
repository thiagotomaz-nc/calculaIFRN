package com.thiago.tomaz.calculaifrn

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toDrawable
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.thiago.tomaz.calculaifrn.databinding.ActivityResultadoAlunoBinding
import kotlin.math.roundToInt

class ResultadoAluno : AppCompatActivity() {
    private lateinit var bidding: ActivityResultadoAlunoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bidding = ActivityResultadoAlunoBinding.inflate(layoutInflater)
        setContentView(bidding.root)

        val notas = intent
        var nota1 = 0;
        var nota2 = 0;

        //Boa pratica para se saber se a activity não recebeu nenhum valor
        if (notas == null) {
            Snackbar.make(
                findViewById<View>(android.R.id.content), "Preencha o campo da nota 1",
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(Color.RED).setTextColor(Color.WHITE).show()
            closeWindows()
        }

        //preenchimento das informaçções
        nota1 = notas.getStringExtra("nota1")?.toInt() ?: 0
        nota2 = notas.getStringExtra("nota2")?.toInt() ?: 0

        val media = CalcularMediaNotas(nota1, nota2)

        statusAluno(
            media,
            nota1,
            nota2,
            bidding.txtMediaScreen,
            bidding.txtNotaEtapa1,
            bidding.txtNotaEtapa2,
            bidding.txtStatus,
            bidding.txtProvaFinal
        )



        bidding.button.setOnClickListener {
            closeWindows()
        }

    }

    private fun statusAluno(
        mediaNotas: Int,
        nota1: Int,
        nota2: Int,
        txtMediaScreen: TextView,
        txtNotaEtapa1: TextView,
        txtNotaEtapa2: TextView,
        txtStatus: TextView,
        txtProvaFinal: TextView
    ) {

        txtMediaScreen.text = mediaNotas.toString()
        txtNotaEtapa1.text = "1ª Etapa: nota  = $nota1 pontos"

        var backgorund = getColor(R.color.background_light)
        var cardViewBackgroundColor = getColor(R.color.background_cinza)
        var textColor = getColor(R.color.black)


        //nesse caso ainda não existe reprovação, pode ser tratada posteriormente;
        var status = ""
        var etapa2 = ""

        if (nota2 == 0) {
            etapa2 =
                "2ª Etapa: nota necessária = ${calcularNotaSegundaEtapa(nota1)} pontos"
            status = "status: Pendente"

        } else {
            //calcular APROVADO/REPROVADO/PROVA FINAL
            //If prova final - CALCULAR QUANTO DEVE TIRAR NA PROVA FINAL
            etapa2 = "2ª Etapa: nota  = $nota2 pontos"

            if (mediaNotas >= 60) {
                backgorund = getColor(R.color.aprovado_background)
                cardViewBackgroundColor = getColor(R.color.primary_light)
                textColor = getColor(R.color.primary_dark)
                status = "Aprovado"
            } else if (mediaNotas >= 20 && mediaNotas < 60) {

                backgorund = getColor(R.color.prova_final_background)
                cardViewBackgroundColor = getColor(R.color.prova_final_cardview)
                textColor = getColor(R.color.prova_final_texto)

                txtProvaFinal.visibility = View.VISIBLE
                txtProvaFinal.text = "Prova Final: nota necessaria = ${
                    calcularNotaProvaFinal(
                        nota1,
                        nota2,
                        mediaNotas
                    )
                } pontos"
                status = "Prova Final"
            } else {

                status = "Reprovado"
                backgorund = getColor(R.color.reprovado_background)
                cardViewBackgroundColor = getColor(R.color.reprovado_cardview)
                textColor = getColor(R.color.reprovado_texto)


            }
        }

        txtNotaEtapa2.text = etapa2
        txtStatus.text = status

        bidding.scrollBackground.setBackgroundColor(backgorund)
        bidding.cardView.radius = 50f
        bidding.cardView.setCardBackgroundColor(cardViewBackgroundColor)

        bidding.button.setBackgroundColor(cardViewBackgroundColor)
        bidding.button.setTextColor(textColor)
        txtStatus.setTextColor(textColor)
        txtNotaEtapa1.setTextColor(textColor)
        txtNotaEtapa2.setTextColor(textColor)
        txtMediaScreen.setTextColor(textColor)
        bidding.textView.setTextColor(textColor)
        bidding.materialToolbar2.setBackgroundColor(textColor)

    }


    private fun calcularNotaProvaFinal(nota1: Int, nota2: Int, mediaNotas: Int): Int {

        //aprovado após a avaliação final, o estudante que obtiver média final igual ou superior a 60 pontos
        var notaProvafinal = 0

        var nf1 = 0
        var nf2 = 0
        var nf3 = 0

        nf1 = (2.0 * 60 - mediaNotas).roundToInt()
        nf2 = calcularNotaProvaFinalEtapa1(nota2)
        nf3 = calcularNotaSegundaEtapa(nota1)
        notaProvafinal = nf1

        if (nf2 < notaProvafinal) {
            notaProvafinal = nf2
        }
        if (nf3 < notaProvafinal) {
            notaProvafinal = nf3
        }

        return notaProvafinal

    }

    private fun calcularNotaProvaFinalEtapa1(nota2: Int): Int {
        return ((300 - (3 * nota2)) / 2.0).roundToInt()
    }

    private fun calcularNotaSegundaEtapa(nota1: Int): Int {

        val notaNecessaria2 = (300 - (nota1 * 2)) / 3.0
        return notaNecessaria2.roundToInt()
    }

    private fun CalcularMediaNotas(nota1: Int, nota2: Int): Int {
        val media = ((nota1 * 2) + (nota2 * 3)) / 5.0
        return media.roundToInt()
    }

    fun closeWindows() {
        finish()
    }
}