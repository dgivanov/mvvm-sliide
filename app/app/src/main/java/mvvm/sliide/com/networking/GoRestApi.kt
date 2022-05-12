package mvvm.sliide.com.networking

import io.reactivex.rxjava3.core.Single
import mvvm.sliide.com.networking.model.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoRestApi {

    @GET(GET_USERS)
    fun getAllUsers(@Query("page") page: Int = 0): Single<UsersResponse>
}
