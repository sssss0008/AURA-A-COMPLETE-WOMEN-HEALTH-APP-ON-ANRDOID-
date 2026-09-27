package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.mock.MockDataProvider
import com.example.data.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DashboardCardVisibility(
    val showCycle: Boolean = true,
    val showMood: Boolean = true,
    val showSleep: Boolean = true,
    val showWater: Boolean = true,
    val showMovement: Boolean = true,
    val showReminders: Boolean = true,
    val showArticles: Boolean = true,
    val showJournal: Boolean = true
)

data class AuraUiState(
    val currentScreen: AppScreen = AppScreen.SPLASH,
    val currentTab: NavigationTab = NavigationTab.HOME,
    val lifeStageFocuses: Set<LifeStageFocus> = setOf(
        LifeStageFocus.PERIOD_CYCLE,
        LifeStageFocus.GENERAL_WELLNESS,
        LifeStageFocus.SLEEP_RECOVERY
    ),
    // Today metrics
    val cycleDay: Int = 12,
    val averageCycleLength: Int = 28,
    val periodDuration: Int = 5,
    val todayMood: MoodType? = MoodType.GOOD,
    val hydration: HydrationState = HydrationState(currentGlasses = 5, goalGlasses = 8),
    val sleep: SleepRecord = SleepRecord(bedtime = "10:45 PM", wakeTime = "6:27 AM", durationHours = 7.7, quality = "Restful & Good"),
    val activityMinutes: Int = 32,
    val activityType: ActivityType = ActivityType.YOGA,
    // Cycle & Calendar
    val calendarDays: Map<Int, PeriodDayLog> = MockDataProvider.calendarDays,
    val selectedCalendarDay: Int = 12,
    val selectedPeriodFlow: PeriodFlow = PeriodFlow.MEDIUM,
    val todaySymptoms: Set<String> = setOf("skin_changes"),
    val dailyJournalNote: String = "Feeling vibrant and motivated today. Practiced 30 minutes of gentle yoga.",
    // Lists
    val journalEntries: List<JournalEntry> = listOf(
        JournalEntry(
            id = "j1",
            date = "Today, Sep 27",
            mood = MoodType.GOOD,
            energyLevel = 4,
            sleepHours = 7.7,
            waterGlasses = 5,
            activityMinutes = 32,
            symptoms = listOf("Skin Changes"),
            notes = "Feeling light and creative during the follicular phase.",
            tags = listOf("Follicular", "Yoga", "Hydrated"),
            isPrivate = false
        ),
        JournalEntry(
            id = "j2",
            date = "Sep 26, 2026",
            mood = MoodType.CALM,
            energyLevel = 4,
            sleepHours = 8.0,
            waterGlasses = 8,
            activityMinutes = 45,
            symptoms = emptyList(),
            notes = "Enjoyed an unhurried sunset walk in the park.",
            tags = listOf("Walking", "Restful"),
            isPrivate = true
        )
    ),
    val habits: List<HabitItem> = MockDataProvider.initialHabits,
    val appointments: List<AppointmentItem> = MockDataProvider.initialAppointments,
    val medications: List<MedicationReminder> = MockDataProvider.initialMedications,
    val documents: List<HealthDocument> = MockDataProvider.initialDocuments,
    val timelineEvents: List<HealthTimelineEvent> = MockDataProvider.initialTimeline,
    val challenges: List<WellnessChallenge> = MockDataProvider.wellnessChallenges,
    val cardVisibility: DashboardCardVisibility = DashboardCardVisibility(),
    // Detail Overlays
    val selectedArticle: ArticleItem? = null,
    val selectedWorkout: WorkoutItem? = null,
    val selectedDoctor: DoctorProfile? = null,
    val selectedSymptomForInfo: SymptomDefinition? = null,
    val isQuickLogOpen: Boolean = false,
    val showDailyCheckIn: Boolean = false,
    val showBookingDialog: Boolean = false,
    val showAddAppointmentDialog: Boolean = false,
    val showAddMedicationDialog: Boolean = false,
    val showAddDocumentDialog: Boolean = false,
    val showEmergencyDialog: Boolean = false,
    val showExportDialog: Boolean = false,
    val showDeleteConfirmDialog: Boolean = false,
    val deleteTargetCategory: String = "",
    val activeQuiz: Quiz? = null,
    val quizQuestionIndex: Int = 0,
    val quizSelectedOption: Int? = null,
    val quizShowExplanation: Boolean = false,
    val quizCorrectCount: Int = 0,
    // Privacy & App Lock
    val isAppLockEnabled: Boolean = false,
    val isAppLocked: Boolean = false,
    val pinCode: String = "1234",
    val enteredPin: String = "",
    val notificationPrivacy: NotificationPrivacy = NotificationPrivacy.NEUTRAL,
    val isDiscreetMode: Boolean = false,
    val selectedLanguage: LanguageCode = LanguageCode.EN,
    val highContrast: Boolean = false,
    val darkModeOverride: Boolean? = null, // null for system, true/false manual
    val isOfflineMode: Boolean = false,
    val toastMessage: String? = null
)

class AuraViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AuraUiState())
    val uiState: StateFlow<AuraUiState> = _uiState.asStateFlow()

    init {
        // Splash screen auto transition
        viewModelScope.launch {
            delay(1600)
            if (_uiState.value.currentScreen == AppScreen.SPLASH) {
                _uiState.update { it.copy(currentScreen = AppScreen.ONBOARDING) }
            }
        }
    }

    // Navigation
    fun setScreen(screen: AppScreen) {
        _uiState.update { it.copy(currentScreen = screen) }
    }

    fun setTab(tab: NavigationTab) {
        _uiState.update { it.copy(currentTab = tab, selectedArticle = null, selectedWorkout = null) }
    }

    fun toggleFocus(focus: LifeStageFocus) {
        _uiState.update { state ->
            val updated = if (state.lifeStageFocuses.contains(focus)) {
                if (state.lifeStageFocuses.size > 1) state.lifeStageFocuses - focus else state.lifeStageFocuses
            } else {
                state.lifeStageFocuses + focus
            }
            state.copy(lifeStageFocuses = updated)
        }
    }

    fun completeOnboarding() {
        _uiState.update { it.copy(currentScreen = AppScreen.FOCUS_SELECTION) }
    }

    fun finishFocusSelection() {
        _uiState.update { it.copy(currentScreen = AppScreen.MAIN_APP) }
    }

    // Daily Mood Logging
    fun setTodayMood(mood: MoodType) {
        _uiState.update { state ->
            val updatedCalendar = state.calendarDays.toMutableMap()
            val existing = updatedCalendar[state.selectedCalendarDay] ?: PeriodDayLog(dayOfMonth = state.selectedCalendarDay)
            updatedCalendar[state.selectedCalendarDay] = existing.copy(mood = mood)
            state.copy(todayMood = mood, calendarDays = updatedCalendar, toastMessage = "Logged mood: ${mood.label}")
        }
    }

    // Hydration
    fun addWater(ml: Int) {
        _uiState.update { state ->
            val glassesToAdd = (ml / state.hydration.glassVolumeMl).coerceAtLeast(1)
            val newGlasses = (state.hydration.currentGlasses + glassesToAdd).coerceAtMost(20)
            state.copy(
                hydration = state.hydration.copy(currentGlasses = newGlasses),
                toastMessage = "Added +${ml}ml water! Stay hydrated."
            )
        }
    }

    fun updateWaterGoal(goal: Int) {
        _uiState.update { it.copy(hydration = it.hydration.copy(goalGlasses = goal.coerceIn(4, 20))) }
    }

    // Calendar & Period Logging
    fun selectCalendarDay(day: Int) {
        _uiState.update { it.copy(selectedCalendarDay = day) }
    }

    fun logPeriodForSelectedDay(flow: PeriodFlow) {
        _uiState.update { state ->
            val updated = state.calendarDays.toMutableMap()
            val existing = updated[state.selectedCalendarDay] ?: PeriodDayLog(dayOfMonth = state.selectedCalendarDay)
            updated[state.selectedCalendarDay] = existing.copy(flow = flow)
            state.copy(calendarDays = updated, selectedPeriodFlow = flow, toastMessage = "Logged ${flow.label} flow for Day ${state.selectedCalendarDay}")
        }
    }

    fun toggleSymptom(symptomId: String) {
        _uiState.update { state ->
            val updatedSymptoms = if (state.todaySymptoms.contains(symptomId)) {
                state.todaySymptoms - symptomId
            } else {
                state.todaySymptoms + symptomId
            }
            val updatedCalendar = state.calendarDays.toMutableMap()
            val existing = updatedCalendar[state.selectedCalendarDay] ?: PeriodDayLog(dayOfMonth = state.selectedCalendarDay)
            updatedCalendar[state.selectedCalendarDay] = existing.copy(symptoms = updatedSymptoms.toList())
            state.copy(todaySymptoms = updatedSymptoms, calendarDays = updatedCalendar)
        }
    }

    fun saveJournalEntry(note: String, tags: List<String>, isPrivate: Boolean) {
        _uiState.update { state ->
            val newEntry = JournalEntry(
                id = "j_${System.currentTimeMillis()}",
                date = "Day ${state.selectedCalendarDay}, Today",
                mood = state.todayMood ?: MoodType.GOOD,
                energyLevel = 4,
                sleepHours = state.sleep.durationHours,
                waterGlasses = state.hydration.currentGlasses,
                activityMinutes = state.activityMinutes,
                symptoms = state.todaySymptoms.map { id -> MockDataProvider.symptomsList.find { it.id == id }?.name ?: id },
                notes = note,
                tags = tags.ifEmpty { listOf("Wellness", "Reflection") },
                isPrivate = isPrivate
            )
            state.copy(
                journalEntries = listOf(newEntry) + state.journalEntries,
                dailyJournalNote = note,
                toastMessage = "Journal entry saved safely."
            )
        }
    }

    // Habits & Challenges
    fun toggleHabit(habitId: String) {
        _uiState.update { state ->
            val updated = state.habits.map { habit ->
                if (habit.id == habitId) {
                    val nextCompleted = !habit.isCompletedToday
                    habit.copy(
                        isCompletedToday = nextCompleted,
                        currentStreak = if (nextCompleted) habit.currentStreak + 1 else (habit.currentStreak - 1).coerceAtLeast(0)
                    )
                } else habit
            }
            state.copy(habits = updated)
        }
    }

    // Medications
    fun toggleMedicationTaken(medId: String) {
        _uiState.update { state ->
            val updated = state.medications.map { med ->
                if (med.id == medId) med.copy(isTakenToday = !med.isTakenToday) else med
            }
            state.copy(medications = updated, toastMessage = "Medication reminder updated.")
        }
    }

    fun addMedication(name: String, dosage: String, time: String, frequency: String, notes: String) {
        _uiState.update { state ->
            val newMed = MedicationReminder(
                id = "med_${System.currentTimeMillis()}",
                name = name,
                dosage = dosage,
                time = time,
                frequency = frequency,
                notes = notes,
                isTakenToday = false
            )
            state.copy(medications = state.medications + newMed, showAddMedicationDialog = false, toastMessage = "Reminder set for $name")
        }
    }

    // Appointments & Booking
    fun addAppointment(doctorName: String, specialty: String, date: String, time: String, location: String, reason: String, questions: List<String>) {
        _uiState.update { state ->
            val newApt = AppointmentItem(
                id = "apt_${System.currentTimeMillis()}",
                doctorName = doctorName,
                specialty = specialty,
                date = date,
                time = time,
                location = location,
                reason = reason,
                questions = questions
            )
            state.copy(
                appointments = state.appointments + newApt,
                showAddAppointmentDialog = false,
                showBookingDialog = false,
                selectedDoctor = null,
                toastMessage = "Appointment scheduled with $doctorName"
            )
        }
    }

    // Overlays & Sheets
    fun openQuickLog(open: Boolean) = _uiState.update { it.copy(isQuickLogOpen = open) }
    fun openDailyCheckIn(open: Boolean) = _uiState.update { it.copy(showDailyCheckIn = open) }
    fun selectArticle(article: ArticleItem?) = _uiState.update { it.copy(selectedArticle = article) }
    fun selectWorkout(workout: WorkoutItem?) = _uiState.update { it.copy(selectedWorkout = workout) }
    fun selectDoctor(doctor: DoctorProfile?) = _uiState.update { it.copy(selectedDoctor = doctor) }
    fun selectSymptomForInfo(symptom: SymptomDefinition?) = _uiState.update { it.copy(selectedSymptomForInfo = symptom) }
    fun openEmergencyDialog(open: Boolean) = _uiState.update { it.copy(showEmergencyDialog = open) }
    fun openExportDialog(open: Boolean) = _uiState.update { it.copy(showExportDialog = open) }
    fun openBookingDialog(open: Boolean) = _uiState.update { it.copy(showBookingDialog = open) }

    // Quizzes
    fun startQuiz(quiz: Quiz) {
        _uiState.update {
            it.copy(
                activeQuiz = quiz,
                quizQuestionIndex = 0,
                quizSelectedOption = null,
                quizShowExplanation = false,
                quizCorrectCount = 0
            )
        }
    }

    fun answerQuizQuestion(optionIndex: Int) {
        _uiState.update { state ->
            val quiz = state.activeQuiz ?: return@update state
            val currentQ = quiz.questions.getOrNull(state.quizQuestionIndex) ?: return@update state
            val isCorrect = optionIndex == currentQ.correctIndex
            state.copy(
                quizSelectedOption = optionIndex,
                quizShowExplanation = true,
                quizCorrectCount = if (isCorrect) state.quizCorrectCount + 1 else state.quizCorrectCount
            )
        }
    }

    fun nextQuizQuestion() {
        _uiState.update { state ->
            val quiz = state.activeQuiz ?: return@update state
            if (state.quizQuestionIndex + 1 < quiz.questions.size) {
                state.copy(
                    quizQuestionIndex = state.quizQuestionIndex + 1,
                    quizSelectedOption = null,
                    quizShowExplanation = false
                )
            } else {
                state.copy(activeQuiz = null, toastMessage = "Quiz completed! Great learning.")
            }
        }
    }

    fun exitQuiz() = _uiState.update { it.copy(activeQuiz = null) }

    // Privacy & App Lock
    fun setPinCode(pin: String) = _uiState.update { it.copy(pinCode = pin, isAppLockEnabled = true) }
    fun enterPinDigit(digit: String) {
        _uiState.update { state ->
            val newPin = (state.enteredPin + digit).take(4)
            if (newPin.length == 4) {
                if (newPin == state.pinCode) {
                    state.copy(isAppLocked = false, enteredPin = "", currentScreen = AppScreen.MAIN_APP, toastMessage = "Health space unlocked")
                } else {
                    state.copy(enteredPin = "", toastMessage = "Incorrect PIN. Try 1234")
                }
            } else {
                state.copy(enteredPin = newPin)
            }
        }
    }

    fun clearPinDigit() {
        _uiState.update { it.copy(enteredPin = it.enteredPin.dropLast(1)) }
    }

    fun lockApp() {
        _uiState.update { it.copy(isAppLocked = true, currentScreen = AppScreen.APP_LOCK_UNLOCK) }
    }

    fun setNotificationPrivacy(privacy: NotificationPrivacy) {
        _uiState.update { it.copy(notificationPrivacy = privacy, toastMessage = "Notification style: ${privacy.label}") }
    }

    fun toggleDiscreetMode() {
        _uiState.update { it.copy(isDiscreetMode = !it.isDiscreetMode) }
    }

    fun setLanguage(lang: LanguageCode) {
        _uiState.update { it.copy(selectedLanguage = lang, toastMessage = "Language set to ${lang.displayName}") }
    }

    fun toggleHighContrast() {
        _uiState.update { it.copy(highContrast = !it.highContrast) }
    }

    fun toggleDarkModeOverride() {
        _uiState.update { state ->
            val next = when (state.darkModeOverride) {
                null -> true
                true -> false
                false -> null
            }
            state.copy(darkModeOverride = next)
        }
    }

    fun toggleOfflineMode() {
        _uiState.update { it.copy(isOfflineMode = !it.isOfflineMode) }
    }

    // Delete local data controls
    fun requestDeleteCategory(category: String) {
        _uiState.update { it.copy(showDeleteConfirmDialog = true, deleteTargetCategory = category) }
    }

    fun confirmDeleteData() {
        _uiState.update { state ->
            val category = state.deleteTargetCategory
            val updated = when (category) {
                "Tracking Data" -> state.copy(calendarDays = emptyMap(), todaySymptoms = emptySet())
                "Health Journal" -> state.copy(journalEntries = emptyList(), dailyJournalNote = "")
                "Documents" -> state.copy(documents = emptyList())
                "All Local Data" -> state.copy(
                    calendarDays = emptyMap(),
                    todaySymptoms = emptySet(),
                    journalEntries = emptyList(),
                    documents = emptyList(),
                    appointments = emptyList(),
                    dailyJournalNote = ""
                )
                else -> state
            }
            updated.copy(showDeleteConfirmDialog = false, deleteTargetCategory = "", toastMessage = "$category deleted locally.")
        }
    }

    fun dismissDeleteDialog() = _uiState.update { it.copy(showDeleteConfirmDialog = false, deleteTargetCategory = "") }

    fun clearToast() = _uiState.update { it.copy(toastMessage = null) }
}
