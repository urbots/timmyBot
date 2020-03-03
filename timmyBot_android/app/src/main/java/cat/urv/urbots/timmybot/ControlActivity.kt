package cat.urv.urbots.timmybot

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class ControlActivity: AppCompatActivity() {

    companion object {
        val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        lateinit var progressBar: ProgressBar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        progressBar = findViewById(R.id.progressBar);
        val device: BluetoothDevice = intent.extras!!.getParcelable(MainActivity.EXTRA_DEVICE)!!

        println(device.name)

    }

}