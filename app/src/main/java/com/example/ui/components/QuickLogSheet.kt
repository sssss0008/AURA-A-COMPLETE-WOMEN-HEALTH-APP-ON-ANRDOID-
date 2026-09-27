package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PeriodFlow
import com.example.ui.theme.*

data class QuickLogAction(
    val title: String,
    val icon: String,
    val color: Color,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickLogBottomSheet(
    onDismiss: () -> Unit,
    onLogPeriod: () -> Unit,
    onLogMood: () -> Unit,
    onLogSymptom: () -> Unit,
    onAddWater: (Int) -> Unit,
    onLogSleep: () -> Unit,
    onLogActivity: () -> Unit,
    onOpenJournal: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier.testTag("quick_log_bottom_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 36.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Quick Log Today",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                }
            }

            Text(
                text = "Capture how you feel in one easy tap.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            val actions = listOf(
                QuickLogAction("Period", "🌸", PhaseMenstrual) {
                    onLogPeriod()
                    onDismiss()
                },
                QuickLogAction("Mood", "✨", MoodHappy) {
                    onLogMood()
                    onDismiss()
                },
                QuickLogAction("Symptom", "⚡", MaterialTheme.colorScheme.secondary) {
                    onLogSymptom()
                    onDismiss()
                },
                QuickLogAction("+250ml Water", "💧", HydrationBlue) {
                    onAddWater(250)
                    onDismiss()
                },
                QuickLogAction("Sleep", "🌙", SleepViolet) {
                    onLogSleep()
                    onDismiss()
                },
                QuickLogAction("Movement", "🧘‍♀️", MovementAmber) {
                    onLogActivity()
                    onDismiss()
                },
                QuickLogAction("Journal", "📝", MaterialTheme.colorScheme.primary) {
                    onOpenJournal()
                    onDismiss()
                }
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(actions) { action ->
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable(onClick = action.onClick),
                        shape = RoundedCornerShape(16.dp),
                        color = action.color.copy(alpha = 0.12f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, action.color.copy(alpha = 0.3f))
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(action.color.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = action.icon, fontSize = 22.sp)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = action.title,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
