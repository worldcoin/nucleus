// This file is auto-generated. Do not edit manually.

package com.worldcoin.nucleus.tokens

import androidx.annotation.DrawableRes
import com.worldcoin.nucleus.R

/**
 * A Nucleus design token icon.
 *
 * Each icon is a singleton object whose variant availability is encoded in the type system:
 * an icon implements [HasOutline], [HasRegular], and/or [HasSolid] exactly for the variants
 * it ships, so the per-variant resource IDs are non-null and accessing a variant an icon
 * does not ship is a compile error — never a runtime crash.
 *
 * ```
 * Icon(
 *     painter = painterResource(NucleusIcon.ProfileCircle.solidRes),
 *     contentDescription = null,
 *     tint = ..., // drawables ship with a black placeholder fill — tint at the call site
 * )
 * ```
 *
 * When handling icons generically, branch on availability with a type check, for example
 * `if (icon is NucleusIcon.HasSolid) painterResource(icon.solidRes)`.
 */
sealed class NucleusIcon(val resourceName: String) {
    /**
     * Implemented by icons that ship an outline variant.
     */
    sealed interface HasOutline {
        @get:DrawableRes
        val outlineRes: Int
    }

    /**
     * Implemented by icons that ship a regular variant.
     */
    sealed interface HasRegular {
        @get:DrawableRes
        val regularRes: Int
    }

    /**
     * Implemented by icons that ship a solid variant.
     */
    sealed interface HasSolid {
        @get:DrawableRes
        val solidRes: Int
    }

    data object Airplane : NucleusIcon("airplane"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_airplane_outline
        override val regularRes get() = R.drawable.nucleus_icon_airplane_regular
        override val solidRes get() = R.drawable.nucleus_icon_airplane_solid
    }

    data object AntennaSignal : NucleusIcon("antenna-signal"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_antenna_signal_outline
        override val regularRes get() = R.drawable.nucleus_icon_antenna_signal_regular
        override val solidRes get() = R.drawable.nucleus_icon_antenna_signal_solid
    }

    data object AppleMac : NucleusIcon("apple-mac"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_apple_mac_outline
        override val regularRes get() = R.drawable.nucleus_icon_apple_mac_regular
        override val solidRes get() = R.drawable.nucleus_icon_apple_mac_solid
    }

    data object ArrowDown : NucleusIcon("arrow-down"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_arrow_down_outline
        override val regularRes get() = R.drawable.nucleus_icon_arrow_down_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_down_solid
    }

    data object ArrowDownCircle : NucleusIcon("arrow-down-circle"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_arrow_down_circle_outline
        override val regularRes get() = R.drawable.nucleus_icon_arrow_down_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_down_circle_solid
    }

    data object ArrowDownLeft : NucleusIcon("arrow-down-left"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_arrow_down_left_outline
        override val regularRes get() = R.drawable.nucleus_icon_arrow_down_left_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_down_left_solid
    }

    data object ArrowDownRight : NucleusIcon("arrow-down-right"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_arrow_down_right_outline
        override val regularRes get() = R.drawable.nucleus_icon_arrow_down_right_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_down_right_solid
    }

    data object ArrowLeft : NucleusIcon("arrow-left"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_arrow_left_outline
        override val regularRes get() = R.drawable.nucleus_icon_arrow_left_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_left_solid
    }

    data object ArrowRight : NucleusIcon("arrow-right"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_arrow_right_outline
        override val regularRes get() = R.drawable.nucleus_icon_arrow_right_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_right_solid
    }

    data object ArrowSplit : NucleusIcon("arrow-split"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_arrow_split_outline
        override val regularRes get() = R.drawable.nucleus_icon_arrow_split_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_split_solid
    }

    data object ArrowUp : NucleusIcon("arrow-up"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_arrow_up_outline
        override val regularRes get() = R.drawable.nucleus_icon_arrow_up_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_up_solid
    }

    data object ArrowUpCircle : NucleusIcon("arrow-up-circle"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_arrow_up_circle_outline
        override val regularRes get() = R.drawable.nucleus_icon_arrow_up_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_up_circle_solid
    }

    data object ArrowUpLeft : NucleusIcon("arrow-up-left"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_arrow_up_left_outline
        override val regularRes get() = R.drawable.nucleus_icon_arrow_up_left_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_up_left_solid
    }

    data object ArrowUpRight : NucleusIcon("arrow-up-right"), HasOutline, HasRegular {
        override val outlineRes get() = R.drawable.nucleus_icon_arrow_up_right_outline
        override val regularRes get() = R.drawable.nucleus_icon_arrow_up_right_regular
    }

    data object ArrowUturnLeft : NucleusIcon("arrow-uturn-left"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_arrow_uturn_left_outline
        override val regularRes get() = R.drawable.nucleus_icon_arrow_uturn_left_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_uturn_left_solid
    }

    data object ArrowUturnRight : NucleusIcon("arrow-uturn-right"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_arrow_uturn_right_outline
        override val regularRes get() = R.drawable.nucleus_icon_arrow_uturn_right_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_uturn_right_solid
    }

    data object ArrowsTransfer : NucleusIcon("arrows-transfer"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_arrows_transfer_outline
        override val regularRes get() = R.drawable.nucleus_icon_arrows_transfer_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrows_transfer_solid
    }

    data object AtSign : NucleusIcon("at-sign"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_at_sign_outline
        override val regularRes get() = R.drawable.nucleus_icon_at_sign_regular
        override val solidRes get() = R.drawable.nucleus_icon_at_sign_solid
    }

    data object BadgeCheck : NucleusIcon("badge-check"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_badge_check_outline
        override val regularRes get() = R.drawable.nucleus_icon_badge_check_regular
        override val solidRes get() = R.drawable.nucleus_icon_badge_check_solid
    }

    data object BadgeXmark : NucleusIcon("badge-xmark"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_badge_xmark_outline
        override val regularRes get() = R.drawable.nucleus_icon_badge_xmark_regular
        override val solidRes get() = R.drawable.nucleus_icon_badge_xmark_solid
    }

    data object Bag : NucleusIcon("bag"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_bag_outline
        override val regularRes get() = R.drawable.nucleus_icon_bag_regular
        override val solidRes get() = R.drawable.nucleus_icon_bag_solid
    }

