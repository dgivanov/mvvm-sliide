package mvvm.sliide.com.data.mapper

import mvvm.sliide.com.domain.model.User
import mvvm.sliide.com.networking.model.UsersResponse

fun UsersResponse.toListOfUsers(): List<User> {
    val usersList = mutableListOf<User>()
    this.data.forEach { user ->
        usersList.add(User(user.id, user.name, user.email))
    }
    return usersList
}
