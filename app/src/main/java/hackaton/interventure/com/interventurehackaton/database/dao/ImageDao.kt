package hackaton.interventure.com.interventurehackaton.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import hackaton.interventure.com.interventurehackaton.database.entity.Image
import hackaton.interventure.com.interventurehackaton.database.entity.Team

@Dao
interface ImageDao {

    @Query("SELECT * FROM Image WHERE eventId=:eventId")
    fun getImages(eventId: Int): List<Image>
}