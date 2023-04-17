package ru.skillbox.feature_film_page.models

import android.os.Parcel
import android.os.Parcelable
import ru.skillbox.core.domain.entities.GenreObject

class GenreObjectDto(override val id: Int?, override val genre: String?) : GenreObject, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(genre)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return this.genre ?: ""
    }

    companion object CREATOR : Parcelable.Creator<GenreObjectDto> {
        override fun createFromParcel(parcel: Parcel): GenreObjectDto {
            return GenreObjectDto(parcel)
        }

        override fun newArray(size: Int): Array<GenreObjectDto?> {
            return arrayOfNulls(size)
        }
    }
}