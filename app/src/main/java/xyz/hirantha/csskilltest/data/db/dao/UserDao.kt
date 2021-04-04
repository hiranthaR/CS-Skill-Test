package xyz.hirantha.csskilltest.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import xyz.hirantha.csskilltest.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertUsers(posts: List<User>)

    @Query("SELECT * FROM users;")
    fun getUsers(): LiveData<List<User>>
}