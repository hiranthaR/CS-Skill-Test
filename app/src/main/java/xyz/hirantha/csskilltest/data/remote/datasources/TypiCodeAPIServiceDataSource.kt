package xyz.hirantha.csskilltest.data.remote.datasources

interface TypiCodeAPIServiceDataSource {
    suspend fun getPosts()
}