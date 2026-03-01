package com.thiago.tomaz.calculaifrn.utils

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat

object MoedaConfig {

    fun converterBigDecimalToString(valor: BigDecimal): String {
        val nf = NumberFormat.getNumberInstance(AppConfigGlobais.DEFAULT_LOCALE)
        nf.minimumFractionDigits = 2
        nf.maximumFractionDigits = 2
        return nf.format(valor)
    }

    fun converterStringToBigDecimal(valor: String): BigDecimal{
        val nf = NumberFormat.getNumberInstance(AppConfigGlobais.DEFAULT_LOCALE) as DecimalFormat
        nf.isParseBigDecimal = true

        return nf.parse(valor) as BigDecimal
    }

    fun formatarTexto(texto: String): String = texto.replace(".","").replace(",","")

}