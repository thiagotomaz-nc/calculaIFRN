package com.thiago.tomaz.calculaifrn

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.thiago.tomaz.calculaifrn.databinding.ActivityExibirResultadoSemestralBinding
import kotlin.math.roundToInt

class SemestralExibirResultado : AppCompatActivity() {

    private lateinit var bidding: ActivityExibirResultadoSemestralBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bidding = ActivityExibirResultadoSemestralBinding.inflate(layoutInflater)
        setContentView(bidding.root)

        bidding.btnVoltar.setOnClickListener {
            fecharTela()
        }
        bidding.imgBack.setOnClickListener {
            fecharTela()
        }

        val notas = intent
        var nota1 = 0;
        var nota2 = 0;

        //Boa pratica para se saber se a activity não recebeu nenhum valor
        if (notas == null) {
            Snackbar.make(
                findViewById<View>(android.R.id.content), "Preencha o campo da nota 1",
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(Color.RED).setTextColor(Color.WHITE).show()
            fecharTela()
        }

        //preenchimento das informaçções
        nota1 = notas.getStringExtra("nota1")?.toInt() ?: 0
        nota2 = notas.getStringExtra("nota2")?.toInt() ?: -1

        val media = CalcularMediaNotas(nota1, if (nota2 ==-1) 0 else nota2 )

        statusAluno(
            media,
            nota1,
            nota2,
            bidding.txtMediaScreenResult,
            bidding.txtNotaEtapa01,
            bidding.txtNotaEtapa02,
            bidding.txtStatusAluno,
            bidding.txtSituacaoAluno,
            bidding.imgStatusConteiner,
            bidding.imgStatusConteinerIcon,
            bidding.situacaoAluno,
            bidding.txtSituacaoAluno,
            bidding.imgStatusConteiner2,
            bidding.imgStatusConteinerIcon2,

        )
    }

