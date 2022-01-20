package com.example.blackjack_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.blackjack_project.databinding.ActivityMainBinding
import com.example.blackjack_project.websocket.MessageListener
import com.example.blackjack_project.websocket.WebSocketManager
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), MessageListener {
    private val serverUrl = "wss://web-socket-ex-1.conveyor.cloud/api/WebSocket?nom={test"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super .onCreate (savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WebSocketManager.init(serverUrl, this)
        binding.connectBtn.setOnClickListener {
            thread {
                kotlin.run {
                    WebSocketManager.connect()
                }
            }
        }
        binding.clientSendBtn.setOnClickListener {
            if ( WebSocketManager.sendMessage( " message send " )) {
                addText( " Send from the client \n " )
            }
        }
        binding.closeConnectionBtn.setOnClickListener {
            WebSocketManager.close()
        }
    }

    override fun onConnectSuccess() {
        addText( " Connected successfully \n " )
    }

    override fun onConnectFailed() {
        addText( " Connection failed \n " )
    }

    override fun onClose() {
        addText( " Closed successfully \n " )
    }

    override fun onMessage(text: String?) {
        addText( " Receive message: $text \n " )
    }

    private fun addText(text: String?) {
        runOnUiThread {
            binding.contentEt.text.append(text)
        }
    }

    override fun onDestroy() {
        super .onDestroy ()
        WebSocketManager.close()
    }
}
