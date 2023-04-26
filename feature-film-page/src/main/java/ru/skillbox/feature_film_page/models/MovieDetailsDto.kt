package ru.skillbox.feature_film_page.models

import android.os.Parcel
import android.os.Parcelable
import ru.skillbox.core.domain.entities.MovieDetails
import ru.skillbox.core.domain.entities.TranslatableName

class MovieDetailsDto(
    override val id: Long,
    override val nameRu: String?,
    override val nameEn: String?,
    override val nameOriginal: String?,
    override val posterUrl: String?,
    override val logoUrl: String?,
    override val ratingKinopoisk: Double?,
    override val year: Int?,
    override val filmLength: Int?,
    override val description: String?,
    override val shortDescription: String?,
    override val ratingAgeLimits: String?,
    override val countries: List<CountryObjectDto>?,
    override val genres: List<GenreObjectDto>?,
    override val isSerial: Boolean?,
    override val coverUrl: String?,
    override val posterUrlPreview: String?
) : MovieDetails, Parcelable, TranslatableName {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(CountryObjectDto),
        parcel.createTypedArrayList(GenreObjectDto),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(nameRu)
        parcel.writeString(nameEn)
        parcel.writeString(nameOriginal)
        parcel.writeString(posterUrl)
        parcel.writeString(logoUrl)
        parcel.writeValue(ratingKinopoisk)
        parcel.writeValue(year)
        parcel.writeValue(filmLength)
        parcel.writeString(description)
        parcel.writeString(shortDescription)
        parcel.writeString(ratingAgeLimits)
        parcel.writeParcelableArray(countries?.toTypedArray(), flags)
        parcel.writeParcelableArray(genres?.toTypedArray(), flags)
        parcel.writeValue(isSerial)
        parcel.writeString(coverUrl)
        parcel.writeString(posterUrlPreview)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieDetailsDto> {
        override fun createFromParcel(parcel: Parcel): MovieDetailsDto {
            return MovieDetailsDto(parcel)
        }

        override fun newArray(size: Int): Array<MovieDetailsDto?> {
            return arrayOfNulls(size)
        }
    }
}