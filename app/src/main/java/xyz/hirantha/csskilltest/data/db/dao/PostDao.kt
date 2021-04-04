package xyz.hirantha.csskilltest.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import xyz.hirantha.csskilltest.models.Post
import xyz.hirantha.csskilltest.models.PostAndUser

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertPosts(posts: List<Post>)

    @Transaction
    @Query("SELECT * FROM posts;")
    fun getPosts(): LiveData<List<PostAndUser>>
}