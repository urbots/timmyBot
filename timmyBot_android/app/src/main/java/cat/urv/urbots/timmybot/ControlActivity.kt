package cat.urv.urbots.timmybot

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import kotlinx.android.synthetic.main.activity_control.*

class ControlActivity: AppCompatActivity() {

    companion object {
        lateinit var device: BluetoothDevice
        lateinit var bluetoothService: BluetoothService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        device = intent.extras!!.getParcelable(MainActivity.EXTRA_DEVICE)!!

        bluetoothService = BluetoothService(device)
        bluetoothService.connect()

        joystick.setOnMoveListener { angle, strength ->

            val angleRadians = Math.toRadians(angle.toDouble())
            val x = (strength * kotlin.math.cos(angleRadians)) / 100
            val y = (strength * kotlin.math.sin(angleRadians)) / 100

            val data:String = String.format("%.2f#%.2f#\r", y, x)

            bluetoothService.write(data.toByteArray())
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        bluetoothService.disconnect()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothService.disconnect()
        finish()
    }

}