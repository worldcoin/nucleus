package com.worldcoin.nucleus

sealed interface CardEvent {
    data object Tap : CardEvent
    data class CtaTap(val cta: CardCta) : CardEvent
}
