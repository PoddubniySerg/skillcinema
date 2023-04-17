package ru.skillbox.feature_film_page.models

import android.os.Parcel
import android.os.Parcelable
import ru.skillbox.core.domain.entities.CountryObject

class CountryObjectDto(
    override val id: Int?,
    override val country: String?
) : CountryObject, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(country)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return this.country ?: ""
    }

    companion object CREATOR : Parcelable.Creator<CountryObjectDto> {
        override fun createFromParcel(parcel: Parcel): CountryObjectDto {
            return CountryObjectDto(parcel)
        }

        override fun newArray(size: Int): Array<CountryObjectDto?> {
            return arrayOfNulls(size)
        }
    }
}