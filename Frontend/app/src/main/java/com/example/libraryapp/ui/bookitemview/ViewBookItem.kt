package com.example.libraryapp.ui.bookitemview



import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.R
import com.example.libraryapp.data.api.ApiInterface
import com.example.libraryapp.databinding.ActivityViewBookItemBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewBookItem : AppCompatActivity() {


    lateinit var binding: ActivityViewBookItemBinding//? = null



    var itemsArray: ArrayList<BookItemCell> = ArrayList()

    lateinit var adapter: BookItemAdapter

    //Data
    var bookid: String? = null
    var bookname: String? = null
    var photo: String? = null
    var bookdescription: String? = null
    var bookauthor: String? = null
    var bookitems: String? = null
    var bookItem: String? = null
    var isAdmin = true

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityViewBookItemBinding.inflate(layoutInflater)
        val view = binding.root

        bookid = intent.getStringExtra("id")
        bookname = intent.getStringExtra("name")
        photo = intent.getStringExtra("photo")
        bookdescription = intent.getStringExtra("description")
        bookauthor = intent.getStringExtra("author")
        bookitems = intent.getStringExtra("bookitems")
        bookItem = intent.getStringExtra("bookitem")

        setupRecyclerView()
        setContentView(view)

        val service = ApiInterface.invoke()

        CoroutineScope(Dispatchers.IO).launch {

            // Do the POST request and get response

            val response = service.getBookItems(bookid).execute()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(response.body())

                    Log.d("Pretty bookitems new :", prettyJson)
                    val items = response.body()?.results
                    if (items != null) {
                        for (i in 0 until items.count()) {
                            val id = items[i].id as Int
                            val shelf = items[i].shelf ?: "N/A"
                            val condition = items[i].condition ?: "N/A"
                            val book = items[i].book ?: "N/A"
                            val bookitemname = bookname ?: "N/A"
                            val bookitemauthor = bookauthor ?: "N/A"
                            val bookitemphoto = photo ?: "N/A"
                            val bookItem = bookItem ?: "N/A"


                            itemsArray.add(
                                BookItemCell(
                                    id,
                                    shelf,
                                    condition,
                                    book,
                                    bookitemname,
                                    bookitemauthor,
                                    bookitemphoto,
                                    bookItem,
                                )
                            )

                            adapter = BookItemAdapter(itemsArray, baseContext, isAdmin)//,this)
                            adapter.notifyDataSetChanged()

                        }
                    }
                    binding.bookitemrecycler.adapter = adapter

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }

    }


    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)

        binding.bookitemrecycler.layoutManager = layoutManager
        binding.bookitemrecycler.setHasFixedSize(true)
        val dividerItemDecoration =
            DividerItemDecoration(
                binding.bookitemrecycler.context,
                layoutManager.orientation
            )
        ContextCompat.getDrawable(this, R.drawable.line_divider)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.bookitemrecycler.addItemDecoration(dividerItemDecoration)
    }
}