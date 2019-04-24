package com.example.passaver12

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var providers : List<AuthUI.IdpConfig>
    private val MY_REQUEST_CODE: Int = 777

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //iniciar
        providers = Arrays.asList<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(), //Login con Email
            AuthUI.IdpConfig.FacebookBuilder().build(), //Login con Facebook
            AuthUI.IdpConfig.GoogleBuilder().build() //Login con Google
        )

        showSignInOptions()

        //evento
        btn_sign_out.setOnClickListener {
            //Cerrar Sesion
            AuthUI.getInstance().signOut(this@MainActivity)
                .addOnCompleteListener {
                    btn_sign_out.isEnabled = false
                    showSignInOptions()
                }
                .addOnFailureListener{
                        e -> Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MY_REQUEST_CODE){
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this, "" + user!!.email,Toast.LENGTH_SHORT).show()

                btn_sign_out.isEnabled = true
                val intent = Intent(this, Principal::class.java)
                intent.putExtra("IdUser", user!!.uid)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this, ""+response!!.error!!.message, Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun showSignInOptions() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.MiTema)
            .build(), MY_REQUEST_CODE)
    }

}
