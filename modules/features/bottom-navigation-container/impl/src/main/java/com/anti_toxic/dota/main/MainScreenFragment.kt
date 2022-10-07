package com.anti_toxic.dota.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.anti_toxic.dota.main.databinding.FragmentMainScreenBinding
import com.anti_toxic.dota.ui.ViewBindingFragment

class MainScreenFragment :
    ViewBindingFragment<FragmentMainScreenBinding>(FragmentMainScreenBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        val navController = navHostFragment.navController
        val bottom = viewBinding.bottomNav
        bottom.setupWithNavController(navController)
    }
}