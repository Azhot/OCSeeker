package fr.azhot.ocseeker.presentation.ui.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.azhot.ocseeker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    // variables
    private lateinit var binding: ActivityMainBinding


    // overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}