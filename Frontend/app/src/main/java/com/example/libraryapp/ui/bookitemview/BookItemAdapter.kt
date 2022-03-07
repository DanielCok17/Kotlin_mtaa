package com.example.libraryapp.ui.bookitemview


import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.data.api.ApiInterface
import com.example.libraryapp.databinding.CellbookitemBinding
import com.example.libraryapp.ui.MainActivityNew
import com.example.libraryapp.ui.userview.UserMainActivity
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


class BookItemAdapter( private val cell: ArrayList<BookItemCell>, private val mmContext: Context, private var isAdmin: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class CellViewHolder(var viewBinding: CellbookitemBinding) : RecyclerView.ViewHolder(viewBinding.root)
    {
        init{
            itemView.setOnClickListener{

                v: View -> val position: Int = adapterPosition

                Log.d("Position ", position.toString())

                val myIntent = if(isAdmin)
                    Intent(mmContext, MainActivityNew::class.java)
                else {
                    postRent(position)
                    Toast.makeText(
                        mmContext,
                        "You have rented: " + cell[position].name,
                        Toast.LENGTH_LONG
                    ).show()
                    Intent(mmContext, UserMainActivity::class.java)
                }
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myIntent.putExtra("condition",cell[position].condition)
                myIntent.putExtra("id",cell[position].id.toString())
                mmContext.startActivity(myIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CellbookitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as CellViewHolder
        itemViewHolder.viewBinding.bookNameTextview.text = cell[position].name
        itemViewHolder.viewBinding.authorTextview.text = cell[position].author
        if(cell[position].condition == "1"){
            itemViewHolder.viewBinding.conditionTextview.text = "New"
        } else if(cell[position].condition == "2"){
            itemViewHolder.viewBinding.conditionTextview.text = "Preserved"
        } else{
            itemViewHolder.viewBinding.conditionTextview.text = "Old"
        }

        val imageBytes = Base64.decode(cell[position].item, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        itemViewHolder.viewBinding.CellBookView.setImageBitmap(decodedImage)
    }

    override fun getItemCount(): Int {
        return cell.size
    }


    private fun postRent(position: Int) {

        Log.d("My bookitem ID",cell[position].id.toString())
        // Create Service
        val service = ApiInterface.invoke()

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("rented", true)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val id: String = cell[position].id.toString() ?: "1"
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                val response = service.rentBook(requestBody, id).execute()
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

                        Log.d("Rent response :", prettyJson)

                    } else {

                        Log.e("RETROFIT_ERROR", response.code().toString())

                    }
                }
            }
        }
    }

}
