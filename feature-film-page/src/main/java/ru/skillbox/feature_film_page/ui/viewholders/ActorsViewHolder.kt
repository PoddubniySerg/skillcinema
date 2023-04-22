package ru.skillbox.feature_film_page.ui.viewholders

import ru.skillbox.core.databinding.CollectionBinding
import ru.skillbox.feature_film_page.models.TranslatableStaffItem

class ActorsViewHolder(
    title: String,
    onClickAllButton: (List<TranslatableStaffItem>) -> Unit,
    onItemClick: (TranslatableStaffItem) -> Unit,
    binding: CollectionBinding
) : AbstractStaffViewHolder(
    title,
    onClickAllButton,
    onItemClick,
    maxItemsViewed = 20,
    spanCount = 4,
    binding
)