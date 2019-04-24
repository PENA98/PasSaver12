package com.example.passaver12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import kotlinx.android.synthetic.main.activity_ver_cuenta_individual.*
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.Toast


class VerCuentaIndividual : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_cuenta_individual)

        val objetoIntent: Intent = intent
        val uid = objetoIntent.getStringExtra("idUser")
        val servicio = objetoIntent.getStringExtra("servicio")
        val nick = objetoIntent.getStringExtra("nickname")
        val contrasena = objetoIntent.getStringExtra("contrasena")

        serviciotv.text = servicio
        usertv.text = nick
        contrasenatv.setText(contrasena)
        //Clicl listener del boton que muestra y oculta la contrasena
        toggleButton.setOnClickListener {
            if(toggleButton.isChecked){
                //la mostramos
                contrasenatv.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else
            {
                //la ocultamos
                contrasenatv.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        contrasenatv.setOnLongClickListener(object : OnLongClickListener {
            override fun onLongClick(v: View): Boolean {
                // obtenemos el texto de contrasenatv
                val text = contrasenatv.getText().toString()
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("text", text)
                clipboard.primaryClip = clip
                Toast.makeText(applicationContext, "Contraseña copiada al portapapeles", Toast.LENGTH_SHORT).show()
                return false
            }


        }
        )

        btnVolver.setOnClickListener {
            val intent = Intent(this, Principal::class.java)
            intent.putExtra("IdUser", uid)
            startActivity(intent)
            finish()
        }
    }
}
