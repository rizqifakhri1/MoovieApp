package com.rizqi.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizqi.myapplication.databinding.UpcomingRvBinding
import com.rizqi.myapplication.model.ResultUpcoming

class UpcomingAdapter(private val onItemClick: OnClickListener):
    RecyclerView.Adapter<UpcomingAdapter.ViewHolder>() {

    private val IMAGE_BASE ="https://image.tmdb.org/t/p/w500/"

    private val diffCallBack = object : DiffUtil.ItemCallback<ResultUpcoming>(){
        override fun areItemsTheSame(
            oldItem: ResultUpcoming,
            newItem: ResultUpcoming
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ResultUpcoming,
            newItem: ResultUpcoming
        ): Boolean = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitData(value: List<ResultUpcoming>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(UpcomingRvBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: UpcomingAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: UpcomingRvBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ResultUpcoming) {
            binding.apply {
                Glide.with(binding.root).load(IMAGE_BASE+data.posterPath).into(ivPoster)
                tvJudul.text = data.originalTitle
                tvRelease.text = data.releaseDate
                tvRating.text = data.voteAverage.toString()
                root.setOnClickListener{
                    onItemClick.onClickItem(data)
                }
            }
        }
    }
    interface OnClickListener{
        fun onClickItem(data: ResultUpcoming)
    }

}