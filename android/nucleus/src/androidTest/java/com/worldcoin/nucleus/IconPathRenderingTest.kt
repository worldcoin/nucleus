package com.worldcoin.nucleus

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.vector.PathParser
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertTrue
import org.junit.Test
import org.xmlpull.v1.XmlPullParser

class IconPathRenderingTest {
    @Test
    fun generatedPathsRenderLikeAndroidCoreParser() {
        val resources = InstrumentationRegistry.getInstrumentation().targetContext.resources
        val failures = mutableListOf<String>()
        val icons = R.drawable::class.java.fields.filter { it.name.startsWith("nucleus_icon_") }
        assertTrue("No icons discovered", icons.isNotEmpty())
        for (icon in icons) {
            resources.getXml(icon.getInt(null)).use { xml ->
                while (xml.next() != XmlPullParser.END_DOCUMENT) {
                    if (xml.eventType != XmlPullParser.START_TAG || xml.name != "path") continue
                    val data = xml.getAttributeValue(ANDROID_NAMESPACE, "pathData")
                    val fillType = if (xml.getAttributeIntValue(ANDROID_NAMESPACE, "fillType", 0) == 1) {
                        Path.FillType.EVEN_ODD
                    } else {
                        Path.FillType.WINDING
                    }
                    val reference = androidx.core.graphics.PathParser
                        .createPathFromPathData(data)!!
                    val actual = PathParser().parsePathString(data).toPath().asAndroidPath()
                    reference.fillType = fillType
                    actual.fillType = fillType
                    val expectedPixels = render(reference)
                    val actualPixels = render(actual)
                    // Allow small antialiasing differences between native path
                    // constructions, while detecting displaced or missing contours.
                    val mismatches = expectedPixels.indices.count {
                        kotlin.math.abs((expectedPixels[it] ushr 24) - (actualPixels[it] ushr 24)) > 64
                    }
                    if (mismatches > 4) failures += "${icon.name}: $mismatches pixels"
                }
            }
        }
        assertTrue("Compose/core rendering differences:\n${failures.joinToString("\n")}", failures.isEmpty())
    }

    private fun render(path: Path): IntArray {
        val bitmap = Bitmap.createBitmap(240, 240, Bitmap.Config.ARGB_8888)
        try {
            val canvas = Canvas(bitmap)
            canvas.scale(10f, 10f)
            canvas.drawPath(path, Paint(Paint.ANTI_ALIAS_FLAG))
            return IntArray(240 * 240).also { bitmap.getPixels(it, 0, 240, 0, 0, 240, 240) }
        } finally {
            bitmap.recycle()
        }
    }

    private companion object {
        const val ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android"
    }
}
