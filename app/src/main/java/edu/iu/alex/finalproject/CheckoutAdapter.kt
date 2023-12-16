package edu.iu.alex.finalproject

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CheckoutAdapter(var orders: List<List<String>>) :
    RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder>() {

    class CheckoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.textViewItemName)
        val itemQuantity: TextView = view.findViewById(R.id.textViewQuantity)
        val itemPrice: TextView = view.findViewById(R.id.textViewItemPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.checkout_item, parent, false)
        return CheckoutViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
        val order = orders[position]
        holder.itemName.text = order[0]
        holder.itemQuantity.text = "Quantity: ${order[1]}"
        holder.itemPrice.text = "Price: ${order[2]}"
    }

    override fun getItemCount() = orders.size

    fun updateOrders(newOrders: List<List<String>>) {
        orders = newOrders
        notifyDataSetChanged()
    }
}
