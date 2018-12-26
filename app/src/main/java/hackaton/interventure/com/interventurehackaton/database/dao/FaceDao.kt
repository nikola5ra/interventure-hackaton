package hackaton.interventure.com.interventurehackaton.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import hackaton.interventure.com.interventurehackaton.database.entity.Face

@Dao
interface FaceDao {

    @Query("SELECT * FROM Face")
    fun getFaces(): List<Face>

    @Query("SELECT * FROM Face WHERE teamId=:teamId")
    fun getFaces(teamId:Int): List<Face>
}