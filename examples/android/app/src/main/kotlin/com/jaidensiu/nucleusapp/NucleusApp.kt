@file:OptIn(ExperimentalFoundationApi::class)

package com.worldcoin.nucleusapp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.worldcoin.nucleus.NucleusPrimitiveColors

@Composable
fun NucleusApp() {
    Scaffold(
        topBar = {
            Text(
                text = "Nucleus",
                style = MaterialTheme.typography.headlineLarge,
                color = NucleusPrimitiveColors.grey950,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NucleusPrimitiveColors.grey50.copy(alpha = 0.92f))
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp),
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(NucleusPrimitiveColors.grey50)
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(primitiveGroups) { group ->
                ColorGroupSection(group)
            }
        }
    }
}

@Composable
private fun ColorGroupSection(group: TokenGroup) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = group.name,
            style = MaterialTheme.typography.titleMedium,
            color = NucleusPrimitiveColors.grey950,
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            group.tokens.forEach { token ->
                ColorCard(token)
            }
        }
    }
}

@Composable
private fun ColorCard(token: TokenSwatch) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(NucleusPrimitiveColors.grey0)
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .border(1.dp, NucleusPrimitiveColors.grey200, RoundedCornerShape(12.dp))
                    .background(token.color, RoundedCornerShape(12.dp)),
            )

            Column {
                Text(
                    text = token.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = NucleusPrimitiveColors.grey950,
                )
                Text(
                    text = token.hex,
                    style = MaterialTheme.typography.bodySmall,
                    color = NucleusPrimitiveColors.grey700,
                )
            }
        }
    }
}

private data class TokenGroup(
    val name: String,
    val tokens: List<TokenSwatch>,
)

private data class TokenSwatch(
    val name: String,
    val color: Color,
    val hex: String,
)

private val primitiveGroups = listOf(
    TokenGroup(
        name = "Grey",
        tokens = listOf(
            TokenSwatch("grey0", NucleusPrimitiveColors.grey0, "#FFFFFF"),
            TokenSwatch("grey50", NucleusPrimitiveColors.grey50, "#F9FAFB"),
            TokenSwatch("grey100", NucleusPrimitiveColors.grey100, "#F2F3F5"),
            TokenSwatch("grey200", NucleusPrimitiveColors.grey200, "#EAEBED"),
            TokenSwatch("grey300", NucleusPrimitiveColors.grey300, "#D7D9DC"),
            TokenSwatch("grey400", NucleusPrimitiveColors.grey400, "#B5B7BA"),
            TokenSwatch("grey500", NucleusPrimitiveColors.grey500, "#9FA2A5"),
            TokenSwatch("grey600", NucleusPrimitiveColors.grey600, "#8A8C8F"),
            TokenSwatch("grey700", NucleusPrimitiveColors.grey700, "#747679"),
            TokenSwatch("grey800", NucleusPrimitiveColors.grey800, "#595B5E"),
            TokenSwatch("grey900", NucleusPrimitiveColors.grey900, "#3F4144"),
            TokenSwatch("grey950", NucleusPrimitiveColors.grey950, "#17181A"),
        ),
    ),
    TokenGroup(
        name = "Error",
        tokens = listOf(
            TokenSwatch("error100", NucleusPrimitiveColors.error100, "#FFF8F7"),
            TokenSwatch("error200", NucleusPrimitiveColors.error200, "#FFD7D1"),
            TokenSwatch("error300", NucleusPrimitiveColors.error300, "#FFB4A8"),
            TokenSwatch("error400", NucleusPrimitiveColors.error400, "#FF8D7E"),
            TokenSwatch("error500", NucleusPrimitiveColors.error500, "#FC6150"),
            TokenSwatch("error600", NucleusPrimitiveColors.error600, "#F2261D"),
            TokenSwatch("error700", NucleusPrimitiveColors.error700, "#B8110C"),
            TokenSwatch("error800", NucleusPrimitiveColors.error800, "#7F0001"),
            TokenSwatch("error900", NucleusPrimitiveColors.error900, "#490000"),
        ),
    ),
    TokenGroup(
        name = "Warning",
        tokens = listOf(
            TokenSwatch("warning100", NucleusPrimitiveColors.warning100, "#FFF9F1"),
            TokenSwatch("warning200", NucleusPrimitiveColors.warning200, "#FFEBD0"),
            TokenSwatch("warning300", NucleusPrimitiveColors.warning300, "#FFDDAD"),
            TokenSwatch("warning400", NucleusPrimitiveColors.warning400, "#FFCE87"),
            TokenSwatch("warning500", NucleusPrimitiveColors.warning500, "#FFBF5A"),
            TokenSwatch("warning600", NucleusPrimitiveColors.warning600, "#FEAF00"),
            TokenSwatch("warning700", NucleusPrimitiveColors.warning700, "#B47A00"),
            TokenSwatch("warning800", NucleusPrimitiveColors.warning800, "#6F4A00"),
            TokenSwatch("warning900", NucleusPrimitiveColors.warning900, "#311F00"),
        ),
    ),
    TokenGroup(
        name = "Success",
        tokens = listOf(
            TokenSwatch("success100", NucleusPrimitiveColors.success100, "#F1FEF1"),
            TokenSwatch("success200", NucleusPrimitiveColors.success200, "#CEF4CE"),
            TokenSwatch("success300", NucleusPrimitiveColors.success300, "#AAE8AB"),
            TokenSwatch("success400", NucleusPrimitiveColors.success400, "#84DC88"),
            TokenSwatch("success500", NucleusPrimitiveColors.success500, "#58CF62"),
            TokenSwatch("success600", NucleusPrimitiveColors.success600, "#00C235"),
            TokenSwatch("success700", NucleusPrimitiveColors.success700, "#008C24"),
            TokenSwatch("success800", NucleusPrimitiveColors.success800, "#005A14"),
            TokenSwatch("success900", NucleusPrimitiveColors.success900, "#002C06"),
        ),
    ),
    TokenGroup(
        name = "Info",
        tokens = listOf(
            TokenSwatch("info100", NucleusPrimitiveColors.info100, "#F7FAFF"),
            TokenSwatch("info200", NucleusPrimitiveColors.info200, "#CADEFF"),
            TokenSwatch("info300", NucleusPrimitiveColors.info300, "#9CC2FF"),
            TokenSwatch("info400", NucleusPrimitiveColors.info400, "#6DA4FF"),
            TokenSwatch("info500", NucleusPrimitiveColors.info500, "#3A84FF"),
            TokenSwatch("info600", NucleusPrimitiveColors.info600, "#0064EE"),
            TokenSwatch("info700", NucleusPrimitiveColors.info700, "#004BB7"),
            TokenSwatch("info800", NucleusPrimitiveColors.info800, "#003484"),
            TokenSwatch("info900", NucleusPrimitiveColors.info900, "#001E53"),
        ),
    ),
)
