package cat.urv.urbots.timmybot

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException

private const val TAG = "TIMMY_DEBUG_TAG"

class BluetoothService(private val device: BluetoothDevice) {

    fun connect(){
        ConnectThread(device).run()
    }

    fun disconnect(){
        ConnectThread(device).cancel()
    }

    private inner class ConnectThread(device: BluetoothDevice) : Thread() {

        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(ControlActivity.MY_UUID)
        }

        override fun run() {
            mmSocket?.use { socket ->
                socket.connect()
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