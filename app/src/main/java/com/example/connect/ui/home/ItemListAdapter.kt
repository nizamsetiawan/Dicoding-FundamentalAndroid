package com.example.connect.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.connect.data.remote.response.ItemResponse
import com.example.connect.databinding.ItemListBinding
import com.example.connect.ui.detailuser.DetailActivity

class ItemListAdapter : ListAdapter <ItemResponse, ItemListAdapter.MyViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemListAdapter.MyViewHolder {
       val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)
    }



    class MyViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users: ItemResponse) {
            Glide.with(binding.imgItemPhoto.context)
                .load(users.avatarUrl)
                .into(binding.imgItemPhoto)
            binding.tvItemlistUsername.text = "${users.login}"
            binding.tvItemlistType.text = "${users.type}"

            binding.root.setOnClickListener{
                getDetailActivity(users)
            }
        }
        private fun getDetailActivity(users: ItemResponse) {
            val intent = Intent(binding.root.context, DetailActivity::class.java)
            intent.putExtra(USERNAME_KEY, users.login)
            intent.putExtra(ID_KEY, users.id)
            intent.putExtra(AVATAR_KEY, users.avatarUrl)
            intent.putExtra(TYPE_KEY, users.type)

            binding.root.context.startActivity(intent)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemResponse>() {
            override fun areItemsTheSame(oldItem: ItemResponse, newItem: ItemResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemResponse, newItem: ItemResponse): Boolean {
                return oldItem == newItem
            }
        }

        const val USERNAME_KEY = "username_data"
        const val ID_KEY = "id_data"
        const val AVATAR_KEY = "avatar_data"
        const val TYPE_KEY = "type"


    }

}

