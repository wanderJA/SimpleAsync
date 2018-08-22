package com.wander.simpleasync.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.wander.simpleasync.R

class MainActivity : AppCompatActivity(),View.OnClickListener {
    override fun onClick(v: View?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AsyncUtil.executorSingle()
    }
}
