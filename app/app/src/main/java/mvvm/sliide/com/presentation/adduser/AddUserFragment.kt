package mvvm.sliide.com.presentation.adduser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mvvm.sliide.com.R
import mvvm.sliide.com.databinding.FragmentAddUserBinding

@AndroidEntryPoint
class AddUserFragment : Fragment() {

    private val addUserViewModel: AddUserViewModel by viewModels()
    private lateinit var binding: FragmentAddUserBinding

    private val onEventObserver = Observer<AddUserViewModel.Event> {
        when (it) {
            AddUserViewModel.Event.Success -> findNavController().popBackStack()
            AddUserViewModel.Event.Error -> showError(getString(R.string.add_user_error_text))
            AddUserViewModel.Event.EmptyEmail -> showError(getString(R.string.add_user_empty_email_error_text))
            AddUserViewModel.Event.EmptyName -> showError(getString(R.string.add_user_empty_name_error_Text))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addUserViewModel.event.observe(viewLifecycleOwner, onEventObserver)
        with(binding) {
            createUserButton.setOnClickListener {
                addUserViewModel.createUser(
                    nameEditText.text.toString(),
                    emailEditText.text.toString()
                )
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(
            activity,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}
