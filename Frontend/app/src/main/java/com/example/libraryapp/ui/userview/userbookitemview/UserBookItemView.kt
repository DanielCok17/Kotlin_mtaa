package com.example.libraryapp.ui.userview.userbookitemview


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.R
import com.example.libraryapp.data.api.ApiInterface
import com.example.libraryapp.databinding.ActivityUserBookItemViewBinding
import com.example.libraryapp.ui.MainActivityNew
import com.example.libraryapp.ui.bookitemview.BookItemAdapter
import com.example.libraryapp.ui.bookitemview.BookItemCell
import com.example.libraryapp.ui.userview.UserMainActivity
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_user_book_item_view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserBookItemView : AppCompatActivity() {

    lateinit var binding: ActivityUserBookItemViewBinding

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
    var isAdmin : Boolean = false

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBookItemViewBinding.inflate(layoutInflater)
        val view = binding.root

        bookid = intent.getStringExtra("id")
        bookname = intent.getStringExtra("name")
        photo = intent.getStringExtra("photo")
        bookdescription = intent.getStringExtra("description")
        bookauthor = intent.getStringExtra("author")
        bookitems = intent.getStringExtra("bookitems")
        bookItem = intent.getStringExtra("bookitem")
        isAdmin = intent.getBooleanExtra("isAdmin", false)


        setupRecyclerView()
        setContentView(view)

        val service = ApiInterface.invoke()

        CoroutineScope(Dispatchers.IO).launch {

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
                                    bookItem
                                )
                            )

                            adapter = BookItemAdapter(itemsArray, baseContext, isAdmin)//,this)
                            adapter.notifyDataSetChanged()

                        }
                    }
                    binding.userbookitemrecycler.adapter = adapter

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }

        userbookitemUpdate.setOnClickListener{
            if (isAdmin) {
                val intent = Intent(this, MainActivityNew::class.java)
                startActivity(intent)
            }
            else {
                val intent = Intent(this, UserMainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)

        binding.userbookitemrecycler.layoutManager = layoutManager
        binding.userbookitemrecycler.setHasFixedSize(true)
        val dividerItemDecoration =
            DividerItemDecoration(
                binding.userbookitemrecycler.context,
                layoutManager.orientation
            )
        ContextCompat.getDrawable(this, R.drawable.line_divider)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.userbookitemrecycler.addItemDecoration(dividerItemDecoration)
    }
}