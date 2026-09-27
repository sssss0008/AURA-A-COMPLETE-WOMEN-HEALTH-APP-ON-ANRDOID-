package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
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
import com.example.data.model.LifeStageFocus
import com.example.ui.components.WellnessNotice

data class OnboardingStep(
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: String,
    val badgeColor: Color
)

@Composable
fun OnboardingScreen(
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    var stepIndex by remember { mutableIntStateOf(0) }

    val steps = listOf(
        OnboardingStep(
            title = "Understand your cycle",
            subtitle = "Track periods & natural rhythms",
            description = "Track periods, identify recurring patterns, and learn about the four natural phases of your cycle in an educational, non-judgmental space.",
            icon = "🌸",
            badgeColor = Color(0xFFDE5268)
        ),
        OnboardingStep(
            title = "Know your body",
            subtitle = "Tune in to your cues",
            description = "Record physical sensations, mood shifts, daily energy levels, and wellness factors without medical pressure or diagnostic assumptions.",
            icon = "✨",
            badgeColor = Color(0xFF8A599B)
        ),
        OnboardingStep(
            title = "Build healthy habits",
            subtitle = "Gentle everyday balance",
            description = "Nourish yourself with mindful hydration, restorative sleep routines, joyful movement, and self-care that fits your real lifestyle.",
            icon = "🌿",
            badgeColor = Color(0xFF3B9B7B)
        ),
        OnboardingStep(
            title = "Learn with confidence",
            subtitle = "Clear health education",
            description = "Explore trusted, medically responsible guides on reproductive health, menstrual hygiene, nutrition, puberty, pregnancy, and menopause.",
            icon = "📖",
            badgeColor = Color(0xFF3989C9)
        ),
        OnboardingStep(
            title = "Your information stays personal",
            subtitle = "Privacy by design",
            description = "Your health logs and notes belong solely to you. Enjoy built-in discreet mode, optional PIN app lock, and complete data export and deletion controls.",
            icon = "🔒",
            badgeColor = Color(0xFF75565F)
        )
    )

    val currentStep = steps[stepIndex]

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("onboarding_screen"),
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(24.dp)
            ) {
                // Step Indicator Dots
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    steps.indices.forEach { index ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(if (index == stepIndex) 20.dp else 8.dp, 8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (index == stepIndex) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (stepIndex > 0) {
                        TextButton(
                            onClick = { stepIndex-- },
                            modifier = Modifier.testTag("onboarding_back_btn")
                        ) {
                            Text("Back")
                        }
                    } else {
                        TextButton(
                            onClick = onFinished,
                            modifier = Modifier.testTag("onboarding_skip_btn")
                        ) {
                            Text("Skip")
                        }
                    }

                    Button(
                        onClick = {
                            if (stepIndex < steps.size - 1) {
                                stepIndex++
                            } else {
                                onFinished()
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .testTag("onboarding_next_btn")
                    ) {
                        Text(if (stepIndex == steps.size - 1) "Choose Focus" else "Next")
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(currentStep.badgeColor.copy(alpha = 0.15f))
                    .border(2.dp, currentStep.badgeColor.copy(alpha = 0.4f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentStep.icon,
                    fontSize = 52.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = currentStep.badgeColor.copy(alpha = 0.12f)
            ) {
                Text(
                    text = currentStep.subtitle,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = currentStep.title,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = currentStep.description,
                style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 24.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            WellnessNotice(
                text = "Aura is designed for educational reflection. It does not provide medical diagnoses or replace doctor care."
            )
        }
    }
}

@Composable
fun FocusSelectionScreen(
    selectedFocuses: Set<LifeStageFocus>,
    onToggleFocus: (LifeStageFocus) -> Unit,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("focus_selection_screen"),
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(20.dp),
                color = Color.Transparent
            ) {
                Button(
                    onClick = onComplete,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("focus_get_started_btn")
                ) {
                    Text(
                        text = "Get Started",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Personalize Your Space",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "What would you like to focus on right now? Select all that apply. You can change these anytime in Settings.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
                WellnessNotice(
                    text = "We never ask for unnecessary sensitive data. Your preferences only shape your dashboard."
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(LifeStageFocus.entries.toTypedArray()) { focus ->
                val isSelected = selectedFocuses.contains(focus)
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onToggleFocus(focus) }
                        .testTag("focus_item_${focus.name}"),
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                            else MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = "Selected",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = focus.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = focus.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
