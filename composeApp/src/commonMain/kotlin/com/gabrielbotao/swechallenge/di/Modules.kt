package com.gabrielbotao.swechallenge.di

import com.gabrielbotao.swechallenge.data.repository.RepositoryImpl
import com.gabrielbotao.swechallenge.domain.mapper.LoginMapper
import com.gabrielbotao.swechallenge.domain.mapper.ProductsMapper
import com.gabrielbotao.swechallenge.domain.repository.Repository
import com.gabrielbotao.swechallenge.domain.usecase.GetLoggedInStatusUseCase
import com.gabrielbotao.swechallenge.domain.usecase.GetLoggedInStatusUseCaseImpl
import com.gabrielbotao.swechallenge.domain.usecase.GetProductsUseCase
import com.gabrielbotao.swechallenge.domain.usecase.GetProductsUseCaseImpl
import com.gabrielbotao.swechallenge.domain.usecase.PostLoginUseCase
import com.gabrielbotao.swechallenge.domain.usecase.PostLoginUseCaseImpl
import com.gabrielbotao.swechallenge.domain.usecase.SaveLoggedInStatusUseCase
import com.gabrielbotao.swechallenge.domain.usecase.SaveLoggedInStatusUseCaseImpl
import com.gabrielbotao.swechallenge.ui.bottomnavigation.viewmodel.MainViewModel
import com.gabrielbotao.swechallenge.ui.home.viewmodel.HomeViewModel
import com.gabrielbotao.swechallenge.ui.initial.viewmodel.InitialViewModel
import com.gabrielbotao.swechallenge.ui.login.viewmodel.LoginViewModel
import com.gabrielbotao.swechallenge.ui.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

expect val platformModule: Module

val shareModule = module {
    getRepositoryModule(this)
    getUseCaseModule(this)
    getViewModelModule(this)
    getMapperModule(this)
}

private fun getRepositoryModule(module: Module) {
    with(module) {
        single<Repository> { RepositoryImpl(get(), get()) }
    }
}

private fun getUseCaseModule(module: Module) {
    with(module) {
        single<GetLoggedInStatusUseCase> { GetLoggedInStatusUseCaseImpl(get(), Dispatchers.IO) }
        single<SaveLoggedInStatusUseCase> { SaveLoggedInStatusUseCaseImpl(get()) }
        single<PostLoginUseCase> { PostLoginUseCaseImpl(get(), get(), Dispatchers.IO) }
        single<GetProductsUseCase> { GetProductsUseCaseImpl(get(), get(), Dispatchers.IO) }
    }
}

private fun getViewModelModule(module: Module) {
    with(module) {
        singleOf(::MainViewModel)
        singleOf(::InitialViewModel)
        singleOf(::LoginViewModel)
        singleOf(::ProfileViewModel)
        singleOf(::HomeViewModel)
    }
}

private fun getMapperModule(module: Module) {
    with(module) {
        factoryOf(::LoginMapper)
        factoryOf(::ProductsMapper)
    }
}