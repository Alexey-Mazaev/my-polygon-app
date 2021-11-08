package com.example.mypolygonapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mypolygonapp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel.receivedMessage.observe(this) { result ->
            val newText = binding.receivedInformation.text.toString() + "$result\n"
            binding.receivedInformation.text = newText
        }
    }
}