package com.interventure.hackathontv.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Ison(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val faceId: Int
)
