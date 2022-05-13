@file:Suppress("UNCHECKED_CAST")

package com.example.sarahsapp.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sarahsapp.R
import com.example.sarahsapp.databinding.RelaxFragmentBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RelaxFragment : Fragment() {

    private var _binding: RelaxFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageView: ImageView
    private lateinit var backgroundView: FrameLayout
    private lateinit var imageList: Map<Any, Any>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = RelaxFragmentBinding.inflate(inflater, container, false)

        imageView = binding.imageView
        backgroundView = binding.container

        retrieveImageDatabase()

        return binding.root
    }

    private fun retrieveImageDatabase() {
        // Reference to an image file in Cloud Storage
        val databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.image_folder))

        databaseReference.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                imageList = snapshot.value as HashMap<Any,Any>
                setImageView()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, R.string.firebase_storage_error, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setImageView(){
        val randomImgBackgroundColor: String = imageList.keys.random() as String
        val randomImage = imageList[randomImgBackgroundColor]

        Glide.with(this)
            .load(randomImage)
            .into(imageView)

        backgroundView.setBackgroundColor(Color.parseColor("#$randomImgBackgroundColor"))
    }
}