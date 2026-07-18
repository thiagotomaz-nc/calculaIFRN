package com.thiago.tomaz.calculaifrn

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.thiago.tomaz.calculaifrn.databinding.ActivityExibirResultadoBimestralBinding
import kotlin.math.ceil
import kotlin.math.roundToInt

class BimestralExibirResultado : AppCompatActivity() {

    private lateinit var bindingBimestralExibirResultado: ActivityExibirResultadoBimestralBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingBimestralExibirResultado = ActivityExibirResultadoBimestralBinding.inflate(layoutInflater)
        setContentView(bindingBimestralExibirResultado.root)

        val notas = intent

        var nota1 = 0;
        var nota2 = 0;
        var nota3 = 0;
        var nota4 = 0;

        //Boa pratica para se saber se a activity não recebeu nenhum valor
        if (notas == null) {
            Snackbar.make(
                findViewById<View>(android.R.id.content), "Preencha, pelo menos, o campo da nota 1",
                Snackbar.LENGTH_SHORT
            ).setBackgroundTint(Color.RED).setTextColor(Color.WHITE).show()
            fecharTela()
        }

        //preenchimento das informaçções
        nota1 = notas.getStringExtra("nota1")?.toInt() ?: 0
        nota2 = notas.getStringExtra("nota2")?.toInt() ?: -1
        nota3 = notas.getStringExtra("nota3")?.toInt() ?: -1
        nota4 = notas.getStringExtra("nota4")?.toInt() ?: -1

        val media = CalcularMediaNotas(nota1, if (nota2==-1) 0 else nota2, if (nota3==-1) 0 else nota3, if (nota4==-1) 0 else nota4)

        statusAluno(
            media,
            nota1,
            nota2,
            nota3,
            nota4
        )

