package com.example.headlinehunter.util

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f
)
annotation class FontScalePreviews

@Preview(
    name = "phone",
    device = "spec:width=360dp,height=640dp,dpi=480"
)
@Preview(
    name = "landscape",
    device = "spec:width=640dp,height=360dp,dpi=480"
)
@Preview(
    name = "foldable",
    device = "spec:width=673dp,height=841dp,dpi=480"
)
@Preview(
    name = "tablet",
    device = "spec:width=1280dp,height=800dp,dpi=480"
)
annotation class DevicePreviews
