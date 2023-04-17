package ru.skillbox.core.domain.entities

interface StaffItem {

    val staffId: Long
    val nameRu: String?
    val nameEn: String?
    val description: String?
    val posterUrl: String?
    val profession: String
    val professionKey: StaffProfessionKey
}