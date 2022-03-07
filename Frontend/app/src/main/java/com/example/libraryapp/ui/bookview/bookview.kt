package com.example.libraryapp.ui.bookview


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.CellBookItem
import com.example.libraryapp.CellImage
import com.example.libraryapp.R
import com.example.libraryapp.data.api.ApiInterface
import com.example.libraryapp.data.api.BookI
import com.example.libraryapp.databinding.ActivityBookviewBinding
import com.example.libraryapp.ui.MainActivityNew
import com.example.libraryapp.ui.bookitemview.ViewBookItem
import com.example.libraryapp.ui.userview.userbookitemview.UserBookItemView
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_bookview.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class bookview : AppCompatActivity() {

    var bookid: String? = null
    var bookname: String? = null
    var photo: String? = null
    var bookdescription: String? = null
    var bookauthor: String? = null
    var bookitems: String? = null
    var bookItem: String? = null
    var isAdmin = true

    var itemsArray: ArrayList<CellImage> = ArrayList()

    lateinit var binding: ActivityBookviewBinding



    lateinit var adapter: BookViewAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bookid = intent.getStringExtra("id")
        bookname = intent.getStringExtra("name")
        photo = intent.getStringExtra("photo")
        bookdescription = intent.getStringExtra("description")
        bookauthor = intent.getStringExtra("author")
        bookitems = intent.getStringExtra("bookitems")


        binding = ActivityBookviewBinding.inflate(layoutInflater)
        val view = binding.root

        setupRecyclerView()
        setContentView(view)

        val service = ApiInterface.invoke()


        /* Calls the endpoint set on getUsers (/api) from UserService using enqueue method
         * that creates a new worker thread to make the HTTP call */
        service.getBookImage(bookid).enqueue(object : Callback<BookI> {


            /* The HTTP call failed. This method is run on the main thread */
            override fun onFailure(call: Call<BookI>, t: Throwable) {
                Log.d("TAG_", "An error happened in main ac!")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<BookI>, response: Response<BookI>) {


                CoroutineScope(Dispatchers.IO).launch {


                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {

                            val items = response.body()
                            if (items != null) {



                                    val id = items.id as Int
                                    val bookName = items.name ?: "N/A"
                                    val bookDescription = items.description ?: "N/A"
                                    val bookAuthor = items.author ?: "N/A"

                                    val bookCategory = items.category
                                    val bookItems = items.bookitems ?: "N/A"

                                    val bookPhoto = items.photo ?: "N/A"
                                    bookItem = items.item ?: "N/A"

                                    itemsArray.add(
                                        CellImage(
                                            bookItem!!,
                                            id,
                                            bookName,
                                            bookAuthor,
                                            bookDescription,
                                            bookPhoto,
                                            bookCategory,
                                            bookItems ,
                                        )
                                    )

                                    adapter = BookViewAdapter(itemsArray, baseContext, isAdmin)//,this)
                                    adapter.notifyDataSetChanged()

                            }
                            binding.adminRecyclerview.adapter = adapter
                        } else {
                            Log.e("RETROFIT_ERROR", response.code().toString())
                        }
                    }
                }
            }
        })


        if (bookitems == "0") {
            buttonrent.isEnabled = false
            buttonBookItems.isEnabled = false
        }

        buttonrent.setOnClickListener {
            //TODO PUT request rent
            val myIntent = Intent(this, UserBookItemView::class.java)
            myIntent.putExtra("id",bookid)
            myIntent.putExtra("name",bookname)
            myIntent.putExtra("description",bookdescription)
            myIntent.putExtra("author",bookauthor)
            myIntent.putExtra("photo",photo)
            myIntent.putExtra("bookitems",bookitems)
            myIntent.putExtra("bookitem",bookItem)
            myIntent.putExtra("isAdmin",true)
            startActivity(myIntent)

        }

        buttonremove.setOnClickListener {
            //TODO DELETE request book
            callDeleteBook()
            val myIntent = Intent(this, MainActivityNew::class.java)
            startActivity(myIntent)
        }

        buttonBookItems.setOnClickListener {
            //TODO PUT request bookitem

            val myIntent = Intent(this, ViewBookItem::class.java)
            myIntent.putExtra("id",bookid)
            myIntent.putExtra("name",bookname)
            myIntent.putExtra("description",bookdescription)
            myIntent.putExtra("author",bookauthor)
            myIntent.putExtra("photo",photo)
            myIntent.putExtra("bookitems",bookitems)
            myIntent.putExtra("bookitem",bookItem)
            startActivity(myIntent)
        }
    }


    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)

        binding.adminRecyclerview.layoutManager = layoutManager
        binding.adminRecyclerview.setHasFixedSize(true)
        val dividerItemDecoration =
                DividerItemDecoration(
                        binding.adminRecyclerview.context,
                        layoutManager.orientation
                )
        ContextCompat.getDrawable(this, R.drawable.line_divider)
                ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.adminRecyclerview.addItemDecoration(dividerItemDecoration)
    }


    private fun callDeleteBook(){
        val service = ApiInterface.invoke()

        CoroutineScope(Dispatchers.IO).launch {

            // Do the DELETE request and get response
            val response = service.deleteBook(bookid).execute()
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
                    Log.d("Pretty Printed JSON :", prettyJson)

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }
}



