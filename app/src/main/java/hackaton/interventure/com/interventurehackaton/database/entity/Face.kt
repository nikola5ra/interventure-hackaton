package com.interventure.hackathontv.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Face(
    @PrimaryKey
    val id: Int,
    val name: String,
    val image: String,
    val desc: String,
    val teamId: Int
)
