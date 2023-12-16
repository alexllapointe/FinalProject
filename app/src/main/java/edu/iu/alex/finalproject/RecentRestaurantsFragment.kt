package edu.iu.alex.finalproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecentRestaurantsFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RestaurantAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recentrestaurants_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvRecentRestaurants)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        adapter = RestaurantAdapter(mutableListOf(), mainViewModel)
        recyclerView.adapter = adapter

        mainViewModel.recentRestaurantNames.observe(viewLifecycleOwner) { restaurantNames ->
            adapter.updateData(restaurantNames)
        }

        mainViewModel.isUserAuthenticated.observe(viewLifecycleOwner) { isAuthenticated ->
            if (isAuthenticated) {
                mainViewModel.fetchRestaurants()
            }
        }
    }
}




