package com.example.passaver12.Interfaces

import com.example.passaver12.Models.Account

interface RecyclerAccountListener {
    fun onClick(account: Account, position: Int)
    fun onLongClick(account: Account, position: Int)
}