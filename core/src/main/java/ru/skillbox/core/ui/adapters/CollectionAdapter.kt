package ru.skillbox.core.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.skillbox.core.databinding.CollectionBinding
import ru.skillbox.core.domain.models.AppCollection
import ru.skillbox.core.domain.entities.AdapterFactory
import ru.skillbox.core.domain.entities.ManagerFactory

class CollectionAdapter<I, VH : ViewHolder>(
    private val managerFactory: ManagerFactory,
    private val adapterFactory: AdapterFactory<I, VH>,
    private val setTextAllButton: (AppCompatButton, Int) -> Unit,
    private val dividerItemDecorations: List<DividerItemDecoration>,
    private val onClickAllButton: (AppCollection<I>) -> Unit
) : ListAdapter<AppCollection<I>, CollectionViewHolder>(AppCollectionDiffUtil()) {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        return CollectionViewHolder(
            CollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val item = getItem(position)
        val adapter = adapterFactory.getAdapter { onClickAllButton(item) }
        with(holder.binding) {
            nameCollection.text = item.title
            setTextAllButton(buttonShowAll, item.movies.size)
            collection.adapter = adapter
            collection.layoutManager = managerFactory.getLayoutManager()
            collection.setRecycledViewPool(viewPool)
            dividerItemDecorations.forEach { collection.addItemDecoration(it) }
            adapter.submitList(item.movies)
            buttonShowAll.setOnClickListener { onClickAllButton(item) }
        }
    }

}

class CollectionViewHolder(val binding: CollectionBinding) : ViewHolder(binding.root)

class AppCollectionDiffUtil<I> : DiffUtil.ItemCallback<AppCollection<I>>() {

    override fun areItemsTheSame(oldItem: AppCollection<I>, newItem: AppCollection<I>): Boolean {
        return oldItem.index == newItem.index
    }

    override fun areContentsTheSame(oldItem: AppCollection<I>, newItem: AppCollection<I>): Boolean {
        return oldItem == newItem
    }

}