package com.github.pr1st0n.cflintintellij.services

import com.intellij.openapi.project.Project
import com.github.pr1st0n.cflintintellij.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
