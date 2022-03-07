package com.example.libraryapp.ui.bookview

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.CellImage
import com.example.libraryapp.databinding.BookviewrecyclerBinding
import com.example.libraryapp.ui.userview.userbookview.UserBookView


class BookViewAdapter( private val cell: ArrayList<CellImage>, private val mContext: Context, private val isAdmin: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class CellViewHolder(var viewBinding: BookviewrecyclerBinding) : RecyclerView.ViewHolder(viewBinding.root)
    {

        init{
            itemView.setOnClickListener{

                v: View -> val position: Int = adapterPosition


                Log.d("Position ", position.toString())

                Log.d("ID Boook ",cell[position].id.toString() )



                val myIntent = if(isAdmin)
                    Intent(mContext, bookview::class.java)
                else
                    Intent(mContext, UserBookView::class.java)

                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myIntent.putExtra("id",cell[position].id.toString())
                myIntent.putExtra("name",cell[position].name)
                myIntent.putExtra("description",cell[position].description)
                myIntent.putExtra("author",cell[position].author)
                myIntent.putExtra("photo",cell[position].photo)
                myIntent.putExtra("bookitems",cell[position].bookitems)
                mContext.startActivity(myIntent)

            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CellViewHolder(BookviewrecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as CellViewHolder

        itemViewHolder.viewBinding.bookname.text = cell[position].name
        itemViewHolder.viewBinding.bookauthor.text = cell[position].author
        itemViewHolder.viewBinding.bookavailablecount.text = cell[position].bookitems
        itemViewHolder.viewBinding.bookdescription.text = cell[position].description


        val imageBytes = Base64.decode(cell[position].item, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        itemViewHolder.viewBinding.bookview.setImageBitmap(decodedImage)
    }

    override fun getItemCount(): Int {
        return cell.size
    }
}
