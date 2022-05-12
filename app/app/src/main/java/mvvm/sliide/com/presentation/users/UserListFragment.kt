package mvvm.sliide.com.presentation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import mvvm.sliide.com.databinding.FragmentUserListBinding

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private val userListViewModel: UserListViewModel by activityViewModels()
    private lateinit var binding: FragmentUserListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }
}
