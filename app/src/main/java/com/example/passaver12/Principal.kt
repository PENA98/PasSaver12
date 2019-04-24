package com.example.passaver12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.passaver12.Adapters.AccountAdapter
import com.example.passaver12.Interfaces.RecyclerAccountListener
import com.example.passaver12.Models.Account
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_principal.*

class Principal : AppCompatActivity() {

    // Variables para Firebase Firestore
    private var iduser : String? = null
    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var accountDBRef: CollectionReference
    private val accountList: ArrayList<Account> = ArrayList()
    private lateinit var adapter: AccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val objetoIntent: Intent = intent
        val id = objetoIntent.getStringExtra("IdUser")
        iduser = id

        accountDBRef = store.collection("cuenta")

        val intent2 = Intent(this, VerCuentaIndividual::class.java)

        val layoutManager = LinearLayoutManager(this)
        adapter = AccountAdapter(accountList, object: RecyclerAccountListener{
            override fun onClick(account: Account, position: Int) {
                Toast.makeText(applicationContext, account.servicio,Toast.LENGTH_SHORT).show()

                intent2.putExtra("servicio", account.servicio)
                intent2.putExtra("nickname", account.user)
                intent2.putExtra("contrasena", account.contrasena)
                intent2.putExtra("idUser", id)
                startActivity(intent2)
                finish()


            }

            override fun onLongClick(account: Account, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        rvCuentas.setHasFixedSize(true)
        rvCuentas.layoutManager = layoutManager
        rvCuentas.itemAnimator = DefaultItemAnimator()
        rvCuentas.adapter = adapter

        getAccountFromDb()

        fabAgregarCuenta.setOnClickListener{

            val intent = Intent(this, AddAccount::class.java)
            intent.putExtra("IdUser", id)
            startActivity(intent)
            finish()
        }

    }

    private fun getAccountFromDb() {

        accountDBRef.whereEqualTo("userID", "$iduser").addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
                exception?.let{
                    Toast.makeText(applicationContext, "Error al momento de cargar la informacion", Toast.LENGTH_SHORT).show()
                    return
                }
                snapshot?.let {
                    val account = it.toObjects(Account::class.java)
                    accountList.addAll(account)
                    adapter.notifyDataSetChanged()
                }

            }
        })
    }
}
