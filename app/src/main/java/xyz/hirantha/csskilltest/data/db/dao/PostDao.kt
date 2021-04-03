package xyz.hirantha.csskilltest.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import xyz.hirantha.csskilltest.models.Post

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertPosts(posts: List<Post>)

    @Query("SELECT * FROM posts;")
    fun getPosts(): LiveData<List<Post>>
}