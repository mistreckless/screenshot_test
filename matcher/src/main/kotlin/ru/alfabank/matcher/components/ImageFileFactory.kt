package ru.alfabank.matcher.components

import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

interface ImageFileFactory {

    fun createFromMultipartFile(file: MultipartFile): Mono<BufferedImage>

    fun createFromLocalFileId(fileId: String): Mono<BufferedImage>
}

@Component
class DefaultImageFileFactory : ImageFileFactory {

    override fun createFromMultipartFile(file: MultipartFile): Mono<BufferedImage> {
        val fileInputStream = file.inputStream
        return Mono.just(ImageIO.read(fileInputStream))
    }

    override fun createFromLocalFileId(fileId: String): Mono<BufferedImage> {
        val file = ClassPathResource(fileId).file
        return Mono.just(ImageIO.read(file))
    }

}
