package hackaton.interventure.com.interventurehackaton.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import hackaton.interventure.com.interventurehackaton.database.entity.Team

@Dao
interface TeamDao {

    @Query("SELECT * FROM Team")
    fun getTeams(): List<Team>
}