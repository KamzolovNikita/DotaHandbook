package com.anti_toxic.dota.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragment<T : ViewBinding>(
    private val bindingInflater: (layoutInflater: LayoutInflater) -> T
) : Fragment() {

    private var _viewBinding: T? = null

    protected val viewBinding
        get() = _viewBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinding = bindingInflater.invoke(inflater)
        return _viewBinding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}