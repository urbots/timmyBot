package cat.urv.urbots.timmybot

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.io.OutputStream
import java.util.*

private const val TAG = "TIMMY_DEBUG_TAG"

class BluetoothService(private val device: BluetoothDevice) {

    private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
        device.createRfcommSocketToServiceRecord(MY_UUID)
    }

    companion object {
        val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }

    fun connect() {
        mmSocket?.connect()
    }

    fun disconnect() {
        try {
            mmSocket?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Could not close the client socket", e)
        }
    }

    fun write(bytes: ByteArray) {
        val mmOutStream: OutputStream? = mmSocket?.outputStream
        try {
            mmOutStream?.write(bytes)
            mmOutStream?.flush()
        } catch (e: IOException) {
            Log.e(TAG, "Error occurred when sending data", e)
            return
        }
    }
}