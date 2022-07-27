package ru.nikitae57.cirrussandbox.second.screens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import ru.nikitae57.cirrussandbox.second.FirstFragment
import ru.nikitae57.cirrussandbox.R


object FirstScreen : KScreen<FirstScreen>() {
    override val layoutId = R.layout.fragment_first
    override val viewClass = FirstFragment::class.java

    val textView = KTextView { withId(R.id.textview_first) }
    val nextButton = KButton { withId(R.id.button_first) }
    val fab = KButton { withId(R.id.fab) }
}