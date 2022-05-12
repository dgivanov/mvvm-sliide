package mvvm.sliide.com.networking

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import mvvm.sliide.com.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

fun provideOkHttp(httpLoggingInterceptor: HttpLoggingInterceptor, interceptor: Interceptor): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
    okHttpClient.apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(httpLoggingInterceptor)
        }
        addInterceptor(interceptor)
    }
    return okHttpClient.build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): GoRestApi {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(GoRestApi::class.java)
}
