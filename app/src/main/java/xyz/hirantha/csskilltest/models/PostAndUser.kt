package xyz.hirantha.csskilltest.models

import androidx.room.Embedded
import androidx.room.Relation

data class PostAndUser(
    @Embedded val post: Post,
    @Relation(
        parentColumn = "userId",
        entityColumn = "id"
    )
    val user: User
)