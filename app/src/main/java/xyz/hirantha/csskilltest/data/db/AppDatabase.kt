package xyz.hirantha.csskilltest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import xyz.hirantha.csskilltest.data.db.dao.PostDao
import xyz.hirantha.csskilltest.data.db.dao.UserDao
import xyz.hirantha.csskilltest.models.Post
import xyz.hirantha.csskilltest.models.User

@Database(
    entities = [Post::class,User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app.db"
        )
            .build()
    }
}