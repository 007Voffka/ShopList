package com.example.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoplist.R

class EditShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_shop_item)
        if(savedInstanceState == null) {
            launchFragment()
        }
    }

    companion object {
        private const val EXTRA_SHOP_ITEM_ID = "shopItemExtra"
        private const val NO_ID = -1

        fun newIntent(context: Context, id: Int = NO_ID) : Intent {
            val intent = Intent(context, EditShopItemActivity::class.java)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, id)
            return intent
        }
    }

    private fun launchFragment() {
        if(intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
            val fragment = if(intent.getIntExtra(EXTRA_SHOP_ITEM_ID, NO_ID) == NO_ID) {
                ShopItemFragment.newInstanceAddItem(NO_ID)
            } else {
                ShopItemFragment.newInstanceAddItem(intent.getIntExtra(EXTRA_SHOP_ITEM_ID, 0))
            }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_fragment_container, fragment)
            .commit()
        } else {
            throw RuntimeException("Error intent extra")
        }
    }

    override fun onEditFinish() {
        finish()
    }
}