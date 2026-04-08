package com.worldcoin.nucleusapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun HeroSection(
    theme: TokenTheme,
    appearanceThemes: List<AppearanceTheme>,
    selectedAppearanceId: String,
    onSelectAppearance: (String) -> Unit,
    demoSections: List<TokenSectionData>,
    selectedSectionId: String,
    onSelectSection: (String) -> Unit,
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Nucleus",
            style = MaterialTheme.typography.headlineLarge,
            color = theme.text,
        )

        Spacer(modifier = Modifier.weight(1f))

        AppearanceToggle(
            appearanceThemes = appearanceThemes,
            selectedAppearanceId = selectedAppearanceId,
            onSelect = onSelectAppearance,
            theme = theme,
        )

        Box {
            val menuShape = RoundedCornerShape(24.dp)

            HamburgerButton(
                color = theme.text,
                border = theme.border,
                onClick = { menuExpanded = true },
            )

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                modifier = Modifier.background(theme.surface, menuShape),
                shape = menuShape,
            ) {
                demoSections.forEach { section ->
                    val isSelected = section.id == selectedSectionId
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = if (isSelected) "${section.label}  \u2713" else section.label,
                                color = if (isSelected) theme.text else theme.muted,
                            )
                        },
                        onClick = {
                            onSelectSection(section.id)
                            menuExpanded = false
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun AppearanceToggle(
    appearanceThemes: List<AppearanceTheme>,
    selectedAppearanceId: String,
    onSelect: (String) -> Unit,
    theme: TokenTheme,
) {
    val pillShape = RoundedCornerShape(999.dp)

    Row(
        modifier = Modifier
            .border(1.dp, theme.border, pillShape)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        appearanceThemes.forEach { appearance ->
            val selected = appearance.id == selectedAppearanceId
            Box(
                modifier = Modifier
                    .background(
                        if (selected) theme.text else Color.Transparent,
                        pillShape,
                    )
                    .clip(pillShape)
                    .clickable { onSelect(appearance.id) }
                    .padding(horizontal = 14.dp, vertical = 8.dp),
            ) {
                Text(
                    text = appearance.name,
                    color = if (selected) theme.background else theme.text,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
fun HamburgerButton(
    color: Color,
    border: Color,
    onClick: () -> Unit,
) {
    val buttonShape = RoundedCornerShape(22.dp)

    Column(
        modifier = Modifier
            .size(44.dp)
            .clip(buttonShape)
            .border(1.dp, border, buttonShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 11.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(width = 18.dp, height = 2.dp)
                    .background(color, RoundedCornerShape(999.dp)),
            )
        }
    }
}

@Composable
fun TokenSectionCard(
    section: TokenSectionData,
    theme: TokenTheme,
) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        section.groups.forEach { group ->
            ColorGroupSection(group = group, theme = theme)
        }
    }
}

@Composable
fun ColorGroupSection(group: TokenGroup, theme: TokenTheme) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = group.name,
            style = MaterialTheme.typography.labelLarge,
            color = theme.muted,
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            group.tokens.forEach { token ->
                TokenCard(token = token, theme = theme)
            }
        }
    }
}

@Composable
fun TokenCard(token: TokenSwatch, theme: TokenTheme) {
    val clipboardManager = LocalClipboardManager.current
    var copied by remember { mutableStateOf(false) }
    val cardShape = RoundedCornerShape(18.dp)

    LaunchedEffect(copied) {
        if (copied) {
            delay(1200)
            copied = false
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(cardShape)
            .background(theme.surfaceAlt, cardShape)
            .border(1.dp, theme.border, cardShape)
            .clickable {
                clipboardManager.setText(AnnotatedString("#${token.hex}"))
                copied = true
            }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(token.color, RoundedCornerShape(12.dp))
                .border(1.dp, theme.border, RoundedCornerShape(12.dp)),
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = token.name,
                style = MaterialTheme.typography.bodyLarge,
                color = theme.text,
            )
            Text(
                text = "#${token.hex}",
                style = MaterialTheme.typography.bodySmall,
                color = theme.muted,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = if (copied) "Copied" else "Copy",
            style = MaterialTheme.typography.bodySmall,
            color = theme.muted,
        )
    }
}
