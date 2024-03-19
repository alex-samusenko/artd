package ru.artd.warehouse.ui.scanner

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class ScannerActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {
    private lateinit var scanner: ZBarScannerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        scanner = ZBarScannerView(this)
        setContentView(scanner)
    }

    override fun onPause() {
        super.onPause()
        scanner.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        scanner.setResultHandler(this)
        scanner.startCamera()
    }

    override fun handleResult(result: Result?) {
        Log.d("MyApp", "Result: ${result?.contents}")
        intent.putExtra("code", result?.contents)
        setResult(RESULT_OK, intent)
        finish()
    }
}