package edu.iu.alex.finalproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.order_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter with an empty list
        orderAdapter = OrderAdapter(emptyList())

        // Setup RecyclerView
        val recyclerViewOrder = view.findViewById<RecyclerView>(R.id.recyclerViewOrder)
        recyclerViewOrder.adapter = orderAdapter
        recyclerViewOrder.layoutManager = LinearLayoutManager(context)

        // Observe order data from ViewModel
        mainViewModel.orderData.observe(viewLifecycleOwner) { orderData ->
            // Update the adapter with the new order data
            orderAdapter.updateOrders(orderData)
        }
    }
}
