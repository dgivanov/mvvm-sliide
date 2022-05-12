package mvvm.sliide.com.presentation.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import mvvm.sliide.com.domain.model.User
import mvvm.sliide.com.domain.usecase.UserUseCase
import mvvm.sliide.com.presentation.users.model.UserDataModel
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userUseCase: UserUseCase
): ViewModel() {

    private val _onEvent = MutableLiveData<Event>()
    private val disposable = CompositeDisposable()

    val onEvent: LiveData<Event>
        get() = _onEvent

    fun getAllUsers() {
        disposable.add(
            userUseCase.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { userList -> onUsersSuccess(userList) },
                    { onError() }
                )
        )
    }

    private fun onUsersSuccess(usersList: List<User>) {
        if (usersList.isEmpty()) {
            //TODO - Send a message to the UI and show an error
            return
        }
        val listOfUsers = mutableListOf<UserDataModel>().apply {
            usersList.forEach {
                add(
                    UserDataModel(
                        it.id,
                        it.name,
                        it.email
                    )
                )
            }
        }

        _onEvent.value = Event.Success(listOfUsers)
    }

    private fun onError() {
        _onEvent.value = Event.Error
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    sealed class Event {
        data class Success(val listOfUsers: List<UserDataModel>) : Event()
        object Error : Event()
    }
}
