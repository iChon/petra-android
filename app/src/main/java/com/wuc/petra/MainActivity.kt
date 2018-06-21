package com.wuc.petra

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wuc.petra.viewinject.InjectTestAptActivity
import com.wuc.petra.viewinject.InjectTestReflectActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_inject_reflect.setOnClickListener {
            startActivity<InjectTestReflectActivity>()
        }
        btn_inject_apt.setOnClickListener {
            startActivity<InjectTestAptActivity>()
        }
    }

}
