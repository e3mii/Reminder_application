package com.eradotov.homework.data.room

import androidx.room.*
import com.eradotov.homework.data.entity.User

@Dao
abstract class UserDao {

    @Query(value = "SELECT * FROM users WHERE users.username = :username AND users.password = :password")
    abstract suspend fun valideteUser(username: String, password: String): User?

    @Query(value = "SELECT * FROM users WHERE username = :username")
    abstract suspend fun getUserWithUsername(username: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: User): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: User)

    @Delete
    abstract suspend fun  delete(entity: User): Int
}