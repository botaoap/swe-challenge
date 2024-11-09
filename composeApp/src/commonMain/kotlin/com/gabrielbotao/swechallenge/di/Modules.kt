package com.gabrielbotao.swechallenge.di

import com.gabrielbotao.swechallenge.data.repository.RepositoryImpl
import com.gabrielbotao.swechallenge.domain.repository.Repository
import com.gabrielbotao.swechallenge.domain.usecase.GetLoggedInStatusUseCase
import com.gabrielbotao.swechallenge.domain.usecase.GetLoggedInStatusUseCaseImpl
import com.gabrielbotao.swechallenge.domain.usecase.SaveLoggedInStatusUseCase
import com.gabrielbotao.swechallenge.domain.usecase.SaveLoggedInStatusUseCaseImpl
import com.gabrielbotao.swechallenge.ui.bottomnavigation.viewmodel.MainViewModel
import com.gabrielbotao.swechallenge.ui.initial.viewmodel.InitialViewModel
import com.gabrielbotao.swechallenge.ui.login.viewmodel.LoginViewModel
import com.gabrielbotao.swechallenge.ui.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

expect val platformModule: Module

val shareModule = module {
    getRepositoryModule(this)
    getUseCaseModule(this)
    getViewModelModule(this)
}

private fun getRepositoryModule(module: Module) {
    with(module) {
        factory<Repository> { RepositoryImpl(get()) }
    }
}

private fun getUseCaseModule(module: Module) {
    with(module) {
        factory<GetLoggedInStatusUseCase> { GetLoggedInStatusUseCaseImpl(get(), Dispatchers.IO) }
        factory<SaveLoggedInStatusUseCase> { SaveLoggedInStatusUseCaseImpl(get()) }
    }
}

private fun getViewModelModule(module: Module) {
    with(module) {
        factoryOf(::MainViewModel)
        factoryOf(::InitialViewModel)
        factoryOf(::LoginViewModel)
        factoryOf(::ProfileViewModel)
    }
}