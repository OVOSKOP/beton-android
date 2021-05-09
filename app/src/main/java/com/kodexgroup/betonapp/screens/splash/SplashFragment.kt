package com.kodexgroup.betonapp.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.kodexgroup.betonapp.R
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_splash, container, false)

        val icon = root.findViewById<ImageView>(R.id.icon)

        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_splash_icon)

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) { }

            override fun onAnimationEnd(animation: Animation?) {
                val navController = findNavController()
                navController.navigate(R.id.is_signed_to_home)
            }

            override fun onAnimationRepeat(animation: Animation?) { }

        })

        icon.startAnimation(anim)

        return root
    }

}