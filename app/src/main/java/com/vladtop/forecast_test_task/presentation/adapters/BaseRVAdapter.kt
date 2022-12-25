package com.vladtop.forecast_test_task.presentation.adapters

import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vladtop.forecast_test_task.domain.Weather

abstract class BaseRVAdapter(
    private val list: List<Weather>
) : RecyclerView.Adapter<BaseRVAdapter.BaseViewHolder>() {

    abstract inner class BaseViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        abstract fun bind(position: Int)

        protected fun setIcon(url: String, imageView: ImageView) {
            Glide.with(binding.root)
                .load("https:$url")
                .fitCenter()
                .placeholder(android.R.drawable.ic_menu_upload)
                .error(android.R.drawable.stat_notify_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }

        protected fun setTemperature(temperature: Int, textView: TextView) {
            Log.e("temperature", temperature.toString())
            val temp = if (temperature > 0) "+${temperature}°C" else "${temperature}°C"
            textView.text = temp
        }

        protected fun setDate(date: String, textView: TextView) {
            Log.e("formatted", date)
            textView.text = date
        }
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = list.size


}