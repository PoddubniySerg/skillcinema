package ru.skillbox.data.device.dao.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collections")
class CollectionDao(
    @PrimaryKey
    @ColumnInfo(name = "collection_name")
    val name: String
)