package ru.skillbox.feature_film_page.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.skillbox.core.databinding.CollectionBinding
import ru.skillbox.core.ui.viewholders.CollectionViewHolder
import ru.skillbox.feature_film_page.models.TranslatableStaffItem
import ru.skillbox.feature_film_page.ui.viewholders.ActorsViewHolder

class ActorsAdapter(
    private val title: String,
    private val onClickAllButton: (List<TranslatableStaffItem>) -> Unit,
    private val onItemClick: (TranslatableStaffItem) -> Unit
) : AbstractStaffAdapter() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectionViewHolder<List<TranslatableStaffItem>> {
        val binding =
            CollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorsViewHolder(title, onClickAllButton, onItemClick, binding)
    }
}