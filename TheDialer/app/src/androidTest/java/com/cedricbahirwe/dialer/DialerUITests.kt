package com.cedricbahirwe.dialer

import androidx.test.runner.AndroidJUnitRunner
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DialerUITest : AndroidJUnitRunner() {
    private lateinit var app: UiDevice

    @Before
    fun setUp() {
        app = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        app.pressHome()
        val launcherPackage = getLauncherPackageName()
        assertThat(launcherPackage, notNullValue())
        app.launchPackage(launcherPackage)
    }

    @After
    fun tearDown() {
        app.pressHome()
    }

    @Test
    fun testHomeComponentDisplay() {
        val element = app.findObject(UiSelector().className(android.widget.Button::class.java))
        println("Here is: $element ***")

        testHomeMenuItemsDisplay()

        testDialButtonDisplay()

        testNewDialViewComponents()
    }

    private fun testHomeMenuItemsDisplay() {
        assertTrue(app.findObject(UiSelector().text("Buy airtime")).exists())
        assertTrue(app.findObject(UiSelector().text("Transfer/Pay")).exists())
        assertTrue(app.findObject(UiSelector().text("History")).exists())
    }

    private fun testNewDialViewComponents() {
        app.findObject(UiSelector().text("Quick Dial")).click()
        // Delete button is hidden on first view appearance
        assertFalse(app.findObject(UiSelector().text("Backspace")).isEnabled)
    }

    private fun testDialButtonDisplay() {
        assertTrue(app.findObject(UiSelector().text("Quick Dial")).exists())
        assertTrue(app.findObject(UiSelector().text("Quick Dial")).exists())
    }

    private fun getLauncherPackageName(): String? {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        val pm = InstrumentationRegistry.getInstrumentation().context.packageManager
        val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfo.activityInfo.packageName
    }
}
