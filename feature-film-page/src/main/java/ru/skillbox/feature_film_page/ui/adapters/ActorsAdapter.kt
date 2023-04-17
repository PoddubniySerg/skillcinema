package ru.skillbox.feature_film_page.ui.adapters

import ru.skillbox.feature_film_page.models.TranslatableStaffItem

class ActorsAdapter(
    title: String,
    onClickAllButton: (List<TranslatableStaffItem>) -> Unit,
    onItemClick: (TranslatableStaffItem) -> Unit
) : AbstractStaffAdapter(title, onClickAllButton, onItemClick) {

    override val maxItemsViewed: Int = 20
    override val spanCount: Int = 4
}