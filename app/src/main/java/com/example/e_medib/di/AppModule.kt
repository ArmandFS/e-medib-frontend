package com.example.e_medib.di

import com.example.e_medib.network.EMedibApi
import com.example.e_medib.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    // BUILD RETROFIT
    @Singleton
    @Provides
    fun provideEMedibApi(): EMedibApi {

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EMedibApi::class.java)
    }


}