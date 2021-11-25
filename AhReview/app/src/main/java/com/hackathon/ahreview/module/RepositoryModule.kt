package com.hackathon.ahreview.module

import com.hackathon.ahreview.data.repository.ClovaRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single<ClovaRepository> { ClovaRepository() }
}