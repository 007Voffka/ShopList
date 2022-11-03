package com.example.shoplist.di

import androidx.lifecycle.ViewModel
import com.example.shoplist.presentation.view_models.AddItemViewModel
import com.example.shoplist.presentation.view_models.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(impl : MainViewModel) : ViewModel

    @IntoMap
    @ViewModelKey(AddItemViewModel::class)
    @Binds
    fun bindAddItemViewModel(impl : AddItemViewModel) : ViewModel
}