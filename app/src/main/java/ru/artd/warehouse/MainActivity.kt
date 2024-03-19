package ru.artd.warehouse

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import ru.artd.warehouse.databinding.ActivityMainBinding
import ru.artd.warehouse.ui.cells.CellsFragment
import ru.artd.warehouse.ui.settings.SettingsFragment
import ru.artd.warehouse.ui.settings.SettingsViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val settings: SettingsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        settings.currentFragment.observe(this) {
            supportActionBar?.title = getString(R.string.app_name)
            when (it) {
                SettingsFragment::class.java.name -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    supportActionBar?.title = getString(R.string.action_settings)
                    supportActionBar?.hide()
                }

                CellsFragment::class.java.name -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    supportActionBar?.show()
                }
            }
        }

        /*val textView: AutoCompleteTextView = findViewById(R.id.warehouse)
        val countries: Array<out String> = resources.getStringArray(R.array.countries_array)
        ArrayAdapter(this, android.R.layout.activity_list_item, countries).also { adapter ->
            textView.setAdapter(adapter)
        }*/

        openFragment(CellsFragment.newInstance())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                openFragment(CellsFragment.newInstance())
            R.id.action_settings ->
                openFragment(SettingsFragment.newInstance())
        }
        return true
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.place_holder, fragment)
            .commit()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 11) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Log.d("MyApp", "Access denied by user")
                //startActivity(Intent(this, ScannerActivity::class.java))
            }
        }
    }

}