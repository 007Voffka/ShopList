package com.example.shoplist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var viewModel: AddItemViewModel
    private lateinit var editTextName: EditText
    private lateinit var editTextCount: EditText
    private lateinit var button: Button
    private lateinit var editNameInputLayout: TextInputLayout
    private lateinit var editCountInputLayout: TextInputLayout

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
    ): View? {
        return inflater.inflate(R.layout.edit_shop_item_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        observeViewModels()
        setListeners()
    }

    private fun initViews(view : View) {
        viewModel = ViewModelProvider(this)[AddItemViewModel::class.java]
        editTextName = view.findViewById(R.id.editTextNameEditActivity)
        editTextCount = view.findViewById(R.id.editTextCountEditActivity)
        button = view.findViewById(R.id.buttonEditItem)
        editNameInputLayout = view.findViewById(R.id.textInputLayoutName)
        editCountInputLayout = view.findViewById(R.id.textInputLayoutCount)
    }

    private fun observeViewModels() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            if(it == true) {
                editCountInputLayout.error = getString(R.string.invalid_number_error)
            } else {
                editCountInputLayout.error = null
            }
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if(it == true) {
                editNameInputLayout.error = getString(R.string.invalid_text_error)
            }
            else {
                editNameInputLayout.error = null
            }
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            if(it == true) {
                onEditingFinishListener.onEditFinish()
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
                    button.text = getString(R.string.add)
                    button.setOnClickListener {
                        viewModel.addShopItem(editTextName.text, editTextCount.text)
                    }
                } else {
                    viewModel.getShopItem(args.getInt(ARGUMENT_ID)).observe(viewLifecycleOwner) {
                        editTextName.setText(it.name)
                        editTextCount.setText(it.count.toString())
                        setColorChecked(it.isChecked)
                        button.text = getString(R.string.edit)
                        button.setOnClickListener { _ ->
                            viewModel.editShopItem(id = it.id, name = editTextName.text,
                                count = editTextCount.text, isChecked = it.isChecked)
                        }
                    }
                }
            }
        }

    }

    private fun setTextChangedListeners() {
        editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.textIsWriting()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        editTextCount.addTextChangedListener(object : TextWatcher {
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
            editCountInputLayout.background = ContextCompat.getDrawable(it,
                R.color.item_background_checked)
            editNameInputLayout.background = ContextCompat.getDrawable(it,
                R.color.item_background_checked)
            editTextName.background = ContextCompat.getDrawable(it,
                R.color.item_background_checked)
            editTextCount.background = ContextCompat.getDrawable(it,
                R.color.item_background_checked)
        } else {
            editCountInputLayout.background = ContextCompat.getDrawable(it,
                R.color.item_background)
            editNameInputLayout.background = ContextCompat.getDrawable(it,
                R.color.item_background)
            editTextName.background = ContextCompat.getDrawable(it,
                R.color.item_background)
            editTextCount.background = ContextCompat.getDrawable(it,
                R.color.item_background)
        }
        }
    }
}