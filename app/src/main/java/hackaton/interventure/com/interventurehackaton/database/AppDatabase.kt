package com.interventure.hackathontv.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.interventure.hackathontv.database.dao.IsonDao
import com.interventure.hackathontv.database.entity.*
import java.io.File
import java.io.FileOutputStream

@Database(entities = [Events::class, Face::class, Image::class, Ison::class, Team::class, Video::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun isonDao(): IsonDao

    companion object {
        @JvmField
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

        var INSTANCE: AppDatabase? = null
        const val DATABASE_NAME = "media"
        const val ASSETS_PATH = "databases"

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    installDatabaseFromAssets(context)
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                        .addMigrations(MIGRATION_1_2)
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }

        private fun installDatabaseFromAssets(context: Context) {
            val dbPath = context.getDatabasePath(DATABASE_NAME)
            if (dbPath.exists()) return
            dbPath.parentFile.mkdirs()

            try {
                val inputStream = context.assets.open("$ASSETS_PATH/$DATABASE_NAME.sqlite3")
                val outputFile = File(context.getDatabasePath(DATABASE_NAME).path)
                val outputStream = FileOutputStream(outputFile)

                inputStream.copyTo(outputStream)
                inputStream.close()

                outputStream.flush()
                outputStream.close()
            } catch (exception: Throwable) {
                throw RuntimeException("The $DATABASE_NAME database couldn't be installed.", exception)
            }
        }
    }


}