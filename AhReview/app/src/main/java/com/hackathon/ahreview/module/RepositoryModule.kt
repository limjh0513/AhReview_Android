package com.hackathon.ahreview.module

import com.hackathon.ahreview.data.repository.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val RepositoryModule = module {
    single { ClovaRepository() }
    single { AuthRepository(get()) }
    single { ReviewRepository(get()) }
    single { StoreRepository(get()) }
    single { UserRepository(get()) }
}