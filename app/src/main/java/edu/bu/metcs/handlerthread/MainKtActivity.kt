package edu.bu.metcs.handlerthread

import android.content.Intent
import android.os.*
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import edu.bu.metcs.handlerthread.databinding.ActivityMainBinding
// This is an alternative approach using Executor
class MainKtActivity: AppCompatActivity(),TaskUseExecutor.Callback {

    val TAG = this.javaClass.simpleName

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.displayTextid.text = ""

    }

    // called when clicking the display button
    fun display() {
        // get the delay value from the editview
        var delay: Long = 1
        try {
            delay = binding.delayTimeid.text.toString().toInt().toLong()
        } catch (e: Exception) {
            Log.d(TAG, "invalid number")
        }
        binding.displayTextid.text = """execute for (i = 0; i <${delay * 100000000}; i++) ;"""

       TaskUseExecutor().executeTask(this, delay)
    }

    override fun updateUI(str: String?) {
        binding.displayTextid.text=str
    }

}