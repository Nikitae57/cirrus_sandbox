package ru.nikitae57.cirrussandbox.second.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton
import ru.nikitae57.cirrussandbox.R
import ru.nikitae57.cirrussandbox.second.SecondFragment

object SecondScreen : KScreen<SecondScreen>() {
    override val layoutId = R.layout.fragment_second
    override val viewClass = SecondFragment::class.java

    val previousButton = KButton { withId(R.id.button_second) }
}