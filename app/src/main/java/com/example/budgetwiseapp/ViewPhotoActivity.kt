package com.example.budgetwiseapp

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class ViewPhotoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_photo)

        val imageView = findViewById<ImageView>(R.id.ivExpensePhoto)
        val closeButton = findViewById<Button>(R.id.btnClose)

        // Get image path from intent
        val imagePath = intent.getStringExtra("imagePath")
        if (imagePath != null) {
            val imageFile = File(imagePath)
            if (imageFile.exists()) {
                val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
                imageView.setImageBitmap(bitmap)
            }
        }

        closeButton.setOnClickListener {
            finish()
        }
    }
}
