package com.mte.fitnessapp.di

import com.mte.fitnessapp.network.ApiFactory
import com.mte.fitnessapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory() : GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ) : Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(gsonConverterFactory).build()
    }

    @Singleton
    @Provides
    fun provideApiFactory(retrofit: Retrofit): ApiFactory{
        return retrofit.create(ApiFactory::class.java)
    }
}