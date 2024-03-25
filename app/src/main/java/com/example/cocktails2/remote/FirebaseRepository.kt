package com.example.cocktails2.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cocktails2.remote.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseRepository private constructor() {

    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    private val _user: MutableLiveData<User?> = MutableLiveData(null)
    val user: LiveData<User?> = _user

    fun checkAuth() {
        auth.currentUser?.let {
            val userRef = firestore.collection("users").document(it.uid)

            userRef.get().addOnSuccessListener {
                _user.value = User(
                    email = it.get("email").toString(),
                    username = it.get("username").toString(),
                    id = it.get("id").toString()
                )
            }
        }
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val userRef = it.result.user?.let { it1 ->
                        firestore.collection("users").document(it1.uid)
                    }

                    userRef?.get()?.addOnSuccessListener {
                        _user.value = User(
                            email = it.get("email").toString(),
                            username = it.get("username").toString(),
                            id = it.get("id").toString()
                        )
                    }
                }
            }
    }

    fun register(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val newUser = hashMapOf(
                        "username" to username,
                        "email" to email,
                        "id" to (it.result.user?.uid ?: "noID")
                    )

                    val userRef = it.result.user?.let { it1 ->
                        firestore.collection("users").document(
                            it1.uid)
                    }

                    userRef?.set(newUser)?.addOnSuccessListener {
                        login(email, password)
                    }
                }
            }
            .addOnFailureListener {
                Log.e("REGISTER FAILED", "Mail: $email")
                Log.e("REGISTER FAILED", it.toString())
            }
    }

    fun updateUsername(newUsername: String, id: String) {
        val userRef = firestore.collection("users").document(id)

        userRef.update("username", newUsername).addOnCompleteListener {
            it.addOnSuccessListener {
                val updatedUser = _user.value?.let { it1 ->
                    User(
                        email = it1.email,
                        username = newUsername,
                        id = it1.id
                    )
                }

                _user.value = updatedUser
            }
        }
    }

    fun logout() {
        auth.signOut()
        _user.value = null
    }

    companion object {
        @Volatile private var instance: FirebaseRepository? = null

        fun getInstance(): FirebaseRepository {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = FirebaseRepository()
                    }
                }
            }
            return instance!!
        }
    }
}