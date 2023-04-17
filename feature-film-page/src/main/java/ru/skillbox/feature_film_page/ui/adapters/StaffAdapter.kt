package ru.skillbox.feature_film_page.ui.adapters

import ru.skillbox.feature_film_page.models.TranslatableStaffItem

class StaffAdapter(
    title: String,
    onClickAllButton: (List<TranslatableStaffItem>) -> Unit,
    onItemClick: (TranslatableStaffItem) -> Unit
) : AbstractStaffAdapter(title, onClickAllButton, onItemClick) {

    override val maxItemsViewed: Int = 6
    override val spanCount: Int = 2
}