package com.ssafy.phonesin.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssafy.phonesin.network.ApiService
import com.ssafy.phonesin.network.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val baseUrl = "http://3.36.49.178:8080"

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdW5nb29uMDY0NkBnbWFpbC5jb20iLCJtZW1iZXJJZCI6MiwiYXV0aG9yaXRpZXMiOiJST0xFX0FETUlOIiwiaWF0IjoxNjkxMzg4Nzc4LCJleHAiOjE2OTE0NzUxNzh9.hY9TO4KsdYoHwrRyWm05X6kFnqD4ZxLnQT2CgRbfKjU"// 저장된 JWT 토큰을 가져옵니다.

                // JWT 토큰을 Authorization 헤더에 첨부합니다.
                val requestBuilder = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .method(originalRequest.method(), originalRequest.body())

                val request = requestBuilder.build()
//                HttpLoggingInterceptor().apply {
//                    level = HttpLoggingInterceptor.Level.BODY
//                }
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}