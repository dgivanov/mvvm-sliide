package mvvm.sliide.com.networking.model

data class CreateUserRequest(
    val name: String,
    val email: String,
    // Added this as default, because without them it was not adding the user
    val gender: String = "Male",
    val status: String = "Active"
)
