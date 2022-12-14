package com.example.shoplist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.databinding.ActivityMainBinding
import com.example.shoplist.presentation.rw.ShopListAdapter
import com.example.shoplist.presentation.view_models.MainViewModel
import com.example.shoplist.presentation.view_models.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var viewModel : MainViewModel

    private var _binding : ActivityMainBinding? = null
    private val binding : ActivityMainBinding
    get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        (application as ShopListApp).component.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        setContentView(binding.root)
        setupRecyclerView()
        viewModel.getShopList().observe(this) {
            shopListAdapter.submitList(it)
        }
    }

    private fun isOnePaneMode() : Boolean {
        return binding.shopItemContainer == null
    }

    private fun setupRecyclerView() {
        with(binding.recyclerViewShopItemList) {
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
        setupOnSwipeListener(binding.recyclerViewShopItemList)
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
            binding.buttonAddShopItem.setOnClickListener {
                val intent = EditShopItemActivity.newIntent(this)
                startActivity(intent)
            }
        } else {
            binding.buttonAddShopItem.setOnClickListener {
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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}