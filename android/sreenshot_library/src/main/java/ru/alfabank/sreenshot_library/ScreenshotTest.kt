package ru.alfabank.sreenshot_library

import android.app.Activity
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.alfabank.sreenshot_library.service.MatchStatus
import ru.alfabank.sreenshot_library.service.MatcherService

object ScreenshotTest {

    private val matcherService by lazy {
        MatcherService.build()
    }

    fun test(activity: Activity, view: View, imageId: String, similarity: Double = 100.0) {
        val multipartFile = FileBuilder.buildMultipartFile(activity, view)
        matcherService.compare(multipartFile, imageId, similarity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { response ->
                assert(response.status == MatchStatus.SUCCESS, lazyMessage = {
                    "assertion error match status ${response.status} difference ${response.difference}"
                })
            }
            .subscribe()
    }

}