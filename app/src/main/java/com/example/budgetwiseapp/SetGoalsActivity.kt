package com.example.budgetwiseapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SetGoalsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goals)

        val seekBarMin = findViewById<SeekBar>(R.id.seekBarMinGoal)
        val seekBarMax = findViewById<SeekBar>(R.id.seekBarMaxGoal)
        val tvMinValue = findViewById<TextView>(R.id.tvMinGoalValue)
        val tvMaxValue = findViewById<TextView>(R.id.tvMaxGoalValue)
        val btnSave = findViewById<Button>(R.id.btnSaveGoals)

        // Update text when sliders move
        seekBarMin.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                tvMinValue.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        seekBarMax.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                tvMaxValue.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        btnSave.setOnClickListener {
            val min = seekBarMin.progress
            val max = seekBarMax.progress
            if (min <= max) {
                Toast.makeText(this, "Goals saved: Min $min, Max $max", Toast.LENGTH_SHORT).show()
                // Optionally save to Room DB or SharedPreferences here
            } else {
                Toast.makeText(this, "Min goal can't be greater than max goal", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
