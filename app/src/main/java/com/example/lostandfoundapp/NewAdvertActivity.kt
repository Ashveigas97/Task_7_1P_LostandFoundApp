package com.example.lostandfoundapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NewAdvertActivity : AppCompatActivity() {

    private lateinit var radioLost: RadioButton
    private lateinit var radioFound: RadioButton
    private lateinit var editName: EditText
    private lateinit var editPhone: EditText
    private lateinit var editDescription: EditText
    private lateinit var editDate: EditText
    private lateinit var editLocation: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_advert)

        radioLost = findViewById(R.id.radioLost)
        radioFound = findViewById(R.id.radioFound)
        editName = findViewById(R.id.etName)
        editPhone = findViewById(R.id.etPhoneNumber)
        editDescription = findViewById(R.id.etDescription)
        editDate = findViewById(R.id.etDate)
        editLocation = findViewById(R.id.etLocation)
        btnSave = findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            val itemType = if (radioLost.isChecked) "Lost" else "Found"
            val name = editName.text.toString().trim()
            val phone = editPhone.text.toString().trim()
            val description = editDescription.text.toString().trim()
            val date = editDate.text.toString().trim()
            val location = editLocation.text.toString().trim()

            // Save the advert to the database
            val dbHelper = DatabaseHelper(this)
            dbHelper.insertItem(itemType, name, phone, description, date, location)
            dbHelper.close()

            // Display a success message
            Toast.makeText(this, "Advert saved successfully", Toast.LENGTH_SHORT).show()

            // Navigate back to MainActivity
            finish()
        }
    }
}
