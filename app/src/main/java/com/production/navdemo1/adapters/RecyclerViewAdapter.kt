package com.production.navdemo1.adapters

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.production.navdemo1.R
import com.production.navdemo1.model.Book


class RecyclerViewAdapter(mContext: Context, mData: List<Book>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    private val mContext: Context
    private val mData: List<Book>
    private val options: RequestOptions
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyViewHolder {
        val view: View
        val inflater = LayoutInflater.from(mContext)
        view = inflater.inflate(R.layout.row_item, parent, false)
        val viewHolder = MyViewHolder(view)

        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {
        val book: Book = mData[i]
        holder.tvTitle.text = book.title
        holder.tvAuthor.text = book.author

        //load image from internet and set it into imageView using Glide
        Glide.with(mContext).load(book.imageUrl).apply(options).into(holder.ivThumbnail)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivThumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)
        var tvTitle = itemView.findViewById<TextView>(R.id.title)
        var tvAuthor = itemView.findViewById<TextView>(R.id.author)
    }

    init {
        this.mContext = mContext
        this.mData = mData

        //Request option for Glide
        options = RequestOptions().centerCrop().placeholder(R.drawable.default_placeholder)
            .error(R.drawable.default_placeholder)
    }
}