package mvvm.sliide.com.presentation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mvvm.sliide.com.R
import mvvm.sliide.com.databinding.FragmentUserListBinding
import mvvm.sliide.com.presentation.users.model.UserDataModel

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private val userListViewModel: UserListViewModel by viewModels()
    private lateinit var binding: FragmentUserListBinding
    private val userListAdapter by lazy {
        UserListAdapter {
            showDeleteUserWarning(it)
        }
    }

    private val observer = Observer<UserListViewModel.Event> { event ->
        when (event) {
            is UserListViewModel.Event.Success -> onSuccess(event.listOfUsers)
            UserListViewModel.Event.Error -> showError()
            UserListViewModel.Event.UserDeleteSuccess -> updateUserList()
            UserListViewModel.Event.UserDeleteError -> showUserDeleteError()
            UserListViewModel.Event.SuccessEmptyList -> onEmptyListSuccess()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userListViewModel.onEvent.observe(viewLifecycleOwner, observer)
        userListViewModel.getAllUsers()
        onAddUserClickListener()
    }

    private fun onSuccess(listOfUsers: List<UserDataModel>) {
        with(binding) {
            userListRecyclerView.layoutManager = LinearLayoutManager(activity)
            userListAdapter.allUsersList.clear()
            userListRecyclerView.adapter = userListAdapter
            userListAdapter.allUsersList.addAll(listOfUsers)
        }
    }

    private fun showError() {
        Toast.makeText(
            activity,
            getString(R.string.user_list_general_error_text),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showDeleteUserWarning(id: Long) {
        val dialog = context?.let {
            AlertDialog.Builder(it).setTitle(getString(R.string.user_list_dialog_title_text))
                .setPositiveButton(
                    getString(R.string.user_list_dialog_positive_button_text)
                ) { view, _ ->
                    userListViewModel.deleteUser(id)
                    view.dismiss()
                }
        }
        dialog?.show()
    }

    private fun updateUserList() {
        userListAdapter.allUsersList.clear()
        userListViewModel.getAllUsers()
    }

    private fun showUserDeleteError() {
        Toast.makeText(
            activity,
            getString(R.string.user_list_deleted_user_failure),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun onAddUserClickListener() {
        binding.addUserButton.setOnClickListener {
            findNavController().navigate(R.id.userListFragment_to_addUserDialogFragment)
        }
    }

    private fun onEmptyListSuccess() {
        Toast.makeText(
            activity,
            getString(R.string.user_list_empty_text),
            Toast.LENGTH_LONG
        ).show()
    }
}
