package ru.nikitae57.cirrussandbox

import android.content.Context
import com.kaspersky.kaspresso.files.resources.ResourcesRootDirsProvider
import java.io.File

class CustomRootDirsProvider(context: Context) : ResourcesRootDirsProvider {
    override val logcatRootDir = File(context.filesDir, "logcat/")
    override val screenshotsRootDir = File(context.filesDir, "screenshots/")
    override val videoRootDir = File(context.filesDir, "video/")
    override val viewHierarchy = File(context.filesDir, "view_hierarchy/")
}