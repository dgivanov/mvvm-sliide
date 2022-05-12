package mvvm.sliide.com.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mvvm.sliide.com.networking.provideHttpLoggingInterceptor
import mvvm.sliide.com.networking.provideOkHttp
import mvvm.sliide.com.networking.provideRetrofit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

private const val AUTH_HEADER = "Bearer 4ff082b8e37b9816230c6e205d88822906939c6f2907dfba618e1bbab0b32e90"
private const val AUTHORIZATION_KEY = "Authorization"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOKHttpClientLoggingInterceptor() = provideHttpLoggingInterceptor()

    @Singleton
    @Provides
    fun provideOKHttpClientInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()

            val newRequest = original.newBuilder()
                .addHeader(AUTHORIZATION_KEY, AUTH_HEADER)
                .build()

            chain.proceed(newRequest)
        }
    }

    @Singleton
    @Provides
    fun provideOKHttpClient(logInterceptor: HttpLoggingInterceptor, interceptor: Interceptor) =
        provideOkHttp(logInterceptor, interceptor)

    @Singleton
    @Provides
    fun provideAppRetrofit(client: OkHttpClient) = provideRetrofit(client)
}
