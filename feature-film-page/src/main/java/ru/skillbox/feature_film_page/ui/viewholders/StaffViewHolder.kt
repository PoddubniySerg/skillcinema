package ru.skillbox.feature_film_page.ui.viewholders

import ru.skillbox.core.databinding.CollectionBinding
import ru.skillbox.feature_film_page.models.TranslatableStaffItem

class StaffViewHolder(
    title: String,
    onClickAllButton: (List<TranslatableStaffItem>) -> Unit,
    onItemClick: (TranslatableStaffItem) -> Unit,
    binding: CollectionBinding
) : AbstractStaffViewHolder(
    title,
    onClickAllButton,
    onItemClick,
    maxItemsViewed = 6,
    spanCount = 2,
    binding
)