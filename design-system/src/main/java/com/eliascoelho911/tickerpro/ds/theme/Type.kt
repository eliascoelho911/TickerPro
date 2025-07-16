package com.eliascoelho911.tickerpro.ds.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.eliascoelho911.tickerpro.ds.R

val HelveticaNeueFontFamily = FontFamily(
    Font(R.font.helvetica_neue_regular, FontWeight.Normal),
    Font(R.font.helvetica_neue_medium, FontWeight.Medium),
    Font(R.font.helvetica_neue_bold, FontWeight.Bold),
)

val LatoFontFamily = FontFamily(
    Font(R.font.lato_regular, FontWeight.Normal),
    Font(R.font.lato_bold, FontWeight.Medium),
    Font(R.font.lato_bold, FontWeight.Bold),
)

val JosefinSansFontFamily = FontFamily(
    Font(R.font.josefin_sans_medium),
)

val Typography = Typography().let { defaultTypography ->
    defaultTypography.copy(
        displayLarge = defaultTypography.displayLarge.copy(
            fontFamily = LatoFontFamily,
        ),
        displayMedium = defaultTypography.displayMedium.copy(
            fontFamily = LatoFontFamily,
        ),
        displaySmall = defaultTypography.displaySmall.copy(
            fontFamily = LatoFontFamily,
        ),
        headlineLarge = defaultTypography.headlineLarge.copy(
            fontFamily = LatoFontFamily,
        ),
        headlineMedium = defaultTypography.headlineMedium.copy(
            fontFamily = LatoFontFamily,
        ),
        headlineSmall = defaultTypography.headlineSmall.copy(
            fontFamily = LatoFontFamily,
        ),
        titleLarge = defaultTypography.titleLarge.copy(
            fontFamily = LatoFontFamily,
        ),
        titleMedium = defaultTypography.titleMedium.copy(
            fontFamily = LatoFontFamily,
        ),
        titleSmall = defaultTypography.titleSmall.copy(
            fontFamily = LatoFontFamily,
        ),
        bodyLarge = defaultTypography.bodyLarge.copy(
            fontFamily = LatoFontFamily,
        ),
        bodyMedium = defaultTypography.bodyMedium.copy(
            fontFamily = LatoFontFamily,
        ),
        bodySmall = defaultTypography.bodySmall.copy(
            fontFamily = LatoFontFamily,
        ),
        labelLarge = defaultTypography.labelLarge.copy(
            fontFamily = LatoFontFamily,
        ),
        labelMedium = defaultTypography.labelMedium.copy(
            fontFamily = LatoFontFamily,
        ),
        labelSmall = defaultTypography.labelSmall.copy(
            fontFamily = LatoFontFamily,
        ),
    )
}