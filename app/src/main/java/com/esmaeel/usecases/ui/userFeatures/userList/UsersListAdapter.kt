package com.esmaeel.usecases.ui.userFeatures.userList

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esmaeel.usecases.databinding.UserItemBinding
import com.esmaeel.usecases.ui.userFeatures.User
import com.esmaeel.usecases.utils.ktx.layoutInflator

class UsersListAdapter(
    private val onItemClicked: (item: User, position: Int) -> Unit,
) : ListAdapter<User, UsersListAdapter.UserViewHolder>(ItemDiffUtil) {

    private object ItemDiffUtil : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem == newItem
        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(UserItemBinding.inflate(parent.layoutInflator, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binder: UserItemBinding) :
        RecyclerView.ViewHolder(binder.root) {

        fun bind(item: User) = with(binder) {
            root.setOnClickListener { onItemClicked(item, adapterPosition) }
            user = item
            executePendingBindings()
        }
    }

}