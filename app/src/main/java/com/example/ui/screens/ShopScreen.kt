package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.BankAccountInfo
import com.example.data.model.PurchaseItem
import com.example.data.model.ShopPackage
import com.example.ui.theme.AsalGold
import com.example.ui.theme.AsalGoldLight
import com.example.ui.theme.AsalPurpleDark
import com.example.ui.theme.AsalPurpleLight
import com.example.ui.theme.AsalPurplePrimary

@Composable
fun ShopScreen(
    packages: List<ShopPackage>,
    purchaseList: List<PurchaseItem>,
    bankInfo: BankAccountInfo,
    onStartPayment: (ShopPackage) -> Unit,
    onOpenJanous: () -> Unit,
    onOpenTariff: () -> Unit,
    onOpenEditBankCard: () -> Unit,
    onSearchPurchase: (String) -> PurchaseItem?,
    onClearHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showPackagesDialog by remember { mutableStateOf(false) }
    var showPurchaseCheckDialog by remember { mutableStateOf(false) }
    var showPurchaseListDialog by remember { mutableStateOf(false) }
    var selectedReceiptItem by remember { mutableStateOf<PurchaseItem?>(null) }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Screen Title
            Text(
                text = "فروشگاه عسل آباد 🛒",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AsalPurpleDark,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Destination Bank Card Banner (قابلیت کپی و تغییر شماره کارت توسط کاربر)
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(3.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFF2E1065), Color(0xFF581C87))
                            )
                        )
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CreditCard, contentDescription = null, tint = AsalGold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "شماره کارت واریز: ${bankInfo.bankName}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = AsalGoldLight
                            )
                        }

                        // Edit Button to change card to user's own card
                        Surface(
                            onClick = onOpenEditBankCard,
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "تغییر", tint = Color.White, modifier = Modifier.size(14.dp))
                                Text("تغییر شماره کارت", fontSize = 10.sp, color = Color.White)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = bankInfo.cardNumber,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            letterSpacing = 2.sp
                        )

                        // Quick Copy Button
                        IconButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("شماره کارت", bankInfo.cardNumber.replace("-", ""))
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "شماره کارت کپی شد: ${bankInfo.cardNumber}", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "کپی", tint = AsalGold)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "صاحب حساب: ${bankInfo.cardHolderName}",
                        fontSize = 11.sp,
                        color = Color(0xFFDDD2F5)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Shop Options List (Matches Screenshot 3, 5, 11)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                // 1. فروشگاه 2X
                ShopOptionCard(
                    title = "فروشگاه 2X",
                    subtitle = "با خرید الماس، یک گردونه هم دریافت کن",
                    iconEmoji = "🏪",
                    iconBgColor = Color(0xFFF3E8FF),
                    badge = "۶ بسته با درگاه بانکی",
                    onClick = { showPackagesDialog = true }
                )

                // 2. بررسی خرید
                ShopOptionCard(
                    title = "بررسی خرید",
                    subtitle = "خریدی که دریافت نکردی رو با کد پیگیری بررسی کن",
                    iconEmoji = "🛍️",
                    iconBgColor = Color(0xFFEDE9FE),
                    badge = "جستجوی واقعی",
                    onClick = { showPurchaseCheckDialog = true }
                )

                // 3. لیست خریدها
                ShopOptionCard(
                    title = "لیست خریدها",
                    subtitle = "مشاهده فاکتور و رسید تراکنش‌های بانکی",
                    iconEmoji = "📋",
                    iconBgColor = Color(0xFFDDD2F5),
                    badge = "${purchaseList.size} خرید",
                    onClick = { showPurchaseListDialog = true }
                )

                // 4. جانوس
                ShopOptionCard(
                    title = "جانوس",
                    subtitle = "الماس‌های خود را به جانوس تبدیل کنید",
                    iconEmoji = "🟣",
                    iconBgColor = Color(0xFFC4B5FD),
                    badge = "۱۰۰۰ الماس = ۱ جانوس",
                    onClick = onOpenJanous
                )

                // 5. تعرفه
                ShopOptionCard(
                    title = "تعرفه",
                    subtitle = "تعرفه اهدای امتیاز گروهی و محاسبه جوایز",
                    iconEmoji = "🏢",
                    iconBgColor = Color(0xFFA78BFA),
                    badge = "جدول و ماشین حساب",
                    onClick = onOpenTariff
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Packages Dialog (فروشگاه 2X)
        if (showPackagesDialog) {
            ShopPackagesDialog(
                packages = packages,
                onPackageSelected = { pkg ->
                    showPackagesDialog = false
                    onStartPayment(pkg)
                },
                onDismiss = { showPackagesDialog = false }
            )
        }

        // Purchase Check Dialog
        if (showPurchaseCheckDialog) {
            PurchaseCheckDialog(
                recentPurchases = purchaseList,
                onSearch = onSearchPurchase,
                onDismiss = { showPurchaseCheckDialog = false }
            )
        }

        // Purchase List Dialog
        if (showPurchaseListDialog) {
            PurchaseListDialog(
                items = purchaseList,
                onClearHistory = onClearHistory,
                onItemClick = { item -> selectedReceiptItem = item },
                onDismiss = { showPurchaseListDialog = false }
            )
        }

        // Selected Purchase Detail Modal
        if (selectedReceiptItem != null) {
            com.example.ui.components.PaymentSuccessReceiptDialog(
                receipt = selectedReceiptItem!!,
                onDismiss = { selectedReceiptItem = null }
            )
        }
    }
}

