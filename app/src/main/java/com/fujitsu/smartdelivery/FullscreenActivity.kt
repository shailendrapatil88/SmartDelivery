package  com.fujitsu.smartdelivery

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_fullscreen.*
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttException


class FullscreenActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {

        when {
            v?.id == R.id.btn0 -> addDigit("0")
            v?.id == R.id.btn1 -> addDigit("1")
            v?.id == R.id.btn2 -> addDigit("2")
            v?.id == R.id.btn3 -> addDigit("3")
            v?.id == R.id.btn4 -> addDigit("4")
            v?.id == R.id.btn5 -> addDigit("5")
            v?.id == R.id.btn6 -> addDigit("6")
            v?.id == R.id.btn7 -> addDigit("7")
            v?.id == R.id.btn8 -> addDigit("8")
            v?.id == R.id.btn9 -> addDigit("9")
            v?.id == R.id.ivDelete -> removeLastDigit()

        }
    }

    private fun removeLastDigit() {
        val text = StringBuilder(etPin.text.toString())
        if (text.isNotEmpty()) {
            etPin.setText(text.substring(0, text.length - 1))
        }
    }

    private fun addDigit(digit: String) {
        val text = StringBuilder(etPin.text.toString())
        if (text.length < 4) {
            text.append(digit)
        }
        etPin.setText(text)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
//        window.decorView.systemUiVisibility =
//            View.SYSTEM_UI_FLAG_LOW_PROFILE or
//                    View.SYSTEM_UI_FLAG_FULLSCREEN or
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
//                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        btn0.setOnClickListener(this)
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)
        btn7.setOnClickListener(this)
        btn8.setOnClickListener(this)
        btn9.setOnClickListener(this)
        ivDelete.setOnClickListener(this)
        btnLockUnlock.setOnClickListener(this)

        val clientId = MqttClient.generateClientId()
        val client = MqttAndroidClient(
            this.applicationContext, "tcp://broker.hivemq.com:1883",
            clientId
        )

        try {
            val token = client.connect()
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    // We are connected
                    Log.d("MqttClient", "onSuccess")
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("MqttClient", "onFailure")

                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }
}
