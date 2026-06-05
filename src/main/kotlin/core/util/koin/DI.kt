package com.example.core.util.koin

import com.example.core.util.hashing.PassHasher
import com.example.core.util.hashing.PassHasherService
import org.koin.dsl.module

val utilModule = module{

    single<PassHasher> { PassHasherService() }

}