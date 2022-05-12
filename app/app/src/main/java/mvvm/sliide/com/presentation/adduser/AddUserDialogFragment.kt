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
import mvvm.sliide.com.databinding.DialogFragmentAddUserBinding

@AndroidEntryPoint
class AddUserDialogFragment : Fragment() {

    private val addUserDialogViewModel: AddUserDialogViewModel by viewModels()
    private lateinit var binding: DialogFragmentAddUserBinding

    private val onEventObserver = Observer<AddUserDialogViewModel.Event> {
        when (it) {
            AddUserDialogViewModel.Event.Success -> findNavController().popBackStack()
            AddUserDialogViewModel.Event.Error -> showError(getString(R.string.add_user_error_text))
            AddUserDialogViewModel.Event.EmptyEmail -> showError(getString(R.string.add_user_empty_email_error_text))
            AddUserDialogViewModel.Event.EmptyName -> showError(getString(R.string.add_user_empty_name_error_Text))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addUserDialogViewModel.event.observe(viewLifecycleOwner, onEventObserver)
        with(binding) {
            createUserButton.setOnClickListener {
                addUserDialogViewModel.createUser(
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
