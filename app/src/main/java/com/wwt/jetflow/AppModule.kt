package com.wwt.jetflow

import com.wwt.jetflow.home.login.LoginViewModel
import com.wwt.jetflow.utilis.ResourcesHelper
import org.koin.core.component.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object AppModule : KoinComponent {
    fun init() {

        val genericModules = module {
            single { ResourcesHelper(get()) }
            single { LoginViewModel(get()) }
        }

        val moduleList = listOf(
            genericModules,
        )
        loadKoinModules(moduleList)
    }
}