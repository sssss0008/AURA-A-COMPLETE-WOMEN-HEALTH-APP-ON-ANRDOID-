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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.mock.MockDataProvider
import com.example.data.model.*
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    uiState: com.example.ui.viewmodel.AuraUiState,
    onMoodSelected: (MoodType) -> Unit,
    onNavigateTab: (NavigationTab) -> Unit,
    onQuickLogOpen: () -> Unit,
    onDailyCheckInOpen: () -> Unit,
    onHabitToggle: (String) -> Unit,
    onMedicationToggle: (String) -> Unit,
    onSelectArticle: (ArticleItem) -> Unit,
    onEmergencyClick: () -> Unit,
    onLockClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
        item {
            AuraTopBar(
                title = "Good morning",
                subtitle = "How are you feeling today?",
                isDiscreetMode = uiState.isDiscreetMode,
                isAppLockEnabled = uiState.isAppLockEnabled,
                onLockClick = onLockClick,
                onEmergencyClick = onEmergencyClick
            )
        }

        // Offline Banner if simulated offline
        if (uiState.isOfflineMode) {
            item {
                OfflineBanner()
            }
        }

        // Mood Quick Selector
        item {
            HealthCard(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Today's Mood",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (uiState.todayMood != null) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = uiState.todayMood.color.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "${uiState.todayMood.emoji} ${uiState.todayMood.label}",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = uiState.todayMood.color,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                MoodSelectorRow(
                    selectedMood = uiState.todayMood,
                    onMoodSelected = onMoodSelected
                )
            }
        }

        // Hero "Today" Card
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable { onNavigateTab(NavigationTab.TRACK) }
                    .testTag("today_hero_card"),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 3.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    PhaseFollicularLight.copy(alpha = 0.6f),
                                    MaterialTheme.colorScheme.surface
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(PhaseFollicular.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("🌸", fontSize = 18.sp)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Today • Cycle Day ${uiState.cycleDay}",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Follicular Phase • Rising energy",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = PhaseFollicular
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "View tracker",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Estimated window: In ~2 to 4 days, your body may enter its estimated fertile window. (Estimate based on your logged history)",
                        style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // 3 Metric Pills
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            color = SleepVioletLight.copy(alpha = 0.7f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("🌙 Sleep", style = MaterialTheme.typography.labelSmall, color = SleepViolet)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("7h 42m", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = SleepViolet)
                            }
                        }

                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            color = HydrationBlueLight.copy(alpha = 0.7f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("💧 Water", style = MaterialTheme.typography.labelSmall, color = HydrationBlue)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("${uiState.hydration.currentGlasses}/${uiState.hydration.goalGlasses} cups", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = HydrationBlue)
                            }
                        }

                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            color = MovementAmberLight.copy(alpha = 0.7f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("🧘‍♀️ Movement", style = MaterialTheme.typography.labelSmall, color = MovementAmber)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("${uiState.activityMinutes} min", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = MovementAmber)
                            }
                        }
                    }
                }
            }
        }

        // Daily Check-in Card (Item 48)
        item {
            HealthCard(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .testTag("daily_checkin_card"),
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
                borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                onClick = onDailyCheckInOpen
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.EditNote,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Today's Wellness Check-In",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Record energy, symptoms, and self-care reflections",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Button(
                        onClick = onDailyCheckInOpen,
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("Check In", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }

        // Wellness Overview Cards (Item 17)
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Wellness Overview",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Based on tracked habits",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Sleep",
                        value = "${uiState.sleep.durationHours}h",
                        subtitle = uiState.sleep.quality,
                        icon = "🌙",
                        accentColor = SleepViolet,
                        onClick = { onNavigateTab(NavigationTab.WELLNESS) }
                    )
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Hydration",
                        value = "${uiState.hydration.currentGlasses}/${uiState.hydration.goalGlasses}",
                        subtitle = "${uiState.hydration.currentMl} ml logged",
                        icon = "💧",
                        accentColor = HydrationBlue,
                        onClick = { onNavigateTab(NavigationTab.WELLNESS) }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Movement",
                        value = "${uiState.activityMinutes} min",
                        subtitle = "Yoga & stretching",
                        icon = "🧘‍♀️",
                        accentColor = MovementAmber,
                        onClick = { onNavigateTab(NavigationTab.WELLNESS) }
                    )
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Self-Care",
                        value = "4 / 7",
                        subtitle = "Gentle habits done",
                        icon = "🌿",
                        accentColor = SelfCareTeal,
                        onClick = { onNavigateTab(NavigationTab.WELLNESS) }
                    )
                }
            }
        }

        // Today's Reminders (Item 34)
        item {
            HealthCard(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Today's Reminders",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${uiState.medications.count { it.isTakenToday }} of ${uiState.medications.size} completed",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                uiState.medications.forEach { med ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onMedicationToggle(med.id) },
                        shape = RoundedCornerShape(12.dp),
                        color = if (med.isTakenToday) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (med.isTakenToday) MaterialTheme.colorScheme.primary
                                        else Color.Transparent
                                    )
                                    .border(
                                        1.5.dp,
                                        if (med.isTakenToday) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.outline,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (med.isTakenToday) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = "Taken",
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = med.name,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${med.dosage} • ${med.time} (${med.frequency})",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

        // Gentle Habits Row (Items 72, 73, 74)
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Gentle Habits",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Ready to start again today?",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(uiState.habits) { habit ->
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { onHabitToggle(habit.id) },
                            shape = RoundedCornerShape(16.dp),
                            color = if (habit.isCompletedToday) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                                    else MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (habit.isCompletedToday) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = habit.icon, fontSize = 24.sp)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = habit.title,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${habit.currentStreak} day streak",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }

        // Recommended Reading (Item 41)
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recommended Reading",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    TextButton(onClick = { onNavigateTab(NavigationTab.LEARN) }) {
                        Text("Explore All")
                    }
                }

                MockDataProvider.articles.take(2).forEach { article ->
                    HealthCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        onClick = { onSelectArticle(article) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("📖", fontSize = 22.sp)
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = article.category.title,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = article.title,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "${article.readingTime} • ${article.date}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

        // Subtly placed medical disclaimer at the bottom (Item 2)
        item {
            WellnessNotice(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = "Tracking information helps you understand patterns, but it does not replace professional medical advice. For concerning symptoms, consult a qualified healthcare provider."
            )
        }
    }
}
