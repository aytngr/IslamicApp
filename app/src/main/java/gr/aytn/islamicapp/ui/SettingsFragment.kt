package gr.aytn.islamicapp.ui

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import gr.aytn.islamicapp.NotificationService
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.databinding.FragmentHomeBinding
import gr.aytn.islamicapp.databinding.FragmentSettingsBinding
import gr.aytn.islamicapp.prefs


class SettingsFragment : Fragment(), View.OnClickListener {

    private lateinit var minute1: TextView
    private lateinit var minute2: TextView
    private lateinit var minute3: TextView
    private lateinit var minute4: TextView
    private lateinit var minute5: TextView

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        val root: View = binding.root

        val calculationSettings = binding.calculationSettings

        val notificationSettings = binding.notificationSettings

        val quranSettings = binding.quranSettings

        val generalSettings = binding.generalSettings

        calculationSettings.setOnClickListener {
            val calcSettBottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
            val bottomSheetView: View = LayoutInflater.from(requireActivity()).inflate(R.layout.fragment_calculation_settings,null)
            calcSettBottomSheet.setContentView(bottomSheetView)

            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.skipCollapsed = true

            val layout: LinearLayout? = calcSettBottomSheet.findViewById(R.id.calculation_settings_layout)
            layout?.minimumHeight = Resources.getSystem().displayMetrics.heightPixels *85/100

            val location = calcSettBottomSheet.findViewById<LinearLayout>(R.id.location_settings)
            val asr = calcSettBottomSheet.findViewById<LinearLayout>(R.id.asr_settings)
            val selected_asr = calcSettBottomSheet.findViewById<TextView>(R.id.selected_asr)
            val adjustments = calcSettBottomSheet.findViewById<LinearLayout>(R.id.adjustment_settings)

            selected_asr!!.text = prefs.selected_asr_calculation

            location!!.setOnClickListener {
                findNavController().navigate(R.id.locationFragment)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
            asr!!.setOnClickListener {
                val builder = android.app.AlertDialog.Builder(context,R.style.AlertDialogStyle)
                    .create()
                val view = layoutInflater.inflate(R.layout.asr_calculation_settings_dialog, null)
                val radioGroup: RadioGroup = view.findViewById(R.id.RGroup)
                var selectedAsrCalculation = ""
                radioGroup.setOnCheckedChangeListener { _, i ->
                    if (i == R.id.standard) {
                        selectedAsrCalculation = "Standart (Şafi, Maliki, Hənbəli)"
                    } else if (i == R.id.hanafi) {
                        selectedAsrCalculation = "Hənəfi"
                    }
                }
                val okBtn: Button = view.findViewById(R.id.ok_button)
                val cancelBtn: Button = view.findViewById(R.id.cancel_button)
                okBtn.setOnClickListener {
                    prefs.selected_asr_calculation = selectedAsrCalculation
                    builder.dismiss()
                }
                cancelBtn.setOnClickListener {
                    builder.dismiss()
                }
                builder.setView(view)
                builder.show()
            }

            adjustments!!.setOnClickListener {

                function()
            }

            calcSettBottomSheet.show()
//            calcSettBottomSheet.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            calcSettBottomSheet.window?.attributes?.windowAnimations = R.style.DialogAnimation
//            calcSettBottomSheet.window?.setGravity(Gravity.BOTTOM)
//            calcSettBottomSheet.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,Resources.getSystem().displayMetrics.heightPixels *85/100)
            }

        notificationSettings.setOnClickListener {
            val notfSettBottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
            val bottomSheetView: View = LayoutInflater.from(requireActivity()).inflate(R.layout.fragment_notifications_settings,null)
            notfSettBottomSheet.setContentView(bottomSheetView)

            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.skipCollapsed = true

            val layout: ScrollView? = notfSettBottomSheet.findViewById(R.id.notf_settings_layout)
            layout?.minimumHeight = Resources.getSystem().displayMetrics.heightPixels *85/100


            val switch = notfSettBottomSheet.findViewById<SwitchCompat>(R.id.switch_sticky_notf_settings)
            val icon = notfSettBottomSheet.findViewById<LinearLayout>(R.id.icon_settings)
            val style = notfSettBottomSheet.findViewById<LinearLayout>(R.id.style_settings)
            val bg = notfSettBottomSheet.findViewById<RelativeLayout>(R.id.bg_settings)

            val tvSelectedIcon = notfSettBottomSheet.findViewById<TextView>(R.id.selected_icon)
            val tvSelectedStyle = notfSettBottomSheet.findViewById<TextView>(R.id.selected_style)
            val tvSelectedNotfBg = notfSettBottomSheet.findViewById<ImageView>(R.id.selected_notf_bg)

            tvSelectedIcon?.text = prefs.selected_notf_icon
            tvSelectedStyle?.text = prefs.selected_notf_style
//            tvSelectedNotfBg = prefs.sel

            switch!!.isChecked = prefs.sticky_notf
            val intent = Intent(requireActivity(), NotificationService::class.java)
            switch.setOnClickListener {
                if (switch.isChecked){
                    prefs.sticky_notf = true
                    Log.i("frag","swithc on")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        requireActivity().startForegroundService(intent);
                    } else {
                        requireActivity().startService(intent);
                    }
//                    requireActivity().startService(intent)
                }
                else{
                    prefs.sticky_notf = false
                    Log.i("frag","swithc off")
                    requireActivity().stopService(intent)
                }
            }
            icon!!.setOnClickListener {
                val builder = android.app.AlertDialog.Builder(context,R.style.AlertDialogStyle)
                    .create()
                val view = layoutInflater.inflate(R.layout.notf_icon_checkbox_dialog, null)
                val radioGroup: RadioGroup = view.findViewById(R.id.RGroup)
                var selectedIcon = ""
                radioGroup.setOnCheckedChangeListener { _, i ->
                    if (i == R.id.app_icon) {
                        selectedIcon = "Tətbiq nişanı"
                    } else if (i == R.id.moon_phases) {
                        selectedIcon = "Ay fazaları"
                    }
                }
                val okBtn: Button = view.findViewById(R.id.ok_button)
                val cancelBtn: Button = view.findViewById(R.id.cancel_button)
                okBtn.setOnClickListener {
                    prefs.selected_notf_icon = selectedIcon
                    builder.dismiss()
                }
                cancelBtn.setOnClickListener {
                    builder.dismiss()
                }
                builder.setView(view)
                builder.show()
            }

            style!!.setOnClickListener {
                val builder = android.app.AlertDialog.Builder(context,R.style.AlertDialogStyle)
                    .create()
                val view = layoutInflater.inflate(R.layout.notf_style_checkbox_dialog, null)
                val radioGroup: RadioGroup = view.findViewById(R.id.RGroup)
                var selectedStyle = ""
                radioGroup.setOnCheckedChangeListener { _, i ->
                    if (i == R.id.style1) {
                        selectedStyle = "Stil 1"
                    } else if (i == R.id.style2) {
                        selectedStyle = "Stil 2"
                    }
                }
                val okBtn: Button = view.findViewById(R.id.ok_button)
                val cancelBtn: Button = view.findViewById(R.id.cancel_button)
                okBtn.setOnClickListener {
                    prefs.selected_notf_style = selectedStyle
                    builder.dismiss()
                }
                cancelBtn.setOnClickListener {
                    builder.dismiss()
                }
                builder.setView(view)
                builder.show()
            }
            notfSettBottomSheet.show()
//            notfSettBottomSheet.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            notfSettBottomSheet.window?.attributes?.windowAnimations = R.style.DialogAnimation
//            calcSettBottomSheet.window?.setGravity(Gravity.BOTTOM)
        }

