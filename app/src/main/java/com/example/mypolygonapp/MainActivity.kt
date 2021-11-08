package com.example.mypolygonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.example.mypolygonapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.send.setOnClickListener {
            viewModel.sendMessage(binding.message.text.toString())
        }
        binding.message.doOnTextChanged { text, _, _, _ ->
            binding.send.isEnabled = !text.isNullOrBlank()
        }

        viewModel.receivedMessage.observe(this) { result ->
            val newText = binding.receivedInformation.text.toString() + "$result\n"
            binding.receivedInformation.text = newText
        }
    }
}