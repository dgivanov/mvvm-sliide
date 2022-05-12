package mvvm.sliide.com.networking

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import mvvm.sliide.com.networking.model.CreateUserRequest
import mvvm.sliide.com.networking.model.UsersResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GoRestApi {

    @GET(GET_USERS)
    fun getAllUsers(@Query("page") page: Int = 0): Single<UsersResponse>

    @DELETE(DELETE_USER)
    fun deleteUser(@Path("id") id: Long): Completable

    @POST(CREATE_USER)
    fun createUser(@Body user: CreateUserRequest): Completable
}
