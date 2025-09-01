package com.vedianbunka.lab_week_02_b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    companion object{
        private const val COLOR_KEY = "COLOR_KEY"
        private const val ERROR_KEY = "ERROR_KEY"
    }

    private val submitButton : Button get() = findViewById(R.id.submit_button)

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            activityResult ->
        val data = activityResult.data
        val error = data?.getBooleanExtra(ERROR_KEY, false)
        if(error == true){
            Toast.makeText(this, getString(R.string.color_code_input_invalid), Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.background_screen)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        submitButton.setOnClickListener {
            val colorCode = findViewById<TextInputEditText>(R.id.color_code_input_field).text.toString()
            if(colorCode.isNotEmpty()){
                if(colorCode.length < 6){
                    Toast.makeText(this, getString(R.string.color_code_input_wrong_length), Toast.LENGTH_LONG).show()
                } else {
                    val ResultIntent = Intent(this, ResultActivity::class.java)
                    ResultIntent.putExtra(COLOR_KEY, colorCode)
//                    startActivity(ResultIntent)
                    startForResult.launch(ResultIntent)
                }
            } else {
                Toast.makeText(this, getString(R.string.color_code_input_empty), Toast.LENGTH_LONG).show()
            }
        }

    }
}