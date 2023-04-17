package ru.skillbox.feature_film_page.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.skillbox.core.domain.entities.TranslatableName
import ru.skillbox.core.utils.TranslatableNameProcessor
import ru.skillbox.feature_film_page.R
import ru.skillbox.feature_film_page.databinding.FilmPageStaffItemBinding
import ru.skillbox.feature_film_page.models.TranslatableStaffItem

class StaffItemAdapter(
    private val onItemClick: (TranslatableStaffItem) -> Unit
) : ListAdapter<TranslatableStaffItem, StaffItemViewHolder>(StaffItemDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffItemViewHolder {
        return StaffItemViewHolder(
            FilmPageStaffItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StaffItemViewHolder, position: Int) {
        val staff = getItem(position)
        with(holder.binding) {
            TranslatableNameProcessor.setName(staff as TranslatableName, nameStaff)
            descriptionStaff.text = staff.description
            Glide.with(root.context)
                .load(staff.posterUrl)
                .placeholder(R.drawable.film_page_collections_staff_item_placeholder)
                .into(staffPhotoImageView)

            root.setOnClickListener { onItemClick(staff) }
        }
    }
}

class StaffItemViewHolder(val binding: FilmPageStaffItemBinding) :
    RecyclerView.ViewHolder(binding.root)

class StaffItemDiffUtilCallback : DiffUtil.ItemCallback<TranslatableStaffItem>() {

    override fun areItemsTheSame(
        oldItem: TranslatableStaffItem,
        newItem: TranslatableStaffItem
    ): Boolean {
        return oldItem.staffId == newItem.staffId
    }

    override fun areContentsTheSame(
        oldItem: TranslatableStaffItem,
        newItem: TranslatableStaffItem
    ): Boolean {
        return oldItem.nameRu == newItem.nameRu
                && oldItem.nameEn == newItem.nameEn
                && oldItem.profession == newItem.profession
                && oldItem.description == newItem.description
                && oldItem.posterUrl == newItem.posterUrl
                && oldItem.professionKey == newItem.professionKey
    }
}