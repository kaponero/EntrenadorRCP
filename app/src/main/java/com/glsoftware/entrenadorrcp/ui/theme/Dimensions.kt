package com.glsoftware.entrenadorrcp.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val small:Dp,
    val smallMedium:Dp,
    val medium:Dp,
    val mediumLarge:Dp,
    val large:Dp,
    val radio1:Dp,
    val radio2:Dp,
    val radio1f:Float,
    val radio2f:Float,
    val mano_size1:Dp,
    val mano_size2:Dp,
)

val smallDimensions = Dimensions(
    small = 2.dp,
    smallMedium = 4.dp,
    medium = 6.dp,
    mediumLarge = 9.dp,
    large = 13.dp,
    radio1 = 150.dp,
    radio2 = 100.dp,
    radio1f = 220f,
    radio2f = 92f,
    mano_size1 = 80.dp,
    mano_size2 = 60.dp
)

val compactDimensions = Dimensions(
    small = 3.dp,
    smallMedium = 5.dp,
    medium = 8.dp,
    mediumLarge = 11.dp,
    large = 15.dp,
    radio1 = 200.dp,
    radio2 = 115.dp,
    radio1f = 180f,
    radio2f = 72f,
    mano_size1 = 90.dp,
    mano_size2 = 70.dp
)

val mediumDimensions = Dimensions(
    small = 4.dp,
    smallMedium = 7.dp,
    medium = 10.dp,
    mediumLarge = 13.dp,
    large = 18.dp,
    radio1 = 250.dp,
    radio2 = 130.dp,
    radio1f = 150f,
    radio2f = 52f,
    mano_size1 = 100.dp,
    mano_size2 = 80.dp

)

val largeDimensions = Dimensions(
    small = 8.dp,
    smallMedium = 11.dp,
    medium = 15.dp,
    mediumLarge = 20.dp,
    large = 25.dp,
    radio1 = 300.dp,
    radio2 = 150.dp,
    radio1f = 120f,
    radio2f = 32f,
    mano_size1 = 110.dp,
    mano_size2 = 90.dp
)

val xlargeDimensions = Dimensions(
    small = 12.dp,
    smallMedium = 15.dp,
    medium = 20.dp,
    mediumLarge = 25.dp,
    large = 30.dp,
    radio1 = 350.dp,
    radio2 = 170.dp,
    radio1f = 100f,
    radio2f = 22f,
    mano_size1 = 110.dp,
    mano_size2 = 90.dp
)