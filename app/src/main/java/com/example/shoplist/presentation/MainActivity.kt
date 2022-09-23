package com.example.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var floatingButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        floatingButton = findViewById(R.id.buttonAddShopItem)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getShopList().observe(this) {
            shopListAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        val recyclerViewShopList = findViewById<RecyclerView>(R.id.recyclerViewShopItemList)
        with(recyclerViewShopList) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.USUAL_TYPE, ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.CHECKED_TYPE, ShopListAdapter.MAX_POOL_SIZE
            )
        }
        setupOnClickListeners()
        setupOnLongClickListener()
        setupOnSwipeListener(recyclerViewShopList)
    }


    private fun setupOnClickListeners() {
        shopListAdapter.onUsualClickListener = {
            val intent = EditShopItemActivity.newIntent(this, it.id)
            startActivity(intent)
        }
        floatingButton.setOnClickListener {
            val intent = EditShopItemActivity.newIntent(this)
            startActivity(intent)
        }
    }

    private fun setupOnSwipeListener(recyclerViewShopList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback
            (0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item.id)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerViewShopList)
    }

    private fun setupOnLongClickListener() {
        shopListAdapter.onLongClickListener = {
            with(it) {
                if (it.isChecked) {
                    viewModel.editShopItem(id, name, count, false)
                } else {
                    viewModel.editShopItem(id, name, count, true)
                }
            }
        }
    }
}