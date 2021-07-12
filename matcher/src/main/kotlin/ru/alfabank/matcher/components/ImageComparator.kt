package ru.alfabank.matcher.components

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.awt.image.BufferedImage
import kotlin.math.abs

sealed class ImageCompareResult {
    object WrongDimension: ImageCompareResult()
    data class Compared(val difference: Double): ImageCompareResult()
}

@Component
class ImageComparator {

    fun getDifferencePercent(img1: BufferedImage, img2: BufferedImage): Mono<ImageCompareResult> {
        val width = img1.width
        val height = img1.height
        val width2 = img2.width
        val height2 = img2.height
        if (width != width2 || height != height2) {
            return Mono.just(ImageCompareResult.WrongDimension)
        }
        var diff = 0L
        for (y in 0 until height) {
            for (x in 0 until width) {
                diff += pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y))
            }
        }
        val maxDiff = 3L * 255 * width * height
        return Mono.just(ImageCompareResult.Compared(100.0 * diff / maxDiff))
    }

    private fun pixelDiff(rgb1: Int, rgb2: Int): Int {
        val r1 = (rgb1 shr 16) and 0xff
        val g1 = (rgb1 shr 8)  and 0xff
        val b1 =  rgb1         and 0xff
        val r2 = (rgb2 shr 16) and 0xff
        val g2 = (rgb2 shr 8)  and 0xff
        val b2 =  rgb2         and 0xff
        return abs(r1 - r2) + abs(g1 - g2) + abs(b1 - b2)
    }
}