        quranSettings.setOnClickListener {
            val quranSettBottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
            val bottomSheetView: View = LayoutInflater.from(requireActivity())
                .inflate(R.layout.fragment_quran_settings, null)
            quranSettBottomSheet.setContentView(bottomSheetView)

            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.skipCollapsed = true

            val layout: ScrollView? = quranSettBottomSheet.findViewById(R.id.quran_settings_layout)
            layout?.minimumHeight = Resources.getSystem().displayMetrics.heightPixels * 85 / 100

            val translation =
                quranSettBottomSheet.findViewById<LinearLayout>(R.id.translation_settings)
            val arabic_font =
                quranSettBottomSheet.findViewById<LinearLayout>(R.id.arabic_font_size_settings)
            val translation_font =
                quranSettBottomSheet.findViewById<LinearLayout>(R.id.translation_font_size_settings)
            val rGroup = quranSettBottomSheet.findViewById<RadioGroup>(R.id.LangRGroup)
            val araz = quranSettBottomSheet.findViewById<RadioButton>(R.id.araz)
            val ar = quranSettBottomSheet.findViewById<RadioButton>(R.id.ar)
            val az = quranSettBottomSheet.findViewById<RadioButton>(R.id.az)

            val tvSelectedArabicFontSize =
                quranSettBottomSheet.findViewById<TextView>(R.id.selected_arabic_font_size)
            val tvSelectedTranslationFontSize =
                quranSettBottomSheet.findViewById<TextView>(R.id.selected_translation_font_size)
            val tvSelectedTranslation =
                quranSettBottomSheet.findViewById<TextView>(R.id.selected_translation)

            tvSelectedTranslation?.text = prefs.selected_translation
            tvSelectedArabicFontSize?.text = prefs.arabic_font_size.toString() + "sp"
            tvSelectedTranslationFontSize?.text = prefs.translation_font_size.toString() + "sp"

            when (prefs.selected_text_language) {
                "ar" -> ar!!.isChecked = true
                "az" -> az!!.isChecked = true
                "araz" -> araz!!.isChecked = true
            }

            rGroup?.setOnCheckedChangeListener { _, i ->
                when (i) {
                    R.id.ar -> prefs.selected_text_language = "ar"
                    R.id.az -> prefs.selected_text_language = "az"
                    R.id.araz -> prefs.selected_text_language = "araz"
                }
            }

            arabic_font?.setOnClickListener {
                val builder = android.app.AlertDialog.Builder(context,R.style.AlertDialogStyle)
                    .create()
                val view = layoutInflater.inflate(R.layout.arabic_font_checkbox_dialog, null)
                val radioGroup: RadioGroup = view.findViewById(R.id.RGroup)
                val s26RadioBtn: RadioButton = view.findViewById(R.id.s26)
                val s28RadioBtn: RadioButton = view.findViewById(R.id.s28)
                val s30RadioBtn: RadioButton = view.findViewById(R.id.s30)
                val s32RadioBtn: RadioButton = view.findViewById(R.id.s32)
                var selectedSize = 20

                when (prefs.arabic_font_size) {
                    26 -> s26RadioBtn.isChecked = true
                    28 -> s28RadioBtn.isChecked = true
                    30 -> s30RadioBtn.isChecked = true
                    32 -> s32RadioBtn.isChecked = true
                }
                radioGroup.setOnCheckedChangeListener { _, i ->
                    when (i) {
                        R.id.s26 -> selectedSize = 26
                        R.id.s28 -> selectedSize = 28
                        R.id.s30 -> selectedSize = 30
                        R.id.s32 -> selectedSize = 32
                    }
                }
                val okBtn: Button = view.findViewById(R.id.ok_button)
                val cancelBtn: Button = view.findViewById(R.id.cancel_button)
                okBtn.setOnClickListener {
                    prefs.arabic_font_size = selectedSize
                    tvSelectedArabicFontSize!!.text = prefs.arabic_font_size.toString() + "sp"
                    builder.dismiss()
                }
                cancelBtn.setOnClickListener {
                    builder.dismiss()
                }
                builder.setView(view)
                builder.show()
            }

            translation_font?.setOnClickListener {
                val builder = android.app.AlertDialog.Builder(context,R.style.AlertDialogStyle)
                    .create()
                val view = layoutInflater.inflate(R.layout.translation_font_checkbox_dialog, null)
                val radioGroup: RadioGroup = view.findViewById(R.id.RGroup)
                val s16RadioBtn: RadioButton = view.findViewById(R.id.s16)
                val s18RadioBtn: RadioButton = view.findViewById(R.id.s18)
                val s20RadioBtn: RadioButton = view.findViewById(R.id.s20)
                val s22RadioBtn: RadioButton = view.findViewById(R.id.s22)
                val s24RadioBtn: RadioButton = view.findViewById(R.id.s24)
                var selectedSize = 0

                when (prefs.translation_font_size) {
                    16 -> s16RadioBtn.isChecked = true
                    18 -> s18RadioBtn.isChecked = true
                    20 -> s20RadioBtn.isChecked = true
                    22 -> s22RadioBtn.isChecked = true
                    24 -> s24RadioBtn.isChecked = true
                }
                radioGroup.setOnCheckedChangeListener { _, i ->
                    when (i) {
                        R.id.s16 -> selectedSize = 16
                        R.id.s18 -> selectedSize = 18
                        R.id.s20 -> selectedSize = 20
                        R.id.s22 -> selectedSize = 22
                        R.id.s24 -> selectedSize = 24
                    }
                }
                val okBtn: Button = view.findViewById(R.id.ok_button)
                val cancelBtn: Button = view.findViewById(R.id.cancel_button)
                okBtn.setOnClickListener {
                    prefs.translation_font_size = selectedSize
                    tvSelectedTranslationFontSize!!.text =
                        prefs.translation_font_size.toString() + "sp"
                    builder.dismiss()
                }
                cancelBtn.setOnClickListener {
                    builder.dismiss()
                }
                builder.setView(view)
                builder.show()
            }

            translation?.setOnClickListener {
                val builder = android.app.AlertDialog.Builder(context,R.style.AlertDialogStyle)
                    .create()
                val view = layoutInflater.inflate(R.layout.translation_checkbox_dialog, null)
                val radioGroup: RadioGroup = view.findViewById(R.id.RGroup)
                val alikhanRadioBtn: RadioButton = view.findViewById(R.id.alikhan)
                val vasimRadioBtn: RadioButton = view.findViewById(R.id.vasim)
                var selectedTranslation = ""

                if (prefs.selected_translation == "Əlixan Musayev") {
                    alikhanRadioBtn.isChecked = true
                } else if (prefs.selected_translation == "Vasim Məmmədəliyev və Ziya Bünyadov") {
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
                    if (prefs.selected_translation == "Əlixan Musayev") {
                        tvSelectedTranslation!!.text = "Əlixan Musayev"
                    } else if (prefs.selected_translation == "Vasim Məmmədəliyev və Ziya Bünyadov") {
                        tvSelectedTranslation!!.text = "Vasim Məmmədəliyev və Ziya Bünyadov"
                    }
                    builder.dismiss()
                }
                cancelBtn.setOnClickListener {
                    builder.dismiss()
                }
                builder.setView(view)
                builder.show()
            }
            quranSettBottomSheet.show()
//            quranSettBottomSheet.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            quranSettBottomSheet.window?.attributes?.windowAnimations = R.style.DialogAnimation
//            calcSettBottomSheet.window?.setGravity(Gravity.BOTTOM)
        }

