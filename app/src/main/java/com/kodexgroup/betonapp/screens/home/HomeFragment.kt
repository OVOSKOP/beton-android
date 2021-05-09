package com.kodexgroup.betonapp.screens.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.utils.isEmpty
import com.kodexgroup.betonapp.utils.setupWithNavController
import com.kodexgroup.betonapp.utils.views.ToolbarView

class HomeFragment : Fragment() {

    private lateinit var root: View
    private lateinit var toolbar: ToolbarView
    private lateinit var shadow: View

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var currentController: LiveData<NavController>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_home, container, false)

        toolbar = root.findViewById(R.id.main_toolbar)
        shadow = root.findViewById(R.id.shadow)
        bottomNavigationView = root.findViewById(R.id.bottom_nav_app)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        setupBottomNavigationBar()

        return root
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(R.navigation.home_graph, R.navigation.map_graph, R.navigation.profile_graph)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = childFragmentManager,
                containerId = R.id.nav_controller_app,
                intent = requireActivity().intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(viewLifecycleOwner, {

            println("HELLO" + it.currentDestination?.label)

            it.addOnDestinationChangedListener { controller, _, _ ->
                println("HELLO" + it.currentDestination?.label)
                toolbar.setNavigationOnClickListener {
                    controller.popBackStack()
                }

                if (controller.isEmpty()) {
                    toolbar.search = false
                    toolbar.navigationIcon = null
                    shadow.visibility = View.GONE
                } else {
                    toolbar.search = true
                    toolbar.setNavigationIcon(R.drawable.ic_back)
                    shadow.visibility = View.VISIBLE
                }

            }

        })

        currentController = controller
    }

    fun toProduct(id: String?) {

        val args = Bundle()
        args.putString("productId", id)

        currentController.value?.navigate(R.id.to_product, args)
    }

    fun toProfile() {
        bottomNavigationView.selectedItemId = R.id.profile_app_fragment
    }
}