    data object Bank : NucleusIcon("bank"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_bank_outline
        override val regularRes get() = R.drawable.nucleus_icon_bank_regular
        override val solidRes get() = R.drawable.nucleus_icon_bank_solid
    }

    data object Bell : NucleusIcon("bell"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_bell_outline
        override val regularRes get() = R.drawable.nucleus_icon_bell_regular
        override val solidRes get() = R.drawable.nucleus_icon_bell_solid
    }

    data object BellNotification : NucleusIcon("bell-notification"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_bell_notification_outline
        override val regularRes get() = R.drawable.nucleus_icon_bell_notification_regular
        override val solidRes get() = R.drawable.nucleus_icon_bell_notification_solid
    }

    data object BellSlash : NucleusIcon("bell-slash"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_bell_slash_outline
        override val regularRes get() = R.drawable.nucleus_icon_bell_slash_regular
        override val solidRes get() = R.drawable.nucleus_icon_bell_slash_solid
    }

    data object Book : NucleusIcon("book"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_book_outline
        override val regularRes get() = R.drawable.nucleus_icon_book_regular
        override val solidRes get() = R.drawable.nucleus_icon_book_solid
    }

    data object Bookmark : NucleusIcon("bookmark"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_bookmark_outline
        override val regularRes get() = R.drawable.nucleus_icon_bookmark_regular
        override val solidRes get() = R.drawable.nucleus_icon_bookmark_solid
    }

