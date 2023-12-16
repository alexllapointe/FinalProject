package edu.iu.alex.finalproject

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

class RestaurantAdapter(
    private var restaurantNames: MutableList<String>,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    // Listener for click events
    var onItemClick: (() -> Unit)? = null


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnRestaurant: Button = view.findViewById(R.id.btnRestaurant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_restaurant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurantName = restaurantNames[position]
        val sentRestaurantName = restaurantName.replace(" ", "").lowercase()
        holder.btnRestaurant.text = restaurantName

        holder.btnRestaurant.setOnClickListener {
            Log.d("RestaurantAdapter", "Selected Restaurant: $sentRestaurantName")
            mainViewModel.selectRestaurant(sentRestaurantName)
            it.findNavController().navigate(R.id.homeFragment_to_restaurantFragment)
        }
    }

    fun updateData(newData: List<String>) {
        Log.d("RestaurantAdapter", "Updating data with ${newData.size} items")
        restaurantNames.clear()
        restaurantNames.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount() = restaurantNames.size
}