@Composable
private fun ShopOptionCard(
    title: String,
    subtitle: String,
    iconEmoji: String,
    iconBgColor: Color,
    badge: String? = null,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
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
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(iconBgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(iconEmoji, fontSize = 22.sp)
                }
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = AsalPurpleDark
                        )
                        if (badge != null) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFFF3E8FF))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(badge, fontSize = 9.sp, color = AsalPurplePrimary, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
            Text("‹", fontSize = 20.sp, color = Color.Gray)
        }
    }
}

// -------------------------------------------------------------
// Packages Dialog (Matches Screenshot 11)
// -------------------------------------------------------------
@Composable
private fun ShopPackagesDialog(
    packages: List<ShopPackage>,
    onPackageSelected: (ShopPackage) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "بستن")
                    }
                    Text(
                        text = "فروشگاه 2X 💎",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = AsalPurpleDark
                    )
                    Spacer(modifier = Modifier.size(36.dp))
                }

                Text(
                    text = "با خرید هر بسته الماس، ۱ گردونه شانس هدیه بگیرید!",
                    fontSize = 12.sp,
                    color = Color(0xFFD97706),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 2x3 Grid of Packages
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    val rows = packages.chunked(3)
                    rows.forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowItems.forEach { pkg ->
                                PackageGridItem(
                                    pkg = pkg,
                                    modifier = Modifier.weight(1f),
                                    onClick = { onPackageSelected(pkg) }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = AsalPurplePrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("بستن", color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun PackageGridItem(
    pkg: ShopPackage,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Bonus Wheel Tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text("🎡", fontSize = 16.sp)
            }
            Text("💎", fontSize = 28.sp)
            Spacer(modifier = Modifier.height(2.dp))

            // Strikethrough original
            Text(
                text = "${pkg.originalDiamonds}",
                fontSize = 10.sp,
                color = Color.Red,
                textDecoration = TextDecoration.LineThrough
            )
            // Doubled amount
            Text(
                text = "${pkg.doubledDiamonds}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = AsalPurpleDark
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Price Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF10B981))
                    .padding(horizontal = 6.dp, vertical = 3.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = pkg.formattedPrice,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

// -------------------------------------------------------------
// Purchase Status Verification Dialog (Matches Screenshot 5)
// -------------------------------------------------------------
@Composable
private fun PurchaseCheckDialog(
    recentPurchases: List<PurchaseItem>,
    onSearch: (String) -> PurchaseItem?,
    onDismiss: () -> Unit
) {
    var tokenInput by remember { mutableStateOf("") }
    var foundItem by remember { mutableStateOf<PurchaseItem?>(null) }
    var hasSearched by remember { mutableStateOf(false) }

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
                    text = "بررسی وضعیت خرید 🔍",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AsalPurpleDark
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "توکن خرید یا شماره مرجع بانکی (RRN) خود را وارد کنید:",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = tokenInput,
                    onValueChange = { tokenInput = it },
                    placeholder = { Text("مثال: TOKEN-883921 یا RRN") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AsalPurplePrimary
                    )
                )

                // Quick chips of recent tokens
                if (recentPurchases.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("توکن‌های اخیر شما جهت بررسی سریع:", fontSize = 10.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(recentPurchases.take(4)) { item ->
                            Surface(
                                onClick = {
                                    tokenInput = item.token
                                    foundItem = onSearch(item.token)
                                    hasSearched = true
                                },
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFF3E8FF)
                            ) {
                                Text(
                                    text = item.token,
                                    fontSize = 10.sp,
                                    color = AsalPurplePrimary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Search Result Card
                if (hasSearched) {
                    if (foundItem != null) {
                        val item = foundItem!!
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFECFDF5),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF10B981)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("✅ خرید یافت و تایید شد", fontWeight = FontWeight.Bold, color = Color(0xFF065F46), fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("بسته: ${item.title}", fontSize = 11.sp, color = AsalPurpleDark)
                                Text("مبلغ: ${item.priceToman} تومان", fontSize = 11.sp, color = AsalPurpleDark)
                                Text("مرجع بانکی: ${item.refId}", fontSize = 11.sp, color = Color.Gray)
                                Text("روش پرداخت: ${item.paymentMethod}", fontSize = 11.sp, color = Color.Gray)
                                Text("تاریخ: ${item.date}", fontSize = 10.sp, color = Color.Gray)
                            }
                        }
                    } else {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFFEF2F2),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "❌ خریدی با این کد یافت نشد. لطفاً کد را به دقت بررسی کنید.",
                                color = Color(0xFF991B1B),
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("بستن", color = Color.Gray)
                    }
                    Button(
                        onClick = {
                            hasSearched = true
                            foundItem = onSearch(tokenInput)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AsalPurplePrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("بررسی وضعیت 🔍", color = Color.White)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// Purchase List Dialog (Matches Screenshot 1 Exactly)
// -------------------------------------------------------------
@Composable
private fun PurchaseListDialog(
    items: List<PurchaseItem>,
    onClearHistory: () -> Unit,
    onItemClick: (PurchaseItem) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Header with soft lavender background matching Screenshot 1
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFFF3E8FF), Color(0xFFFAF5FF))
                            )
                        )
                        .padding(vertical = 18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "لیست خریدها",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E2E)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    if (items.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("خریدی در تاریخچه موجود نیست.", fontSize = 13.sp, color = Color.Gray)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(items) { item ->
                                // Card layout matching Screenshot 1
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color.White,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 14.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        // Left side: بررسی action button (cyan/blue)
                                        TextButton(
                                            onClick = { onItemClick(item) }
                                        ) {
                                            Text(
                                                text = "بررسی",
                                                color = Color(0xFF0284C7),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp
                                            )
                                        }

                                        // Right side: Title & Date (Persian font)
                                        Column(
                                            horizontalAlignment = Alignment.End
                                        ) {
                                            Text(
                                                text = item.title,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF1F2937)
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = item.date,
                                                fontSize = 12.sp,
                                                color = Color(0xFF6B7280)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Bottom Action Buttons matching Screenshot 1: حذف همه, بستن
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = onClearHistory) {
                            Text(
                                text = "حذف همه",
                                color = AsalPurplePrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(onClick = onDismiss) {
                            Text(
                                text = "بستن",
                                color = AsalPurplePrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
