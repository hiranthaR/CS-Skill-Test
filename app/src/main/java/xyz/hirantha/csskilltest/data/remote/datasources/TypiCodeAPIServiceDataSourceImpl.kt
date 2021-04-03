package xyz.hirantha.csskilltest.data.remote.datasources

import xyz.hirantha.csskilltest.data.remote.api.TypiCodeAPIService

class TypiCodeAPIServiceDataSourceImpl(typiCodeAPIService: TypiCodeAPIService) :
    TypiCodeAPIServiceDataSource {
    override suspend fun getPosts() {
        TODO("Not yet implemented")
    }
}