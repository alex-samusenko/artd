package ru.artd.warehouse.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel: ViewModel() {
    val currentFragment = MutableLiveData<String>()
    val useInternalCamera = MutableLiveData<Boolean>()
}