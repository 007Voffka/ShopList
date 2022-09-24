package com.example.shoplist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var floatingButton: FloatingActionButton

    private var fragmentContainerView : FragmentContainerView? = null

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

    private fun isOnePaneMode() : Boolean {
        fragmentContainerView = findViewById(R.id.shop_item_container)
        return fragmentContainerView == null
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

    private fun setupFragment(id: Int = NO_ID) {
        val fragment = ShopItemFragment.newInstanceAddItem(id)
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val NO_ID = -1
    }

    private fun setupOnClickListeners() {
        shopListAdapter.onUsualClickListener = {
            if(isOnePaneMode()) {
                val intent = EditShopItemActivity.newIntent(this, it.id)
                startActivity(intent)
            } else {
                setupFragment(it.id)
            }
        }
        if(isOnePaneMode()) {
            floatingButton.setOnClickListener {
                val intent = EditShopItemActivity.newIntent(this)
                startActivity(intent)
            }
        } else {
            floatingButton.setOnClickListener {
                setupFragment()
            }
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

    override fun onEditFinish() {
        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}