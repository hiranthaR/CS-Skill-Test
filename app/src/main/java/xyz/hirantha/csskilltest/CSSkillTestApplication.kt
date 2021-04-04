package xyz.hirantha.csskilltest

import android.app.Application
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule
import xyz.hirantha.csskilltest.data.db.AppDatabase
import xyz.hirantha.csskilltest.data.providers.ThemeProvider
import xyz.hirantha.csskilltest.data.providers.ThemeProviderImpl
import xyz.hirantha.csskilltest.data.remote.api.TypiCodeAPIService
import xyz.hirantha.csskilltest.data.remote.datasources.APIServiceDataSource
import xyz.hirantha.csskilltest.data.remote.datasources.APIServiceDataSourceImpl
import xyz.hirantha.csskilltest.data.remote.interceptors.ConnectivityInterceptor
import xyz.hirantha.csskilltest.data.remote.interceptors.ConnectivityInterceptorImpl
import xyz.hirantha.csskilltest.data.repository.Repository
import xyz.hirantha.csskilltest.data.repository.RepositoryImpl
import xyz.hirantha.csskilltest.ui.createpost.CreatePostViewModelFactory
import xyz.hirantha.csskilltest.ui.post.PostViewModelFactory
import xyz.hirantha.csskilltest.ui.posts.PostsViewModelFactory
import xyz.hirantha.csskilltest.ui.user.ProfileViewModelFactory

class CSSkillTestApplication : Application(), DIAware {
    override val di: DI = DI.lazy {
        import(androidXModule(this@CSSkillTestApplication))

        // database
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { instance<AppDatabase>().postDao() }
        bind() from singleton { instance<AppDatabase>().userDao() }
        bind() from singleton { instance<AppDatabase>().commentDao() }

        //interceptors
        bind<ConnectivityInterceptor>() with singleton {
            ConnectivityInterceptorImpl(
                instance()
            )
        }

        // api services
        bind<TypiCodeAPIService>() with singleton { TypiCodeAPIService(instance()) }

        //data source
        bind<APIServiceDataSource>() with singleton {
            APIServiceDataSourceImpl(
                instance()
            )
        }

        // providers
        bind<ThemeProvider>() with singleton { ThemeProviderImpl(instance()) }

        // repository
        bind<Repository>() with singleton {
            RepositoryImpl(
                instance(),
                instance(),
                instance(),
                instance(),
                instance()
            )
        }

        // view model factories
        bind() from provider { PostsViewModelFactory(instance()) }
        bind() from factory { postId: Int -> PostViewModelFactory(instance(), postId) }
        bind() from factory { userId: Int -> ProfileViewModelFactory(instance(), userId) }
        bind() from provider { CreatePostViewModelFactory(instance()) }
    }
}