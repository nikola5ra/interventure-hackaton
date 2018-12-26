package com.interventure.hackathontv.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Image(
    @PrimaryKey
    val id: Int,
    val name: String,
    val image: String,
    val eventId: Int
)