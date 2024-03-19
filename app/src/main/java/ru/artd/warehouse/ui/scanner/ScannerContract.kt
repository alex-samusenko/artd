package ru.artd.warehouse.ui.scanner

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class ScannerContract  : ActivityResultContract<String, String?>() {
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, ScannerActivity::class.java)
            .putExtra("key", input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }
        return intent?.getStringExtra("key")
    }
}