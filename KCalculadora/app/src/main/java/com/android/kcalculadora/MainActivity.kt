package com.android.kcalculadora

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    lateinit var editText: EditText

    var conta: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editTextConta)

    }

    fun positionButtom(view: View) = when (view.id) {
        R.id.btn0 -> digite("0")
        R.id.btn1 -> digite("1")
        R.id.btn2 -> digite("2")
        R.id.btn3 -> digite("3")
        R.id.btn4 -> digite("4")
        R.id.btn5 -> digite("5")
        R.id.btn6 -> digite("6")
        R.id.btn7 -> digite("7")
        R.id.btn8 -> digite("8")
        R.id.btn9 -> digite("9")
        R.id.btnMais -> digite("+")
        R.id.btnMenos -> digite("-")
        R.id.btnDividi -> digite("/")
        R.id.btnMult -> digite("*")
        R.id.btnPonto -> digite(".")
        R.id.btnLimparT -> limpar()
        R.id.btnIgual -> calcular()
        R.id.btnDeletar -> deletar(1)
        else -> Log.d("nada", "Algo diferente")
    }

    fun digite(s: String) {
        var auxS = s
        conta = editText.text.toString()
        if (conta.length == 0) {
            if (s.equals("+") || s.equals("-") || s.equals("/") || s.equals("*")) {
                auxS = ""
            } else {
                conta += auxS
                editText.append(conta)
            }
        } else {

            if (s.equals("+") || s.equals("-") || s.equals("/") || s.equals("*")) {
                if (conta.length > 1) {
                    var auxS2 = conta.get(conta.length - 2).toString()
                    auxS = verificarOperacao(auxS2, s)
                    conta += auxS
                    editText.setText(conta)
                } else {
                    conta += " " + s + " "
                    editText.setText(conta)
                }
            } else {
                conta = auxS
                editText.append(conta)
            }
        }
    }

    fun limpar() {
        editText.setText("")
        conta = ""
    }

    fun calcular() {
        // Modo Difícil

        /*var expression = mutableListOf<Char>()
        for (i in conta.indices) {
            var string: String = conta.get(i).toString()
            if (string.equals("+") || string.equals("-") || string.equals("/") || string.equals("*")) {
                expression.add(conta.get(i))
            }
        }
        var separador: List<String> = conta.split("+", "-", "*", "/")
        var array = separador.toMutableList()
        var int = 0
        try {
            while (expression.size != 0) {
                var numero1 = array.get(0).toDouble()
                var numero2 = array.get(1).toDouble()
                var operador = expression.get(0)
                var total: Double = 0.0
                total = executarOperacao(numero1, operador, numero2)

                array.removeAt(0)
                expression.removeAt(0)
                if (int == 0) {
                    array.removeAt(0)
                    int++
                }
                array.add(0, total.toString())
            }
            editText.setText(array.get(0))
            conta = array.get(0)
        }catch (e :NumberFormatException){
            editText.setText("0")
            conta = "0"
        }*/

        //Modo mamão com açucar

        if (!editText.text.toString().equals("")) {
            try {
                val expression = ExpressionBuilder(editText.text.toString()).build()
                val result: Double = expression.evaluate()
                conta = result.toString()
                editText.setText(conta)
            } catch (ex: NumberFormatException) {
                Log.e("Erro", ex.toString())
                conta = "0"
                editText.setText(conta)
            }
        }
    }

    fun deletar(int: Int) {
        if (conta.length > 0) {
            conta = conta.substring(0, conta.length - int)
            editText.setText(conta)
        }
    }

    fun verificarOperacao(aux: String, operacao: String): String {
        var retorno: String = operacao
        if (aux == operacao) {
            retorno = ""
        } else if (operacao.equals("+")) {
            if (aux.equals("-") || aux.equals("/") || aux.equals("*")) {
                retorno = operacao + " "
                deletar(2)
            } else {
                retorno = " + "
            }
        } else if (operacao.equals("-")) {
            if (aux.equals("+") || aux.equals("/") || aux.equals("*")) {
                retorno = operacao + " "
                deletar(2)
            } else {
                retorno = " - "
            }
        } else if (operacao.equals("/")) {
            if (aux.equals("+") || aux.equals("-") || aux.equals("*")) {
                retorno = operacao + " "
                deletar(2)
            } else {
                retorno = " / "
            }
        } else if (operacao.equals("*")) {
            if (aux.equals("+") || aux.equals("/") || aux.equals("-")) {
                retorno = operacao + " "
                deletar(2)
            } else {
                retorno = " * "
            }
        } else {
            if (operacao.equals("+")) {
                retorno = " + "
            } else if (operacao.equals("-")) {
                retorno = " - "
            } else if (operacao.equals("/")) {
                retorno = " / "
            } else if (operacao.equals("*")) {
                retorno = " * "
            } else {
                retorno = operacao
            }
        }
        return retorno
    }
    /*fun executarOperacao(numero1: Double, operador: Char, numero2: Double): Double {
        var resultado: Double = 0.0
            if (operador == '+') {
                resultado = numero1 + numero2
            } else if (operador == '-') {
                resultado = numero1 - numero2
            } else if (operador == '/') {
                resultado = numero1 / numero2
            } else if (operador == '*') {
                resultado = numero1 * numero2
            }
        return resultado
    }*/
}
