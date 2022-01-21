package com.wwt.jetflow

import com.wwt.jetflow.welcome.WelcomeViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object AppModule : KoinComponent {
    fun init(){
        val welcomeModules = module {
            single{WelcomeViewModel()}
        }

        val moduleList = listOf(
            welcomeModules
        )
        loadKoinModules(moduleList)
    }
}