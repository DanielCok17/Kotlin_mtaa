package com.example.libraryapp.ui.userview.userbookview


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.CellImage
import com.example.libraryapp.R
import com.example.libraryapp.data.api.ApiInterface
import com.example.libraryapp.data.api.BookI
import com.example.libraryapp.databinding.ActivityUserBookViewBinding
import com.example.libraryapp.ui.bookview.BookViewAdapter
import com.example.libraryapp.ui.userview.userbookitemview.UserBookItemView
import kotlinx.android.synthetic.main.activity_bookview.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class UserBookView : AppCompatActivity() {

    var bookid: String? = null
    var bookname: String? = null
    var photo: String? = null
    var bookdescription: String? = null
    var bookauthor: String? = null
    var bookitems: String? = null
    var bookItem: String? = null

    var isAdmin = false

    var itemsArray: ArrayList<CellImage> = ArrayList()

    lateinit var binding: ActivityUserBookViewBinding

    lateinit var adapter: BookViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        bookid = intent.getStringExtra("id")
        bookname = intent.getStringExtra("name")
        photo = intent.getStringExtra("photo")
        bookdescription = intent.getStringExtra("description")
        bookauthor = intent.getStringExtra("author")
        bookitems = intent.getStringExtra("bookitems")


        binding = ActivityUserBookViewBinding.inflate(layoutInflater)
        val view = binding.root

        setupRecyclerView()
        setContentView(view)

        val service = ApiInterface.invoke()

        service.getBookImage(bookid).enqueue(object : Callback<BookI> {

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
                            binding.userRecyclerview.adapter = adapter
                        } else {
                            Log.e("RETROFIT_ERROR", response.code().toString())
                        }
                    }
                }
            }
        })
        Log.d("MOOOJ OMTAZOK ", photo.toString())



        if (bookitems == "0") {
            buttonrent.isEnabled = false
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
            myIntent.putExtra("isAdmin",isAdmin)

            startActivity(myIntent)
        }
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