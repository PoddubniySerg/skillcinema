package ru.skillbox.core.dto

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import ru.skillbox.core.domain.entities.GenreString
import ru.skillbox.core.domain.entities.MainMovie
import ru.skillbox.core.domain.entities.TranslatableName

open class MovieDto(
    override val genres: List<GenreString>?,
    override val rating: String?,
    override val id: Int,
    override val nameRu: String?,
    override val nameEn: String?,
    override val posterUrl: String?,
    override val posterUrlPreview: String?,
    override var seen: Boolean
) : MainMovie, Parcelable, TranslatableName {
    constructor(parcel: Parcel) : this(
        arrayListOf<GenreString>().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                parcel.readList(
                    this,
                    GenreStringDto::class.java.classLoader,
                    GenreString::class.java
                )
            } else {
                parcel.readList(this, GenreStringDto::class.java.classLoader)
            }
        },
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(rating)
        parcel.writeInt(id)
        parcel.writeString(nameRu)
        parcel.writeString(nameEn)
        parcel.writeString(posterUrl)
        parcel.writeString(posterUrlPreview)
        parcel.writeByte(if (seen) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieDto> {
        override fun createFromParcel(parcel: Parcel): MovieDto {
            return MovieDto(parcel)
        }

        override fun newArray(size: Int): Array<MovieDto?> {
            return arrayOfNulls(size)
        }
    }
}