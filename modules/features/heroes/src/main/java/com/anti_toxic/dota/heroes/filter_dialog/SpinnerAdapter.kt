package com.anti_toxic.dota.heroes.filter_dialog

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.anti_toxic.dota.heroes.R

abstract class SpinnerAdapter<T>(
    context: Context,
    values: List<T>
) : ArrayAdapter<T>(context, 0, values) {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    open val hint: String = ""

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView
            ?: layoutInflater.inflate(R.layout.custom_spinner_layout, parent, false)
        getItem(position)?.let { item ->
            setItem(view, item)
        } ?: run {
            view.findViewById<TextView>(R.id.selector_text).text = hint
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        if (position == 0) {
            view = layoutInflater.inflate(R.layout.custom_spinner_header_item, parent, false)
            view.findViewById<TextView>(R.id.header_text).text = hint
            view.setOnClickListener {
                val root = parent.rootView
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK))
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK))
            }
        } else {
            view = layoutInflater.inflate(R.layout.custom_spinner_dropdown_item, parent, false)
        }
        getItem(position)?.let { item ->
            setItem(view, item)
        }
        return view
    }

    override fun getItem(position: Int): T? {
        if (position == 0) {
            return null
        }
        return super.getItem(position - 1)
    }

    override fun getCount() = super.getCount() + 1

    abstract fun setItem(view: View, item: T)
}
