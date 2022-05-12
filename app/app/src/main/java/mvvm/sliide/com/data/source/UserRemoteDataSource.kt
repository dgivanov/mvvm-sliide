package mvvm.sliide.com.data.source

import mvvm.sliide.com.networking.GoRestApi
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val goRestApi: GoRestApi
) {
    fun getAllUsers() = goRestApi.getAllUsers()

    fun getLastPage(page: Int) = goRestApi.getAllUsers(page)
}
