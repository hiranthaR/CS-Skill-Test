package xyz.hirantha.csskilltest.data.db.dao

import androidx.room.*
import xyz.hirantha.csskilltest.models.Comment

@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertComments(comments: List<Comment>)
}