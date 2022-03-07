package com.example.libraryapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.Cell
import com.example.libraryapp.R
import com.example.libraryapp.data.api.ApiInterface
import com.example.libraryapp.data.api.BookResponse
import com.example.libraryapp.databinding.ActivityMainNewBinding
import com.example.libraryapp.ui.bookview.addbookview
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main_new.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class   MainActivityNew : AppCompatActivity() {

    lateinit var binding: ActivityMainNewBinding

    var itemsArray: ArrayList<Cell> = ArrayList()

    lateinit var adapter: BookAdapter

    val isAdmin = true

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainNewBinding.inflate(layoutInflater)
        val view = binding.root

        setupRecyclerView()
        setContentView(view)

        val service = ApiInterface.invoke()


        /* Calls the endpoint set on getUsers (/api) from UserService using enqueue method
         * that creates a new worker thread to make the HTTP call */
        service.getBooks().enqueue(object : Callback<BookResponse> {


            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.d("TAG_", "An error happened in main ac!")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {


                CoroutineScope(Dispatchers.IO).launch {


                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {

                            // Convert raw JSON to pretty JSON using GSON library
                            val gson = GsonBuilder().setPrettyPrinting().create()


                            val items = response.body()?.results
                            if (items != null) {
                                for (i in 0 until items.count()) {


                                    val id = items[i].id as Int
//                                    Log.d("ID: ", id.toString())

                                    // Employee Name
                                    val bookName = items[i].name ?: "N/A"
//                                    Log.d("Employee Name: ", bookName)

                                    // Employee Salary in USD
                                    val description = items[i].description ?: "N/A"
//                                    Log.d("Description: ", description)

                                    // Employee Salary in EUR
                                    val author = items[i].author ?: "N/A"

                                    val category = items[i].category
                                    val bookitems = items[i].bookitems ?: "N/A"
                                    val photo = items[i].photo ?: "N/A"

                                   itemsArray.add(
                                        Cell(
                                            id,
                                            bookName,
                                            author,
                                            description,
                                            photo,
                                            category,
                                            bookitems,
                                        )
                                   )

                                    adapter = BookAdapter(itemsArray, baseContext, isAdmin)//,this)
                                    adapter.notifyDataSetChanged()
                                }
                            }
                            binding.jsonResultsRecyclerview.adapter = adapter
                        } else {
                            Log.e("RETROFIT_ERROR", response.code().toString())
                        }
                    }
                }
            }
        })

        bookAdd.setOnClickListener {
            //TODO POST request add book inside view
            val myIntent = Intent(this, addbookview::class.java)
            startActivity(myIntent)
        }
    }


    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)

        binding.jsonResultsRecyclerview.layoutManager = layoutManager
        binding.jsonResultsRecyclerview.setHasFixedSize(true)
        val dividerItemDecoration =
            DividerItemDecoration(
                binding.jsonResultsRecyclerview.context,
                layoutManager.orientation
            )
        ContextCompat.getDrawable(this, R.drawable.line_divider)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.jsonResultsRecyclerview.addItemDecoration(dividerItemDecoration)
    }

}