    data object BoxIso : NucleusIcon("box-iso"), HasOutline, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_box_iso_outline
        override val solidRes get() = R.drawable.nucleus_icon_box_iso_solid
    }

    data object Bus : NucleusIcon("bus"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_bus_outline
        override val regularRes get() = R.drawable.nucleus_icon_bus_regular
        override val solidRes get() = R.drawable.nucleus_icon_bus_solid
    }

    data object Calendar : NucleusIcon("calendar"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_calendar_outline
        override val regularRes get() = R.drawable.nucleus_icon_calendar_regular
        override val solidRes get() = R.drawable.nucleus_icon_calendar_solid
    }

    data object CalendarPlus : NucleusIcon("calendar-plus"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_calendar_plus_outline
        override val regularRes get() = R.drawable.nucleus_icon_calendar_plus_regular
        override val solidRes get() = R.drawable.nucleus_icon_calendar_plus_solid
    }

    data object Camera : NucleusIcon("camera"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_camera_outline
        override val regularRes get() = R.drawable.nucleus_icon_camera_regular
        override val solidRes get() = R.drawable.nucleus_icon_camera_solid
    }

    data object CardCredential : NucleusIcon("card-credential"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_card_credential_outline
        override val regularRes get() = R.drawable.nucleus_icon_card_credential_regular
        override val solidRes get() = R.drawable.nucleus_icon_card_credential_solid
    }

    data object CardCredit : NucleusIcon("card-credit"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_card_credit_outline
        override val regularRes get() = R.drawable.nucleus_icon_card_credit_regular
        override val solidRes get() = R.drawable.nucleus_icon_card_credit_solid
    }

    data object CardShield : NucleusIcon("card-shield"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_card_shield_outline
        override val regularRes get() = R.drawable.nucleus_icon_card_shield_regular
        override val solidRes get() = R.drawable.nucleus_icon_card_shield_solid
    }

    data object CardWorld : NucleusIcon("card-world"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_card_world_outline
        override val regularRes get() = R.drawable.nucleus_icon_card_world_regular
        override val solidRes get() = R.drawable.nucleus_icon_card_world_solid
    }

    data object Cash : NucleusIcon("cash"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_cash_outline
        override val regularRes get() = R.drawable.nucleus_icon_cash_regular
        override val solidRes get() = R.drawable.nucleus_icon_cash_solid
    }

    data object CashMulti : NucleusIcon("cash-multi"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_cash_multi_outline
        override val regularRes get() = R.drawable.nucleus_icon_cash_multi_regular
        override val solidRes get() = R.drawable.nucleus_icon_cash_multi_solid
    }

    data object Cellular : NucleusIcon("cellular"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_cellular_outline
        override val regularRes get() = R.drawable.nucleus_icon_cellular_regular
        override val solidRes get() = R.drawable.nucleus_icon_cellular_solid
    }

    data object CellularNo signal : NucleusIcon("cellular-no signal"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_cellular_no signal_outline
        override val regularRes get() = R.drawable.nucleus_icon_cellular_no signal_regular
        override val solidRes get() = R.drawable.nucleus_icon_cellular_no signal_solid
    }

    data object ChatBubble : NucleusIcon("chat-bubble"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_chat_bubble_outline
        override val regularRes get() = R.drawable.nucleus_icon_chat_bubble_regular
        override val solidRes get() = R.drawable.nucleus_icon_chat_bubble_solid
    }

    data object ChatBubbleEmpty : NucleusIcon("chat-bubble-empty"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_chat_bubble_empty_outline
        override val regularRes get() = R.drawable.nucleus_icon_chat_bubble_empty_regular
        override val solidRes get() = R.drawable.nucleus_icon_chat_bubble_empty_solid
    }

    data object ChatBubbleQuestion : NucleusIcon("chat-bubble-question"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_chat_bubble_question_outline
        override val regularRes get() = R.drawable.nucleus_icon_chat_bubble_question_regular
        override val solidRes get() = R.drawable.nucleus_icon_chat_bubble_question_solid
    }

    data object ChatBubbleTranslate : NucleusIcon("chat-bubble-translate"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_chat_bubble_translate_outline
        override val regularRes get() = R.drawable.nucleus_icon_chat_bubble_translate_regular
        override val solidRes get() = R.drawable.nucleus_icon_chat_bubble_translate_solid
    }

    data object ChatBubbleWarning : NucleusIcon("chat-bubble-warning"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_chat_bubble_warning_outline
        override val regularRes get() = R.drawable.nucleus_icon_chat_bubble_warning_regular
        override val solidRes get() = R.drawable.nucleus_icon_chat_bubble_warning_solid
    }

    data object ChatLines : NucleusIcon("chat-lines"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_chat_lines_outline
        override val regularRes get() = R.drawable.nucleus_icon_chat_lines_regular
        override val solidRes get() = R.drawable.nucleus_icon_chat_lines_solid
    }

    data object Check : NucleusIcon("check"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_check_outline
        override val regularRes get() = R.drawable.nucleus_icon_check_regular
        override val solidRes get() = R.drawable.nucleus_icon_check_solid
    }

    data object CheckCircle : NucleusIcon("check-circle"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_check_circle_outline
        override val regularRes get() = R.drawable.nucleus_icon_check_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_check_circle_solid
    }

    data object CheckDouble : NucleusIcon("check-double"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_check_double_outline
        override val regularRes get() = R.drawable.nucleus_icon_check_double_regular
        override val solidRes get() = R.drawable.nucleus_icon_check_double_solid
    }

    data object ChevronDown : NucleusIcon("chevron-down"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_chevron_down_outline
        override val regularRes get() = R.drawable.nucleus_icon_chevron_down_regular
        override val solidRes get() = R.drawable.nucleus_icon_chevron_down_solid
    }

    data object ChevronLeft : NucleusIcon("chevron-left"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_chevron_left_outline
        override val regularRes get() = R.drawable.nucleus_icon_chevron_left_regular
        override val solidRes get() = R.drawable.nucleus_icon_chevron_left_solid
    }

    data object ChevronRight : NucleusIcon("chevron-right"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_chevron_right_outline
        override val regularRes get() = R.drawable.nucleus_icon_chevron_right_regular
        override val solidRes get() = R.drawable.nucleus_icon_chevron_right_solid
    }

    data object ChevronUp : NucleusIcon("chevron-up"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_chevron_up_outline
        override val regularRes get() = R.drawable.nucleus_icon_chevron_up_regular
        override val solidRes get() = R.drawable.nucleus_icon_chevron_up_solid
    }

    data object ChevronsExpand : NucleusIcon("chevrons-expand"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_chevrons_expand_outline
        override val regularRes get() = R.drawable.nucleus_icon_chevrons_expand_regular
        override val solidRes get() = R.drawable.nucleus_icon_chevrons_expand_solid
    }

    data object ChevronsMinimize : NucleusIcon("chevrons-minimize"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_chevrons_minimize_outline
        override val regularRes get() = R.drawable.nucleus_icon_chevrons_minimize_regular
        override val solidRes get() = R.drawable.nucleus_icon_chevrons_minimize_solid
    }

    data object Circle : NucleusIcon("circle"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_circle_outline
        override val regularRes get() = R.drawable.nucleus_icon_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_circle_solid
    }

    data object Clock : NucleusIcon("clock"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_clock_outline
        override val regularRes get() = R.drawable.nucleus_icon_clock_regular
        override val solidRes get() = R.drawable.nucleus_icon_clock_solid
    }

    data object ClockRotateRight : NucleusIcon("clock-rotate-right"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_clock_rotate_right_outline
        override val regularRes get() = R.drawable.nucleus_icon_clock_rotate_right_regular
        override val solidRes get() = R.drawable.nucleus_icon_clock_rotate_right_solid
    }

    data object Cloud : NucleusIcon("cloud"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_cloud_outline
        override val regularRes get() = R.drawable.nucleus_icon_cloud_regular
        override val solidRes get() = R.drawable.nucleus_icon_cloud_solid
    }

    data object CloudDownload : NucleusIcon("cloud-download"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_cloud_download_outline
        override val regularRes get() = R.drawable.nucleus_icon_cloud_download_regular
        override val solidRes get() = R.drawable.nucleus_icon_cloud_download_solid
    }

    data object Coins : NucleusIcon("coins"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_coins_outline
        override val regularRes get() = R.drawable.nucleus_icon_coins_regular
        override val solidRes get() = R.drawable.nucleus_icon_coins_solid
    }

    data object CoinsStack : NucleusIcon("coins-stack"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_coins_stack_outline
        override val regularRes get() = R.drawable.nucleus_icon_coins_stack_regular
        override val solidRes get() = R.drawable.nucleus_icon_coins_stack_solid
    }

    data object Commodity : NucleusIcon("commodity"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_commodity_outline
        override val regularRes get() = R.drawable.nucleus_icon_commodity_regular
        override val solidRes get() = R.drawable.nucleus_icon_commodity_solid
    }

    data object Compass : NucleusIcon("compass"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_compass_outline
        override val regularRes get() = R.drawable.nucleus_icon_compass_regular
        override val solidRes get() = R.drawable.nucleus_icon_compass_solid
    }

    data object Compose : NucleusIcon("compose"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_compose_outline
        override val regularRes get() = R.drawable.nucleus_icon_compose_regular
        override val solidRes get() = R.drawable.nucleus_icon_compose_solid
    }

    data object ContactBook : NucleusIcon("contact-book"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_contact_book_outline
        override val regularRes get() = R.drawable.nucleus_icon_contact_book_regular
        override val solidRes get() = R.drawable.nucleus_icon_contact_book_solid
    }

    data object Copy : NucleusIcon("copy"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_copy_outline
        override val regularRes get() = R.drawable.nucleus_icon_copy_regular
        override val solidRes get() = R.drawable.nucleus_icon_copy_solid
    }

    data object Coupon : NucleusIcon("coupon"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_coupon_outline
        override val regularRes get() = R.drawable.nucleus_icon_coupon_regular
        override val solidRes get() = R.drawable.nucleus_icon_coupon_solid
    }

    data object Cube : NucleusIcon("cube"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_cube_outline
        override val regularRes get() = R.drawable.nucleus_icon_cube_regular
        override val solidRes get() = R.drawable.nucleus_icon_cube_solid
    }

    data object CursorPointer : NucleusIcon("cursor-pointer"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_cursor_pointer_outline
        override val regularRes get() = R.drawable.nucleus_icon_cursor_pointer_regular
        override val solidRes get() = R.drawable.nucleus_icon_cursor_pointer_solid
    }

    data object DecorativeSpark : NucleusIcon("decorative-spark"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_decorative_spark_outline
        override val regularRes get() = R.drawable.nucleus_icon_decorative_spark_regular
        override val solidRes get() = R.drawable.nucleus_icon_decorative_spark_solid
    }

    data object DeliveryCheck : NucleusIcon("delivery-check"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_delivery_check_outline
        override val regularRes get() = R.drawable.nucleus_icon_delivery_check_regular
        override val solidRes get() = R.drawable.nucleus_icon_delivery_check_solid
    }

    data object DeliveryTruck : NucleusIcon("delivery-truck"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_delivery_truck_outline
        override val regularRes get() = R.drawable.nucleus_icon_delivery_truck_regular
        override val solidRes get() = R.drawable.nucleus_icon_delivery_truck_solid
    }

    data object Dollar : NucleusIcon("dollar"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_dollar_outline
        override val regularRes get() = R.drawable.nucleus_icon_dollar_regular
        override val solidRes get() = R.drawable.nucleus_icon_dollar_solid
    }

    data object Download : NucleusIcon("download"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_download_outline
        override val regularRes get() = R.drawable.nucleus_icon_download_regular
        override val solidRes get() = R.drawable.nucleus_icon_download_solid
    }

    data object EditPencil : NucleusIcon("edit-pencil"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_edit_pencil_outline
        override val regularRes get() = R.drawable.nucleus_icon_edit_pencil_regular
        override val solidRes get() = R.drawable.nucleus_icon_edit_pencil_solid
    }

    data object Ellipsis : NucleusIcon("ellipsis"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_ellipsis_outline
        override val regularRes get() = R.drawable.nucleus_icon_ellipsis_regular
        override val solidRes get() = R.drawable.nucleus_icon_ellipsis_solid
    }

    data object EllipsisCircle : NucleusIcon("ellipsis-circle"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_ellipsis_circle_outline
        override val regularRes get() = R.drawable.nucleus_icon_ellipsis_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_ellipsis_circle_solid
    }

    data object EllipsisVertical : NucleusIcon("ellipsis-vertical"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_ellipsis_vertical_outline
        override val regularRes get() = R.drawable.nucleus_icon_ellipsis_vertical_regular
        override val solidRes get() = R.drawable.nucleus_icon_ellipsis_vertical_solid
    }

    data object EmptyPage : NucleusIcon("empty-page"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_empty_page_outline
        override val regularRes get() = R.drawable.nucleus_icon_empty_page_regular
        override val solidRes get() = R.drawable.nucleus_icon_empty_page_solid
    }

    data object Enlarge : NucleusIcon("enlarge"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_enlarge_outline
        override val regularRes get() = R.drawable.nucleus_icon_enlarge_regular
        override val solidRes get() = R.drawable.nucleus_icon_enlarge_solid
    }

    data object Eye : NucleusIcon("eye"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_eye_outline
        override val regularRes get() = R.drawable.nucleus_icon_eye_regular
        override val solidRes get() = R.drawable.nucleus_icon_eye_solid
    }

    data object EyeClosed : NucleusIcon("eye-closed"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_eye_closed_outline
        override val regularRes get() = R.drawable.nucleus_icon_eye_closed_regular
        override val solidRes get() = R.drawable.nucleus_icon_eye_closed_solid
    }

    data object FaceId : NucleusIcon("face-id"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_face_id_outline
        override val regularRes get() = R.drawable.nucleus_icon_face_id_regular
        override val solidRes get() = R.drawable.nucleus_icon_face_id_solid
    }

    data object FilterList : NucleusIcon("filter-list"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_filter_list_outline
        override val regularRes get() = R.drawable.nucleus_icon_filter_list_regular
        override val solidRes get() = R.drawable.nucleus_icon_filter_list_solid
    }

    data object Flash : NucleusIcon("flash"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_flash_outline
        override val regularRes get() = R.drawable.nucleus_icon_flash_regular
        override val solidRes get() = R.drawable.nucleus_icon_flash_solid
    }

    data object Flashlight : NucleusIcon("flashlight"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_flashlight_outline
        override val regularRes get() = R.drawable.nucleus_icon_flashlight_regular
        override val solidRes get() = R.drawable.nucleus_icon_flashlight_solid
    }

    data object FullScreenClose : NucleusIcon("full-screen-close"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_full_screen_close_outline
        override val regularRes get() = R.drawable.nucleus_icon_full_screen_close_regular
        override val solidRes get() = R.drawable.nucleus_icon_full_screen_close_solid
    }

    data object FullScreenOpen : NucleusIcon("full-screen-open"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_full_screen_open_outline
        override val regularRes get() = R.drawable.nucleus_icon_full_screen_open_regular
        override val solidRes get() = R.drawable.nucleus_icon_full_screen_open_solid
    }

    data object Gif : NucleusIcon("gif"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_gif_outline
        override val regularRes get() = R.drawable.nucleus_icon_gif_regular
        override val solidRes get() = R.drawable.nucleus_icon_gif_solid
    }

    data object Gift : NucleusIcon("gift"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_gift_outline
        override val regularRes get() = R.drawable.nucleus_icon_gift_regular
        override val solidRes get() = R.drawable.nucleus_icon_gift_solid
    }

    data object GiftCard : NucleusIcon("gift-card"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_gift_card_outline
        override val regularRes get() = R.drawable.nucleus_icon_gift_card_regular
        override val solidRes get() = R.drawable.nucleus_icon_gift_card_solid
    }

    data object Glasses : NucleusIcon("glasses"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_glasses_outline
        override val regularRes get() = R.drawable.nucleus_icon_glasses_regular
        override val solidRes get() = R.drawable.nucleus_icon_glasses_solid
    }

    data object Globe : NucleusIcon("globe"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_globe_outline
        override val regularRes get() = R.drawable.nucleus_icon_globe_regular
        override val solidRes get() = R.drawable.nucleus_icon_globe_solid
    }

    data object GraduationCap : NucleusIcon("graduation-cap"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_graduation_cap_outline
        override val regularRes get() = R.drawable.nucleus_icon_graduation_cap_regular
        override val solidRes get() = R.drawable.nucleus_icon_graduation_cap_solid
    }

    data object GraphDown : NucleusIcon("graph-down"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_graph_down_outline
        override val regularRes get() = R.drawable.nucleus_icon_graph_down_regular
        override val solidRes get() = R.drawable.nucleus_icon_graph_down_solid
    }

    data object GraphUp : NucleusIcon("graph-up"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_graph_up_outline
        override val regularRes get() = R.drawable.nucleus_icon_graph_up_regular
        override val solidRes get() = R.drawable.nucleus_icon_graph_up_solid
    }

    data object Group : NucleusIcon("group"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_group_outline
        override val regularRes get() = R.drawable.nucleus_icon_group_regular
        override val solidRes get() = R.drawable.nucleus_icon_group_solid
    }

    data object HalfMoon : NucleusIcon("half-moon"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_half_moon_outline
        override val regularRes get() = R.drawable.nucleus_icon_half_moon_regular
        override val solidRes get() = R.drawable.nucleus_icon_half_moon_solid
    }

    data object Heart : NucleusIcon("heart"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_heart_outline
        override val regularRes get() = R.drawable.nucleus_icon_heart_regular
        override val solidRes get() = R.drawable.nucleus_icon_heart_solid
    }

    data object HelpCircle : NucleusIcon("help-circle"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_help_circle_outline
        override val regularRes get() = R.drawable.nucleus_icon_help_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_help_circle_solid
    }

    data object Home : NucleusIcon("home"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_home_outline
        override val regularRes get() = R.drawable.nucleus_icon_home_regular
        override val solidRes get() = R.drawable.nucleus_icon_home_solid
    }

    data object HumanEmblem : NucleusIcon("human-emblem"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_human_emblem_outline
        override val regularRes get() = R.drawable.nucleus_icon_human_emblem_regular
        override val solidRes get() = R.drawable.nucleus_icon_human_emblem_solid
    }

    data object InfoCircle : NucleusIcon("info-circle"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_info_circle_outline
        override val regularRes get() = R.drawable.nucleus_icon_info_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_info_circle_solid
    }

    data object Key : NucleusIcon("key"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_key_outline
        override val regularRes get() = R.drawable.nucleus_icon_key_regular
        override val solidRes get() = R.drawable.nucleus_icon_key_solid
    }

    data object KeyReal : NucleusIcon("key-real"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_key_real_outline
        override val regularRes get() = R.drawable.nucleus_icon_key_real_regular
        override val solidRes get() = R.drawable.nucleus_icon_key_real_solid
    }

    data object Language : NucleusIcon("language"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_language_outline
        override val regularRes get() = R.drawable.nucleus_icon_language_regular
        override val solidRes get() = R.drawable.nucleus_icon_language_solid
    }

    data object Legal : NucleusIcon("legal"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_legal_outline
        override val regularRes get() = R.drawable.nucleus_icon_legal_regular
        override val solidRes get() = R.drawable.nucleus_icon_legal_solid
    }

    data object Link : NucleusIcon("link"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_link_outline
        override val regularRes get() = R.drawable.nucleus_icon_link_regular
        override val solidRes get() = R.drawable.nucleus_icon_link_solid
    }

    data object LinkSlash : NucleusIcon("link-slash"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_link_slash_outline
        override val regularRes get() = R.drawable.nucleus_icon_link_slash_regular
        override val solidRes get() = R.drawable.nucleus_icon_link_slash_solid
    }

    data object List : NucleusIcon("list"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_list_outline
        override val regularRes get() = R.drawable.nucleus_icon_list_regular
        override val solidRes get() = R.drawable.nucleus_icon_list_solid
    }

    data object Lock : NucleusIcon("lock"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_lock_outline
        override val regularRes get() = R.drawable.nucleus_icon_lock_regular
        override val solidRes get() = R.drawable.nucleus_icon_lock_solid
    }

    data object LogIn : NucleusIcon("log-in"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_log_in_outline
        override val regularRes get() = R.drawable.nucleus_icon_log_in_regular
        override val solidRes get() = R.drawable.nucleus_icon_log_in_solid
    }

    data object LogOut : NucleusIcon("log-out"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_log_out_outline
        override val regularRes get() = R.drawable.nucleus_icon_log_out_regular
        override val solidRes get() = R.drawable.nucleus_icon_log_out_solid
    }

    data object MagicWand : NucleusIcon("magic-wand"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_magic_wand_outline
        override val regularRes get() = R.drawable.nucleus_icon_magic_wand_regular
        override val solidRes get() = R.drawable.nucleus_icon_magic_wand_solid
    }

    data object Mail : NucleusIcon("mail"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_mail_outline
        override val regularRes get() = R.drawable.nucleus_icon_mail_regular
        override val solidRes get() = R.drawable.nucleus_icon_mail_solid
    }

    data object Map : NucleusIcon("map"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_map_outline
        override val regularRes get() = R.drawable.nucleus_icon_map_regular
        override val solidRes get() = R.drawable.nucleus_icon_map_solid
    }

    data object MapPin : NucleusIcon("map-pin"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_map_pin_outline
        override val regularRes get() = R.drawable.nucleus_icon_map_pin_regular
        override val solidRes get() = R.drawable.nucleus_icon_map_pin_solid
    }

    data object Microphone : NucleusIcon("microphone"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_microphone_outline
        override val regularRes get() = R.drawable.nucleus_icon_microphone_regular
        override val solidRes get() = R.drawable.nucleus_icon_microphone_solid
    }

    data object Minus : NucleusIcon("minus"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_minus_outline
        override val regularRes get() = R.drawable.nucleus_icon_minus_regular
        override val solidRes get() = R.drawable.nucleus_icon_minus_solid
    }

    data object MinusCircle : NucleusIcon("minus-circle"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_minus_circle_outline
        override val regularRes get() = R.drawable.nucleus_icon_minus_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_minus_circle_solid
    }

    data object Navigation : NucleusIcon("navigation"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_navigation_outline
        override val regularRes get() = R.drawable.nucleus_icon_navigation_regular
        override val solidRes get() = R.drawable.nucleus_icon_navigation_solid
    }

    data object NavigationDiagonal : NucleusIcon("navigation-diagonal"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_navigation_diagonal_outline
        override val regularRes get() = R.drawable.nucleus_icon_navigation_diagonal_regular
        override val solidRes get() = R.drawable.nucleus_icon_navigation_diagonal_solid
    }

    data object OpenNewWindow : NucleusIcon("open-new-window"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_open_new_window_outline
        override val regularRes get() = R.drawable.nucleus_icon_open_new_window_regular
        override val solidRes get() = R.drawable.nucleus_icon_open_new_window_solid
    }

    data object OrbDiamond : NucleusIcon("orb-diamond"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_orb_diamond_outline
        override val regularRes get() = R.drawable.nucleus_icon_orb_diamond_regular
        override val solidRes get() = R.drawable.nucleus_icon_orb_diamond_solid
    }

    data object OrbPearl : NucleusIcon("orb-pearl"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_orb_pearl_outline
        override val regularRes get() = R.drawable.nucleus_icon_orb_pearl_regular
        override val solidRes get() = R.drawable.nucleus_icon_orb_pearl_solid
    }

    data object Page : NucleusIcon("page"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_page_outline
        override val regularRes get() = R.drawable.nucleus_icon_page_regular
        override val solidRes get() = R.drawable.nucleus_icon_page_solid
    }

    data object Password : NucleusIcon("password"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_password_outline
        override val regularRes get() = R.drawable.nucleus_icon_password_regular
        override val solidRes get() = R.drawable.nucleus_icon_password_solid
    }

    data object Percentage : NucleusIcon("percentage"), HasOutline, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_percentage_outline
        override val solidRes get() = R.drawable.nucleus_icon_percentage_solid
    }

    data object Person : NucleusIcon("person"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_person_outline
        override val regularRes get() = R.drawable.nucleus_icon_person_regular
        override val solidRes get() = R.drawable.nucleus_icon_person_solid
    }

    data object PersonCircle : NucleusIcon("person-circle"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_person_circle_outline
        override val regularRes get() = R.drawable.nucleus_icon_person_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_person_circle_solid
    }

    data object PersonKey : NucleusIcon("person-key"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_person_key_outline
        override val regularRes get() = R.drawable.nucleus_icon_person_key_regular
        override val solidRes get() = R.drawable.nucleus_icon_person_key_solid
    }

    data object Photo : NucleusIcon("photo"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_photo_outline
        override val regularRes get() = R.drawable.nucleus_icon_photo_regular
        override val solidRes get() = R.drawable.nucleus_icon_photo_solid
    }

    data object Pin : NucleusIcon("pin"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_pin_outline
        override val regularRes get() = R.drawable.nucleus_icon_pin_regular
        override val solidRes get() = R.drawable.nucleus_icon_pin_solid
    }

    data object Placeholder : NucleusIcon("placeholder"), HasOutline {
        override val outlineRes get() = R.drawable.nucleus_icon_placeholder_outline
    }

    data object Play : NucleusIcon("play"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_play_outline
        override val regularRes get() = R.drawable.nucleus_icon_play_regular
        override val solidRes get() = R.drawable.nucleus_icon_play_solid
    }

    data object Plus : NucleusIcon("plus"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_plus_outline
        override val regularRes get() = R.drawable.nucleus_icon_plus_regular
        override val solidRes get() = R.drawable.nucleus_icon_plus_solid
    }

    data object PlusCircle : NucleusIcon("plus-circle"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_plus_circle_outline
        override val regularRes get() = R.drawable.nucleus_icon_plus_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_plus_circle_solid
    }

    data object Post : NucleusIcon("post"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_post_outline
        override val regularRes get() = R.drawable.nucleus_icon_post_regular
        override val solidRes get() = R.drawable.nucleus_icon_post_solid
    }

    data object Power : NucleusIcon("power"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_power_outline
        override val regularRes get() = R.drawable.nucleus_icon_power_regular
        override val solidRes get() = R.drawable.nucleus_icon_power_solid
    }

    data object Prohibition : NucleusIcon("prohibition"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_prohibition_outline
        override val regularRes get() = R.drawable.nucleus_icon_prohibition_regular
        override val solidRes get() = R.drawable.nucleus_icon_prohibition_solid
    }

    data object QrCode : NucleusIcon("qr-code"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_qr_code_outline
        override val regularRes get() = R.drawable.nucleus_icon_qr_code_regular
        override val solidRes get() = R.drawable.nucleus_icon_qr_code_solid
    }

    data object Refresh : NucleusIcon("refresh"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_refresh_outline
        override val regularRes get() = R.drawable.nucleus_icon_refresh_regular
        override val solidRes get() = R.drawable.nucleus_icon_refresh_solid
    }

    data object RefreshDouble : NucleusIcon("refresh-double"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_refresh_double_outline
        override val regularRes get() = R.drawable.nucleus_icon_refresh_double_regular
        override val solidRes get() = R.drawable.nucleus_icon_refresh_double_solid
    }

    data object Reports : NucleusIcon("reports"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_reports_outline
        override val regularRes get() = R.drawable.nucleus_icon_reports_regular
        override val solidRes get() = R.drawable.nucleus_icon_reports_solid
    }

    data object Safe : NucleusIcon("safe"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_safe_outline
        override val regularRes get() = R.drawable.nucleus_icon_safe_regular
        override val solidRes get() = R.drawable.nucleus_icon_safe_solid
    }

    data object Scan : NucleusIcon("scan"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_scan_outline
        override val regularRes get() = R.drawable.nucleus_icon_scan_regular
        override val solidRes get() = R.drawable.nucleus_icon_scan_solid
    }

    data object Search : NucleusIcon("search"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_search_outline
        override val regularRes get() = R.drawable.nucleus_icon_search_regular
        override val solidRes get() = R.drawable.nucleus_icon_search_solid
    }

    data object Send : NucleusIcon("send"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_send_outline
        override val regularRes get() = R.drawable.nucleus_icon_send_regular
        override val solidRes get() = R.drawable.nucleus_icon_send_solid
    }

    data object SendMail : NucleusIcon("send-mail"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_send_mail_outline
        override val regularRes get() = R.drawable.nucleus_icon_send_mail_regular
        override val solidRes get() = R.drawable.nucleus_icon_send_mail_solid
    }

    data object Settings : NucleusIcon("settings"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_settings_outline
        override val regularRes get() = R.drawable.nucleus_icon_settings_regular
        override val solidRes get() = R.drawable.nucleus_icon_settings_solid
    }

    data object ShareIos : NucleusIcon("share-ios"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_share_ios_outline
        override val regularRes get() = R.drawable.nucleus_icon_share_ios_regular
        override val solidRes get() = R.drawable.nucleus_icon_share_ios_solid
    }

    data object Shield : NucleusIcon("shield"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_shield_outline
        override val regularRes get() = R.drawable.nucleus_icon_shield_regular
        override val solidRes get() = R.drawable.nucleus_icon_shield_solid
    }

    data object ShieldAlert : NucleusIcon("shield-alert"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_shield_alert_outline
        override val regularRes get() = R.drawable.nucleus_icon_shield_alert_regular
        override val solidRes get() = R.drawable.nucleus_icon_shield_alert_solid
    }

    data object ShieldCheck : NucleusIcon("shield-check"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_shield_check_outline
        override val regularRes get() = R.drawable.nucleus_icon_shield_check_regular
        override val solidRes get() = R.drawable.nucleus_icon_shield_check_solid
    }

    data object ShieldHalf : NucleusIcon("shield-half"), HasOutline, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_shield_half_outline
        override val solidRes get() = R.drawable.nucleus_icon_shield_half_solid
    }

    data object Smartphone : NucleusIcon("smartphone"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_smartphone_outline
        override val regularRes get() = R.drawable.nucleus_icon_smartphone_regular
        override val solidRes get() = R.drawable.nucleus_icon_smartphone_solid
    }

    data object SnowFlake : NucleusIcon("snow-flake"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_snow_flake_outline
        override val regularRes get() = R.drawable.nucleus_icon_snow_flake_regular
        override val solidRes get() = R.drawable.nucleus_icon_snow_flake_solid
    }

    data object SocialInstagram : NucleusIcon("social-instagram"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_social_instagram_outline
        override val regularRes get() = R.drawable.nucleus_icon_social_instagram_regular
        override val solidRes get() = R.drawable.nucleus_icon_social_instagram_solid
    }

    data object SocialX : NucleusIcon("social-x"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_social_x_outline
        override val regularRes get() = R.drawable.nucleus_icon_social_x_regular
        override val solidRes get() = R.drawable.nucleus_icon_social_x_solid
    }

    data object SoftwareUpdateSetting : NucleusIcon("software-update-setting"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_software_update_setting_outline
        override val regularRes get() = R.drawable.nucleus_icon_software_update_setting_regular
        override val solidRes get() = R.drawable.nucleus_icon_software_update_setting_solid
    }

    data object Sort : NucleusIcon("sort"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_sort_outline
        override val regularRes get() = R.drawable.nucleus_icon_sort_regular
        override val solidRes get() = R.drawable.nucleus_icon_sort_solid
    }

    data object SortDown : NucleusIcon("sort-down"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_sort_down_outline
        override val regularRes get() = R.drawable.nucleus_icon_sort_down_regular
        override val solidRes get() = R.drawable.nucleus_icon_sort_down_solid
    }

    data object SortUp : NucleusIcon("sort-up"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_sort_up_outline
        override val regularRes get() = R.drawable.nucleus_icon_sort_up_regular
        override val solidRes get() = R.drawable.nucleus_icon_sort_up_solid
    }

    data object Spark : NucleusIcon("spark"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_spark_outline
        override val regularRes get() = R.drawable.nucleus_icon_spark_regular
        override val solidRes get() = R.drawable.nucleus_icon_spark_solid
    }

    data object Sparks : NucleusIcon("sparks"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_sparks_outline
        override val regularRes get() = R.drawable.nucleus_icon_sparks_regular
        override val solidRes get() = R.drawable.nucleus_icon_sparks_solid
    }

    data object Star : NucleusIcon("star"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_star_outline
        override val regularRes get() = R.drawable.nucleus_icon_star_regular
        override val solidRes get() = R.drawable.nucleus_icon_star_solid
    }

    data object StatsUpSquare : NucleusIcon("stats-up-square"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_stats_up_square_outline
        override val regularRes get() = R.drawable.nucleus_icon_stats_up_square_regular
        override val solidRes get() = R.drawable.nucleus_icon_stats_up_square_solid
    }

    data object Suitcase : NucleusIcon("suitcase"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_suitcase_outline
        override val regularRes get() = R.drawable.nucleus_icon_suitcase_regular
        override val solidRes get() = R.drawable.nucleus_icon_suitcase_solid
    }

    data object Sun : NucleusIcon("sun"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_sun_outline
        override val regularRes get() = R.drawable.nucleus_icon_sun_regular
        override val solidRes get() = R.drawable.nucleus_icon_sun_solid
    }

    data object Tag : NucleusIcon("tag"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_tag_outline
        override val regularRes get() = R.drawable.nucleus_icon_tag_regular
        override val solidRes get() = R.drawable.nucleus_icon_tag_solid
    }

    data object Text : NucleusIcon("text"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_text_outline
        override val regularRes get() = R.drawable.nucleus_icon_text_regular
        override val solidRes get() = R.drawable.nucleus_icon_text_solid
    }

    data object Timer : NucleusIcon("timer"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_timer_outline
        override val regularRes get() = R.drawable.nucleus_icon_timer_regular
        override val solidRes get() = R.drawable.nucleus_icon_timer_solid
    }

    data object TimerDots : NucleusIcon("timer-dots"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_timer_dots_outline
        override val regularRes get() = R.drawable.nucleus_icon_timer_dots_regular
        override val solidRes get() = R.drawable.nucleus_icon_timer_dots_solid
    }

    data object Toolkit : NucleusIcon("toolkit"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_toolkit_outline
        override val regularRes get() = R.drawable.nucleus_icon_toolkit_regular
        override val solidRes get() = R.drawable.nucleus_icon_toolkit_solid
    }

    data object Trash : NucleusIcon("trash"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_trash_outline
        override val regularRes get() = R.drawable.nucleus_icon_trash_regular
        override val solidRes get() = R.drawable.nucleus_icon_trash_solid
    }

    data object Trophy : NucleusIcon("trophy"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_trophy_outline
        override val regularRes get() = R.drawable.nucleus_icon_trophy_regular
        override val solidRes get() = R.drawable.nucleus_icon_trophy_solid
    }

    data object VideoCamera : NucleusIcon("video-camera"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_video_camera_outline
        override val regularRes get() = R.drawable.nucleus_icon_video_camera_regular
        override val solidRes get() = R.drawable.nucleus_icon_video_camera_solid
    }

    data object ViewGrid : NucleusIcon("view-grid"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_view_grid_outline
        override val regularRes get() = R.drawable.nucleus_icon_view_grid_regular
        override val solidRes get() = R.drawable.nucleus_icon_view_grid_solid
    }

    data object Voice : NucleusIcon("voice"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_voice_outline
        override val regularRes get() = R.drawable.nucleus_icon_voice_regular
        override val solidRes get() = R.drawable.nucleus_icon_voice_solid
    }

    data object Wallet : NucleusIcon("wallet"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_wallet_outline
        override val regularRes get() = R.drawable.nucleus_icon_wallet_regular
        override val solidRes get() = R.drawable.nucleus_icon_wallet_solid
    }

    data object WarningCircle : NucleusIcon("warning-circle"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_warning_circle_outline
        override val regularRes get() = R.drawable.nucleus_icon_warning_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_warning_circle_solid
    }

    data object WarningHexagon : NucleusIcon("warning-hexagon"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_warning_hexagon_outline
        override val regularRes get() = R.drawable.nucleus_icon_warning_hexagon_regular
        override val solidRes get() = R.drawable.nucleus_icon_warning_hexagon_solid
    }

    data object WarningTriangle : NucleusIcon("warning-triangle"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_warning_triangle_outline
        override val regularRes get() = R.drawable.nucleus_icon_warning_triangle_regular
        override val solidRes get() = R.drawable.nucleus_icon_warning_triangle_solid
    }

    data object Wifi : NucleusIcon("wifi"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_wifi_outline
        override val regularRes get() = R.drawable.nucleus_icon_wifi_regular
        override val solidRes get() = R.drawable.nucleus_icon_wifi_solid
    }

    data object WifiSignalNone : NucleusIcon("wifi-signal-none"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_wifi_signal_none_outline
        override val regularRes get() = R.drawable.nucleus_icon_wifi_signal_none_regular
        override val solidRes get() = R.drawable.nucleus_icon_wifi_signal_none_solid
    }

    data object Worldcoin : NucleusIcon("worldcoin"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_worldcoin_outline
        override val regularRes get() = R.drawable.nucleus_icon_worldcoin_regular
        override val solidRes get() = R.drawable.nucleus_icon_worldcoin_solid
    }

    data object Xmark : NucleusIcon("xmark"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_xmark_outline
        override val regularRes get() = R.drawable.nucleus_icon_xmark_regular
        override val solidRes get() = R.drawable.nucleus_icon_xmark_solid
    }

    data object XmarkCircle : NucleusIcon("xmark-circle"), HasOutline, HasRegular, HasSolid {
        override val outlineRes get() = R.drawable.nucleus_icon_xmark_circle_outline
        override val regularRes get() = R.drawable.nucleus_icon_xmark_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_xmark_circle_solid
    }

    companion object {
        /**
         * Every Nucleus icon, in stable alphabetical order. The type is fully qualified because
         * the [List] icon object shadows `kotlin.collections.List` inside this class body.
         */
        val all: kotlin.collections.List<NucleusIcon> = listOf(
            Airplane,
            AntennaSignal,
            AppleMac,
            ArrowDown,
            ArrowDownCircle,
            ArrowDownLeft,
            ArrowDownRight,
            ArrowLeft,
            ArrowRight,
            ArrowSplit,
            ArrowUp,
            ArrowUpCircle,
            ArrowUpLeft,
            ArrowUpRight,
            ArrowUturnLeft,
            ArrowUturnRight,
            ArrowsTransfer,
            AtSign,
            BadgeCheck,
            BadgeXmark,
            Bag,
            Bank,
            Bell,
            BellNotification,
            BellSlash,
            Book,
            Bookmark,
            BoxIso,
            Bus,
            Calendar,
            CalendarPlus,
            Camera,
            CardCredential,
            CardCredit,
            CardShield,
            CardWorld,
            Cash,
            CashMulti,
            Cellular,
            CellularNo signal,
            ChatBubble,
            ChatBubbleEmpty,
            ChatBubbleQuestion,
            ChatBubbleTranslate,
            ChatBubbleWarning,
            ChatLines,
            Check,
            CheckCircle,
            CheckDouble,
            ChevronDown,
            ChevronLeft,
            ChevronRight,
            ChevronUp,
            ChevronsExpand,
            ChevronsMinimize,
            Circle,
            Clock,
            ClockRotateRight,
            Cloud,
            CloudDownload,
            Coins,
            CoinsStack,
            Commodity,
            Compass,
            Compose,
            ContactBook,
            Copy,
            Coupon,
            Cube,
            CursorPointer,
            DecorativeSpark,
            DeliveryCheck,
            DeliveryTruck,
            Dollar,
            Download,
            EditPencil,
            Ellipsis,
            EllipsisCircle,
            EllipsisVertical,
            EmptyPage,
            Enlarge,
            Eye,
            EyeClosed,
            FaceId,
            FilterList,
            Flash,
            Flashlight,
            FullScreenClose,
            FullScreenOpen,
            Gif,
            Gift,
            GiftCard,
            Glasses,
            Globe,
            GraduationCap,
            GraphDown,
            GraphUp,
            Group,
            HalfMoon,
            Heart,
            HelpCircle,
            Home,
            HumanEmblem,
            InfoCircle,
            Key,
            KeyReal,
            Language,
            Legal,
            Link,
            LinkSlash,
            List,
            Lock,
            LogIn,
            LogOut,
            MagicWand,
            Mail,
            Map,
            MapPin,
            Microphone,
            Minus,
            MinusCircle,
            Navigation,
            NavigationDiagonal,
            OpenNewWindow,
            OrbDiamond,
            OrbPearl,
            Page,
            Password,
            Percentage,
            Person,
            PersonCircle,
            PersonKey,
            Photo,
            Pin,
            Placeholder,
            Play,
            Plus,
            PlusCircle,
            Post,
            Power,
            Prohibition,
            QrCode,
            Refresh,
            RefreshDouble,
            Reports,
            Safe,
            Scan,
            Search,
            Send,
            SendMail,
            Settings,
            ShareIos,
            Shield,
            ShieldAlert,
            ShieldCheck,
            ShieldHalf,
            Smartphone,
            SnowFlake,
            SocialInstagram,
            SocialX,
            SoftwareUpdateSetting,
            Sort,
            SortDown,
            SortUp,
            Spark,
            Sparks,
            Star,
            StatsUpSquare,
            Suitcase,
            Sun,
            Tag,
            Text,
            Timer,
            TimerDots,
            Toolkit,
            Trash,
            Trophy,
            VideoCamera,
            ViewGrid,
            Voice,
            Wallet,
            WarningCircle,
            WarningHexagon,
            WarningTriangle,
            Wifi,
            WifiSignalNone,
            Worldcoin,
            Xmark,
            XmarkCircle,
        )
    }
}
