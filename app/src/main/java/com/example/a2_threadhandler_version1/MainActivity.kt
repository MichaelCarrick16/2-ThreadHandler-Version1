package com.example.a2_threadhandler_version1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var threadOne : Thread? = null
    private lateinit var handler: Handler
    companion object{
        public val KEY_WHAT : Int = 101
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initEvents()
    }

    val rb = object : Runnable {
        override fun run() {
            for (i in 0..10){
                try {
                    Thread.sleep(500)
                }catch (e:InterruptedException){
                    Log.e("abc","Thread interrupted")
                    break
                }
//                Log.e("abc",i.toString())
                var message : Message = Message()
                message.what = KEY_WHAT
                message.arg1 = i
                message.target = handler
                message.sendToTarget()
            }
            Log.e("abc","Thread die")
        }
    }

    private fun initEvents() {
        tv_start.setOnClickListener {
            if(threadOne==null|| threadOne!!.isAlive==false){
                threadOne = Thread(rb)
                threadOne!!.start()
            }

        }

        tv_stop.setOnClickListener {
            threadOne!!.interrupt()
        }

        handler = object : Handler(){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if(msg.what== KEY_WHAT){
                    tv_count.setText(msg.arg1.toString())
                }
            }
        }
    }

    private fun initViews() {

    }

}