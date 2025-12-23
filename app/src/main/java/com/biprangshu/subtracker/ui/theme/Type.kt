package com.biprangshu.subtracker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.biprangshu.subtracker.R

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = AppFonts.googleSansDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = AppFonts.googleSansDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = AppFonts.googleSansDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),

    // HEADLINE: Use the "Headline" font (Mid Optical Size)
    headlineLarge = TextStyle(
        fontFamily = AppFonts.googleSansHeadline,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = AppFonts.googleSansHeadline,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = AppFonts.googleSansHeadline,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),

    // TITLE: Use the "Title" font (Mid-Low Optical Size)
    titleLarge = TextStyle(
        fontFamily = AppFonts.googleSansTitle,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = AppFonts.googleSansTitle,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = AppFonts.googleSansTitle,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    // BODY: Use the "Body" font (Low Optical Size, Readable)
    bodyLarge = TextStyle(
        fontFamily = AppFonts.googleSansBody,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = AppFonts.googleSansBody,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = AppFonts.googleSansBody,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),

    // LABEL: Use the "Body" font (Labels are small, so they need Low Optical Size)
    labelLarge = TextStyle(
        fontFamily = AppFonts.googleSansBody,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = AppFonts.googleSansBody,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = AppFonts.googleSansBody,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

object AppFonts {
    val googleSansFlexId = R.font.google_sans_flex

    /**
     * Display styles (Large text).
     * Material 3 Expressive often uses lighter weights or narrower widths for huge text,
     * but high Optical Sizing is key here for elegance.
     */
    @OptIn(ExperimentalTextApi::class)
    val googleSansDisplay = FontFamily(
        Font(
            resId = googleSansFlexId,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(400),
                FontVariation.grade(0),
                FontVariation.Setting("opsz", 144f)
            )
        )
    )

    /**
     * Headline styles.
     * Expressive layouts often feature prominent headlines.
     */
    @OptIn(ExperimentalTextApi::class)
    val googleSansHeadline = FontFamily(
        Font(
            resId = googleSansFlexId,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(500),
                FontVariation.grade(0),
                FontVariation.Setting("opsz", 48f)
            )
        )
    )

    /**
     * Title styles.
     * Used for shorter, medium-emphasis text.
     */
    @OptIn(ExperimentalTextApi::class)
    val googleSansTitle = FontFamily(
        Font(
            resId = googleSansFlexId,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(500),
                FontVariation.grade(0),
                FontVariation.Setting("opsz", 24f)
            )
        )
    )

    /**
     * Body styles (Reading text).
     * Needs low optical sizing for readability (thicker strokes, open counters).
     */
    @OptIn(ExperimentalTextApi::class)
    val googleSansBody = FontFamily(
        Font(
            resId = googleSansFlexId,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(400),
                FontVariation.grade(0),
                FontVariation.Setting("opsz", 14f)
            )
        )
    )

    /**
     * Optional: Dark Mode variant.
     * You can swap this in your Theme if isSystemInDarkTheme() is true.
     * Increases Grade (GRAD) to make white text on black background look optically correct.
     */
    @OptIn(ExperimentalTextApi::class)
    val googleSansBodyDark = FontFamily(
        Font(
            resId = googleSansFlexId,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(400),
                FontVariation.grade(150),
                FontVariation.Setting("opsz", 14f)
            )
        )
    )

    //roboto flex
    @OptIn(ExperimentalTextApi::class)
    val robotoFlexTopBar = FontFamily(
        Font(
            R.font.robotoflex_variable,
            variationSettings = FontVariation.Settings(
                FontVariation.width(125f),
                FontVariation.weight(1000),
                FontVariation.grade(0),
                FontVariation.Setting("XOPQ", 96F),
                FontVariation.Setting("XTRA", 500F),
                FontVariation.Setting("YOPQ", 79F),
                FontVariation.Setting("YTAS", 750F),
                FontVariation.Setting("YTDE", -203F),
                FontVariation.Setting("YTFI", 738F),
                FontVariation.Setting("YTLC", 514F),
                FontVariation.Setting("YTUC", 712F)
            )
        )
    )

    @OptIn(ExperimentalTextApi::class)
    val robotoFlexHeadline = FontFamily(
        Font(
            R.font.robotoflex_variable,
            variationSettings = FontVariation.Settings(
                FontVariation.width(130f),
                FontVariation.weight(600),
                FontVariation.grade(0)
            )
        )
    )

    @OptIn(ExperimentalTextApi::class)
    val robotoFlexTitle = FontFamily(
        Font(
            R.font.robotoflex_variable,
            variationSettings = FontVariation.Settings(
                FontVariation.width(130f),
                FontVariation.weight(700),
                FontVariation.grade(0)
            )
        )
    )
}