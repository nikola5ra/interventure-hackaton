package hackaton.interventure.com.interventurehackaton.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Team(
    @PrimaryKey
    val id: Int,
    val name: String,
    val thumb: String,
    val desc: String
)
