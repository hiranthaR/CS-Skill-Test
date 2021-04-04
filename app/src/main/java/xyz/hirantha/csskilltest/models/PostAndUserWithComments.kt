package xyz.hirantha.csskilltest.models

import androidx.room.Embedded
import androidx.room.Relation

data class PostAndUserWithComments(
    @Embedded val post: Post,
    @Relation(
        parentColumn = "userId",
        entityColumn = "id"
    )
    val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "postId"
    )
    val comments:List<Comment>
)