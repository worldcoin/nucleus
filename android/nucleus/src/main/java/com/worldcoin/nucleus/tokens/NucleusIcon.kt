// This file is auto-generated. Do not edit manually.

package com.worldcoin.nucleus.tokens

import androidx.annotation.DrawableRes
import com.worldcoin.nucleus.R

/**
 * A Nucleus design token icon.
 *
 * Each icon is a singleton object whose variant availability is encoded in the type system:
 * an icon implements [HasRegular] and/or [HasSolid] exactly for the variants
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

    data object Airplane : NucleusIcon("airplane"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_airplane_regular
        override val solidRes get() = R.drawable.nucleus_icon_airplane_solid
    }

    data object AntennaSignal : NucleusIcon("antenna-signal"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_antenna_signal_regular
        override val solidRes get() = R.drawable.nucleus_icon_antenna_signal_solid
    }

    data object ArrowDown : NucleusIcon("arrow-down"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_arrow_down_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_down_solid
    }

    data object ArrowDownCircle : NucleusIcon("arrow-down-circle"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_arrow_down_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_down_circle_solid
    }

    data object ArrowDownLeft : NucleusIcon("arrow-down-left"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_arrow_down_left_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_down_left_solid
    }

    data object ArrowDownRight : NucleusIcon("arrow-down-right"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_arrow_down_right_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_down_right_solid
    }

    data object ArrowLeft : NucleusIcon("arrow-left"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_arrow_left_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_left_solid
    }

    data object ArrowRight : NucleusIcon("arrow-right"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_arrow_right_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_right_solid
    }

    data object ArrowSplit : NucleusIcon("arrow-split"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_arrow_split_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_split_solid
    }

    data object ArrowUp : NucleusIcon("arrow-up"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_arrow_up_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_up_solid
    }

    data object ArrowUpCircle : NucleusIcon("arrow-up-circle"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_arrow_up_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_up_circle_solid
    }

    data object ArrowUpLeft : NucleusIcon("arrow-up-left"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_arrow_up_left_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_up_left_solid
    }

    data object ArrowUpRight : NucleusIcon("arrow-up-right"), HasRegular {
        override val regularRes get() = R.drawable.nucleus_icon_arrow_up_right_regular
    }

    data object ArrowUturnLeft : NucleusIcon("arrow-uturn-left"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_arrow_uturn_left_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_uturn_left_solid
    }

    data object ArrowUturnRight : NucleusIcon("arrow-uturn-right"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_arrow_uturn_right_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrow_uturn_right_solid
    }

    data object ArrowsTransfer : NucleusIcon("arrows-transfer"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_arrows_transfer_regular
        override val solidRes get() = R.drawable.nucleus_icon_arrows_transfer_solid
    }

    data object AtSign : NucleusIcon("at-sign"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_at_sign_regular
        override val solidRes get() = R.drawable.nucleus_icon_at_sign_solid
    }

    data object BadgeCheck : NucleusIcon("badge-check"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_badge_check_regular
        override val solidRes get() = R.drawable.nucleus_icon_badge_check_solid
    }

    data object BadgeXmark : NucleusIcon("badge-xmark"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_badge_xmark_regular
        override val solidRes get() = R.drawable.nucleus_icon_badge_xmark_solid
    }

    data object Bag : NucleusIcon("bag"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_bag_regular
        override val solidRes get() = R.drawable.nucleus_icon_bag_solid
    }

    data object Bank : NucleusIcon("bank"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_bank_regular
        override val solidRes get() = R.drawable.nucleus_icon_bank_solid
    }

    data object Bell : NucleusIcon("bell"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_bell_regular
        override val solidRes get() = R.drawable.nucleus_icon_bell_solid
    }

    data object BellNotification : NucleusIcon("bell-notification"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_bell_notification_regular
        override val solidRes get() = R.drawable.nucleus_icon_bell_notification_solid
    }

    data object BellSlash : NucleusIcon("bell-slash"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_bell_slash_regular
        override val solidRes get() = R.drawable.nucleus_icon_bell_slash_solid
    }

    data object Book : NucleusIcon("book"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_book_regular
        override val solidRes get() = R.drawable.nucleus_icon_book_solid
    }

    data object Bookmark : NucleusIcon("bookmark"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_bookmark_regular
        override val solidRes get() = R.drawable.nucleus_icon_bookmark_solid
    }

    data object BoxIso : NucleusIcon("box-iso"), HasSolid {
        override val solidRes get() = R.drawable.nucleus_icon_box_iso_solid
    }

    data object BrandApple : NucleusIcon("brand-apple"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_brand_apple_regular
        override val solidRes get() = R.drawable.nucleus_icon_brand_apple_solid
    }

    data object BrandInstagram : NucleusIcon("brand-instagram"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_brand_instagram_regular
        override val solidRes get() = R.drawable.nucleus_icon_brand_instagram_solid
    }

    data object BrandX : NucleusIcon("brand-x"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_brand_x_regular
        override val solidRes get() = R.drawable.nucleus_icon_brand_x_solid
    }

    data object Bus : NucleusIcon("bus"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_bus_regular
        override val solidRes get() = R.drawable.nucleus_icon_bus_solid
    }

    data object Calendar : NucleusIcon("calendar"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_calendar_regular
        override val solidRes get() = R.drawable.nucleus_icon_calendar_solid
    }

    data object CalendarPlus : NucleusIcon("calendar-plus"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_calendar_plus_regular
        override val solidRes get() = R.drawable.nucleus_icon_calendar_plus_solid
    }

    data object Camera : NucleusIcon("camera"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_camera_regular
        override val solidRes get() = R.drawable.nucleus_icon_camera_solid
    }

    data object CardCredential : NucleusIcon("card-credential"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_card_credential_regular
        override val solidRes get() = R.drawable.nucleus_icon_card_credential_solid
    }

    data object CardCredit : NucleusIcon("card-credit"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_card_credit_regular
        override val solidRes get() = R.drawable.nucleus_icon_card_credit_solid
    }

    data object CardShield : NucleusIcon("card-shield"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_card_shield_regular
        override val solidRes get() = R.drawable.nucleus_icon_card_shield_solid
    }

    data object CardWorld : NucleusIcon("card-world"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_card_world_regular
        override val solidRes get() = R.drawable.nucleus_icon_card_world_solid
    }

    data object Cash : NucleusIcon("cash"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_cash_regular
        override val solidRes get() = R.drawable.nucleus_icon_cash_solid
    }

    data object CashMulti : NucleusIcon("cash-multi"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_cash_multi_regular
        override val solidRes get() = R.drawable.nucleus_icon_cash_multi_solid
    }

    data object Cellular : NucleusIcon("cellular"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_cellular_regular
        override val solidRes get() = R.drawable.nucleus_icon_cellular_solid
    }

    data object CellularNoSignal : NucleusIcon("cellular-no-signal"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_cellular_no_signal_regular
        override val solidRes get() = R.drawable.nucleus_icon_cellular_no_signal_solid
    }

    data object ChatBubble : NucleusIcon("chat-bubble"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_chat_bubble_regular
        override val solidRes get() = R.drawable.nucleus_icon_chat_bubble_solid
    }

    data object ChatBubbleEmpty : NucleusIcon("chat-bubble-empty"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_chat_bubble_empty_regular
        override val solidRes get() = R.drawable.nucleus_icon_chat_bubble_empty_solid
    }

    data object ChatBubbleQuestion : NucleusIcon("chat-bubble-question"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_chat_bubble_question_regular
        override val solidRes get() = R.drawable.nucleus_icon_chat_bubble_question_solid
    }

    data object ChatBubbleTranslate : NucleusIcon("chat-bubble-translate"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_chat_bubble_translate_regular
        override val solidRes get() = R.drawable.nucleus_icon_chat_bubble_translate_solid
    }

    data object ChatBubbleWarning : NucleusIcon("chat-bubble-warning"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_chat_bubble_warning_regular
        override val solidRes get() = R.drawable.nucleus_icon_chat_bubble_warning_solid
    }

    data object ChatLines : NucleusIcon("chat-lines"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_chat_lines_regular
        override val solidRes get() = R.drawable.nucleus_icon_chat_lines_solid
    }

    data object Check : NucleusIcon("check"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_check_regular
        override val solidRes get() = R.drawable.nucleus_icon_check_solid
    }

    data object CheckCircle : NucleusIcon("check-circle"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_check_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_check_circle_solid
    }

    data object CheckDouble : NucleusIcon("check-double"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_check_double_regular
        override val solidRes get() = R.drawable.nucleus_icon_check_double_solid
    }

    data object ChevronDown : NucleusIcon("chevron-down"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_chevron_down_regular
        override val solidRes get() = R.drawable.nucleus_icon_chevron_down_solid
    }

    data object ChevronLeft : NucleusIcon("chevron-left"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_chevron_left_regular
        override val solidRes get() = R.drawable.nucleus_icon_chevron_left_solid
    }

    data object ChevronRight : NucleusIcon("chevron-right"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_chevron_right_regular
        override val solidRes get() = R.drawable.nucleus_icon_chevron_right_solid
    }

    data object ChevronUp : NucleusIcon("chevron-up"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_chevron_up_regular
        override val solidRes get() = R.drawable.nucleus_icon_chevron_up_solid
    }

    data object ChevronsExpand : NucleusIcon("chevrons-expand"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_chevrons_expand_regular
        override val solidRes get() = R.drawable.nucleus_icon_chevrons_expand_solid
    }

    data object ChevronsMinimize : NucleusIcon("chevrons-minimize"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_chevrons_minimize_regular
        override val solidRes get() = R.drawable.nucleus_icon_chevrons_minimize_solid
    }

    data object Circle : NucleusIcon("circle"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_circle_solid
    }

    data object Clock : NucleusIcon("clock"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_clock_regular
        override val solidRes get() = R.drawable.nucleus_icon_clock_solid
    }

    data object ClockRotateRight : NucleusIcon("clock-rotate-right"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_clock_rotate_right_regular
        override val solidRes get() = R.drawable.nucleus_icon_clock_rotate_right_solid
    }

    data object Cloud : NucleusIcon("cloud"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_cloud_regular
        override val solidRes get() = R.drawable.nucleus_icon_cloud_solid
    }

    data object CloudDownload : NucleusIcon("cloud-download"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_cloud_download_regular
        override val solidRes get() = R.drawable.nucleus_icon_cloud_download_solid
    }

    data object Coins : NucleusIcon("coins"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_coins_regular
        override val solidRes get() = R.drawable.nucleus_icon_coins_solid
    }

    data object CoinsStack : NucleusIcon("coins-stack"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_coins_stack_regular
        override val solidRes get() = R.drawable.nucleus_icon_coins_stack_solid
    }

    data object Commodity : NucleusIcon("commodity"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_commodity_regular
        override val solidRes get() = R.drawable.nucleus_icon_commodity_solid
    }

    data object Compass : NucleusIcon("compass"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_compass_regular
        override val solidRes get() = R.drawable.nucleus_icon_compass_solid
    }

    data object Compose : NucleusIcon("compose"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_compose_regular
        override val solidRes get() = R.drawable.nucleus_icon_compose_solid
    }

    data object ContactBook : NucleusIcon("contact-book"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_contact_book_regular
        override val solidRes get() = R.drawable.nucleus_icon_contact_book_solid
    }

    data object Copy : NucleusIcon("copy"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_copy_regular
        override val solidRes get() = R.drawable.nucleus_icon_copy_solid
    }

    data object Coupon : NucleusIcon("coupon"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_coupon_regular
        override val solidRes get() = R.drawable.nucleus_icon_coupon_solid
    }

    data object Cube : NucleusIcon("cube"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_cube_regular
        override val solidRes get() = R.drawable.nucleus_icon_cube_solid
    }

    data object CursorPointer : NucleusIcon("cursor-pointer"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_cursor_pointer_regular
        override val solidRes get() = R.drawable.nucleus_icon_cursor_pointer_solid
    }

    data object DecorativeSpark : NucleusIcon("decorative-spark"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_decorative_spark_regular
        override val solidRes get() = R.drawable.nucleus_icon_decorative_spark_solid
    }

    data object DeliveryCheck : NucleusIcon("delivery-check"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_delivery_check_regular
        override val solidRes get() = R.drawable.nucleus_icon_delivery_check_solid
    }

    data object DeliveryTruck : NucleusIcon("delivery-truck"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_delivery_truck_regular
        override val solidRes get() = R.drawable.nucleus_icon_delivery_truck_solid
    }

    data object Dollar : NucleusIcon("dollar"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_dollar_regular
        override val solidRes get() = R.drawable.nucleus_icon_dollar_solid
    }

    data object Download : NucleusIcon("download"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_download_regular
        override val solidRes get() = R.drawable.nucleus_icon_download_solid
    }

    data object EditPencil : NucleusIcon("edit-pencil"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_edit_pencil_regular
        override val solidRes get() = R.drawable.nucleus_icon_edit_pencil_solid
    }

    data object Ellipsis : NucleusIcon("ellipsis"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_ellipsis_regular
        override val solidRes get() = R.drawable.nucleus_icon_ellipsis_solid
    }

    data object EllipsisCircle : NucleusIcon("ellipsis-circle"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_ellipsis_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_ellipsis_circle_solid
    }

    data object EllipsisVertical : NucleusIcon("ellipsis-vertical"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_ellipsis_vertical_regular
        override val solidRes get() = R.drawable.nucleus_icon_ellipsis_vertical_solid
    }

    data object EmptyPage : NucleusIcon("empty-page"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_empty_page_regular
        override val solidRes get() = R.drawable.nucleus_icon_empty_page_solid
    }

    data object Enlarge : NucleusIcon("enlarge"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_enlarge_regular
        override val solidRes get() = R.drawable.nucleus_icon_enlarge_solid
    }

    data object Eye : NucleusIcon("eye"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_eye_regular
        override val solidRes get() = R.drawable.nucleus_icon_eye_solid
    }

    data object EyeClosed : NucleusIcon("eye-closed"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_eye_closed_regular
        override val solidRes get() = R.drawable.nucleus_icon_eye_closed_solid
    }

    data object FaceId : NucleusIcon("face-id"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_face_id_regular
        override val solidRes get() = R.drawable.nucleus_icon_face_id_solid
    }

    data object FilterList : NucleusIcon("filter-list"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_filter_list_regular
        override val solidRes get() = R.drawable.nucleus_icon_filter_list_solid
    }

    data object Flash : NucleusIcon("flash"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_flash_regular
        override val solidRes get() = R.drawable.nucleus_icon_flash_solid
    }

    data object Flashlight : NucleusIcon("flashlight"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_flashlight_regular
        override val solidRes get() = R.drawable.nucleus_icon_flashlight_solid
    }

    data object FullScreenClose : NucleusIcon("full-screen-close"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_full_screen_close_regular
        override val solidRes get() = R.drawable.nucleus_icon_full_screen_close_solid
    }

    data object FullScreenOpen : NucleusIcon("full-screen-open"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_full_screen_open_regular
        override val solidRes get() = R.drawable.nucleus_icon_full_screen_open_solid
    }

    data object Gif : NucleusIcon("gif"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_gif_regular
        override val solidRes get() = R.drawable.nucleus_icon_gif_solid
    }

    data object Gift : NucleusIcon("gift"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_gift_regular
        override val solidRes get() = R.drawable.nucleus_icon_gift_solid
    }

    data object GiftCard : NucleusIcon("gift-card"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_gift_card_regular
        override val solidRes get() = R.drawable.nucleus_icon_gift_card_solid
    }

    data object Glasses : NucleusIcon("glasses"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_glasses_regular
        override val solidRes get() = R.drawable.nucleus_icon_glasses_solid
    }

    data object Globe : NucleusIcon("globe"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_globe_regular
        override val solidRes get() = R.drawable.nucleus_icon_globe_solid
    }

    data object GraduationCap : NucleusIcon("graduation-cap"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_graduation_cap_regular
        override val solidRes get() = R.drawable.nucleus_icon_graduation_cap_solid
    }

    data object GraphDown : NucleusIcon("graph-down"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_graph_down_regular
        override val solidRes get() = R.drawable.nucleus_icon_graph_down_solid
    }

    data object GraphUp : NucleusIcon("graph-up"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_graph_up_regular
        override val solidRes get() = R.drawable.nucleus_icon_graph_up_solid
    }

    data object HalfMoon : NucleusIcon("half-moon"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_half_moon_regular
        override val solidRes get() = R.drawable.nucleus_icon_half_moon_solid
    }

    data object Heart : NucleusIcon("heart"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_heart_regular
        override val solidRes get() = R.drawable.nucleus_icon_heart_solid
    }

    data object HelpCircle : NucleusIcon("help-circle"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_help_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_help_circle_solid
    }

    data object Home : NucleusIcon("home"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_home_regular
        override val solidRes get() = R.drawable.nucleus_icon_home_solid
    }

    data object HumanEmblem : NucleusIcon("human-emblem"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_human_emblem_regular
        override val solidRes get() = R.drawable.nucleus_icon_human_emblem_solid
    }

    data object InfoCircle : NucleusIcon("info-circle"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_info_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_info_circle_solid
    }

    data object Key : NucleusIcon("key"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_key_regular
        override val solidRes get() = R.drawable.nucleus_icon_key_solid
    }

    data object KeyReal : NucleusIcon("key-real"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_key_real_regular
        override val solidRes get() = R.drawable.nucleus_icon_key_real_solid
    }

    data object Language : NucleusIcon("language"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_language_regular
        override val solidRes get() = R.drawable.nucleus_icon_language_solid
    }

    data object Legal : NucleusIcon("legal"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_legal_regular
        override val solidRes get() = R.drawable.nucleus_icon_legal_solid
    }

    data object Link : NucleusIcon("link"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_link_regular
        override val solidRes get() = R.drawable.nucleus_icon_link_solid
    }

    data object LinkSlash : NucleusIcon("link-slash"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_link_slash_regular
        override val solidRes get() = R.drawable.nucleus_icon_link_slash_solid
    }

    data object List : NucleusIcon("list"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_list_regular
        override val solidRes get() = R.drawable.nucleus_icon_list_solid
    }

    data object Lock : NucleusIcon("lock"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_lock_regular
        override val solidRes get() = R.drawable.nucleus_icon_lock_solid
    }

    data object LogIn : NucleusIcon("log-in"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_log_in_regular
        override val solidRes get() = R.drawable.nucleus_icon_log_in_solid
    }

    data object LogOut : NucleusIcon("log-out"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_log_out_regular
        override val solidRes get() = R.drawable.nucleus_icon_log_out_solid
    }

    data object MagicWand : NucleusIcon("magic-wand"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_magic_wand_regular
        override val solidRes get() = R.drawable.nucleus_icon_magic_wand_solid
    }

    data object Mail : NucleusIcon("mail"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_mail_regular
        override val solidRes get() = R.drawable.nucleus_icon_mail_solid
    }

    data object Map : NucleusIcon("map"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_map_regular
        override val solidRes get() = R.drawable.nucleus_icon_map_solid
    }

    data object MapPin : NucleusIcon("map-pin"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_map_pin_regular
        override val solidRes get() = R.drawable.nucleus_icon_map_pin_solid
    }

    data object Microphone : NucleusIcon("microphone"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_microphone_regular
        override val solidRes get() = R.drawable.nucleus_icon_microphone_solid
    }

    data object Minus : NucleusIcon("minus"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_minus_regular
        override val solidRes get() = R.drawable.nucleus_icon_minus_solid
    }

    data object MinusCircle : NucleusIcon("minus-circle"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_minus_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_minus_circle_solid
    }

    data object Navigation : NucleusIcon("navigation"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_navigation_regular
        override val solidRes get() = R.drawable.nucleus_icon_navigation_solid
    }

    data object NavigationDiagonal : NucleusIcon("navigation-diagonal"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_navigation_diagonal_regular
        override val solidRes get() = R.drawable.nucleus_icon_navigation_diagonal_solid
    }

    data object OpenNewWindow : NucleusIcon("open-new-window"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_open_new_window_regular
        override val solidRes get() = R.drawable.nucleus_icon_open_new_window_solid
    }

    data object OrbDiamond : NucleusIcon("orb-diamond"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_orb_diamond_regular
        override val solidRes get() = R.drawable.nucleus_icon_orb_diamond_solid
    }

    data object OrbPearl : NucleusIcon("orb-pearl"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_orb_pearl_regular
        override val solidRes get() = R.drawable.nucleus_icon_orb_pearl_solid
    }

    data object Page : NucleusIcon("page"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_page_regular
        override val solidRes get() = R.drawable.nucleus_icon_page_solid
    }

    data object Password : NucleusIcon("password"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_password_regular
        override val solidRes get() = R.drawable.nucleus_icon_password_solid
    }

    data object Percentage : NucleusIcon("percentage"), HasSolid {
        override val solidRes get() = R.drawable.nucleus_icon_percentage_solid
    }

    data object Person : NucleusIcon("person"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_person_regular
        override val solidRes get() = R.drawable.nucleus_icon_person_solid
    }

    data object PersonCircle : NucleusIcon("person-circle"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_person_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_person_circle_solid
    }

    data object PersonGroup : NucleusIcon("person-group"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_person_group_regular
        override val solidRes get() = R.drawable.nucleus_icon_person_group_solid
    }

    data object PersonKey : NucleusIcon("person-key"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_person_key_regular
        override val solidRes get() = R.drawable.nucleus_icon_person_key_solid
    }

    data object Photo : NucleusIcon("photo"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_photo_regular
        override val solidRes get() = R.drawable.nucleus_icon_photo_solid
    }

    data object Pin : NucleusIcon("pin"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_pin_regular
        override val solidRes get() = R.drawable.nucleus_icon_pin_solid
    }

    data object Play : NucleusIcon("play"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_play_regular
        override val solidRes get() = R.drawable.nucleus_icon_play_solid
    }

    data object Plus : NucleusIcon("plus"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_plus_regular
        override val solidRes get() = R.drawable.nucleus_icon_plus_solid
    }

    data object PlusCircle : NucleusIcon("plus-circle"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_plus_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_plus_circle_solid
    }

    data object Post : NucleusIcon("post"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_post_regular
        override val solidRes get() = R.drawable.nucleus_icon_post_solid
    }

    data object Power : NucleusIcon("power"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_power_regular
        override val solidRes get() = R.drawable.nucleus_icon_power_solid
    }

    data object Prohibition : NucleusIcon("prohibition"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_prohibition_regular
        override val solidRes get() = R.drawable.nucleus_icon_prohibition_solid
    }

    data object QrCode : NucleusIcon("qr-code"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_qr_code_regular
        override val solidRes get() = R.drawable.nucleus_icon_qr_code_solid
    }

    data object Refresh : NucleusIcon("refresh"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_refresh_regular
        override val solidRes get() = R.drawable.nucleus_icon_refresh_solid
    }

    data object RefreshDouble : NucleusIcon("refresh-double"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_refresh_double_regular
        override val solidRes get() = R.drawable.nucleus_icon_refresh_double_solid
    }

    data object Reports : NucleusIcon("reports"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_reports_regular
        override val solidRes get() = R.drawable.nucleus_icon_reports_solid
    }

    data object Safe : NucleusIcon("safe"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_safe_regular
        override val solidRes get() = R.drawable.nucleus_icon_safe_solid
    }

    data object Scan : NucleusIcon("scan"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_scan_regular
        override val solidRes get() = R.drawable.nucleus_icon_scan_solid
    }

    data object Search : NucleusIcon("search"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_search_regular
        override val solidRes get() = R.drawable.nucleus_icon_search_solid
    }

    data object Send : NucleusIcon("send"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_send_regular
        override val solidRes get() = R.drawable.nucleus_icon_send_solid
    }

    data object SendMail : NucleusIcon("send-mail"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_send_mail_regular
        override val solidRes get() = R.drawable.nucleus_icon_send_mail_solid
    }

    data object Settings : NucleusIcon("settings"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_settings_regular
        override val solidRes get() = R.drawable.nucleus_icon_settings_solid
    }

    data object ShareIos : NucleusIcon("share-ios"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_share_ios_regular
        override val solidRes get() = R.drawable.nucleus_icon_share_ios_solid
    }

    data object Shield : NucleusIcon("shield"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_shield_regular
        override val solidRes get() = R.drawable.nucleus_icon_shield_solid
    }

    data object ShieldAlert : NucleusIcon("shield-alert"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_shield_alert_regular
        override val solidRes get() = R.drawable.nucleus_icon_shield_alert_solid
    }

    data object ShieldCheck : NucleusIcon("shield-check"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_shield_check_regular
        override val solidRes get() = R.drawable.nucleus_icon_shield_check_solid
    }

    data object ShieldHalf : NucleusIcon("shield-half"), HasSolid {
        override val solidRes get() = R.drawable.nucleus_icon_shield_half_solid
    }

    data object Smartphone : NucleusIcon("smartphone"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_smartphone_regular
        override val solidRes get() = R.drawable.nucleus_icon_smartphone_solid
    }

    data object SnowFlake : NucleusIcon("snow-flake"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_snow_flake_regular
        override val solidRes get() = R.drawable.nucleus_icon_snow_flake_solid
    }

    data object SoftwareUpdateSetting : NucleusIcon("software-update-setting"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_software_update_setting_regular
        override val solidRes get() = R.drawable.nucleus_icon_software_update_setting_solid
    }

    data object Sort : NucleusIcon("sort"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_sort_regular
        override val solidRes get() = R.drawable.nucleus_icon_sort_solid
    }

    data object SortDown : NucleusIcon("sort-down"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_sort_down_regular
        override val solidRes get() = R.drawable.nucleus_icon_sort_down_solid
    }

    data object SortUp : NucleusIcon("sort-up"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_sort_up_regular
        override val solidRes get() = R.drawable.nucleus_icon_sort_up_solid
    }

    data object Spark : NucleusIcon("spark"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_spark_regular
        override val solidRes get() = R.drawable.nucleus_icon_spark_solid
    }

    data object Sparks : NucleusIcon("sparks"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_sparks_regular
        override val solidRes get() = R.drawable.nucleus_icon_sparks_solid
    }

    data object Star : NucleusIcon("star"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_star_regular
        override val solidRes get() = R.drawable.nucleus_icon_star_solid
    }

    data object StatsUpSquare : NucleusIcon("stats-up-square"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_stats_up_square_regular
        override val solidRes get() = R.drawable.nucleus_icon_stats_up_square_solid
    }

    data object Suitcase : NucleusIcon("suitcase"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_suitcase_regular
        override val solidRes get() = R.drawable.nucleus_icon_suitcase_solid
    }

    data object Sun : NucleusIcon("sun"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_sun_regular
        override val solidRes get() = R.drawable.nucleus_icon_sun_solid
    }

    data object Tag : NucleusIcon("tag"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_tag_regular
        override val solidRes get() = R.drawable.nucleus_icon_tag_solid
    }

    data object Text : NucleusIcon("text"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_text_regular
        override val solidRes get() = R.drawable.nucleus_icon_text_solid
    }

    data object Timer : NucleusIcon("timer"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_timer_regular
        override val solidRes get() = R.drawable.nucleus_icon_timer_solid
    }

    data object TimerDots : NucleusIcon("timer-dots"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_timer_dots_regular
        override val solidRes get() = R.drawable.nucleus_icon_timer_dots_solid
    }

    data object Toolkit : NucleusIcon("toolkit"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_toolkit_regular
        override val solidRes get() = R.drawable.nucleus_icon_toolkit_solid
    }

    data object Trash : NucleusIcon("trash"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_trash_regular
        override val solidRes get() = R.drawable.nucleus_icon_trash_solid
    }

    data object Trophy : NucleusIcon("trophy"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_trophy_regular
        override val solidRes get() = R.drawable.nucleus_icon_trophy_solid
    }

    data object VideoCamera : NucleusIcon("video-camera"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_video_camera_regular
        override val solidRes get() = R.drawable.nucleus_icon_video_camera_solid
    }

    data object ViewGrid : NucleusIcon("view-grid"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_view_grid_regular
        override val solidRes get() = R.drawable.nucleus_icon_view_grid_solid
    }

    data object Voice : NucleusIcon("voice"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_voice_regular
        override val solidRes get() = R.drawable.nucleus_icon_voice_solid
    }

    data object Wallet : NucleusIcon("wallet"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_wallet_regular
        override val solidRes get() = R.drawable.nucleus_icon_wallet_solid
    }

    data object WarningCircle : NucleusIcon("warning-circle"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_warning_circle_regular
        override val solidRes get() = R.drawable.nucleus_icon_warning_circle_solid
    }

    data object WarningHexagon : NucleusIcon("warning-hexagon"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_warning_hexagon_regular
        override val solidRes get() = R.drawable.nucleus_icon_warning_hexagon_solid
    }

    data object WarningTriangle : NucleusIcon("warning-triangle"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_warning_triangle_regular
        override val solidRes get() = R.drawable.nucleus_icon_warning_triangle_solid
    }

    data object Wifi : NucleusIcon("wifi"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_wifi_regular
        override val solidRes get() = R.drawable.nucleus_icon_wifi_solid
    }

    data object WifiSignalNone : NucleusIcon("wifi-signal-none"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_wifi_signal_none_regular
        override val solidRes get() = R.drawable.nucleus_icon_wifi_signal_none_solid
    }

    data object Worldcoin : NucleusIcon("worldcoin"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_worldcoin_regular
        override val solidRes get() = R.drawable.nucleus_icon_worldcoin_solid
    }

    data object Xmark : NucleusIcon("xmark"), HasRegular, HasSolid {
        override val regularRes get() = R.drawable.nucleus_icon_xmark_regular
        override val solidRes get() = R.drawable.nucleus_icon_xmark_solid
    }

    data object XmarkCircle : NucleusIcon("xmark-circle"), HasRegular, HasSolid {
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
            BrandApple,
            BrandInstagram,
            BrandX,
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
            CellularNoSignal,
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
            PersonGroup,
            PersonKey,
            Photo,
            Pin,
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
