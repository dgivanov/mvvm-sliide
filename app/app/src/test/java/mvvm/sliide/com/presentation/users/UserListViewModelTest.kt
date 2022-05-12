package mvvm.sliide.com.presentation.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import mvvm.sliide.com.domain.model.User
import mvvm.sliide.com.domain.usecase.UserUseCase
import mvvm.sliide.com.presentation.users.model.UserDataModel
import mvvm.sliide.com.utils.RxImmediateSchedulerRule

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserListViewModelTest {

    @get:Rule
    var testSchedulerRule = RxImmediateSchedulerRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val userUseCase: UserUseCase = mockk()
    private val observer: Observer<UserListViewModel.Event> = mockk()

    private val userListViewModel = UserListViewModel(userUseCase)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userListViewModel.onEvent.observeForever(observer)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getAllUsers Given response is successful Then show the list of users`() {
        val userList = mutableListOf<User>()
        userList.add(User(1, "name", "email"))
        every { observer.onChanged(any()) } just Runs
        every { userUseCase.getAllUsers() } returns Single.just(userList)

        val listOfUsers = mutableListOf<UserDataModel>().apply {
            userList.forEach {
                add(UserDataModel(it.id, it.name, it.email))
            }
        }

        userListViewModel.getAllUsers()

        verify {
            observer.onChanged(UserListViewModel.Event.Success(listOfUsers))
        }
    }

    @Test
    fun `getAllUsers Given response has empty list Then show empty list message`() {
        val userList = mutableListOf<User>()
        every { observer.onChanged(any()) } just Runs
        every { userUseCase.getAllUsers() } returns Single.just(userList)

        userListViewModel.getAllUsers()

        verify {
            observer.onChanged(UserListViewModel.Event.SuccessEmptyList)
        }
    }

    @Test
    fun `getAllUsers Given response is not successful Then show error`() {
        val exception = Exception()
        every { observer.onChanged(any()) } just Runs
        every { userUseCase.getAllUsers() } returns Single.error(exception)

        userListViewModel.getAllUsers()

        verify {
            observer.onChanged(UserListViewModel.Event.Error)
        }
    }

    @Test
    fun `deleteUser Given response is successful Then show UserDeleteSuccess`() {
        every { userUseCase.deleteUser(1) } returns Completable.complete()
        every { observer.onChanged(any()) } just Runs

        userListViewModel.deleteUser(1)

        verify {
            observer.onChanged(UserListViewModel.Event.UserDeleteSuccess)
        }
    }

    @Test
    fun `deleteUser Given response is not successful Then show UserDeleteError`() {
        every { userUseCase.deleteUser(1) } returns Completable.error(Exception())
        every { observer.onChanged(any()) } just Runs

        userListViewModel.deleteUser(1)

        verify {
            observer.onChanged(UserListViewModel.Event.UserDeleteError)
        }
    }
}
