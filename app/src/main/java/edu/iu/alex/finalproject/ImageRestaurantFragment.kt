package edu.iu.alex.finalproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage

class ImageRestaurantFragment : Fragment() {

    private lateinit var imagesRecyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.imagerestaurant_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imagesRecyclerView = view.findViewById(R.id.imagesRecyclerView)

        // Initialize the adapter with an empty list
        imageAdapter = ImageAdapter(mutableListOf())
        imagesRecyclerView.adapter = imageAdapter
        imagesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        mainViewModel.selectedRestaurantName.observe(viewLifecycleOwner) { restaurantName ->
            Log.d("ImageRestaurant", "Received Restaurant: $restaurantName")
            fetchImageUrls(restaurantName)
        }
    }

    private fun updateAdapterData(imageUrls: List<String>) {
        imageAdapter.updateData(imageUrls)
    }

    private fun fetchImageUrls(restaurantName: String) {
        val storageReference = FirebaseStorage.getInstance().reference.child(restaurantName)

        val imageUrls = mutableListOf<String>()
        for (i in 1..3) {
            val imageRef = storageReference.child("image$i.png")
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                imageUrls.add(uri.toString())
                Log.d("ImageRestaurant", "Download URL $i: ${uri.toString()}")
                if (imageUrls.size == 3) {
                    updateAdapterData(imageUrls)
                }
            }.addOnFailureListener {
                Log.e("ImageRestaurant", "Error getting download URL for image$i", it)
            }
        }
    }

}

