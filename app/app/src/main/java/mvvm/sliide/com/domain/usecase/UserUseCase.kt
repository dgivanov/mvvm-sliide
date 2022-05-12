package mvvm.sliide.com.domain.usecase

import mvvm.sliide.com.domain.repository.UserRepository
import javax.inject.Inject

class UserUseCase  @Inject constructor(
    private val userRepository: UserRepository
) {

    fun getAllUsers() = userRepository.getAllUsers()
}
