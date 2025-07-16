package com.eliascoelho911.ebookreader.extension

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project

internal val Project.android: BaseExtension
    get() = extensions.findByName("android") as? BaseExtension
        ?: error("Not an Android module: $name")