package com.rizqi.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizqi.myapplication.databinding.HighlightRvBinding
import com.rizqi.myapplication.model.ResultAdapter

class PopularAdapter(private val onItemClick: OnClickListener):
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    private val IMAGE_BASE ="https://image.tmdb.org/t/p/w500/"

    private val diffCallBack = object : DiffUtil.ItemCallback<ResultAdapter>(){
        override fun areItemsTheSame(
            oldItem: ResultAdapter,
            newItem: ResultAdapter
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ResultAdapter,
            newItem: ResultAdapter
        ): Boolean = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitData(value: List<ResultAdapter>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(HighlightRvBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: PopularAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: HighlightRvBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ResultAdapter) {
            binding.apply {
                Glide.with(binding.root).load(IMAGE_BASE+data.posterPath).into(ivPoster)
                tvJudul.text = data.originalTitle
                tvScore.text = data.voteAverage.toString()
                tvRelease.text = data.releaseDate
                tvPopular.text = data.popularity.toString()
                tvBahasa.text = data.originalLanguage.toUpperCase()
                root.setOnClickListener{
                    onItemClick.onClickItem(data)
                }
            }
        }
    }
    interface OnClickListener{
        fun onClickItem(data: ResultAdapter)
    }
}