        generalSettings.setOnClickListener {
            val generalSettBottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
            val bottomSheetView: View = LayoutInflater.from(requireActivity())
                .inflate(R.layout.fragment_general_settings, null)
            generalSettBottomSheet.setContentView(bottomSheetView)

            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.skipCollapsed = true

            val layout: ScrollView? = generalSettBottomSheet.findViewById(R.id.general_settings_layout)
            layout?.minimumHeight = Resources.getSystem().displayMetrics.heightPixels * 85 / 100

            val theme = generalSettBottomSheet.findViewById<LinearLayout>(R.id.theme_settings)
            val tvSelectedTheme = generalSettBottomSheet.findViewById<TextView>(R.id.selected_theme)

            tvSelectedTheme?.text = prefs.theme

            theme?.setOnClickListener {
                val builder = android.app.AlertDialog.Builder(context,R.style.AlertDialogStyle)
                    .create()
                val view = layoutInflater.inflate(R.layout.theme_checkbox_dialog, null)
                val radioGroup: RadioGroup = view.findViewById(R.id.RGroup)
                val lightRadioBtn: RadioButton = view.findViewById(R.id.light)
                val darkRadioBtn: RadioButton = view.findViewById(R.id.dark)
                var selectedTheme = ""

                if (prefs.theme == "Light") {
                    lightRadioBtn.isChecked = true
                } else if (prefs.theme == "Dark") {
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
                    if (prefs.theme == "Light") {
                        tvSelectedTheme?.text = prefs.theme
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    } else if (prefs.theme == "Dark") {
                        tvSelectedTheme?.text = prefs.theme
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                    }
                    builder.dismiss()
                }
                cancelBtn.setOnClickListener {
                    builder.dismiss()
                }
                builder.setView(view)
                builder.show()
            }
            generalSettBottomSheet.show()
//            generalSettBottomSheet.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            generalSettBottomSheet.window?.attributes?.windowAnimations = R.style.DialogAnimation
//            calcSettBottomSheet.window?.setGravity(Gravity.BOTTOM)
        }

