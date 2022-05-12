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
        _onEvent.value = Event.ShowLoading
        disposable.add(
            userUseCase.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(::hideLoading)
                .subscribe(
                    { userList -> onUsersSuccess(userList) },
                    { onError() }
                )
        )
    }

    fun deleteUser(id: Long) {
        _onEvent.value = Event.ShowLoading
        disposable.add(
            userUseCase.deleteUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(::hideLoading)
                .subscribe({
                    _onEvent.value = Event.UserDeleteSuccess
                }, {
                    _onEvent.value = Event.UserDeleteError
                })
        )
    }

    private fun hideLoading() {
        _onEvent.value = Event.HideLoading
    }

    private fun onUsersSuccess(usersList: List<User>) {
        if (usersList.isEmpty()) {
            _onEvent.value = Event.SuccessEmptyList
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
        object SuccessEmptyList : Event()
        object UserDeleteSuccess : Event()
        object UserDeleteError : Event()
        object Error : Event()
        object ShowLoading : Event()
        object HideLoading : Event()
    }
}
