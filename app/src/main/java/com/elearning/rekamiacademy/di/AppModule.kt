package com.elearning.rekamiacademy.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elearning.rekamiacademy.data.local.LocalDataSource
import com.elearning.rekamiacademy.data.local.room.NewsDao
import com.elearning.rekamiacademy.data.local.room.NewsDatabase
import com.elearning.rekamiacademy.data.remote.RemoteDataSource
import com.elearning.rekamiacademy.data.remote.api.ApiService
import com.elearning.rekamiacademy.data.repository.NewsRepository
import com.elearning.rekamiacademy.data.repository.NewsRepositoryImpl
import com.elearning.rekamiacademy.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Named("API_KEY")
    fun apiKey(): String = Constant.KEY_API

    @Provides
    @Named("BASE_URL")
    fun baseUrl(): String = Constant.BASE_URL

    @Provides
    @Named("COUNTRY")
    fun country(): String = Constant.COUNTRY

    @Provides
    @Named("CATEGORY")
    fun category() : String = Constant.CATEGORY

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("BASE_URL")
        baseUrl : String
    ) : Retrofit{
        val client = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY),
            ).build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiService: ApiService,
        @Named("API_KEY")
        apiKey : String,
        @Named("COUNTRY")
        country : String,
        @Named("CATEGORY")
        category: String
    ) : RemoteDataSource =
        RemoteDataSource(apiService,apiKey,country,category)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context : Context) : NewsDatabase =
        Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideLocalDataSource(database : NewsDatabase) : LocalDataSource =
        LocalDataSource(database.newsDao())

   @Provides
   @Singleton
   fun provideNewsRepository(localDataSource: LocalDataSource,remoteDataSource: RemoteDataSource) : NewsRepository =
       NewsRepositoryImpl(remoteDataSource,localDataSource)



}