        bindingBimestralExibirResultado.btnVoltar.setOnClickListener {
            fecharTela()
        }
        bindingBimestralExibirResultado.imgBack.setOnClickListener {
            fecharTela()
        }
    }

    private fun statusAluno(media: Int, nota1: Int, nota2: Int, nota3: Int, nota4: Int) {

        bindingBimestralExibirResultado.txtMediaScreenResult.text =  media.toString()
        bindingBimestralExibirResultado.txtNotaEtapa01.text = "Nota  = $nota1 pontos"
        bindingBimestralExibirResultado.progressBarNotaetapa1.progress = nota1



        var bordaViewMediaNota = getDrawable(R.drawable.circulo_borda_pendente)
        var colorCardViewSituacaoAluno = getColor(R.color.background_light)
        var colorStatusTexto = getColor(R.color.amarelo_queimado)
        var colorStatusTextoSituacaoAluno = getColor(R.color.amarelo_queimado)


        var corTextoNotasSegundo = getColor(R.color.black)
        var corTextoNotasTerceiro = getColor(R.color.black)
        var corTextoNotasQuarto = getColor(R.color.black)

        var status = ""
        var segundoBimestre = ""
        var terceiroBimestre = ""
        var quartoBimestre = ""
        var tituloSituacaoAluno = ""



        //NOTAS PENDENTES PARA SEREM PREENCHIDAS
        if (nota2 == -1) {

            val primeiraNota = nota1*2
            var segundaNota = 240 - primeiraNota

            segundaNota = if ((segundaNota/2) > 100) 200 else segundaNota

            var segundoGrupo = 600 - (segundaNota + primeiraNota)
            var terceiraNota = ceil((segundoGrupo/2)/3.0).toInt()

            val quartaNotaTemp = 600 - ((terceiraNota*3) + segundaNota + primeiraNota)

            var quartaNota =  ceil(quartaNotaTemp/3.0).toInt()

            //tratar a partir da nota2
            tituloSituacaoAluno = "Notas necessárias para ser aprovado"
            bordaViewMediaNota = getDrawable(R.drawable.circulo_borda_pendente)
            colorCardViewSituacaoAluno = getColor(R.color.amarelo_queimado)
            status = "Pendente"

            segundoBimestre = "Nota necessária = ${segundaNota/2} pontos"
            terceiroBimestre = "Nota necessária = ${terceiraNota} pontos"

            quartoBimestre = "Nota necessária = ${quartaNota} pontos"

            corTextoNotasSegundo = getColor(R.color.red_900)
            corTextoNotasTerceiro = getColor(R.color.red_900)
            corTextoNotasQuarto = getColor(R.color.red_900)

        }else if (nota3 == -1){

            val notaPrimeiroGrupo = (nota1*2) + (nota2*2)

            var segundoGrupo = 600 - notaPrimeiroGrupo

            var terceiraNota = ceil((segundoGrupo/2)/3.0).toInt()

            val quartaNotaTemp = 600 - ((terceiraNota*3) + notaPrimeiroGrupo)

            var quartaNota =  ceil(quartaNotaTemp/3.0).toInt()

            //tratar a partir da nota2
            tituloSituacaoAluno = "Notas necessárias para ser aprovado"
            bordaViewMediaNota = getDrawable(R.drawable.circulo_borda_pendente)
            colorCardViewSituacaoAluno = getColor(R.color.amarelo_queimado)
            status = "Pendente"

            segundoBimestre = "Nota  = $nota2 pontos"
            terceiroBimestre = "Nota necessária = ${terceiraNota} pontos"
            quartoBimestre = "Nota necessária = ${quartaNota} pontos"

            corTextoNotasTerceiro = getColor(R.color.red_900)
            corTextoNotasQuarto = getColor(R.color.red_900)
            bindingBimestralExibirResultado.progressBarNotaEtapa2.progress = nota2

        }else if (nota4 == -1){
            //tratar apenas a nota4


            corTextoNotasQuarto = getColor(R.color.red_900)
            tituloSituacaoAluno = "Notas necessárias para ser aprovado"
            status = "Pendente"
            //a partor daqui os calculos

            var segundoGrupo = 600 - ((nota1*2) + (nota2*2) + (nota3*3))

            var quartaNota =  if ( segundoGrupo > 0) ceil(segundoGrupo/3.0).toInt() else 0

            //fim dos calculos

            if ((segundoGrupo / 3) > 100){

                corTextoNotasQuarto = getColor(R.color.white)
                var situacaoDoAluno = "Nota mínima = ${
                    calcularNotaProvaFinal(
                        nota1,
                        nota2,
                        nota3,
                        nota4,
                        media
                    )
                } pontos"

                tituloSituacaoAluno = "Você esta em Prova final. Boa sorte!\n\n${situacaoDoAluno}"
                status = "Aluno em prova Final"
                colorStatusTextoSituacaoAluno = getColor(R.color.blue_900)

                bindingBimestralExibirResultado.imgStatusConteiner2.setImageDrawable(getDrawable(R.drawable.circulo_centro_falso_grafico_borda))
                bindingBimestralExibirResultado.imgStatusConteinerIcon2.setImageDrawable(getDrawable(R.drawable.ic_status_prova_final_refresh_24))

            }

            //tratar a partir da nota2
            bordaViewMediaNota = getDrawable(R.drawable.circulo_borda_pendente)
            colorCardViewSituacaoAluno = getColor(R.color.amarelo_queimado)

            segundoBimestre = "Nota = $nota2 pontos"
            terceiroBimestre = "Nota = ${nota3} pontos"

            bindingBimestralExibirResultado.progressBarNotaEtapa2.progress = nota2
            bindingBimestralExibirResultado.progressBarNotaEtapa3.progress = nota3

            //aqui verificar se a nota do semestre ultrapassa os 100, caso ultrapasse aluno em prova final.
            quartoBimestre = "Nota necessária = ${quartaNota} pontos"

        }else{
         //notas preenchidas para calcular se o aluno foi aprovado, reprovado ou prova final e informar as notas necessárias para a aprovação dele
            segundoBimestre = "Nota = $nota2 pontos"
            terceiroBimestre = "Nota = ${nota3} pontos"
            quartoBimestre = "Nota = ${nota4} pontos"

            bindingBimestralExibirResultado.progressBarNotaEtapa2.progress=nota2
            bindingBimestralExibirResultado.progressBarNotaEtapa3.progress=nota3
            bindingBimestralExibirResultado.progressBarNotaEtapa4.progress=nota4

            if (media >= 60) {
                tituloSituacaoAluno = "Parabéns! Continue Assim."
                colorStatusTexto = getColor(R.color.green_900)
                bordaViewMediaNota = getDrawable(R.drawable.circulo_borda_aprovado_gradiente_verde)
                bindingBimestralExibirResultado.imgStatusConteiner.setImageDrawable(getDrawable(R.drawable.circulo_aprovado_solid_verde))
                bindingBimestralExibirResultado.imgStatusConteinerIcon.setImageDrawable(getDrawable(R.drawable.ic_status_aprovado_check_24))
                bindingBimestralExibirResultado.imgStatusConteiner2.setImageDrawable(getDrawable(R.drawable.circulo_aprovado_solid_verde))
                bindingBimestralExibirResultado.imgStatusConteinerIcon2.setImageDrawable(getDrawable(R.drawable.ic_status_aprovado_check_24))
                status = "APROVADO(A)"
                 colorStatusTextoSituacaoAluno = getColor(R.color.green_900)

            }else if (media >= 20 && media < 60) {

                bindingBimestralExibirResultado.cardViewStatus.visibility = View.VISIBLE

                 colorStatusTexto = getColor(R.color.blue_900)
                 colorStatusTextoSituacaoAluno = getColor(R.color.blue_900)
                 // tituloSituacaoProvaAluno.setTextColor(getColor(R.color.prova_final_background))
                 bordaViewMediaNota = getDrawable(R.drawable.circulo_centro_falso_grafico_borda)
                 bindingBimestralExibirResultado.imgStatusConteiner.setImageDrawable(getDrawable(R.drawable.circulo_centro_falso_grafico_borda))
                 bindingBimestralExibirResultado.imgStatusConteinerIcon.setImageDrawable(getDrawable(R.drawable.ic_status_prova_final_refresh_24))
                 bindingBimestralExibirResultado.imgStatusConteiner2.setImageDrawable(getDrawable(R.drawable.circulo_centro_falso_grafico_borda))
                 bindingBimestralExibirResultado.imgStatusConteinerIcon2.setImageDrawable(getDrawable(R.drawable.ic_status_prova_final_refresh_24))

                val notaMinimaNecessaria  =  calcularNotaProvaFinal(
                        nota1,
                        nota2,
                        nota3,
                        nota4,
                        media)

                bindingBimestralExibirResultado.notaNecessaria.text = "${notaMinimaNecessaria}"

                 status = "PROVA FINAL"
                 tituloSituacaoAluno = "Você esta em Prova final. Boa sorte!"

             }else {

                 status = "REPROVADO(A)"
                 colorStatusTexto = getColor(R.color.red_900)
                 colorStatusTextoSituacaoAluno = getColor(R.color.red_900)

                 bindingBimestralExibirResultado.imgStatusConteiner.setImageDrawable(getDrawable(R.drawable.circulo_reprovado))
                 bindingBimestralExibirResultado.imgStatusConteinerIcon.setImageDrawable(getDrawable(R.drawable.ic_status_reprovado_24))
                 bindingBimestralExibirResultado.imgStatusConteiner2.setImageDrawable(getDrawable(R.drawable.circulo_reprovado))
                 bindingBimestralExibirResultado.imgStatusConteinerIcon2.setImageDrawable(getDrawable(R.drawable.ic_status_reprovado_24))
                 bordaViewMediaNota =
                     getDrawable(R.drawable.circulo_centro_falso_grafico_borda_reprovado)
                 tituloSituacaoAluno = "Não foi desa vez. Continue estundando e não desista!"

             }

        }

        bindingBimestralExibirResultado.txtNotaEtapa02.text = segundoBimestre
        bindingBimestralExibirResultado.txtNotaEtapa03.text = terceiroBimestre
        bindingBimestralExibirResultado.txtNotaEtapa04.text = quartoBimestre

        bindingBimestralExibirResultado.txtNotaEtapa02.setTextColor(corTextoNotasSegundo)
        bindingBimestralExibirResultado.txtNotaEtapa03.setTextColor(corTextoNotasTerceiro)
        bindingBimestralExibirResultado.txtNotaEtapa04.setTextColor(corTextoNotasQuarto)

        bindingBimestralExibirResultado.txtStatusAluno.text = status
        bindingBimestralExibirResultado.txtStatusAluno.setTextColor(colorStatusTextoSituacaoAluno)

        bindingBimestralExibirResultado.constraintLayoutBordaMedia.background = bordaViewMediaNota
        bindingBimestralExibirResultado.txtMediaScreenResult.setTextColor( colorStatusTexto)

       bindingBimestralExibirResultado.txtSituacaoAluno.text = tituloSituacaoAluno

    }

    private fun calcularNotaProvaFinal(
        nota1: Int,
        nota2: Int,
        nota3: Int,
        nota4: Int,
        media: Int
    ): Int {

        //aprovado após a avaliação final, o estudante que obtiver média final igual ou superior a 60 pontos
        var notaProvafinal = 0

        var nf1 = 0
        var nf2 = 0
        var nf3 = 0
        var nf4 = 0
        var nf5 = 0

        //formulas para calcular as notas dos alunos
        nf1 = ((2.0 * 60) - media).roundToInt()
        nf2 = ceil(((10 * 60) - (nota2*2) - (nota3*3)-(nota4*3) ) / 2.0).toInt()
        nf3 = ceil(((10 * 60) - (nota1*2) - (nota3*3)-(nota4*3) ) / 2.0).toInt()
        nf4 = ceil(((10 * 60) - (nota1*2) -(nota2*2) -(nota4*3) ) / 3.0).toInt()
        nf5 = ceil(((10 * 60) - (nota1*2) -(nota2*2) -(nota3*3) ) / 3.0).toInt()


        notaProvafinal = nf1

        if (nf2 < notaProvafinal) {
            notaProvafinal = nf2
        }
        if (nf3 < notaProvafinal) {
            notaProvafinal = nf3
        }
        if (nf4 < notaProvafinal) {
            notaProvafinal = nf3
        }
        if (nf5 < notaProvafinal) {
            notaProvafinal = nf3
        }

        return notaProvafinal

    }



    private fun CalcularMediaNotas(nota1: Int, nota2: Int,nota3: Int,nota4: Int): Int {
        val media = ((nota1 * 2) + (nota2 * 2) + (nota3 * 3) + (nota4 * 3)) / 10
        return  media
    }

    private fun fecharTela() {
        finish()
    }
}