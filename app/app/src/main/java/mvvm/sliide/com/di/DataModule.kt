package mvvm.sliide.com.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import mvvm.sliide.com.data.source.UserRemoteDataSource
import mvvm.sliide.com.domain.repository.UserRepository
import mvvm.sliide.com.domain.usecase.UserUseCase
import mvvm.sliide.com.networking.GoRestApi

@Module
@InstallIn(ViewModelComponent::class)
class DataModule {

    @Provides
    fun bindUserRemoteDataSource(goRestApi: GoRestApi) = UserRemoteDataSource(goRestApi)

    @Provides
    fun bindUserUseCase(userRepository: UserRepository) = UserUseCase(userRepository)
}