        return root
    }

    fun function(){
        val manualAdjustmentsBottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        manualAdjustmentsBottomSheet.setContentView(R.layout.manual_bottom_sheet)

        val time1 = manualAdjustmentsBottomSheet.findViewById<TextView>(R.id.time1)
        time1!!.text = prefs.fajr_time
        val time2 = manualAdjustmentsBottomSheet.findViewById<TextView>(R.id.time2)
        time2!!.text = prefs.fajr_time
        val time3 = manualAdjustmentsBottomSheet.findViewById<TextView>(R.id.time3)
        time3!!.text = prefs.fajr_time
        val time4 = manualAdjustmentsBottomSheet.findViewById<TextView>(R.id.time4)
        time4!!.text = prefs.fajr_time
        val time5 = manualAdjustmentsBottomSheet.findViewById<TextView>(R.id.time5)
        time5!!.text = prefs.fajr_time

        val minus1 = manualAdjustmentsBottomSheet.findViewById<ImageView>(R.id.minus1)
        val minus2 = manualAdjustmentsBottomSheet.findViewById<ImageView>(R.id.minus2)
        val minus3 = manualAdjustmentsBottomSheet.findViewById<ImageView>(R.id.minus3)
        val minus4 = manualAdjustmentsBottomSheet.findViewById<ImageView>(R.id.minus4)
        val minus5 = manualAdjustmentsBottomSheet.findViewById<ImageView>(R.id.minus5)

        minus1!!.setOnClickListener(this)
        minus2!!.setOnClickListener(this)
        minus3!!.setOnClickListener(this)
        minus4!!.setOnClickListener(this)
        minus5!!.setOnClickListener(this)

        minute1 = manualAdjustmentsBottomSheet.findViewById<TextView>(R.id.minute1)!!
        minute2 = manualAdjustmentsBottomSheet.findViewById<TextView>(R.id.minute2)!!
        minute3 = manualAdjustmentsBottomSheet.findViewById<TextView>(R.id.minute3)!!
        minute4 = manualAdjustmentsBottomSheet.findViewById<TextView>(R.id.minute4)!!
        minute5 = manualAdjustmentsBottomSheet.findViewById<TextView>(R.id.minute5)!!

        minute1.text = prefs.fajr_manual.toString()
        minute2.text = prefs.dhuhr_manual.toString()
        minute3.text = prefs.asr_manual.toString()
        minute4.text = prefs.maghrib_manual.toString()
        minute5.text = prefs.isha_manual.toString()

        val plus1 = manualAdjustmentsBottomSheet.findViewById<ImageView>(R.id.plus1)
        val plus2 = manualAdjustmentsBottomSheet.findViewById<ImageView>(R.id.plus2)
        val plus3 = manualAdjustmentsBottomSheet.findViewById<ImageView>(R.id.plus3)
        val plus4 = manualAdjustmentsBottomSheet.findViewById<ImageView>(R.id.plus4)
        val plus5 = manualAdjustmentsBottomSheet.findViewById<ImageView>(R.id.plus5)

        plus1!!.setOnClickListener(this)
        plus2!!.setOnClickListener(this)
        plus3!!.setOnClickListener(this)
        plus4!!.setOnClickListener(this)
        plus5!!.setOnClickListener(this)

        manualAdjustmentsBottomSheet.show()
        manualAdjustmentsBottomSheet.window?.attributes?.windowAnimations = R.style.DialogAnimation

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.minus1 -> minute1.text = (minute1.text.toString().toInt()-1).toString()
            R.id.minus2 -> minute2.text = (minute2.text.toString().toInt()-1).toString()
            R.id.minus3 -> minute3.text = (minute3.text.toString().toInt()-1).toString()
            R.id.minus4 -> minute4.text = (minute4.text.toString().toInt()-1).toString()
            R.id.minus5 -> minute5.text = (minute5.text.toString().toInt()-1).toString()

            R.id.plus1 -> minute1.text = (minute1.text.toString().toInt()+1).toString()
            R.id.plus2 -> minute2.text = (minute2.text.toString().toInt()+1).toString()
            R.id.plus3 -> minute3.text = (minute3.text.toString().toInt()+1).toString()
            R.id.plus4 -> minute4.text = (minute4.text.toString().toInt()+1).toString()
            R.id.plus5 -> minute5.text = (minute5.text.toString().toInt()+1).toString()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}