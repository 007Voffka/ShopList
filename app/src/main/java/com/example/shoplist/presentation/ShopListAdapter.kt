package com.example.shoplist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoplist.R
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
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutResId, parent, false)
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.textViewName.text = shopItem.name
        holder.textViewCount.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener {
            onLongClickListener?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onUsualClickListener?.invoke(shopItem)
        }
    }
}