package ru.alfabank.matcher.figma

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class FigmaService(
    private val webClient: WebClient
) {

    fun getImage(nodeId: String): Mono<String> {
        return webClient.get()
            .uri("/images/jcbrP2ULUVYkNiXQ4HahzT?ids=$nodeId&format=png&scale=1")
            .retrieve()
            .bodyToMono(FigmaImageResponse::class.java)
            .map { it.images[nodeId] }
    }
}
