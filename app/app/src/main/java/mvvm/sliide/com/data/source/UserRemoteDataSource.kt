package mvvm.sliide.com.data.source

import mvvm.sliide.com.networking.GoRestApi
import mvvm.sliide.com.networking.model.CreateUserRequest
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val goRestApi: GoRestApi
) {
    fun getAllUsers() = goRestApi.getAllUsers()

    fun deleteUser(id: Long) = goRestApi.deleteUser(id)

    fun createUser(user: CreateUserRequest) = goRestApi.createUser(user)
}
