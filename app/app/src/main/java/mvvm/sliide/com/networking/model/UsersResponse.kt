package mvvm.sliide.com.networking.model

data class UsersResponse(
    val code: Int,
    val meta: Meta,
    val data: List<User>
)

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val gender: String,
    val status: String,
)

data class Meta(
    val pagination: Pagination
)

data class Pagination(
    val total: Int,
    val pages: Int,
    val page: Int,
    val limit: Int
)
