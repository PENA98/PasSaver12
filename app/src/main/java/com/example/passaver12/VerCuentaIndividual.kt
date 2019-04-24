package com.example.passaver12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import kotlinx.android.synthetic.main.activity_ver_cuenta_individual.*

class VerCuentaIndividual : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_cuenta_individual)

        val objetoIntent: Intent = intent
        val Uid = objetoIntent.getStringExtra("idUser")
        val servicio = objetoIntent.getStringExtra("servicio")
        val nick = objetoIntent.getStringExtra("nickname")
        var contrasena = objetoIntent.getStringExtra("contrasena")

        serviciotv.text = servicio
        usertv.text = nick
        contrasenatv.setText(contrasena)
        toggleButton.setOnClickListener {
            if(toggleButton.isChecked){
                contrasenatv.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else
            {
                contrasenatv.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        btnVolver.setOnClickListener {
            val intent = Intent(this, Principal::class.java)
            intent.putExtra("IdUser", Uid)
            startActivity(intent)
            finish()
        }
    }
}
