package com.github.libretube.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.preference.Preference
import com.github.libretube.R
import com.github.libretube.constants.PreferenceKeys
import com.github.libretube.constants.PreferenceRanges
import com.github.libretube.databinding.DialogSliderBinding
import com.github.libretube.extensions.setSliderRangeAndValue
import com.github.libretube.util.PreferenceHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Preference that includes a slider
 */
class SliderPreference(
    context: Context,
    attributeSet: AttributeSet
) : Preference(
    context,
    attributeSet
) {
    private lateinit var sliderBinding: DialogSliderBinding

    override fun onClick() {
        sliderBinding = DialogSliderBinding.inflate(
            LayoutInflater.from(context)
        )
        val range = when (key) {
            PreferenceKeys.PLAYBACK_SPEED -> PreferenceRanges.playbackSpeed
            PreferenceKeys.BACKGROUND_PLAYBACK_SPEED -> PreferenceRanges.playbackSpeed
            PreferenceKeys.SEEK_INCREMENT -> PreferenceRanges.seekIncrement
            else -> null
        }

        if (range == null) return

        sliderBinding.slider.setSliderRangeAndValue(range)

        sliderBinding.slider.value = PreferenceHelper.getString(
            key,
            range.defaultValue.toString()
        ).toFloat()

        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setView(sliderBinding.root)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.okay) { _, _ ->
                PreferenceHelper.putString(
                    key,
                    sliderBinding.slider.value.toString()
                )
            }
            .show()
        super.onClick()
    }
}
