package gr.aytn.islamicapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import gr.aytn.islamicapp.NotificationService
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.databinding.FragmentSettingsBinding
import gr.aytn.islamicapp.prefs


class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var navHost: FragmentContainerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater,container,false)
        val root: View = binding.root

        val settingsLocation = binding.locationSettings
        val tvSelectedLocation = binding.selectedLocation

//        val settingsAsrCalculation = binding.asrCalculationSettings
//        val tvSelectedAsrCalculation = binding.selectedAsrLocation

        val settingsTranslation = binding.translationSettings
        val tvSelectedTranslation = binding.selectedTranslation

        val settingsTheme = binding.themeSettings
        val tvSelectedTheme = binding.selectedTheme

        val switchNotf = binding.switchStickyNotfSettings

        tvSelectedTheme.text = prefs.theme
        tvSelectedTranslation.text= prefs.selected_translation
        tvSelectedLocation.text = prefs.selected_location

        settingsLocation.setOnClickListener {
            findNavController().navigate(R.id.locationFragment)
        }

        switchNotf.isChecked = prefs.sticky_notf
        val intent = Intent(requireActivity(), NotificationService::class.java)
        switchNotf.setOnClickListener {
            if (switchNotf.isChecked){
                prefs.sticky_notf = true
                Log.i("frag","swithc on")
                requireActivity().startService(intent)
            }
            else{
                prefs.sticky_notf = false
                Log.i("frag","swithc off")
                requireActivity().stopService(intent)
            }
        }

        settingsTranslation.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(context)
                .create()
            val view = layoutInflater.inflate(R.layout.translation_checkbox_dialog, null)
            val radioGroup: RadioGroup = view.findViewById(R.id.RGroup)
            val alikhanRadioBtn : RadioButton = view.findViewById(R.id.alikhan)
            val vasimRadioBtn : RadioButton = view.findViewById(R.id.vasim)
            var selectedTranslation = ""

            if(prefs.selected_translation == "Əlixan Musayev"){
                alikhanRadioBtn.isChecked = true
            }else if(prefs.selected_translation == "Vasim Məmmədəliyev və Ziya Bünyadov") {
                vasimRadioBtn.isChecked = true
            }
            radioGroup.setOnCheckedChangeListener { _, i ->
                if (i == R.id.alikhan) {
                    selectedTranslation = "Əlixan Musayev"
                } else if (i == R.id.vasim) {
                    selectedTranslation = "Vasim Məmmədəliyev və Ziya Bünyadov"
                }
            }
            val okBtn: Button = view.findViewById(R.id.ok_button)
            val cancelBtn: Button = view.findViewById(R.id.cancel_button)
            okBtn.setOnClickListener {
                prefs.selected_translation = selectedTranslation
                if (prefs.selected_translation == "Əlixan Musayev"){
                    tvSelectedTranslation.text = "Əlixan Musayev"
                }else if (prefs.selected_translation == "Vasim Məmmədəliyev və Ziya Bünyadov"){
                    tvSelectedTranslation.text = "Vasim Məmmədəliyev və Ziya Bünyadov"
                }
                builder.dismiss()
            }
            cancelBtn.setOnClickListener {
                builder.dismiss()
            }
            builder.setView(view)
            builder.setCanceledOnTouchOutside(true)
            builder.show()
        }
//        settingsAsrCalculation.setOnClickListener {
//            val builder = android.app.AlertDialog.Builder(context)
//                .create()
//            val view = layoutInflater.inflate(R.layout.method_checkbox_dialog, null)
//            val radioGroup: RadioGroup = view.findViewById(R.id.RGroup)
//            var selectedAsrCalculation = ""
//            radioGroup.setOnCheckedChangeListener { _, i ->
//                if (i == R.id.standard) {
//                    selectedAsrCalculation = "standart"
//                } else if (i == R.id.hanafi) {
//                    selectedAsrCalculation = "hanafi"
//                }
//            }
//            val okBtn: Button = view.findViewById(R.id.ok_button)
//            val cancelBtn: Button = view.findViewById(R.id.cancel_button)
//            okBtn.setOnClickListener {
//                builder.dismiss()
//            }
//            cancelBtn.setOnClickListener {
//                builder.dismiss()
//            }
//            builder.setView(view)
//            builder.setCanceledOnTouchOutside(false)
//            builder.show()
//        }

        settingsTheme.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(context)
                .create()
            val view = layoutInflater.inflate(R.layout.theme_checkbox_dialog, null)
            val radioGroup: RadioGroup = view.findViewById(R.id.RGroup)
            val lightRadioBtn : RadioButton = view.findViewById(R.id.light)
            val darkRadioBtn : RadioButton = view.findViewById(R.id.dark)
            var selectedTheme = ""

            if(prefs.theme == "Light"){
                lightRadioBtn.isChecked = true
            }else if(prefs.theme == "Dark") {
                darkRadioBtn.isChecked = true
            }
            radioGroup.setOnCheckedChangeListener { _, i ->
                if (i == R.id.light) {
                    selectedTheme = "Light"
                } else if (i == R.id.dark) {
                    selectedTheme = "Dark"
                }
            }
            val okBtn: Button = view.findViewById(R.id.ok_button)
            val cancelBtn: Button = view.findViewById(R.id.cancel_button)
            okBtn.setOnClickListener {
                prefs.theme = selectedTheme
                if (prefs.theme == "Light"){
                    tvSelectedTheme.text = prefs.theme
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }else if (prefs.theme == "Dark"){
                    tvSelectedTheme.text = prefs.theme
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                }
                builder.dismiss()
            }
            cancelBtn.setOnClickListener {
                builder.dismiss()
            }
            builder.setView(view)
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }

        return root
    }

}