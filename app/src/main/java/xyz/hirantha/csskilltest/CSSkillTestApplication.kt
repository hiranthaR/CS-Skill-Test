package xyz.hirantha.csskilltest

import android.app.Application
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule
import xyz.hirantha.csskilltest.data.db.AppDatabase
import xyz.hirantha.csskilltest.data.remote.api.TypiCodeAPIService
import xyz.hirantha.csskilltest.data.remote.datasources.TypiCodeAPIServiceDataSource
import xyz.hirantha.csskilltest.data.remote.datasources.TypiCodeAPIServiceDataSourceImpl
import xyz.hirantha.csskilltest.data.remote.interceptors.ConnectivityInterceptor
import xyz.hirantha.csskilltest.data.remote.interceptors.ConnectivityInterceptorImpl
import xyz.hirantha.csskilltest.data.repository.Repository
import xyz.hirantha.csskilltest.data.repository.RepositoryImpl
import xyz.hirantha.csskilltest.ui.posts.PostsViewModelFactory

class CSSkillTestApplication : Application(), DIAware {
    override val di: DI = DI.lazy {
        import(androidXModule(this@CSSkillTestApplication))

        // database
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { instance<AppDatabase>().postDao() }
        bind() from singleton { instance<AppDatabase>().userDao() }

        //interceptors
        bind<ConnectivityInterceptor>() with singleton {
            ConnectivityInterceptorImpl(
                instance()
            )
        }

        // api services
        bind<TypiCodeAPIService>() with singleton { TypiCodeAPIService(instance()) }

        //data source
        bind<TypiCodeAPIServiceDataSource>() with singleton {
            TypiCodeAPIServiceDataSourceImpl(
                instance()
            )
        }

        // repository
        bind<Repository>() with singleton { RepositoryImpl(instance(), instance(),instance()) }

        // view model factories
        bind() from provider { PostsViewModelFactory(instance()) }
    }
}