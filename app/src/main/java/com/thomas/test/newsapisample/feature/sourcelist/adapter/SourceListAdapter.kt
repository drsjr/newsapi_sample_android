package com.thomas.test.newsapisample.feature.sourcelist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.size.Scale
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import com.thomas.test.newsapisample.R
import com.thomas.test.newsapisample.data.model.Source
import kotlinx.android.synthetic.main.source_item.view.*

class SourceListAdapter(
    private val onItemClick: (Source) -> Unit
) : RecyclerView.Adapter<SourceListViewHolder>() {
    private val sources: MutableList<Source> = mutableListOf()

    fun setSources(list: List<Source>) {
        sources.clear()
        sources.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = sources.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceListViewHolder {
        return SourceListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SourceListViewHolder, position: Int) {
        holder.bind(sources[position], onItemClick)
    }

}

class SourceListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(source: Source, onItemClick: (Source) -> Unit) {
        with(itemView) {
            setOnClickListener {
                onItemClick(source)
            }

            val flagUrl = "https://www.countryflags.io/${source.country}/flat/64.png"
            ivSourceAvatar.load(flagUrl) {
                scale(Scale.FILL)
                crossfade(true)
                placeholder(R.drawable.avatar_circle)
                error(R.drawable.avatar_circle)
                transformations(BlurTransformation(context, 16f, 1.5f), CircleCropTransformation())
            }

            tvSourceAvatar.text = getAvatarText(source)
            tvSourceTitle.text = source.name
            tvSourceCategory.text = source.category
            tvSourceDescription.text = source.description
        }
    }

    private fun getAvatarText(source: Source): String {
        return source.name.split(" ")
            .filterIndexed { index, _ -> index < 2 }
            .map { it[0].toString() }
            .reduce { acc, s -> acc + s }
            .toUpperCase()
    }

    companion object {
        fun create(parent: ViewGroup): SourceListViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.source_item, parent, false)
            return SourceListViewHolder(view)
        }
    }
}
