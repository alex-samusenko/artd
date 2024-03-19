package ru.artd.warehouse.ui.cells

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.preference.PreferenceManager
import ru.artd.warehouse.R
import ru.artd.warehouse.ui.scanner.ScannerActivity
import ru.artd.warehouse.databinding.FragmentCellsBinding
import ru.artd.warehouse.ui.settings.SettingsViewModel

class CellsFragment : Fragment() {
    private lateinit var binding: FragmentCellsBinding
    //private val viewModel: CellsViewModel by viewModels()
    private val settings: SettingsViewModel by activityViewModels()

    companion object {
        fun newInstance() = CellsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCellsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settings.currentFragment.value = this::class.java.name

        val textView: AutoCompleteTextView = binding.warehouse
        val countries: Array<out String> = resources.getStringArray(R.array.warehouses_array)
        ArrayAdapter(view.context, android.R.layout.simple_list_item_1, countries).also { adapter ->
            textView.setAdapter(adapter)
        }
        val preferences = activity?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        if (preferences!!.getBoolean("camera", false))
            binding.cell.setImeOptions(EditorInfo.IME_ACTION_GO)
        else
            binding.cell.setImeOptions(EditorInfo.IME_ACTION_SEARCH)
        settings.useInternalCamera.observe(activity as LifecycleOwner) {
            if (it) binding.cell.setImeOptions(EditorInfo.IME_ACTION_SEARCH)
            else binding.cell.setImeOptions(EditorInfo.IME_ACTION_GO)
        }

        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent != null)
                    binding.cell.setText(intent.getStringExtra("code"))
            }
        }

        binding.cell.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    startForResult.launch(Intent(view.context, ScannerActivity::class.java))
                    true
                }
                else -> {
                    false
                }
            }

        }
        //view.let { checkCameraPermission(it) }
    }

}