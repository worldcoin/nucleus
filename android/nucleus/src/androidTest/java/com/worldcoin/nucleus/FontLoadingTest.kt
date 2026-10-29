package com.worldcoin.nucleus

import android.graphics.Paint
import androidx.core.content.res.ResourcesCompat
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class FontLoadingTest {
    @Test
    fun generatedFontResourceLoadsWithPrivateUseGlyph() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val typeface = ResourcesCompat.getFont(context, R.font.world_pro)
        assertNotNull("Generated World Pro font could not be loaded", typeface)
        val paint = Paint().apply {
            this.typeface = typeface
            textSize = 32f
        }
        // U+E000 is the add-calendar icon in the supplied codepoint map.
        assertTrue("World Pro icon glyph is missing", paint.hasGlyph("\uE000"))
    }
}
