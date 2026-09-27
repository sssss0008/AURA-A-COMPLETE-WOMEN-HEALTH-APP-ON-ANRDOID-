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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.mock.MockDataProvider
import com.example.data.model.*
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun CycleTrackerScreen(
    uiState: com.example.ui.viewmodel.AuraUiState,
    onDaySelected: (Int) -> Unit,
    onLogPeriodFlow: (PeriodFlow) -> Unit,
    onToggleSymptom: (String) -> Unit,
    onSymptomInfoClick: (SymptomDefinition) -> Unit,
    onSaveJournal: (String, List<String>, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var showFlowDialog by remember { mutableStateOf(false) }
    var journalNoteText by remember { mutableStateOf(uiState.dailyJournalNote) }
    val selectedDayLog = uiState.calendarDays[uiState.selectedCalendarDay]

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("cycle_tracker_screen"),
        contentPadding = PaddingValues(bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                Text(
                    text = "Menstrual Health & Cycle",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Understand your natural rhythms and body patterns",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Cycle Wheel / Dial Hero Card
        item {
            HealthCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProgressRing(
                        progress = uiState.cycleDay / uiState.averageCycleLength.toFloat(),
                        size = 140.dp,
                        strokeWidth = 12.dp,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        progressColor = PhaseFollicular
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Day ${uiState.cycleDay}",
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "of ${uiState.averageCycleLength} days",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = PhaseFollicular.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = "Follicular Phase • Days 6–13",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = PhaseFollicular,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Rising estrogen levels often correlate with increased physical stamina, clear focus, and vitality.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Log Period Primary Button
                    Button(
                        onClick = { showFlowDialog = true },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PhaseMenstrual),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("log_period_button")
                    ) {
                        Icon(imageVector = Icons.Filled.WaterDrop, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Log Period for Day ${uiState.selectedCalendarDay}",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        }

        // Monthly Interactive Calendar (Item 10)
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
                        text = "September 2026",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Tap a date to view or log",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Day-of-week header
                val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    daysOfWeek.forEach { dayLetter ->
                        Text(
                            text = dayLetter,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.width(36.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 30 Days of September grid (5 rows)
                val totalDays = 30
                val startDayOffset = 2 // September starts on Tuesday in this simulation
                val weeks = (totalDays + startDayOffset + 6) / 7

                for (w in 0 until weeks) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (d in 0 until 7) {
                            val dayNumber = w * 7 + d - startDayOffset + 1
                            if (dayNumber in 1..totalDays) {
                                val isSelected = dayNumber == uiState.selectedCalendarDay
                                val dayLog = uiState.calendarDays[dayNumber]
                                val isPeriodDay = dayLog?.flow != null
                                val isEstimated = dayLog?.isEstimatedFuture == true
                                val hasSymptoms = !dayLog?.symptoms.isNullOrEmpty()

                                val backgroundColor = when {
                                    isSelected -> MaterialTheme.colorScheme.primaryContainer
                                    isPeriodDay -> PhaseMenstrualLight
                                    isEstimated -> PhaseOvulationLight
                                    else -> Color.Transparent
                                }

                                val textColor = when {
                                    isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
                                    isPeriodDay -> PhaseMenstrual
                                    isEstimated -> PhaseOvulation
                                    else -> MaterialTheme.colorScheme.onSurface
                                }

                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(backgroundColor)
                                        .border(
                                            width = if (isSelected) 1.5.dp else 0.dp,
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                            shape = CircleShape
                                        )
                                        .clickable { onDaySelected(dayNumber) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "$dayNumber",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontWeight = if (isSelected || isPeriodDay) FontWeight.Bold else FontWeight.Normal
                                            ),
                                            color = textColor
                                        )
                                        if (hasSymptoms) {
                                            Box(
                                                modifier = Modifier
                                                    .size(4.dp)
                                                    .clip(CircleShape)
                                                    .background(MaterialTheme.colorScheme.secondary)
                                            )
                                        }
                                    }
                                }
                            } else {
                                Box(modifier = Modifier.size(36.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Legend
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(PhaseMenstrual))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Period", style = MaterialTheme.typography.labelSmall)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(PhaseOvulation))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Est. Ovulation", style = MaterialTheme.typography.labelSmall)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(MaterialTheme.colorScheme.secondary))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Symptoms", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }

        // Daily Log Detail for Selected Day (Item 10)
        item {
            HealthCard(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Daily Log for September ${uiState.selectedCalendarDay}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (selectedDayLog != null) {
                    if (selectedDayLog.flow != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Flow:", style = MaterialTheme.typography.labelMedium)
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = PhaseMenstrual.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "${selectedDayLog.flow.label} Flow",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = PhaseMenstrual,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    if (selectedDayLog.isEstimatedFuture) {
                        Text(
                            text = "Estimated tracking window: Based on previously entered cycle lengths, this day may be near your fertile window.",
                            style = MaterialTheme.typography.bodySmall,
                            color = PhaseOvulation
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    if (selectedDayLog.mood != null) {
                        Text(
                            text = "Mood: ${selectedDayLog.mood.emoji} ${selectedDayLog.mood.label}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    if (selectedDayLog.symptoms.isNotEmpty()) {
                        Text(
                            text = "Logged symptoms: ${selectedDayLog.symptoms.joinToString(", ")}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    Text(
                        text = "No entries yet for this date. You can log your flow, mood, or symptoms below.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Log Symptoms for Today / Selected Day (Item 14)
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "Record Symptoms",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Tap a symptom to log it; tap (?) to view non-diagnostic guidance.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Physical Symptoms
                Text(
                    text = "Physical",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val physical = MockDataProvider.symptomsList.filter { it.category == SymptomCategory.PHYSICAL }
                    items(physical) { item ->
                        SymptomChip(
                            name = item.name,
                            icon = item.icon,
                            isSelected = uiState.todaySymptoms.contains(item.id),
                            onToggle = { onToggleSymptom(item.id) },
                            onInfoClick = { onSymptomInfoClick(item) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Digestive Symptoms
                Text(
                    text = "Digestive",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val digestive = MockDataProvider.symptomsList.filter { it.category == SymptomCategory.DIGESTIVE }
                    items(digestive) { item ->
                        SymptomChip(
                            name = item.name,
                            icon = item.icon,
                            isSelected = uiState.todaySymptoms.contains(item.id),
                            onToggle = { onToggleSymptom(item.id) },
                            onInfoClick = { onSymptomInfoClick(item) }
                        )
                    }
                }
            }
        }

        // Cycle Insights (Item 13)
        item {
            HealthCard(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Your Cycle Insights",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Based on your logged information over the last 3 cycles",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("Avg. Cycle Length", style = MaterialTheme.typography.labelSmall)
                            Text("28 Days", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary)
                        }
                    }
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("Period Duration", style = MaterialTheme.typography.labelSmall)
                            Text("5 Days", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Commonly logged symptoms: Mild Cramps (Days 1–2), Bloating (late luteal phase).",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Previous Cycle History (Item 12)
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "Cycle History",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                MockDataProvider.cycleHistoryList.forEach { history ->
                    HealthCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Cycle ${history.cycleNumber} • Started ${history.startDate}",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                                )
                                Text(
                                    text = "${history.cycleLengthDays} days total • ${history.periodDurationDays} period days",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = history.dominantMood.color.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "${history.dominantMood.emoji} ${history.dominantMood.label}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = history.dominantMood.color,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Health Disclaimer notice
        item {
            WellnessNotice(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = "Future cycle predictions are estimates calculated from your past logged entries. Cycles naturally fluctuate with sleep, stress, and lifestyle."
            )
        }
    }

    // Log Period Flow Dialog (Item 11)
    if (showFlowDialog) {
        AlertDialog(
            onDismissRequest = { showFlowDialog = false },
            title = {
                Text(
                    text = "Log Period for Day ${uiState.selectedCalendarDay}",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column {
                    Text(
                        text = "Choose your flow level. Language is neutral and non-judgmental.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    PeriodFlow.entries.forEach { flow ->
                        val isSelected = uiState.selectedPeriodFlow == flow
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    onLogPeriodFlow(flow)
                                    showFlowDialog = false
                                },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) PhaseMenstrualLight else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) PhaseMenstrual else Color.Transparent
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.WaterDrop,
                                    contentDescription = null,
                                    tint = PhaseMenstrual,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = flow.label,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showFlowDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}
