package ru.alfabank.matcher.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono
import ru.alfabank.matcher.service.MatcherService

@RestController
class MatcherController {

    @Autowired
    lateinit var matcherService: MatcherService

    @PostMapping(value = ["compare"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun compare(@RequestParam("file") file: MultipartFile,
                @RequestParam("imageId") imageId: String,
                @RequestParam("similarity") similarity: Double): Mono<MatchResponse> {
        println("CALLED COMPARE")
        return matcherService.compare(file, imageId, similarity)
    }
}
