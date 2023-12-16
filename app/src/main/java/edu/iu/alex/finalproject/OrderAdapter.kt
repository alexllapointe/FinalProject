package edu.iu.alex.finalproject

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderAdapter(private var orders: List<List<String>>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderNumber: TextView = view.findViewById(R.id.textViewOrderNumber)
        val date: TextView = view.findViewById(R.id.textViewDate)
        val time: TextView = view.findViewById(R.id.textViewTime)
        val address: TextView = view.findViewById(R.id.textViewAddress)
        val specialInstructions: TextView = view.findViewById(R.id.textViewSpecialInstructions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_item, parent, false)
        return OrderViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        // Get the current system date and time
        val currentDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        holder.address.text = "Address: ${order[0]}"
        holder.specialInstructions.text = "Special Instructions: ${order[1]}"
    }

    override fun getItemCount() = orders.size

    fun updateOrders(newOrders: List<List<String>>) {
        orders = newOrders
        notifyDataSetChanged()
    }
}

