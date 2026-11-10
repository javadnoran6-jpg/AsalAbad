package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.UserProfile
import com.example.ui.theme.AsalGold
import com.example.ui.theme.AsalGoldLight
import com.example.ui.theme.AsalPurpleDark
import com.example.ui.theme.AsalPurpleLight
import com.example.ui.theme.AsalPurplePrimary

@Composable
fun ProfileScreen(
    user: UserProfile,
    creatorProfile: UserProfile,
    onOpenVipModal: () -> Unit,
    onUpdateBio: (String, String) -> Unit,
    onOpenPrivateChatWithCreator: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var isViewingCreator by remember { mutableStateOf(false) }
    var showBioDialog by remember { mutableStateOf(false) }

    val activeProfile = if (isViewingCreator) creatorProfile else user

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // View Switcher (من / پریا چش رنگی سازنده)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (!isViewingCreator) AsalPurplePrimary else Color.Transparent)
                        .clickable { isViewingCreator = false }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "پروفایل من",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (!isViewingCreator) Color.White else AsalPurpleDark
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isViewingCreator) AsalPurplePrimary else Color.Transparent)
                        .clickable { isViewingCreator = true }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "پریا چش رنگی 👑 (سازنده)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isViewingCreator) Color.White else AsalPurpleDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Large Circular Avatar (Matches Screenshot 6)
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(AsalGold, AsalPurpleLight, Color(0xFFEC4899))
                        )
                    )
                    .padding(3.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF230B48)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isViewingCreator) "👸" else "🌸",
                    fontSize = 58.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Profile Info Card (Matches Screenshot 6)
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(3.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = activeProfile.name,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = AsalPurpleDark
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(AsalPurplePrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "تایید شده",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    Text(
                        text = activeProfile.username,
                        fontSize = 13.sp,
                        color = AsalPurplePrimary,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "سن آباد : ${activeProfile.ageDays} روز",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = activeProfile.bio,
                        fontSize = 13.sp,
                        color = Color(0xFF374151),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Stats 3-Col Card (الماس | اهدای اختصاصی | اهدای امتیاز)
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("💎", fontSize = 18.sp)
                        Text("${activeProfile.diamonds}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AsalPurpleDark)
                        Text("الماس", fontSize = 11.sp, color = Color.Gray)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("👑", fontSize = 18.sp)
                        Text("${activeProfile.specialGiftsCount}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AsalPurpleDark)
                        Text("اهدای اختصاصی", fontSize = 11.sp, color = Color.Gray)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("⭐", fontSize = 18.sp)
                        Text("${activeProfile.pointsGiftCount}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AsalPurpleDark)
                        Text("اهدای امتیاز", fontSize = 11.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Reactions Bar (Matches Screenshot 6)
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    activeProfile.reactions.forEach { (emoji, count) ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFF5F3FF),
                            modifier = Modifier.padding(horizontal = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(emoji, fontSize = 16.sp)
                                Text("$count", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AsalPurpleDark)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Actions: چت خصوصی / گروه‌ها
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onOpenPrivateChatWithCreator,
                    colors = ButtonDefaults.buttonColors(containerColor = AsalPurpleDark),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                ) {
                    Text("چت خصوصی 💬", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = AsalPurpleDark),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                ) {
                    Text("گروه‌ها 👥", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Profile Settings List (Matches Screenshot 13)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "حساب اختصاصی ...",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AsalPurpleDark,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )

                ProfileSettingItem(
                    title = "خرید حساب اختصاصی",
                    subtitle = user.vipRemainingText,
                    emoji = "👑",
                    onClick = onOpenVipModal
                )

                ProfileSettingItem(
                    title = "بیوگرافی",
                    subtitle = "بیو چک اد رایگان",
                    emoji = "✍️",
                    onClick = { showBioDialog = true }
                )

                ProfileSettingItem(
                    title = "پس‌زمینه پیام",
                    subtitle = "رنگ پس‌زمینه پیام خود را انتخاب کنید",
                    emoji = "🎨",
                    onClick = {}
                )

                ProfileSettingItem(
                    title = "نماد",
                    subtitle = "برای خود یک نماد انتخاب کنید (${user.badgeSymbol})",
                    emoji = user.badgeSymbol,
                    onClick = { showBioDialog = true }
                )

                Text(
                    text = "ذخیره / بازیابی حساب ...",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AsalPurpleDark,
                    modifier = Modifier.padding(top = 8.dp, start = 4.dp)
                )

                ProfileSettingItem(
                    title = "ذخیره حساب",
                    subtitle = "حساب خود را با Gmail ذخیره کنید",
                    emoji = "☁️",
                    onClick = {}
                )

                ProfileSettingItem(
                    title = "بازیابی حساب",
                    subtitle = "حساب خود را با Gmail بازیابی کنید",
                    emoji = "🔄",
                    onClick = {}
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Bio Edit Dialog
        if (showBioDialog) {
            BioEditDialog(
                currentBio = user.bio,
                currentBadge = user.badgeSymbol,
                onSave = { bio, badge ->
                    onUpdateBio(bio, badge)
                    showBioDialog = false
                },
                onDismiss = { showBioDialog = false }
            )
        }
    }
}

@Composable
private fun ProfileSettingItem(
    title: String,
    subtitle: String,
    emoji: String,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF3E8FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(emoji, fontSize = 20.sp)
                }
                Column {
                    Text(title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AsalPurpleDark)
                    Text(subtitle, fontSize = 11.sp, color = Color.Gray)
                }
            }
            Text("‹", fontSize = 18.sp, color = Color.Gray)
        }
    }
}

@Composable
private fun BioEditDialog(
    currentBio: String,
    currentBadge: String,
    onSave: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var bioInput by remember { mutableStateOf(currentBio) }
    var badgeInput by remember { mutableStateOf(currentBadge) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ویرایش بیوگرافی و نماد ✍️",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AsalPurpleDark
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = bioInput,
                    onValueChange = { bioInput = it },
                    label = { Text("متن بیوگرافی شما") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text("انتخاب نماد کاربری ویژه:", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    listOf("👑", "🐼", "✔️", "💖", "⚡", "🦋").forEach { b ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(if (badgeInput == b) Color(0xFFDDD2F5) else Color(0xFFF9F7FD))
                                .border(
                                    1.dp,
                                    if (badgeInput == b) AsalPurplePrimary else Color.Transparent,
                                    CircleShape
                                )
                                .clickable { badgeInput = b },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(b, fontSize = 20.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("انصراف", color = AsalPurplePrimary)
                    }
                    Button(
                        onClick = { onSave(bioInput, badgeInput) },
                        colors = ButtonDefaults.buttonColors(containerColor = AsalPurplePrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("ذخیره تغییرات", color = Color.White)
                    }
                }
            }
        }
    }
}
