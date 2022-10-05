package com.example.shoplist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.shoplist.R
import com.example.shoplist.databinding.ShopItemBinding
import com.example.shoplist.databinding.ShopItemCheckedBinding
import com.example.shoplist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopListViewHolder>(ShopItemDiffCallback()) {

    var onLongClickListener: ((ShopItem) -> Unit)? = null
    var onUsualClickListener: ((ShopItem) -> Unit)? = null

    companion object {
        const val USUAL_TYPE = 1
        const val CHECKED_TYPE = 2
        const val MAX_POOL_SIZE = 15
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.isChecked) {
            CHECKED_TYPE
        } else {
            USUAL_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val layoutResId = if (viewType == USUAL_TYPE) {
            R.layout.shop_item
        } else {
            R.layout.shop_item_checked
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context),
            layoutResId,
            parent, false)
        return ShopListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding
        when(binding) {
            is ShopItemCheckedBinding -> {
                binding.textViewName.text = shopItem.name
                binding.textViewCount.text = shopItem.count.toString()
            }
            is ShopItemBinding -> {
                binding.textViewName.text = shopItem.name
                binding.textViewCount.text = shopItem.count.toString()
            }
        }
        binding.root.setOnLongClickListener {
            onLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            onUsualClickListener?.invoke(shopItem)
        }
    }
}