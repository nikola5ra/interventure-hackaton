package com.interventure.hackathontv.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.interventure.hackathontv.database.entity.Ison

@Dao
interface IsonDao {

    @Query("SELECT * FROM Ison")
    fun getIsons(): List<Ison>
}