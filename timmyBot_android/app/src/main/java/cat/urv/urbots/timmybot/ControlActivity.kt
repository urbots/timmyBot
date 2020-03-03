package cat.urv.urbots.timmybot

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.util.*

private const val TAG = "TIMMY_DEBUG_TAG"

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

        ConnectThread(device).run()

    }

    private inner class ConnectThread(device: BluetoothDevice) : Thread() {

        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(MY_UUID)
        }

        public override fun run() {

            mmSocket?.use { socket ->
                socket.connect()
                progressBar.visibility = View.GONE
                // The connection attempt succeeded. Perform work associated with
                // the connection in a separate thread.
                //manageMyConnectedSocket(socket)
            }
        }

        // Closes the client socket and causes the thread to finish.
        fun cancel() {
            try {
                mmSocket?.close()
            } catch (e: IOException) {
                Log.e(TAG, "Could not close the client socket", e)
            }
        }
    }

}