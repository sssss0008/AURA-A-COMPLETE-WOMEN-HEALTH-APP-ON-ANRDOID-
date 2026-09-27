package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.*

@Composable
fun ProfileSettingsScreen(
    uiState: com.example.ui.viewmodel.AuraUiState,
    onToggleDiscreetMode: () -> Unit,
    onLockApp: () -> Unit,
    onSetNotificationPrivacy: (NotificationPrivacy) -> Unit,
    onSetLanguage: (LanguageCode) -> Unit,
    onToggleHighContrast: () -> Unit,
    onToggleDarkMode: () -> Unit,
    onToggleOfflineMode: () -> Unit,
    onOpenExport: () -> Unit,
    onRequestDeleteCategory: (String) -> Unit,
    onEmergencyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedProfileTab by remember { mutableIntStateOf(0) }
    val profileTabs = listOf("Profile & Trends", "Privacy & Security", "Settings & Display")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("profile_settings_screen"),
        contentPadding = PaddingValues(bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header & Profile Banner
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🌸", fontSize = 32.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Aura Wellness Space",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Private on-device profile • ${uiState.lifeStageFocuses.size} focus areas",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Subtabs
        item {
            ScrollableTabRow(
                selectedTabIndex = selectedProfileTab,
                edgePadding = 20.dp,
                divider = {},
                containerColor = Color.Transparent
            ) {
                profileTabs.forEachIndexed { idx, title ->
                    Tab(
                        selected = selectedProfileTab == idx,
                        onClick = { selectedProfileTab = idx },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = if (selectedProfileTab == idx) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        }
                    )
                }
            }
        }

        when (selectedProfileTab) {
            0 -> {
                // TRENDS & WEEKLY / MONTHLY SUMMARY (Items 49, 50, 51)
                item {
                    HealthCard(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "Weekly Wellness Summary",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "You logged information on 6 of 7 days this week.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Visual Bar Trend for the Week
                        Text(
                            text = "Sleep & Movement Trends (Mon–Sun)",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        val days = listOf("M", "T", "W", "T", "F", "S", "S")
                        val sleepHours = listOf(7.2, 7.8, 6.9, 8.1, 7.5, 8.4, 7.7)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            days.forEachIndexed { i, d ->
                                val h = sleepHours[i]
                                val barHeight = (h / 9.0 * 75).dp
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "${h.toInt()}h",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Box(
                                        modifier = Modifier
                                            .width(22.dp)
                                            .height(barHeight)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(MaterialTheme.colorScheme.primaryContainer)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(text = d, style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        }
                    }
                }

                item {
                    HealthCard(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "Monthly Tracking Consistency",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "• Period logged: 5 days recorded on time\n• Hydration average: 6.2 cups daily\n• Sleep average: 7.6 hours\n• Dominant mood: Calm & Good",
                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp)
                        )
                    }
                }
            }

            1 -> {
                // PRIVACY CENTER & DATA EXPORT (Items 52, 53, 54, 57, 58, 59)
                item {
                    HealthCard(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "Discreet Private Mode",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Changes the app title to 'Daily Wellness' and conceals sensitive health terms on lock screen cards.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Enable Discreet Mode", style = MaterialTheme.typography.bodyMedium)
                            Switch(
                                checked = uiState.isDiscreetMode,
                                onCheckedChange = { onToggleDiscreetMode() }
                            )
                        }
                    }
                }

                // App Lock
                item {
                    HealthCard(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "App Lock Protection",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Require a PIN code to view your health data and logs.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = onLockApp,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Lock App Now (PIN: 1234)")
                        }
                    }
                }

                // Notification Privacy (Item 54)
                item {
                    HealthCard(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "Notification Privacy",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Choose how notifications display on your device.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        NotificationPrivacy.entries.forEach { np ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onSetNotificationPrivacy(np) }
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = uiState.notificationPrivacy == np,
                                    onClick = { onSetNotificationPrivacy(np) }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(np.label, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium))
                                    Text(np.example, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }

                // Data Export & Delete (Items 58, 59)
                item {
                    HealthCard(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "Data Management & Privacy Center",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Your data remains on this device. You can export or delete it anytime.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedButton(
                            onClick = onOpenExport,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(imageVector = Icons.Filled.FileDownload, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Export Health Data (PDF / CSV / JSON)")
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = { onRequestDeleteCategory("All Local Data") },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(imageVector = Icons.Filled.DeleteForever, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Delete All Local Data")
                        }
                    }
                }
            }

            2 -> {
                // SETTINGS, DISPLAY & ACCESSIBILITY (Items 56, 60, 61, 62)
                item {
                    HealthCard(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "Display & Accessibility",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("High Contrast Mode", style = MaterialTheme.typography.bodyMedium)
                            Switch(
                                checked = uiState.highContrast,
                                onCheckedChange = { onToggleHighContrast() }
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Dark Theme", style = MaterialTheme.typography.bodyMedium)
                            Button(
                                onClick = onToggleDarkMode,
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    when (uiState.darkModeOverride) {
                                        null -> "System Default"
                                        true -> "Dark"
                                        false -> "Light"
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Simulate Offline Mode", style = MaterialTheme.typography.bodyMedium)
                            Switch(
                                checked = uiState.isOfflineMode,
                                onCheckedChange = { onToggleOfflineMode() }
                            )
                        }
                    }
                }

                // Language Selection (Item 62)
                item {
                    HealthCard(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "Language Selection",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Supports multilingual localization for women globally.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(LanguageCode.entries.toTypedArray()) { lang ->
                                val isSelected = uiState.selectedLanguage == lang
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { onSetLanguage(lang) },
                                    label = { Text("${lang.displayName} (${lang.nativeName})") }
                                )
                            }
                        }
                    }
                }

                // Emergency Safety Hub (Item 65)
                item {
                    EmergencyNoticeCard(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        onEmergencyClick = onEmergencyClick
                    )
                }

                // About Aura
                item {
                    HealthCard(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "About Aura",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Version 1.0 • Designed with respect, privacy, and evidence-informed health principles.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "“Tracking information can help you understand patterns, but it does not replace professional medical advice.”",
                            style = MaterialTheme.typography.bodySmall.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
