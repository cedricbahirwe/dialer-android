package com.cedricbahirwe.dialer

import com.cedricbahirwe.dialer.model.CodePin
import com.cedricbahirwe.dialer.model.DialerQuickCode
import org.junit.Test
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before

class DialerQuickCodeTests {
    @Before
    fun setUp() {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    @After
    fun tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    @Test
    fun testQuickCodesSuit() {
        testMomoQuickCode()
        testElectricityQuickCode()
        testOtherQuickCode()
    }

    @Test
    fun testMomoQuickCode() {
        val pin = makeCodePin("20000")
        val code1 =  DialerQuickCode.MobileWalletBalance(code = pin)
        assertEquals(code1.ussd, "*182*6*1*20000#")
        assertEquals(code1.ussd, "*182*6*1*${pin.asString}#")

        val code2 =  DialerQuickCode.MobileWalletBalance(code = null)
        assertEquals(code2.ussd, "*182*6*1#")
    }

    @Test
    fun testElectricityQuickCode() {
        val pin = makeCodePin(10_000)
        val meter = "1000000"
        val amount = 1_000
        val code1 =  DialerQuickCode.Electricity(meter = meter,
            amount = amount,
            code = pin)
        assertEquals(code1.ussd, "*182*2*2*1*1*1000000*1000*10000#")
        assertEquals(code1.ussd, "*182*2*2*1*1*1000000*1000*${pin.asString}#")

        val code2 =  DialerQuickCode.Electricity(meter = meter,
            amount = amount,
            code = null)
        assertEquals(code2.ussd, "*182*2*2*1*1*1000000*1000#")
    }

    @Test
    fun testOtherQuickCode() {
        val code1 =  DialerQuickCode.Other("*151#")
        val code2 =  DialerQuickCode.Other("*151*121#")
        val code3 =  DialerQuickCode.Other("*151*1*1#")
        assertEquals(code1.ussd, "*151#")
        assertEquals(code2.ussd, "*151*121#")
        assertEquals(code3.ussd, "*151*1*1#")
    }

    private fun makeCodePin(value: Int): CodePin {
        return CodePin(value)
    }

    private fun makeCodePin(value: String): CodePin {
        return CodePin(value)
    }
}
