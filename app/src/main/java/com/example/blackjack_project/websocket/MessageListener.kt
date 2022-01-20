package com.example.blackjack_project.websocket

interface MessageListener {
    fun onConnectSuccess ()
    fun onConnectFailed ()
    fun onClose ()
    fun onMessage(text: String?)
}