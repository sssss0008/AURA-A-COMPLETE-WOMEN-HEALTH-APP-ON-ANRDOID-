package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.*
import com.example.ui.components.*
import com.example.ui.dialogs.*
import com.example.ui.screens.*
import com.example.ui.theme.AuraTheme
import com.example.ui.viewmodel.AuraViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val auraViewModel: AuraViewModel = viewModel()
            val uiState by auraViewModel.uiState.collectAsStateWithLifecycle()
            val context = LocalContext.current

            // Toast effect
            LaunchedEffect(uiState.toastMessage) {
                uiState.toastMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    auraViewModel.clearToast()
                }
            }

            val isSystemDark = isSystemInDarkTheme()
            val useDark = uiState.darkModeOverride ?: isSystemDark

            AuraTheme(
                darkTheme = useDark,
                highContrast = uiState.highContrast
            ) {
                when (uiState.currentScreen) {
                    AppScreen.SPLASH -> {
                        SplashScreen(
                            onContinue = { auraViewModel.setScreen(AppScreen.ONBOARDING) }
                        )
                    }

                    AppScreen.ONBOARDING -> {
                        OnboardingScreen(
                            onFinished = { auraViewModel.completeOnboarding() }
                        )
                    }

                    AppScreen.FOCUS_SELECTION -> {
                        FocusSelectionScreen(
                            selectedFocuses = uiState.lifeStageFocuses,
                            onToggleFocus = { auraViewModel.toggleFocus(it) },
                            onComplete = { auraViewModel.finishFocusSelection() }
                        )
                    }

                    AppScreen.APP_LOCK_UNLOCK -> {
                        AppLockScreen(
                            enteredPin = uiState.enteredPin,
                            onDigitPress = { auraViewModel.enterPinDigit(it) },
                            onDeletePress = { auraViewModel.clearPinDigit() },
                            onBypassForDemo = { auraViewModel.setScreen(AppScreen.MAIN_APP) }
                        )
                    }

                    AppScreen.MAIN_APP -> {
                        MainAppScaffold(
                            uiState = uiState,
                            viewModel = auraViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainAppScaffold(
    uiState: com.example.ui.viewmodel.AuraUiState,
    viewModel: AuraViewModel
) {
    val context = LocalContext.current

    // Hardware Back Button handling
    BackHandler {
        when {
            uiState.selectedArticle != null -> viewModel.selectArticle(null)
            uiState.selectedWorkout != null -> viewModel.selectWorkout(null)
            uiState.selectedDoctor != null -> viewModel.selectDoctor(null)
            uiState.activeQuiz != null -> viewModel.exitQuiz()
            uiState.currentTab != NavigationTab.HOME -> viewModel.setTab(NavigationTab.HOME)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("main_app_scaffold"),
        bottomBar = {
            AuraBottomBar(
                currentTab = uiState.currentTab,
                onTabSelected = { viewModel.setTab(it) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.openQuickLog(true) },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .offset(y = 10.dp)
                    .testTag("global_fab_log")
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Quick Log Health Data",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentTab) {
                NavigationTab.HOME -> {
                    HomeScreen(
                        uiState = uiState,
                        onMoodSelected = { viewModel.setTodayMood(it) },
                        onNavigateTab = { viewModel.setTab(it) },
                        onQuickLogOpen = { viewModel.openQuickLog(true) },
                        onDailyCheckInOpen = { viewModel.openDailyCheckIn(true) },
                        onHabitToggle = { viewModel.toggleHabit(it) },
                        onMedicationToggle = { viewModel.toggleMedicationTaken(it) },
                        onSelectArticle = { viewModel.selectArticle(it) },
                        onEmergencyClick = { viewModel.openEmergencyDialog(true) },
                        onLockClick = { viewModel.lockApp() }
                    )
                }

                NavigationTab.TRACK -> {
                    CycleTrackerScreen(
                        uiState = uiState,
                        onDaySelected = { viewModel.selectCalendarDay(it) },
                        onLogPeriodFlow = { viewModel.logPeriodForSelectedDay(it) },
                        onToggleSymptom = { viewModel.toggleSymptom(it) },
                        onSymptomInfoClick = { viewModel.selectSymptomForInfo(it) },
                        onSaveJournal = { note, tags, priv -> viewModel.saveJournalEntry(note, tags, priv) }
                    )
                }

                NavigationTab.LEARN -> {
                    LearnScreen(
                        onSelectArticle = { viewModel.selectArticle(it) },
                        onStartQuiz = { viewModel.startQuiz(it) }
                    )
                }

                NavigationTab.WELLNESS -> {
                    WellnessScreen(
                        uiState = uiState,
                        onAddWater = { viewModel.addWater(it) },
                        onHabitToggle = { viewModel.toggleHabit(it) },
                        onSelectWorkout = { viewModel.selectWorkout(it) }
                    )
                }

                NavigationTab.PROFILE -> {
                    ProfileSettingsScreen(
                        uiState = uiState,
                        onToggleDiscreetMode = { viewModel.toggleDiscreetMode() },
                        onLockApp = { viewModel.lockApp() },
                        onSetNotificationPrivacy = { viewModel.setNotificationPrivacy(it) },
                        onSetLanguage = { viewModel.setLanguage(it) },
                        onToggleHighContrast = { viewModel.toggleHighContrast() },
                        onToggleDarkMode = { viewModel.toggleDarkModeOverride() },
                        onToggleOfflineMode = { viewModel.toggleOfflineMode() },
                        onOpenExport = { viewModel.openExportDialog(true) },
                        onRequestDeleteCategory = { viewModel.requestDeleteCategory(it) },
                        onEmergencyClick = { viewModel.openEmergencyDialog(true) }
                    )
                }
            }

            // Quick Log Bottom Sheet
            if (uiState.isQuickLogOpen) {
                QuickLogBottomSheet(
                    onDismiss = { viewModel.openQuickLog(false) },
                    onLogPeriod = { viewModel.setTab(NavigationTab.TRACK) },
                    onLogMood = { viewModel.openDailyCheckIn(true) },
                    onLogSymptom = { viewModel.setTab(NavigationTab.TRACK) },
                    onAddWater = { viewModel.addWater(it) },
                    onLogSleep = { viewModel.setTab(NavigationTab.WELLNESS) },
                    onLogActivity = { viewModel.setTab(NavigationTab.WELLNESS) },
                    onOpenJournal = { viewModel.openDailyCheckIn(true) }
                )
            }

            // Daily Check-in Dialog
            if (uiState.showDailyCheckIn) {
                DailyCheckInDialog(
                    currentMood = uiState.todayMood,
                    onMoodSelected = { viewModel.setTodayMood(it) },
                    todaySymptoms = uiState.todaySymptoms,
                    onToggleSymptom = { viewModel.toggleSymptom(it) },
                    onSaveJournal = { note, tags, priv -> viewModel.saveJournalEntry(note, tags, priv) },
                    onDismiss = { viewModel.openDailyCheckIn(false) }
                )
            }

            // Article Detail Dialog
            uiState.selectedArticle?.let { article ->
                ArticleDetailDialog(
                    article = article,
                    onDismiss = { viewModel.selectArticle(null) }
                )
            }

            // Workout Detail Dialog
            uiState.selectedWorkout?.let { workout ->
                WorkoutDetailDialog(
                    workout = workout,
                    onDismiss = { viewModel.selectWorkout(null) }
                )
            }

            // Doctor Profile Dialog
            uiState.selectedDoctor?.let { doctor ->
                DoctorProfileDialog(
                    doctor = doctor,
                    onBookAppointment = {
                        viewModel.addAppointment(
                            doctorName = it.name,
                            specialty = it.specialty.title,
                            date = "Nov 04, 2026",
                            time = "02:00 PM",
                            location = it.location,
                            reason = "Consultation & Health Planning",
                            questions = listOf("Review cycle trends", "Personalized nutrition advice")
                        )
                    },
                    onDismiss = { viewModel.selectDoctor(null) }
                )
            }

            // Symptom Information Dialog
            uiState.selectedSymptomForInfo?.let { symptom ->
                SymptomInfoDialog(
                    symptom = symptom,
                    onDismiss = { viewModel.selectSymptomForInfo(null) }
                )
            }

            // Emergency Dialog
            if (uiState.showEmergencyDialog) {
                EmergencySafetyDialog(
                    onDismiss = { viewModel.openEmergencyDialog(false) }
                )
            }

            // Data Export Dialog
            if (uiState.showExportDialog) {
                AlertDialog(
                    onDismissRequest = { viewModel.openExportDialog(false) },
                    title = { Text("Export Your Health Data") },
                    text = {
                        Column {
                            Text("Select format for exporting your logs and health entries:")
                            Spacer(modifier = Modifier.height(12.dp))
                            listOf("PDF Summary Report", "CSV Spreadsheet", "JSON Data File").forEach { fmt ->
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                            viewModel.openExportDialog(false)
                                            Toast.makeText(context, "Generated $fmt export", Toast.LENGTH_SHORT).show()
                                        },
                                    shape = RoundedCornerShape(10.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(text = fmt, modifier = Modifier.padding(12.dp), style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { viewModel.openExportDialog(false) }) {
                            Text("Cancel")
                        }
                    }
                )
            }

            // Delete Confirm Dialog
            if (uiState.showDeleteConfirmDialog) {
                AlertDialog(
                    onDismissRequest = { viewModel.dismissDeleteDialog() },
                    title = { Text("Delete ${uiState.deleteTargetCategory}?") },
                    text = {
                        Text("This will permanently remove your ${uiState.deleteTargetCategory} stored locally on this device. This action cannot be undone.")
                    },
                    confirmButton = {
                        Button(
                            onClick = { viewModel.confirmDeleteData() },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Confirm Delete")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { viewModel.dismissDeleteDialog() }) {
                            Text("Cancel")
                        }
                    }
                )
            }

            // Active Quiz Overlay
            uiState.activeQuiz?.let { quiz ->
                val currentQ = quiz.questions.getOrNull(uiState.quizQuestionIndex)
                if (currentQ != null) {
                    AlertDialog(
                        onDismissRequest = { viewModel.exitQuiz() },
                        title = {
                            Text("${quiz.title} (${uiState.quizQuestionIndex + 1}/${quiz.questions.size})")
                        },
                        text = {
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text(text = currentQ.question, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))

                                currentQ.options.forEachIndexed { index, option ->
                                    val isSelected = uiState.quizSelectedOption == index
                                    val isCorrect = index == currentQ.correctIndex

                                    val optionColor = when {
                                        uiState.quizShowExplanation && isCorrect -> Color(0xFFE8F5E9)
                                        uiState.quizShowExplanation && isSelected && !isCorrect -> Color(0xFFFFEBEE)
                                        isSelected -> MaterialTheme.colorScheme.primaryContainer
                                        else -> MaterialTheme.colorScheme.surfaceVariant
                                    }

                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(10.dp))
                                            .clickable(enabled = !uiState.quizShowExplanation) {
                                                viewModel.answerQuizQuestion(index)
                                            },
                                        shape = RoundedCornerShape(10.dp),
                                        color = optionColor
                                    ) {
                                        Text(text = option, modifier = Modifier.padding(12.dp), style = MaterialTheme.typography.bodyMedium)
                                    }
                                }

                                if (uiState.quizShowExplanation) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                                    ) {
                                        Text(
                                            text = "💡 ${currentQ.explanation}",
                                            modifier = Modifier.padding(10.dp),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            if (uiState.quizShowExplanation) {
                                Button(onClick = { viewModel.nextQuizQuestion() }) {
                                    Text(if (uiState.quizQuestionIndex + 1 < quiz.questions.size) "Next Question" else "Finish Quiz")
                                }
                            } else {
                                TextButton(onClick = { viewModel.exitQuiz() }) {
                                    Text("Exit")
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
