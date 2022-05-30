package com.free.auth.di

import com.free.data.repositories.FirebaseAuthRepositoryImpl
import com.free.domain.repositories.AuthRepository
import com.free.domain.repositories.AuthRepositoryAnnotation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    @Singleton
    @AuthRepositoryAnnotation
    fun authRepository(): AuthRepository = FirebaseAuthRepositoryImpl()
}