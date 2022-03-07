package com.example.libraryapp.ui.bookitemview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.libraryapp.R
import com.example.libraryapp.data.api.ApiInterface
import com.example.libraryapp.ui.MainActivityNew
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_book_item_update.*
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class BookItemUpdate : AppCompatActivity() {
    var condition: String? = null
    var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_item_update)


        condition = intent.getStringExtra("condition")
        id = intent.getStringExtra("id")

        Log.d("Book item ID", id.toString())

        findViewById<EditText>(R.id.condition).setText(condition).toString()

        button_update.setOnClickListener {
            UpdateBookItem()

            Toast.makeText(
                applicationContext,
                "BookItem $id Updated",
                Toast.LENGTH_LONG
            ).show()
            val myIntent = Intent(this, MainActivityNew::class.java)
            startActivity(myIntent)
        }

    }

    fun UpdateBookItem(){

        Log.d("My bookitem ID", id.toString())
        // Create Service
        val service = ApiInterface.invoke()

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("condition", findViewById<EditText>(R.id.condition).text.toString())

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val id: String = id.toString() ?: "1"
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.updateBookItem(requestBody, id).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {


                        // Convert raw JSON to pretty JSON using GSON library
                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val prettyJson = gson.toJson(
                            JsonParser.parseString(
                                response.body()
                                    ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                            )
                        )

                        Log.d("Bookitem update :", prettyJson)

                    } else {

                        Log.e("RETROFIT_ERROR", response.code().toString())

                    }
                }
            }
        }

    }
}