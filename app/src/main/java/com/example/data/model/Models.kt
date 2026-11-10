package com.example.data.model

enum class UserStatus(val label: String, val colorHex: Long) {
    ONLINE("آنلاین", 0xFF10B981), // Emerald green
    IN_GAME("در حال بازی 🎲", 0xFF8B5CF6), // Purple
    BUSY("مشغول", 0xFFF59E0B), // Amber
    OFFLINE("آفلاین", 0xFF9CA3AF) // Gray
}

data class UserProfile(
    val id: String = "user_me",
    val name: String = "کاربر عسل‌آبادی",
    val username: String = "@user_asal",
    val level: Int = 4,
    val diamonds: Long = 1000,
    val janous: Long = 1604,
    val ageDays: Int = 105,
    val bio: String = "عاشق چت و بازی در عسل آباد 💜",
    val isVip: Boolean = true,
    val vipRemainingText: String = "۵ روز و ۱۸ ساعت باقی مانده",
    val specialGiftsCount: Int = 2,
    val pointsGiftCount: Int = 14419,
    val badgeSymbol: String = "👑",
    val chatBubbleColorHex: String = "#7E22CE",
    val status: UserStatus = UserStatus.ONLINE,
    val statusCustomText: String = "آنلاین • با مدیریت پریا چش رنگی 👑",
    val avatarEmoji: String = "👑",
    val isCreatorOrManager: Boolean = false,
    val managementRole: String = "مدیریت عسل آباد: پریا چش رنگی 👑",
    val verifiedBadge: Boolean = true,
    val reactions: Map<String, Int> = mapOf(
        "💎" to 8,
        "☕" to 104,
        "❤️" to 17,
        "🤤" to 16,
        "🍓" to 5
    )
)

data class CandidateUser(
    val id: String,
    val name: String,
    val username: String,
    val avatarEmoji: String,
    val status: UserStatus = UserStatus.ONLINE,
    val roleTitle: String,
    val isManager: Boolean = false,
    val isPreselected: Boolean = false
)

data class BankAccountInfo(
    val cardNumber: String = "6037-9975-8421-3965",
    val cardHolderName: String = "پریا چش رنگی",
    val bankName: String = "بانک ملی ایران",
    val iban: String = "IR120170000000123456789012"
)

data class ChatMessage(
    val id: String,
    val senderId: String,
    val senderName: String,
    val text: String,
    val timestamp: String,
    val isMe: Boolean,
    val isCreator: Boolean = false,
    val badge: String = "",
    val stickerType: String? = null,
    val reaction: String? = null,
    val replyToText: String? = null
)

data class ChatRoom(
    val id: String,
    val title: String,
    val subtitle: String,
    val memberCount: Int = 1,
    val onlineCount: Int = 1,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int = 0,
    val isGroup: Boolean = true,
    val isCreatorChat: Boolean = false,
    val avatarEmoji: String = "🐝"
)

data class ShopPackage(
    val id: String,
    val originalDiamonds: Long,
    val doubledDiamonds: Long,
    val priceToman: Long,
    val formattedPrice: String,
    val hasBonusWheel: Boolean = true
)

data class PurchaseItem(
    val id: String,
    val title: String,
    val date: String,
    val status: String,
    val token: String,
    val priceToman: Long = 0L,
    val diamondsGranted: Long = 0L,
    val refId: String = "",
    val paymentMethod: String = "درگاه بانکی شاپرک"
)

data class GroupMarketItem(
    val id: String,
    val groupName: String,
    val score: Long,
    val priceToman: Long,
    val formattedPrice: String,
    val formattedScore: String
)

data class WheelSector(
    val index: Int,
    val label: String,
    val amount: Int,
    val isJanous: Boolean,
    val colorHex: Long
)

data class LeaderboardUser(
    val rank: Int,
    val name: String,
    val score: Int,
    val avatarColorHex: Long = 0xFF9C27B0
)

enum class NotificationType {
    CHAT_MESSAGE,
    PURCHASE_SUCCESS,
    WALLET_UPDATE
}

data class InAppNotification(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    val message: String,
    val iconEmoji: String,
    val type: NotificationType,
    val targetRoomId: String? = null,
    val targetToken: String? = null,
    val timestamp: String = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
)

