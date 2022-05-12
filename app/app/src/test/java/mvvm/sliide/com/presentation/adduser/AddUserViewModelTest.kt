package mvvm.sliide.com.presentation.adduser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import mvvm.sliide.com.domain.usecase.UserUseCase
import mvvm.sliide.com.utils.RxImmediateSchedulerRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AddUserViewModelTest {

    @get:Rule
    var testSchedulerRule = RxImmediateSchedulerRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val userUseCase: UserUseCase = mockk()
    private val observer: Observer<AddUserViewModel.Event> = mockk()

    private val addUserDialogViewModel = AddUserViewModel(userUseCase)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        addUserDialogViewModel.event.observeForever(observer)
    }

    @Test
    fun `createUser Given response is successful Then show success`() {
        every { observer.onChanged(any()) } just Runs
        every { userUseCase.addUser("name", "email") } returns Completable.complete()

        addUserDialogViewModel.createUser("name", "email")

        verify {
            observer.onChanged(AddUserViewModel.Event.Success)
        }
    }

    @Test
    fun `createUser Given response is not successful Then show error`() {
        every { observer.onChanged(any()) } just Runs
        every { userUseCase.addUser("name", "email") } returns Completable.error(Exception())

        addUserDialogViewModel.createUser("name", "email")

        verify {
            observer.onChanged(AddUserViewModel.Event.Error)
        }
    }

    @Test
    fun `createUser Given name is null Then show name error`() {
        every { observer.onChanged(any()) } just Runs

        addUserDialogViewModel.createUser(null, "email")

        verify {
            observer.onChanged(AddUserViewModel.Event.EmptyName)
        }
    }

    @Test
    fun `createUser Given name is empty Then show name error`() {
        every { observer.onChanged(any()) } just Runs

        addUserDialogViewModel.createUser("", "email")

        verify {
            observer.onChanged(AddUserViewModel.Event.EmptyName)
        }
    }

    @Test
    fun `createUser Given email is null Then show email error`() {
        every { observer.onChanged(any()) } just Runs

        addUserDialogViewModel.createUser("name", null)

        verify {
            observer.onChanged(AddUserViewModel.Event.EmptyEmail)
        }
    }

    @Test
    fun `createUser Given email is empty Then show email error`() {
        every { observer.onChanged(any()) } just Runs

        addUserDialogViewModel.createUser("name", "")

        verify {
            observer.onChanged(AddUserViewModel.Event.EmptyEmail)
        }
    }
}
