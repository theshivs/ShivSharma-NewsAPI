package com.example.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class NewsAdapter(private var newsList: ArrayList<News>): RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false)
    return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsAdapter.MyViewHolder, position: Int) {
        val currentItem = newsList[position]
        Log.d("API Error---------------", currentItem.toString())
//        holder.title_image.setImageURI(currentItem.url.toUri())
        if(currentItem.title != "[Removed]") {
            Glide.with(holder.itemView.context)
                .load(currentItem.urlToImage)
                .into(holder.title_image)

            holder.tvHeading.text = currentItem.title
            holder.tvDescription.text = "Open to read..."

        }

        holder.itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity

                val bundle = Bundle()
                bundle.putString("news_title", currentItem.title)
                bundle.putString("image", currentItem.urlToImage)
                bundle.putString("date", currentItem.publishedAt)
                bundle.putString("description", currentItem.description)

                val detailsFragment = NewsDetailsFragment()

                detailsFragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction().replace(R.id.frame_layout, detailsFragment).addToBackStack(null).commit()
            }

        })
    }

    fun updateData(newDataList: ArrayList<News>) {
        newsList = newDataList

        notifyDataSetChanged() // Notify adapter that data set has changed
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title_image = itemView.findViewById<ImageView>(R.id.title_image)
        val tvHeading: TextView = itemView.findViewById(R.id.tvHeading)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)

    }
}