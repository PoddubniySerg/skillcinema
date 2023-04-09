package ru.skillbox.core.dto

import android.os.Parcel
import android.os.Parcelable
import ru.skillbox.core.domain.entities.GenreString

class GenreStringDto(override val genre: String?) : GenreString, Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(genre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GenreStringDto> {
        override fun createFromParcel(parcel: Parcel): GenreStringDto {
            return GenreStringDto(parcel)
        }

        override fun newArray(size: Int): Array<GenreStringDto?> {
            return arrayOfNulls(size)
        }
    }
}