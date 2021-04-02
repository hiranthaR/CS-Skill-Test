package xyz.hirantha.csskilltest

import android.app.Application
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule

class CSSkillTestApplication : Application() , DIAware{
    override val di: DI = DI.lazy {
        import(androidXModule(this@CSSkillTestApplication))

    }
}