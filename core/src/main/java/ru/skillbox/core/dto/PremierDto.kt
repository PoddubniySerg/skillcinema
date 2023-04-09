package ru.skillbox.core.dto

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import ru.skillbox.core.domain.entities.GenreString
import ru.skillbox.core.domain.entities.PremierMovie
import ru.skillbox.core.domain.entities.TranslatableName

class PremierDto(
    override val id: Int,
    override val nameRu: String?,
    override val nameEn: String?,
    override val posterUrl: String?,
    override val posterUrlPreview: String?,
    override var seen: Boolean,
    override val genres: ArrayList<GenreString>?,
    override val premiereRu: String?
) : PremierMovie, Parcelable, TranslatableName {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
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
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nameRu)
        parcel.writeString(nameEn)
        parcel.writeString(posterUrl)
        parcel.writeString(posterUrlPreview)
        parcel.writeByte(if (seen) 1 else 0)
        parcel.writeString(premiereRu)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PremierDto> {
        override fun createFromParcel(parcel: Parcel): PremierDto {
            return PremierDto(parcel)
        }

        override fun newArray(size: Int): Array<PremierDto?> {
            return arrayOfNulls(size)
        }
    }
}