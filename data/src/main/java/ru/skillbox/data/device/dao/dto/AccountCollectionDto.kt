package ru.skillbox.data.device.dao.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.skillbox.core.domain.entities.AccountCollection

@Entity(tableName = "collections")
class AccountCollectionDto(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "collection_id")
    override val id: Int,

    @ColumnInfo(index = true, name = "name")
    override val name: String,

    @ColumnInfo(name = "count")
    override val count: Int

) : AccountCollection