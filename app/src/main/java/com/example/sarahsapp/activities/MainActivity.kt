package com.example.sarahsapp.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sarahsapp.fragments.GPUCalculatorFragment
import com.example.sarahsapp.R
import com.example.sarahsapp.databinding.ActivityMainBinding
import com.example.sarahsapp.ui.utils.contentView
import com.example.sarahsapp.ui.viewmodels.Student
import org.koin.android.ext.android.get
import com.example.sarahsapp.callbacks.OnRotatingHeadClicked
import com.google.android.material.navigation.NavigationBarView


// MainActivity is actually the Bottom Navigation Activity
class MainActivity : AppCompatActivity(), OnRotatingHeadClicked {

    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)
    private lateinit var bottomActionBar: NavigationBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomActionBar = binding.bottomNavigation

        binding.run {
            findNavController(R.id.nav_host_fragment)
        }


        bottomActionBar.setOnItemSelectedListener { tab ->
            when(tab.itemId) {
                R.id.exchanges -> {
                    Toast.makeText(this, "Exchanges", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.calculator -> {
                    Toast.makeText(this, "Calculator", Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, GPUCalculatorFragment() )
                    //findNavController(R.id.calculator).navigate()
                    true
                }
                else -> false
            }
        }

        // Dependency Injection

        val student = get<Student>()
        student.beSmart()

        val student2 = get<Student>()
        student2.beSmart()

//        val viewModel = getViewModel<MainViewModel>()
//        viewModel.performAction()

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        //bottomActionBar.selectedItemId = R.id.exchanges

        // Action Bar Selection Listener



        return super.onCreateView(name, context, attrs)
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        return super.onCreateView(parent, name, context, attrs)
    }

    override fun onClicked() {
        bottomActionBar.visibility = View.VISIBLE
    }

}