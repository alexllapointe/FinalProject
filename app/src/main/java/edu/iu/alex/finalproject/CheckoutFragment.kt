package edu.iu.alex.finalproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CheckoutFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var checkoutAdapter: CheckoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.checkout_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter with an empty list
        checkoutAdapter = CheckoutAdapter(emptyList())

        // Setup RecyclerView
        val recyclerViewCheckout = view.findViewById<RecyclerView>(R.id.recyclerViewCheckout)
        recyclerViewCheckout.adapter = checkoutAdapter
        recyclerViewCheckout.layoutManager = LinearLayoutManager(context)

        // Observe orders data from ViewModel
        mainViewModel.orders.observe(viewLifecycleOwner) { orders ->
            checkoutAdapter.updateOrders(orders)
        }

        val placeOrderButton = view.findViewById<Button>(R.id.buttonCheckout)
        val deliveryAddress = view.findViewById<EditText>(R.id.editTextDeliveryAddress)
        val specialInstructions = view.findViewById<EditText>(R.id.editTextSpecialInstructions)


        placeOrderButton.setOnClickListener {
            // Retrieve data from the EditText fields and RecyclerView
            val deliveryAddressInfo = deliveryAddress.text.toString()
            val specialInstructionsInfo = specialInstructions.text.toString()
            val orders = checkoutAdapter.orders

            // Create a list of lists to represent order data
            val orderData = mutableListOf<List<String>>()
            orderData.add(listOf(deliveryAddressInfo, specialInstructionsInfo))

            // Update the MainViewModel with the order data
            mainViewModel.placeOrder(orderData)

            // Navigate to the OrderFragment
            findNavController().navigate(R.id.checkoutFragment_to_orderFragment)
        }

    }
}
