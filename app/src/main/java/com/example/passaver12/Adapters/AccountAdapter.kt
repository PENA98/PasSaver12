package com.example.passaver12.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.passaver12.Interfaces.RecyclerAccountListener
import com.example.passaver12.Models.Account
import com.example.passaver12.R
import kotlinx.android.synthetic.main.activity_account_template.view.*

class AccountAdapter(private val accounts: List<Account>, private val listener: RecyclerAccountListener)
    : RecyclerView.Adapter<AccountAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_account_template, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = accounts.size

    override fun onBindViewHolder(holder: AccountAdapter.ViewHolder, position: Int) = holder.bind(accounts[position], listener)

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(account: Account, listener: RecyclerAccountListener) = with(itemView){
            tvTitulo.text = account.servicio
            tvPrimeraLetra.text = account.servicio.first().toUpperCase().toString()

            setOnClickListener { listener.onClick(account, adapterPosition) }
        }
    }

}