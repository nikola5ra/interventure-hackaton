package hackaton.interventure.com.interventurehackaton.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import hackaton.interventure.com.interventurehackaton.database.entity.Team
import hackaton.interventure.com.interventurehackaton.database.entity.Video

@Dao
interface VideoDao {

    @Query("SELECT * FROM Video")
    fun getVideos(): List<Video>
}