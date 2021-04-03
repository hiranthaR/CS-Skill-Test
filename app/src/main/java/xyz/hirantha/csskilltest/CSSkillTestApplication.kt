package xyz.hirantha.csskilltest

import android.app.Application
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bind
import org.kodein.di.provider
import xyz.hirantha.csskilltest.ui.posts.PostsViewModelFactory

class CSSkillTestApplication : Application(), DIAware {
    override val di: DI = DI.lazy {
        import(androidXModule(this@CSSkillTestApplication))

        // view model factories
        bind() from provider { PostsViewModelFactory() }
    }
}