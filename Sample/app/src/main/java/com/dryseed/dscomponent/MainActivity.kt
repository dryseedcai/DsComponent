package com.dryseed.dscomponent

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.dryseed.common.IAModuleCalculateService
import com.dryseed.dscomponent.api.IModuleService
import com.dryseed.dscomponent.api.ModuleServiceManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service =
            ModuleServiceManager.getInstance().loadService("AModuleCalculateService") as IAModuleCalculateService?
        val service2 = ModuleServiceManager.getInstance().loadService("AModuleCalculateService2") as IModuleService?

        btn1.setOnClickListener {
            service?.showMsg(this)
        }

        btn2.setOnClickListener {
            val res: Int?
            var output = Bundle()
            service2?.onStartCommand("calculate", Bundle().apply { putInt("INPUT", 100) }, output, this)
            res = output.getInt("OUTPUT")
            Toast.makeText(this, "Result:$res", Toast.LENGTH_SHORT).show()
        }

        btn3.setOnClickListener {
            val bView = ModuleServiceManager.getInstance().loadView(this, "BModuleView")
            if (bView != null) {
                Toast.makeText(this, "addView", Toast.LENGTH_SHORT).show()
                rootLayout.addView(bView)
            }
        }
    }

}
