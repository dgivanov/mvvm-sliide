package mvvm.sliide.com.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import mvvm.sliide.com.data.repository.UserRepositoryImpl
import mvvm.sliide.com.domain.repository.UserRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository
}
