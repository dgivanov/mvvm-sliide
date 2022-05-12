package mvvm.sliide.com.presentation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mvvm.sliide.com.databinding.FragmentUserListBinding
import mvvm.sliide.com.presentation.users.model.UserDataModel

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private val userListViewModel: UserListViewModel by viewModels()
    private lateinit var binding: FragmentUserListBinding
    private val userListAdapter by lazy {
        UserListAdapter {
            //TODO - On click listener
        }
    }

    private val observer = Observer<UserListViewModel.Event> { event ->
        when (event) {
            is UserListViewModel.Event.Success -> onSuccess(event.listOfUsers)
            UserListViewModel.Event.Error -> showError()
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
    }

    private fun onSuccess(listOfUsers: List<UserDataModel>) {
        with(binding) {
            userListRecyclerView.layoutManager = LinearLayoutManager(activity)
            userListRecyclerView.adapter = userListAdapter
            userListAdapter.allUsersList.addAll(listOfUsers.asReversed())
        }
    }

    private fun showError() {

    }
}
