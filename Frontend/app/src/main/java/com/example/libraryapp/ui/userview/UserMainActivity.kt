package com.example.libraryapp.ui.userview


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.Cell
import com.example.libraryapp.R
import com.example.libraryapp.data.api.ApiInterface
import com.example.libraryapp.data.api.BookResponse
import com.example.libraryapp.databinding.ActivityUserMainBinding
import com.example.libraryapp.ui.BookAdapter
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserMainActivity : AppCompatActivity() {


    lateinit var binding: ActivityUserMainBinding

    var itemsArray: ArrayList<Cell> = ArrayList()

    lateinit var adapter: BookAdapter
    var isAdmin = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityUserMainBinding.inflate(layoutInflater)
        val view = binding.root

        setupRecyclerView()
        setContentView(view)

        val service = ApiInterface.invoke()


        /* Calls the endpoint set on getUsers (/api) from UserService using enqueue method
         * that creates a new worker thread to make the HTTP call */
        service.getBooks().enqueue(object : Callback<BookResponse> {


            /* The HTTP call failed. This method is run on the main thread */
            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.d("TAG_", "An error happened in main ac!")
                t.printStackTrace()
            }

            /* The HTTP call was successful, we should still check status code and response body
             * on a production app. This method is run on the main thread */
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                /* This will print the response of the network call to the Logcat */
                val results = response.body()?.results
//                Log.d("TAG_", response.body().toString())
//                Log.d("TAG_", results?.get(0)?.name.toString())


                CoroutineScope(Dispatchers.IO).launch {


                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {

                            // Convert raw JSON to pretty JSON using GSON library
                            val gson = GsonBuilder().setPrettyPrinting().create()


                            val items = response.body()?.results
                            if (items != null) {
                                for (i in 0 until items.count()) {


                                    val id = items[i].id as Int
                                    val bookName = items[i].name ?: "N/A"
                                    val description = items[i].description ?: "N/A"
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

                            binding.userRecyclerview.adapter = adapter

                        } else {

                            Log.e("RETROFIT_ERROR", response.code().toString())

                        }
                    }
                }
            }
        })

    }


    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)

        binding.userRecyclerview.layoutManager = layoutManager
        binding.userRecyclerview.setHasFixedSize(true)
        val dividerItemDecoration =
                DividerItemDecoration(
                        binding.userRecyclerview.context,
                        layoutManager.orientation
                )
        ContextCompat.getDrawable(this, R.drawable.line_divider)
                ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.userRecyclerview.addItemDecoration(dividerItemDecoration)
    }
}
