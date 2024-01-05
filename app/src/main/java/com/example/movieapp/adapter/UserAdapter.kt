package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R

import com.example.movieapp.data.User

class UserAdapter(private val userList: List<User>?) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    var onItemClickUser: (User) -> Unit = { a ->
    }
    var onClickDeleteUser: (User) -> Unit = { User ->
    }

    inner class UserViewHolder(viewUser: View) : RecyclerView.ViewHolder(viewUser) {
        val tvDisplayPassword = viewUser.findViewById<TextView>(R.id.tvDisplayPassword)
        val tvDisplayUserName = viewUser.findViewById<TextView>(R.id.tvDisplayUserName)
        val tvDisplayTotalSpending = viewUser.findViewById<TextView>(R.id.tvDisplayTotalSpendingUser)
        val tvDisplatMoneyUser = viewUser.findViewById<TextView>(R.id.tvDisplayMoneyUser)
        val btnDeleteUser = viewUser.findViewById<ImageButton>(R.id.btnDeleteUser)
        init {
            viewUser.rootView.setOnClickListener {
                onItemClickUser.invoke(userList?.get(position) ?: User(id = 0, emailName = "", password = "", phoneNumber = "", nickname = ""))
            }

            btnDeleteUser.setOnClickListener {
                onClickDeleteUser.invoke(
                    userList?.get(layoutPosition) ?: User(id = 0, emailName = "", password = "", phoneNumber = "",nickname = "")
                )
            }
        }

        fun onBindUser(user: User) {
            tvDisplatMoneyUser.text = user.money.toString() + " đ"
            tvDisplayTotalSpending.text = user.totalSpending.toString() + " đ"
            tvDisplayUserName.text = user.emailName
            tvDisplayPassword.text = user.password
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewUser = inflater.inflate(R.layout.item_user, parent, false)
        return UserViewHolder(viewUser)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.onBindUser(userList?.get(position) ?: User(id = 0, emailName = "", password = "", phoneNumber = "",nickname = ""))
    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }
}