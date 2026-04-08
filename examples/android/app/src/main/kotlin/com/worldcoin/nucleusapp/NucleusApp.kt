package com.worldcoin.nucleusapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun NucleusApp() {
    val context = LocalContext.current
    val layoutDirection = LocalLayoutDirection.current
    val tokenCatalog = remember(context) { loadTokenCatalog(context) }
    val defaultAppearanceId = tokenCatalog.appearanceThemes.firstOrNull { it.id == "dark" }?.id
        ?: tokenCatalog.appearanceThemes.first().id
    val systemBarPadding = WindowInsets.safeDrawing.asPaddingValues()
    val leftInset = systemBarPadding.calculateLeftPadding(layoutDirection)
    val rightInset = systemBarPadding.calculateRightPadding(layoutDirection)
    val horizontalInset = if (leftInset > rightInset) leftInset else rightInset
    val contentPadding = PaddingValues(
        start = horizontalInset + 16.dp,
        top = systemBarPadding.calculateTopPadding(),
        end = horizontalInset + 16.dp,
        bottom = systemBarPadding.calculateBottomPadding() + 16.dp,
    )

    var selectedSectionId by rememberSaveable { mutableStateOf(tokenCatalog.tokenSections.first().id) }
    var selectedAppearanceId by rememberSaveable { mutableStateOf(defaultAppearanceId) }

    val selectedSection = tokenCatalog.tokenSections.firstOrNull { it.id == selectedSectionId } ?: tokenCatalog.tokenSections.first()
    val activeAppearance = tokenCatalog.appearanceThemes.firstOrNull { it.id == selectedAppearanceId } ?: tokenCatalog.appearanceThemes.first()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(activeAppearance.theme.background),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            HeroSection(
                theme = activeAppearance.theme,
                appearanceThemes = tokenCatalog.appearanceThemes,
                selectedAppearanceId = selectedAppearanceId,
                onSelectAppearance = { selectedAppearanceId = it },
                demoSections = tokenCatalog.tokenSections,
                selectedSectionId = selectedSectionId,
                onSelectSection = { selectedSectionId = it },
            )
        }

        item {
            TokenSectionCard(
                section = selectedSection,
                theme = activeAppearance.theme,
            )
        }
    }
}
