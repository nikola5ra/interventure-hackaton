package hackaton.interventure.com.interventurehackaton.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import hackaton.interventure.com.interventurehackaton.database.entity.Events

@Dao
interface EventsDao {

    @Query("SELECT * FROM Events")
    fun getEvents(): List<Events>
}