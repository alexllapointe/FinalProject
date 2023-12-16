package edu.iu.alex.finalproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class AuthFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.auth_fragment, container, false)

        auth = FirebaseAuth.getInstance()


        val emailEditText = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.email)
        val passwordEditText = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.password)
        val registerButton = view.findViewById<Button>(R.id.btn_next)

        registerButton.setOnClickListener {
            Log.d("AuthFragment","Button clicked");
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            performAuthentication(email, password)
        }
        return view
    }

    private fun performAuthentication(email: String, password: String) {
        Log.d("AuthFragment","Authentication called");
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {

                    viewModel.updateToolbarForUserStatus(true);                    Toast.makeText(context, "Logged in successfully", Toast.LENGTH_SHORT).show()
                    navigateToHome()
                } else {
                    // If sign in fails, try to create a new user
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { registerTask ->
                            if (registerTask.isSuccessful) {
                                Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                                navigateToHome()
                            } else {
                                // If registration fails
                                Toast.makeText(context, "Authentication failed: ${registerTask.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
    }
}
