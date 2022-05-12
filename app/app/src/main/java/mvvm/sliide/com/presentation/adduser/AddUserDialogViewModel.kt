package mvvm.sliide.com.presentation.adduser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import mvvm.sliide.com.domain.usecase.UserUseCase
import javax.inject.Inject

@HiltViewModel
class AddUserDialogViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val _onEvent = MutableLiveData<Event>()

    val event: LiveData<Event>
        get() = _onEvent

    fun createUser(name: String?, email: String?) {
        when {
            name.isNullOrBlank() -> _onEvent.value = Event.EmptyName
            email.isNullOrBlank() -> _onEvent.value = Event.EmptyEmail
            else -> {
                disposable.add(
                    userUseCase.addUser(name, email)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            _onEvent.value = Event.Success
                        }, {
                            _onEvent.value = Event.Error
                        })
                )
            }
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    sealed class Event {
        object Success : Event()
        object Error : Event()
        object EmptyName : Event()
        object EmptyEmail : Event()
    }
}
