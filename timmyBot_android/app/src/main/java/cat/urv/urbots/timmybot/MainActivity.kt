package cat.urv.urbots.timmybot

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private val listDevices : ArrayList<BluetoothDevice> = ArrayList()
    private val listBluetoothAdapter = BluetoothDeviceListAdapter(this, listDevices)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reqPermissions()

        // Register for broadcasts when a device is discovered.
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(receiver, filter)

        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        discover()

        select_device_list.adapter = listBluetoothAdapter
        select_device_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            information_state_txt.setText(R.string.bluetooth_connecting)
            select_device_list.visibility = View.GONE

            val device: BluetoothDevice = listDevices[position]

            val intent = Intent(this, ControlActivity::class.java)
            intent.putExtra(EXTRA_DEVICE, device)
            bluetoothAdapter.cancelDiscovery()

            startActivity(intent)
        }
    }

    private fun reqPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_FINE_LOCATION)
        }
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if(!listDevices.contains(device)){
                        listDevices.add(device)
                        listBluetoothAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun discover() {
        bluetoothAdapter.startDiscovery()
        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter.bondedDevices
        pairedDevices?.forEach { device ->
            listDevices.add(device)
        }
        listBluetoothAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int,  resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(applicationContext, R.string.bluetooth_must_be_enabled, Toast.LENGTH_SHORT).show()
            finish()
        } else {
            discover()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (!(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(applicationContext, R.string.permissions_are_required, Toast.LENGTH_LONG).show()
                    finish()
                }
                return
            }
        }
    }

    override fun onPause() {
        super.onPause()
        bluetoothAdapter.cancelDiscovery()
    }

    override fun onResume() {
        super.onResume()
        select_device_list.visibility = View.VISIBLE
        information_state_txt.setText(R.string.bluetooth_scanning_message)
        bluetoothAdapter.startDiscovery()
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothAdapter.cancelDiscovery()
        unregisterReceiver(receiver)
    }

    companion object {
        private const val REQUEST_ENABLE_BT = 1
        private const val REQUEST_FINE_LOCATION = 2
        const val EXTRA_DEVICE = "DEVICE"
    }
}
