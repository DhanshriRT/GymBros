package com.aumeca.fashionista.firebase

import com.aumeca.fashionista.activity.SplashActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreClass(private val activity: SplashActivity
) {
    private val mFireStore = FirebaseFirestore.getInstance()
    fun getCurrentUserId(): String{
        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if(currentUser != null){
            currentUserId = currentUser.uid
        }

        return currentUserId
    }

//    fun createUserData(userData: StoreUserData) {
//        // Create a reference to the users collection
//        val userDocument = mFireStore.collection("users").document(userData.u_id)
//
//        userDocument
//            .set(userData, SetOptions.merge())
//            .addOnSuccessListener {
//                Log.d(activity.javaClass.simpleName, "User data created/updated successfully!")
//                Toast.makeText(activity, "User data saved successfully.", Toast.LENGTH_SHORT).show()
//                activity.onUserDataSavedSuccessfully()
//            }
//            .addOnFailureListener { exception ->
//                activity.hideProgressDialog()
//                Log.e(activity.javaClass.simpleName, "Error while saving user data.", exception)
//                Toast.makeText(activity, "Failed to save user data.", Toast.LENGTH_SHORT).show()
//            }
//    }
}