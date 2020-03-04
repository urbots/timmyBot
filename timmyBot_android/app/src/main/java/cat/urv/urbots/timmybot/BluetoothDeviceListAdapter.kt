package cat.urv.urbots.timmybot

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class BluetoothDeviceListAdapter(private var activity: Activity, private var items: ArrayList<BluetoothDevice>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var txtAddress: TextView? = null

        init {
            this.txtName = row?.findViewById(R.id.txtName)
            this.txtAddress = row?.findViewById(R.id.txtAddress)
        }
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_device_adapter, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val device = items[position]
        viewHolder.txtName?.text = device.name
        viewHolder.txtAddress?.text = device.address

        return view as View
    }

    override fun getItem(i: Int): BluetoothDevice {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}