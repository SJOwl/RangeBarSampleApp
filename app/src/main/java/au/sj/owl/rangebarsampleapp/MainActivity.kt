package au.sj.owl.rangebarsampleapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.bar1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bar1.setRangeChangeListener { left, right ->
            Log.d("jsp", "left: $left, right: $right")
        }
        // todo change params from code - they should change
    }
}
