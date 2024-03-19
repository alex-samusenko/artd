package ru.artd.warehouse.ui.settings

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ru.artd.warehouse.R

class SettingsFragment : PreferenceFragmentCompat() {
    private val settings: SettingsViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        @Suppress("DEPRECATION")
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settings.currentFragment.value = this::class.java.name
        val camera = preferenceManager.findPreference<Preference>("camera")

        camera?.let {preference ->
            preference.setOnPreferenceChangeListener { _, newValue ->
                settings.useInternalCamera.value = newValue as Boolean
                if (newValue)
                if (ContextCompat.checkSelfPermission(view.context,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    activity?.let { it -> ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.CAMERA), 11) }
                }
                true
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }


}