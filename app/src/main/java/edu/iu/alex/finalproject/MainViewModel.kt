package edu.iu.alex.finalproject;

import android.util.Log
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage

import java.util.ArrayList;

class MainViewModel : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestoreDb = FirebaseFirestore.getInstance()

    private val _isUserAuthenticated = MutableLiveData<Boolean>()
    val isUserAuthenticated: LiveData<Boolean> = _isUserAuthenticated

    val _allRestaurantNames = MutableLiveData<List<String>>()
    val allRestaurantNames: LiveData<List<String>> = _allRestaurantNames

    private val _recentRestaurantNames = MutableLiveData<List<String>>()
    val recentRestaurantNames: LiveData<List<String>> = _recentRestaurantNames

    private val _selectedRestaurantName = MutableLiveData<String>()
    val selectedRestaurantName: LiveData<String> = _selectedRestaurantName

    private val _imageUrls = MutableLiveData<List<String>>()
    val imageUrls: LiveData<List<String>> = _imageUrls

    private val _orders = MutableLiveData<List<List<String>>>()
    val orders: LiveData<List<List<String>>> = _orders

    private val _orderData = MutableLiveData<List<List<String>>>()
    val orderData: LiveData<List<List<String>>>
        get() = _orderData




    init {
        _isUserAuthenticated.value = firebaseAuth.currentUser != null
        firebaseAuth.addAuthStateListener { auth ->
            _isUserAuthenticated.value = auth.currentUser != null
        }
    }

    fun fetchRestaurants() {
        firestoreDb.collection("Order")
            .get()
            .addOnSuccessListener { documents ->
                val restaurantList = mutableListOf<String>()
                for (document in documents) {
                    document.getString("restaurant")?.let {
                        restaurantList.add(it)
                    }
                }
                _recentRestaurantNames.postValue(restaurantList)
            }
            .addOnFailureListener { exception ->
                Log.e("MainViewModel", "Error fetching recent restaurant data", exception)
            }
    }

    fun fetchAllRestaurants() {
        firestoreDb.collection("Restaurant")
            .get()
            .addOnSuccessListener { documents ->
                val restaurantList = mutableListOf<String>()
                for (document in documents) {
                    document.getString("name")?.let {
                        restaurantList.add(it)
                    }
                }
                _allRestaurantNames.postValue(restaurantList)
            }
            .addOnFailureListener { exception ->
                Log.e("MainViewModel", "Error fetching all restaurant data", exception)
            }
    }

    fun updateToolbarForUserStatus(isAuthenticated: Boolean) {
        _isUserAuthenticated.value = isAuthenticated
    }
    fun selectRestaurant(name: String) {
        _selectedRestaurantName.value = name
        Log.d("MainViewModel", "Selected Restaurant Name: $name")
    }
    fun setOrders(ordersList: List<List<String>>) {
        _orders.value = ordersList
    }
    fun placeOrder(orderData: List<List<String>>) {
        _orderData.value = orderData
        Log.d("MainViewModel", "Order data: $orderData")

    }
}


