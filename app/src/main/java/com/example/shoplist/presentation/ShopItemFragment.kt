package com.example.shoplist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.databinding.EditShopItemFragmentBinding

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: AddItemViewModel

    private var _binding: EditShopItemFragmentBinding? = null
    private val binding: EditShopItemFragmentBinding
    get() = _binding ?: throw RuntimeException("EditShopItemFragmentBinding == null")

    private lateinit var onEditingFinishListener : OnEditingFinishListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnEditingFinishListener) {
            onEditingFinishListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditShopItemFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AddItemViewModel::class.java]
        observeViewModels()
        setListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observeViewModels() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            if(it == true) {
                onEditingFinishListener.onEditFinish()
            }
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if(it) {
                binding.textInputLayoutName.error = getString(R.string.invalid_text_error)
            } else {
                binding.textInputLayoutName.error = null
            }
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            if(it) {
                binding.textInputLayoutCount.error = getString(R.string.invalid_number_error)
            } else {
                binding.textInputLayoutCount.error = null
            }
        }
    }

    companion object {
        private const val NO_ID = -1
        private const val ARGUMENT_ID = "argument_id"

        fun newInstanceAddItem(id : Int = NO_ID) : ShopItemFragment {
           return ShopItemFragment().apply {
               arguments = Bundle().apply {
                   putInt(ARGUMENT_ID, id)
               }
           }
        }
    }

    private fun setListeners() {
        setTextChangedListeners()
        val arguments = arguments
        arguments?.let { args ->
            if(args.containsKey(ARGUMENT_ID)) {
                if(args.getInt(ARGUMENT_ID) == NO_ID) {
                    binding.buttonEditItem.text = getString(R.string.add)
                    binding.buttonEditItem.setOnClickListener {
                            binding.editTextNameEditActivity.text?.let { name ->
                                binding.editTextCountEditActivity.text?.let { count ->
                                    viewModel.addShopItem(
                                        name,
                                        count
                                    )
                                }
                            }
                    }
                } else {
                    viewModel.shopItem.observe(viewLifecycleOwner) { shopItem ->
                        binding.editTextNameEditActivity.setText(shopItem.name)
                        binding.editTextCountEditActivity.setText(shopItem.count.toString())
                        setColorChecked(shopItem.isChecked)
                        binding.buttonEditItem.text = getString(R.string.edit)
                        binding.buttonEditItem.setOnClickListener {
                            binding.editTextNameEditActivity.text?.let { name ->
                                binding.editTextCountEditActivity.text?.let { count ->
                                    viewModel.editShopItem(id = shopItem.id, name = name,
                                        count = count, isChecked = shopItem.isChecked)
                                }
                            }
                        }
                    }
                    viewModel.getShopItem(args.getInt(ARGUMENT_ID))
                    }
                }
            }

    }

    private fun setTextChangedListeners() {
        binding.editTextNameEditActivity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.textIsWriting()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.editTextCountEditActivity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.textIsWriting()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    interface OnEditingFinishListener {
        fun onEditFinish()
    }

    private fun setColorChecked(isChecked : Boolean) {
        context?.let {
            if(isChecked) {
            binding.textInputLayoutCount.background = ContextCompat.getDrawable(it,
                R.color.item_background_checked)
            binding.textInputLayoutName.background = ContextCompat.getDrawable(it,
                R.color.item_background_checked)
            binding.editTextNameEditActivity.background = ContextCompat.getDrawable(it,
                R.color.item_background_checked)
            binding.editTextCountEditActivity.background = ContextCompat.getDrawable(it,
                R.color.item_background_checked)
        } else {
            binding.textInputLayoutCount.background = ContextCompat.getDrawable(it,
                R.color.item_background)
                binding.textInputLayoutName.background = ContextCompat.getDrawable(it,
                R.color.item_background)
                binding.editTextNameEditActivity.background = ContextCompat.getDrawable(it,
                R.color.item_background)
                binding.editTextCountEditActivity.background = ContextCompat.getDrawable(it,
                R.color.item_background)
        }
        }
    }
}