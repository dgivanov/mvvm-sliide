package mvvm.sliide.com.data.repository

import io.reactivex.rxjava3.core.Single
import mvvm.sliide.com.data.mapper.toListOfUsers
import mvvm.sliide.com.data.source.UserRemoteDataSource
import mvvm.sliide.com.domain.model.User
import mvvm.sliide.com.domain.repository.UserRepository
import mvvm.sliide.com.networking.model.UsersResponse
import javax.inject.Inject
import javax.inject.Singleton

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun getAllUsers(): Single<List<User>> = userRemoteDataSource.getAllUsers()
        .flatMap { Single.just(it.meta.pagination.pages) }
        .flatMap(::getLastPage)
        .flatMap(::toListOfUsers)

    private fun getLastPage(page: Int): Single<UsersResponse> {
        return userRemoteDataSource.getLastPage(page)
    }

    private fun toListOfUsers(usersResponse: UsersResponse) =
        Single.just(usersResponse.toListOfUsers())
}
