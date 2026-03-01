package com.thiago.tomaz.calculaifrn.utils

import android.content.Context
import android.content.SharedPreferences

object SalvarDadosSharedPreferences {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val CAMPO_SENHA ="calculaIFRN2026"
    private val PREFERENCIA_ANOTAI = "prefsCalculaIFRN"


    fun init(contexto: Context) {
        sharedPreferences = contexto.getSharedPreferences(PREFERENCIA_ANOTAI, Context.MODE_PRIVATE)
    }

    fun salvarOpcaoUsuarioSenha(salvar: Boolean): Boolean{
        editor = sharedPreferences.edit()
        return editor.putBoolean(CAMPO_SENHA,salvar).commit()
    }

    fun consultarOpcaoUsuarioSenha(): Boolean{
        return sharedPreferences.getBoolean(CAMPO_SENHA,false)
    }
}