    private fun statusAluno(
        mediaNotas: Int,
        nota1: Int,
        nota2: Int,
        txtMediaScreenView: TextView,
        txtNotaEtapa1View: TextView,
        txtNotaEtapa2View: TextView,
        txtStatusView: TextView,
        situacaoALunoView: TextView,
        imgStatusConteiner: ImageView,
        imgStatusConteinerIcon: ImageView,
        situacaoAlunoLinearLayout: ConstraintLayout,
        tituloSituacaoProvaAluno: TextView,
        imgStatusConteiner2: ImageView,
        imgStatusConteinerIcon2: ImageView
    ) {

        txtMediaScreenView.text =  mediaNotas.toString()
        txtNotaEtapa1View.text = "$nota1 pontos"

        var situacaoDoAluno = 0
        var bordaViewMediaNota = getDrawable(R.drawable.circulo_borda_pendente)
        var colorCardViewSituacaoAluno = getColor(R.color.background_light)
        var colorStatusTexto = getColor(R.color.amarelo_queimado)


        bidding.progressBarNotaetapa1.max = 100
        bidding.progressBarNotaEtapa2.max = 100

        var status = ""
        var etapa2 = ""
        var tituloSituacaoAluno = ""


        if (nota2 == -1) {

            etapa2 = "Nota não informada"
            330
            bidding.textView15.text = "NOTA NECESSÁRIA PARA A 2ª ETAPA"
           bidding.textView15.setTextColor(getColor(R.color.red_900))
            bidding.notaNecessaria.setTextColor(getColor(R.color.red_900))

            situacaoDoAluno = calcularNotaSegundaEtapa(nota1)
            //tituloSituacaoAluno = "${situacaoDoAluno} pontos"

            bidding.progressBarNotaetapa1.progress = nota1


            bordaViewMediaNota = getDrawable(R.drawable.circulo_borda_pendente)
            colorCardViewSituacaoAluno = getColor(R.color.amarelo_queimado)
            status = "Pendente"
            tituloSituacaoAluno = "Nota da 2ª etapa"

        } else {
            //calcular APROVADO/REPROVADO/PROVA FINAL
            //If prova final - CALCULAR QUANTO DEVE TIRAR NA PROVA FINAL
            etapa2 = "$nota2 pontos"

            bidding.progressBarNotaetapa1.progress = nota1
            bidding.progressBarNotaEtapa2.progress = nota2

            if (mediaNotas >= 60) {


                tituloSituacaoAluno = "Parabéns! Continue Assim."
                colorStatusTexto = getColor(R.color.green_900)
                bordaViewMediaNota = getDrawable(R.drawable.circulo_borda_aprovado_gradiente_verde)
                imgStatusConteiner.setImageDrawable(getDrawable(R.drawable.circulo_aprovado_solid_verde))
                imgStatusConteinerIcon.setImageDrawable(getDrawable(R.drawable.ic_status_aprovado_check_24))
                imgStatusConteiner2.setImageDrawable(getDrawable(R.drawable.circulo_aprovado_solid_verde))
                imgStatusConteinerIcon2.setImageDrawable(getDrawable(R.drawable.ic_status_aprovado_check_24))
                status = "APROVADO(A)"

                bidding.cardViewStatus.visibility = View.GONE

            } else if (mediaNotas >= 20 && mediaNotas < 60) {

                colorStatusTexto = getColor(R.color.blue_900)
                colorCardViewSituacaoAluno = getColor(R.color.prova_final_texto)
               // tituloSituacaoProvaAluno.setTextColor(getColor(R.color.prova_final_background))
                bordaViewMediaNota = getDrawable(R.drawable.circulo_centro_falso_grafico_borda)
                imgStatusConteiner.setImageDrawable(getDrawable(R.drawable.circulo_centro_falso_grafico_borda))
                imgStatusConteinerIcon.setImageDrawable(getDrawable(R.drawable.ic_status_prova_final_refresh_24))
                imgStatusConteiner2.setImageDrawable(getDrawable(R.drawable.circulo_centro_falso_grafico_borda))
                imgStatusConteinerIcon2.setImageDrawable(getDrawable(R.drawable.ic_status_prova_final_refresh_24))

                situacaoDoAluno =
                    calcularNotaProvaFinal(
                        nota1,
                        nota2,
                        mediaNotas
                    )

                status = "PROVA FINAL"
                tituloSituacaoAluno = "Você esta em Prova final. Boa sorte!"


            } else {

                status = "REPROVADO(A)"
                colorStatusTexto = getColor(R.color.red_900)
                imgStatusConteiner.setImageDrawable(getDrawable(R.drawable.circulo_reprovado))
                imgStatusConteinerIcon.setImageDrawable(getDrawable(R.drawable.ic_status_reprovado_24))
                imgStatusConteiner2.setImageDrawable(getDrawable(R.drawable.circulo_reprovado))
                imgStatusConteinerIcon2.setImageDrawable(getDrawable(R.drawable.ic_status_reprovado_24))
                bordaViewMediaNota = getDrawable(R.drawable.circulo_centro_falso_grafico_borda_reprovado)
                tituloSituacaoAluno = "Não foi desa vez. Continue estundando e não desista!"

            }
        }

        txtNotaEtapa2View.text = etapa2
        txtStatusView.text = status
        bidding.constraintLayoutBordaMedia.background = bordaViewMediaNota
        bidding.txtMediaScreenResult.setTextColor( colorStatusTexto)
        //cardViewSituacaoAluno.setCardBackgroundColor(colorCardViewSituacaoAluno)
       // situacaoALunoView.text = situacaoDoAluno
        tituloSituacaoProvaAluno.text = tituloSituacaoAluno
        bidding.notaNecessaria.text = "${situacaoDoAluno}"
        txtStatusView.setTextColor(colorStatusTexto)



    }


    private fun calcularNotaProvaFinal(nota1: Int, nota2: Int, mediaNotas: Int): Int {

        //aprovado após a avaliação final, o estudante que obtiver média final igual ou superior a 60 pontos
        var notaProvafinal = 0

        var nf1 = 0
        var nf2 = 0
        var nf3 = 0

        nf1 = ((2.0 * 60) - mediaNotas).roundToInt()
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
        val media = ((nota1 * 2) + (nota2 * 3)) / 5
        return  media
    }

   private fun fecharTela() {
        finish()
   }
}