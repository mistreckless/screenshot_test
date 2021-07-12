package ru.alfabank.screenshottest

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.google.android.material.button.MaterialButton
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.alfabank.sreenshot_library.ScreenshotTest

class MainActivityTest {
    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun before() {
        InstrumentationRegistry.getInstrumentation().getUiAutomation()
    }

    @Test
    fun successTest() {
        val activity = activityTestRule.launchActivity(null)
        val view = activity.findViewById<MaterialButton>(R.id.capture_btn)
        ScreenshotTest.test(
            activity = activity,
            view = view,
            imageId = "button_success.png",
            similarity = 100.0
        )
    }

    @Test
    fun failedTest() {
        val activity = activityTestRule.launchActivity(null)
        val view = activity.findViewById<MaterialButton>(R.id.capture_btn)
        ScreenshotTest.test(
            activity = activity,
            view = view,
            imageId = "button_failed.png",
            similarity = 100.0
        )
    }
}