package com.instantsystem.newsly.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun `should launch app successfully`() {
        composeTestRule.waitForIdle()
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun `should display content after loading`() {
        composeTestRule.waitForIdle()

        composeTestRule.waitUntil(timeoutMillis = 60000) {
            try {
                composeTestRule.onRoot().assertExists()
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
        composeTestRule.onRoot().assertIsDisplayed()
    }
}