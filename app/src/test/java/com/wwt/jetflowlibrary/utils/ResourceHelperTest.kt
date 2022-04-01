package com.wwt.jetflowlibrary.utils

import android.content.Context
import com.wwt.jetflow.R
import com.wwt.jetflow.utilis.ResourcesHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ResourceHelperTest {

    @Mock
    private lateinit var context: Context

    @Test
    fun `get string resource return string value`() {
        Mockito.`when`(context.getString(R.string.app_name))
            .thenReturn("String Result")

        val testObject = ResourcesHelper(context)

        Assert.assertEquals(
            "String Result",
            testObject.getStringResource(R.string.app_name)
        )
    }
}