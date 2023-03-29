package com.syafei.gitconnect.ui.details.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.syafei.gitconnect.core.R
import com.syafei.gitconnect.core.domain.model.GitUser
import com.syafei.gitconnect.databinding.ListItemFollowBinding

class FollowsAdapter : ListAdapter<GitUser, FollowsAdapter.AdapterViewHolder>(DIFFUTILS) {

    private var onItemClickCallBack: OnItemClickCallBack? = null
    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val binding =
            ListItemFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val positionItem = getItem(position)

        holder.binding.apply {
            tvItemName.text = positionItem.login
            tvItemUsernames.text = positionItem.htmlUrl

            Glide.with(holder.itemView.context).load(positionItem.avatarUrl).circleCrop().apply(
                    RequestOptions.placeholderOf(
                        R.drawable.baseline_refresh_24
                    ).error(R.drawable.baseline_broken_image_24)
                ).into(ivListItemProfile)
        }

        holder.itemView.setOnClickListener {
            if (positionItem != null) {
                onItemClickCallBack?.onItemClicked(positionItem)
            }
        }

    }

    inner class AdapterViewHolder(var binding: ListItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallBack {
        fun onItemClicked(data: GitUser)
    }

    object DIFFUTILS : DiffUtil.ItemCallback<GitUser>() {
        // DiffUtil uses this test to help discover if an item was added, removed, or moved.
        override fun areItemsTheSame(oldItem: GitUser, newItem: GitUser): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GitUser, newItem: GitUser): Boolean {
            return oldItem == newItem
        }
    }

}