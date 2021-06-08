package org.morfly.airin.sample.data.impl.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.morfly.airin.sample.data.impl.network.PixabayApi
import org.morfly.airin.sample.data.impl.network.PixabayRequestFieldsSetter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideHttpClient(requestFieldsSetter: PixabayRequestFieldsSetter): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(requestFieldsSetter)
            .build()

    @Provides
    @Singleton
    fun providePixabayApi(client: OkHttpClient, moshi: Moshi): PixabayApi =
        Retrofit.Builder()
            .baseUrl(PixabayApi.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create()
}