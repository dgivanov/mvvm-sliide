package mvvm.sliide.com.presentation.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mvvm.sliide.com.databinding.ItemUserListBinding
import mvvm.sliide.com.presentation.users.model.UserDataModel

class UserListAdapter(private val onLongItemClick: (Long) -> Unit) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    var allUsersList = mutableListOf<UserDataModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onLongItemClick
    )

    override fun getItemCount() = allUsersList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = allUsersList[position]
        holder.bind(user)
    }

    class ViewHolder(private val binding: ItemUserListBinding, private val onLongItemClick: (Long) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userDataModel: UserDataModel) {
            with(binding) {
                nameTextView.text = userDataModel.name
                emailTextView.text = userDataModel.email
            }

            binding.root.setOnLongClickListener {
                onLongItemClick(userDataModel.id)
                true
            }
        }
    }
}
