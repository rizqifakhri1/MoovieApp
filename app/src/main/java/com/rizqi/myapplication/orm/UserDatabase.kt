package com.rizqi.myapplication.orm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rizqi.myapplication.model.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object{
        const val  DB_NAME = "User.db"
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase?{
            if(INSTANCE == null){
                synchronized(UserDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserDatabase::class.java, DB_NAME).build()
                }
            }
            return  INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}