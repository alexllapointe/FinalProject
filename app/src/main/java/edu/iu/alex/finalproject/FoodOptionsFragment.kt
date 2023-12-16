package edu.iu.alex.finalproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class FoodOptionsFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment and return the View
        return inflater.inflate(R.layout.foodoptions_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup the menu items
        val menuItems = listOf(
            Pair(view.findViewById<View>(R.id.include_burger), "Burger"),
            Pair(view?.findViewById<View>(R.id.include_pizza), "Pizza"),
            Pair(view?.findViewById<View>(R.id.include_fries), "Fries")
        )

        menuItems.forEach { (itemView, itemName) ->
            val textViewItemName = itemView.findViewById<TextView>(R.id.textViewItemName)
            textViewItemName.text = itemName

            val textViewQuantity = itemView.findViewById<TextView>(R.id.textViewQuantity)
            val buttonDecrement = itemView.findViewById<Button>(R.id.btnMinus)
            val buttonIncrement = itemView.findViewById<Button>(R.id.btnPlus)

            buttonDecrement.setOnClickListener {
                val currentQuantity = textViewQuantity.text.toString().toIntOrNull() ?: 0
                if (currentQuantity > 0) {
                    textViewQuantity.text = (currentQuantity - 1).toString()
                }
            }

            buttonIncrement.setOnClickListener {
                val currentQuantity = textViewQuantity.text.toString().toIntOrNull() ?: 0
                textViewQuantity.text = (currentQuantity + 1).toString()
            }
        }


        val checkoutButton = view.findViewById<Button>(R.id.buttonCheckout)
        checkoutButton.setOnClickListener {

            val ordersData = collectOrdersData()

            mainViewModel.setOrders(ordersData)

            findNavController().navigate(R.id.restaurantFragment_to_checkoutFragment)
        }
    }

    private fun collectOrdersData(): List<List<String>> {
        val burgerView = view?.findViewById<View>(R.id.include_burger)
        val pizzaView = view?.findViewById<View>(R.id.include_pizza)
        val friesView = view?.findViewById<View>(R.id.include_fries)

        val orderData = listOfNotNull(
            burgerView?.let { collectOrderDataFromItem(it, "Burger") },
            pizzaView?.let { collectOrderDataFromItem(it, "Pizza") },
            friesView?.let { collectOrderDataFromItem(it, "Fries") }
        )

        return orderData.filterNot { it[1] == "0" }
    }

    private fun collectOrderDataFromItem(itemView: View, itemName: String): List<String> {
        val textViewQuantity = itemView.findViewById<TextView>(R.id.textViewQuantity)
        val quantity = textViewQuantity.text.toString()
        val price = calculatePrice(itemName, quantity.toInt())
        return listOf(itemName, quantity, price)
    }

    private fun calculatePrice(itemName: String, quantity: Int): String {
        val pricePerItem = when (itemName) {
            "Burger" -> 5
            "Pizza" -> 10
            "Fries" -> 3
            else -> 0
        }
        return "$${pricePerItem * quantity}"
    }
}

