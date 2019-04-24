package com.example.passaver12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.passaver12.Models.Account
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_account.*

class AddAccount : AppCompatActivity() {

    // Variables para Firebase Firestore
    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var accountDBRef: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)

        val objetoIntent: Intent = intent
        val id = objetoIntent.getStringExtra("IdUser")


        //establecer la coleccion a utilizar
        accountDBRef = store.collection("cuenta")

        val btnAddAccount = findViewById<Button>(R.id.btnAgregar)

        val tvServicio = findViewById<TextView>(R.id.etServicio)
        val tvNickname = findViewById<TextView>(R.id.etNickName2)
        val tvContrasena = findViewById<TextView>(R.id.etContrasena2)
        val tvConfirmarContrasena = findViewById<TextView>(R.id.etConfirmarContrasena)

        btnAddAccount.setOnClickListener{
            val servicio = tvServicio.text
            val contrasena = tvContrasena.text
            val contrasenaConfirm = tvConfirmarContrasena.text
            if(servicio.isNotEmpty()){
                if(contrasena != contrasenaConfirm){
                    val account = Account(id,servicio.toString(), tvNickname.text.toString(), tvContrasena.text.toString())
                    saveAccount(account)
                }
                else
                {
                    Toast.makeText(this, "las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(this, "Debes colocar el nombre del servicio", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveAccount(account: Account) {
        val newAccount = HashMap<String, Any>()
        newAccount["contrasena"] = account.contrasena
        newAccount["servicio"] = account.servicio
        newAccount["user"] = account.user
        newAccount["userID"] = account.userId


        accountDBRef.add(newAccount)
            .addOnCompleteListener {
                Toast.makeText(this, "Cuenta agregada exitosamente", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Principal::class.java)
                intent.putExtra("IdUser", account.userId)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Hubo un error al almacenar la cuenta", Toast.LENGTH_SHORT).show()
            }


       /* val ref = FirebaseDatabase.getInstance().getReference("cuenta")
        val accountid = ref.push().key

        ref.child(accountid.toString()).setValue(account)
            .addOnCompleteListener {
                Toast.makeText(this, "Cuenta agregada exitosamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Hubo un error al almacenar la cuenta", Toast.LENGTH_SHORT).show()
            }*/


    }
}
