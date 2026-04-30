package com.worldcoin.nucleus

import com.worldcoin.nucleus.card.CardCta

sealed interface CardEvent {
    data object Tap : CardEvent
    data class CtaTap(val cta: CardCta) : CardEvent
}
