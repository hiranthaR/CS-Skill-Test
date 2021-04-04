package xyz.hirantha.csskilltest.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import xyz.hirantha.csskilltest.models.Post
import xyz.hirantha.csskilltest.models.PostAndUser
import xyz.hirantha.csskilltest.models.PostAndUserWithComments

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertPosts(posts: List<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertPost(post: Post)

    @Transaction
    @Query("SELECT * FROM posts;")
    fun getPosts(): LiveData<List<PostAndUser>>

    @Transaction
    @Query("SELECT * FROM posts WHERE id=:postId;")
    fun getPost(postId:Int): LiveData<PostAndUserWithComments>
}