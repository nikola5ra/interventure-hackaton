package hackaton.interventure.com.interventurehackaton.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import hackaton.interventure.com.interventurehackaton.database.entity.Ison

@Dao
interface IsonDao {

    @Query("SELECT * FROM Ison")
    fun getIsons(): List<Ison>

    @Query("SELECT * FROM Ison WHERE faceId = :faceId")
    fun getIsons(faceId: Int): List<Ison>
}