package com.example.libraryapp.ui


import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.Cell
import com.example.libraryapp.R
import com.example.libraryapp.databinding.CellBinding
import com.example.libraryapp.ui.bookview.bookview
import com.example.libraryapp.ui.userview.userbookview.UserBookView


class BookAdapter( private val cell: ArrayList<Cell>, private val mContext: Context, private val isAdmin: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class CellViewHolder(var viewBinding: CellBinding) : RecyclerView.ViewHolder(viewBinding.root)
    {

        init{
            itemView.setOnClickListener{

                v: View -> val position: Int = adapterPosition

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
        val binding = CellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = holder as CellViewHolder

        itemViewHolder.viewBinding.bookNameTextview.text = cell[position].name
        itemViewHolder.viewBinding.descriptionTextview.text = cell[position].description
        itemViewHolder.viewBinding.authorTextview.text = cell[position].author

        val imageBytes = Base64.decode(cell[position].photo, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        itemViewHolder.viewBinding.CellBookView.setImageBitmap(decodedImage)

    }

    override fun getItemCount(): Int {
        return cell.size
    }
}
