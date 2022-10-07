package com.anti_toxic.dota.heroes.filter_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.anti_toxic.dota.core_api.AttackType
import com.anti_toxic.dota.core_api.Attribute
import com.anti_toxic.dota.core_api.Role
import com.anti_toxic.dota.heroes.R
import com.anti_toxic.dota.heroes.databinding.DialogFragmentFilterHeroesBinding


class FilterHeroesDialogFragment : DialogFragment() {

    private var mListener: FiltersChooseDialogListener? = null
    private var viewBinding: DialogFragmentFilterHeroesBinding? = null
    private var heroesFilters = HeroesFilters()
    private var isSubmitButtonEnabled: Boolean = false
        set(value) {
            viewBinding?.submitSelectionBtn.let { button ->
                field = value
                button?.isEnabled = value
                if (value) {
                    button?.alpha = 1f
                } else {
                    button?.alpha = 0.5f
                }
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        viewBinding = DialogFragmentFilterHeroesBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val roleDropdown = viewBinding?.roleDropdown
        val roleAdapter = object : SpinnerAdapter<Role>(requireContext(), Role.values().toList()) {
            override val hint: String = requireContext().getString(R.string.hint_select_role)

            override fun setItem(view: View, item: Role) {
                view.findViewById<TextView>(R.id.selector_text).text =
                    requireContext().getString(item.localizedName)
            }
        }
        roleDropdown?.adapter = roleAdapter
        roleDropdown?.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val role = parent?.getItemAtPosition(position) as Role?
                heroesFilters = heroesFilters.copy(role = role)
                if (role != null) {
                    isSubmitButtonEnabled = true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // no-op
            }
        }


        val primaryAttributeDropdown = viewBinding?.primaryAttrDropdown
        val primaryAttributeAdapter =
            object : SpinnerAdapter<Attribute>(requireContext(), Attribute.values().toList()) {
                override val hint: String = requireContext().getString(R.string.hint_select_primary_attribute)

                override fun setItem(view: View, item: Attribute) {
                    view.findViewById<TextView>(R.id.selector_text).text =
                        requireContext().getString(item.localizedName)
                }
            }
        primaryAttributeDropdown?.adapter = primaryAttributeAdapter
        primaryAttributeDropdown?.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val primaryAttribute = parent?.getItemAtPosition(position) as Attribute?
                heroesFilters = heroesFilters.copy(primaryAttribute = primaryAttribute)
                if (primaryAttribute != null) {
                    isSubmitButtonEnabled = true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // no-op
            }
        }

        val attackTypeDropdown = viewBinding?.attackTypeDropdown
        val attackTypeAdapter = object : SpinnerAdapter<AttackType>(requireContext(), AttackType.values().toList()) {
            override val hint: String = requireContext().getString(R.string.hint_select_attack_type)

            override fun setItem(view: View, item: AttackType) {
                view.findViewById<TextView>(R.id.selector_text).text =
                    requireContext().getString(item.localizedName)
            }
        }
        attackTypeDropdown?.adapter = attackTypeAdapter
        attackTypeDropdown?.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val attackType = parent?.getItemAtPosition(position) as AttackType?
                heroesFilters = heroesFilters.copy(attackType = attackType)
                if (attackType != null) {
                    isSubmitButtonEnabled = true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // no-op
            }
        }

        viewBinding?.clearSelectionBtn?.setOnClickListener {
            roleDropdown?.setSelection(0)
            attackTypeDropdown?.setSelection(0)
            primaryAttributeDropdown?.setSelection(0)
            isSubmitButtonEnabled = false
        }

        viewBinding?.submitSelectionBtn?.setOnClickListener {
            mListener?.onFiltersChosen(heroesFilters)
            dialog?.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        val params = dialog?.window?.attributes
        params?.width = LayoutParams.MATCH_PARENT
        params?.height = LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    fun setOnFiltersChosenListener(listener: FiltersChooseDialogListener) {
        mListener = listener
    }

    interface FiltersChooseDialogListener {
        fun onFiltersChosen(heroesFilters: HeroesFilters)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}