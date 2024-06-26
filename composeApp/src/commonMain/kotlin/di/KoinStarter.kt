package di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

object KoinStarter {
    fun init(config: KoinAppDeclaration? = null) = startKoin {
        config?.invoke(this)
        modules(gameModule)
    }
}
