package ru.nikitae57.cirrussandbox.main

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import ru.nikitae57.cirrussandbox.R

object MainScreen : KScreen<MainScreen>() {
    override val layoutId = R.layout.fragment_main
    override val viewClass = MainFragment::class.java

    val progressBar = KView { withId(R.id.progressBar) }
    val label = KTextView { withId(R.id.label) }
    val button = KButton { withId(R.id.button) }
}