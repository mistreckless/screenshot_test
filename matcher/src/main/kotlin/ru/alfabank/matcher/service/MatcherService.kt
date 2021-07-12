package ru.alfabank.matcher.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono
import ru.alfabank.matcher.components.ImageComparator
import ru.alfabank.matcher.components.ImageCompareResult
import ru.alfabank.matcher.components.ImageFileFactory
import ru.alfabank.matcher.controller.MatchResponse
import ru.alfabank.matcher.controller.MatchStatus
import ru.alfabank.matcher.utils.component1
import ru.alfabank.matcher.utils.component2

interface MatcherService {
    fun compare(actualFile: MultipartFile, expectedFileId: String, similarity: Double): Mono<MatchResponse>
}

@Service
class DefaultMatcherService @Autowired constructor(
    private val imageFileFactory: ImageFileFactory,
    private val imageComparator: ImageComparator
) : MatcherService {

    override fun compare(actualFile: MultipartFile, expectedFileId: String, similarity: Double): Mono<MatchResponse> {
        val expectedImageMono = imageFileFactory.createFromLocalFileId(expectedFileId)
        val actualImageMono = imageFileFactory.createFromMultipartFile(actualFile)

        return Mono.zip(expectedImageMono, actualImageMono)
            .flatMap { (expectedImage, actualImage) ->
                imageComparator.getDifferencePercent(expectedImage, actualImage)
                    .filter { result -> result is ImageCompareResult.Compared }
                    .flatMap { result ->
                        Mono.just(result)
                            .cast(ImageCompareResult.Compared::class.java)
                            .map { compareResult ->
                                val success = (100 - compareResult.difference) >= similarity
                                MatchResponse(
                                    status = if (success) MatchStatus.SUCCESS else MatchStatus.FAILED,
                                    difference = compareResult.difference
                                )
                            }

                    }
                    .defaultIfEmpty(MatchResponse(
                        status = MatchStatus.WRONG_DIMENSION,
                        difference = 0.0
                    ))
            }
            .onErrorReturn(MatchResponse(
                status = MatchStatus.WRONG_IMAGE_ID,
                difference = 0.0
            ))
    }
}
