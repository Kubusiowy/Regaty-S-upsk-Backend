package com.example.core.database.koin


import com.example.core.database.DatabaseFactory
import com.example.core.util.hashing.PassHasher
import com.example.core.util.hashing.PassHasherService
import org.koin.dsl.module


val databaseModule = module {
    single { DatabaseFactory(get()) }
    single<PassHasher> { PassHasherService() }

}