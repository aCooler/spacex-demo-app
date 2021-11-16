package com.example.spacex_demo_app

import android.os.Handler
import android.os.Message
import com.example.spacex_demo_app.api.Client
import org.junit.Test

import org.junit.Assert.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Test
    fun `test_is_true_if_everything_went_fine`(){
        val handler: Handler = object : Handler() {
            override fun handleMessage(msg: Message) {
               assert(msg.data.containsKey("launch"))
            }
        }
        val client = Client(handler)
        client.initialise()
    }

}