package mvvm.sliide.com.domain.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import mvvm.sliide.com.domain.model.User

interface UserRepository {

    fun getAllUsers(): Single<List<User>>

    fun deleteUser(id: Long) : Completable

    fun addUser(name: String, email: String) : Completable
}
