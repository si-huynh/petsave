package dev.sihuynh.petsave.core.model.user

data class UserData(
    val token: Token,
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
)
