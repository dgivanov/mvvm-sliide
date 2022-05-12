package mvvm.sliide.com.data.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import mvvm.sliide.com.data.mapper.toListOfUsers
import mvvm.sliide.com.data.source.UserRemoteDataSource
import mvvm.sliide.com.networking.model.CreateUserRequest
import mvvm.sliide.com.domain.model.User
import mvvm.sliide.com.domain.repository.UserRepository
import mvvm.sliide.com.networking.model.UsersResponse
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun getAllUsers(): Single<List<User>> = userRemoteDataSource.getAllUsers()
        .flatMap(::toListOfUsers)

    override fun deleteUser(id: Long): Completable = userRemoteDataSource.deleteUser(id)

    override fun addUser(name: String, email: String): Completable =
        userRemoteDataSource.createUser(CreateUserRequest(name, email))

    private fun toListOfUsers(usersResponse: UsersResponse) =
        Single.just(usersResponse.toListOfUsers())
}
