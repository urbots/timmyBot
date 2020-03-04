package cat.urv.urbots.timmybot

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import kotlinx.android.synthetic.main.activity_control.*
import java.util.*


class ControlActivity: AppCompatActivity() {

    companion object {
        val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        lateinit var device: BluetoothDevice
        lateinit var bluetoothService: BluetoothService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        device = intent.extras!!.getParcelable(MainActivity.EXTRA_DEVICE)!!

        bluetoothService = BluetoothService(device)

        joystick.setOnMoveListener { angle, strength ->
            println(angle)
            println(strength)
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

    override fun onResume() {
        super.onResume()
        bluetoothService.connect()
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