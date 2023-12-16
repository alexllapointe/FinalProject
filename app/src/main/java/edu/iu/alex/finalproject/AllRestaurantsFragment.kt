package edu.iu.alex.finalproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AllRestaurantsFragment : Fragment() {
    private lateinit var restaurantAdapter: RestaurantAdapter
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.allrestaurants_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.rvAllRestaurants)
        recyclerView.layoutManager = LinearLayoutManager(context)
        restaurantAdapter = RestaurantAdapter(mutableListOf(), mainViewModel)
        recyclerView.adapter = restaurantAdapter

        mainViewModel.fetchAllRestaurants()
        mainViewModel.allRestaurantNames.observe(viewLifecycleOwner) { names ->
            restaurantAdapter.updateData(names)
        }

    }
}
