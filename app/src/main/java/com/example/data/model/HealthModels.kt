package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.*

// App Navigation & Life Stage Focus
enum class AppScreen {
    SPLASH,
    ONBOARDING,
    FOCUS_SELECTION,
    MAIN_APP,
    APP_LOCK_UNLOCK
}

enum class NavigationTab(val label: String) {
    HOME("Home"),
    TRACK("Track"),
    LEARN("Learn"),
    WELLNESS("Wellness"),
    PROFILE("Profile")
}

enum class LifeStageFocus(val title: String, val subtitle: String) {
    PERIOD_CYCLE("Period & Cycle", "Track menstrual cycle and symptom patterns"),
    GENERAL_WELLNESS("General Wellness", "Daily habits, energy, and overall health"),
    FITNESS_MOVEMENT("Fitness & Movement", "Gentle workouts, mobility, and strength"),
    NUTRITION("Balanced Nutrition", "Wholesome meal ideas and vital nutrients"),
    PREGNANCY_EDUCATION("Pregnancy Journey", "Week-by-week educational guidance"),
    MENOPAUSE_EDUCATION("Menopause Education", "Changes, body wisdom, and self-care"),
    SLEEP_RECOVERY("Sleep & Recovery", "Rest routines and sleep quality"),
    MENTAL_WELLBEING("Mental Wellbeing", "Mindfulness, stress relief, and journaling"),
    HEALTH_EDUCATION("Health Education", "Reproductive knowledge and body literacy")
}

// Mood tracking
enum class MoodType(val label: String, val emoji: String, val color: Color) {
    GREAT("Great", "✨", MoodHappy),
    GOOD("Good", "😊", MoodCalm),
    CALM("Calm", "🌿", MoodCalm),
    ENERGETIC("Energetic", "⚡", MoodEnergetic),
    OKAY("Okay", "🙂", MoodNeutral),
    NEUTRAL("Neutral", "😐", MoodNeutral),
    LOW("Low", "🌧️", MoodSad),
    TIRED("Tired", "😴", MoodTired),
    STRESSED("Stressed", "🌪️", MoodStressed),
    SAD("Sad", "💙", MoodSad),
    IRRITATED("Irritated", "🌩️", MoodIrritated)
}

// Menstrual cycle models
enum class PeriodFlow(val label: String) {
    SPOTTING("Spotting"),
    LIGHT("Light"),
    MEDIUM("Medium"),
    HEAVY("Heavy")
}

enum class CyclePhase(val title: String, val subtitle: String, val color: Color, val lightColor: Color) {
    MENSTRUAL("Menstrual Phase", "Days 1–5 • Focus on rest & gentle care", PhaseMenstrual, PhaseMenstrualLight),
    FOLLICULAR("Follicular Phase", "Days 6–13 • Rising energy & vitality", PhaseFollicular, PhaseFollicularLight),
    OVULATION("Ovulation Window", "Days 14–16 • Peak energy & fertility", PhaseOvulation, PhaseOvulationLight),
    LUTEAL("Luteal Phase", "Days 17–28 • Winding down & nourishment", PhaseLuteal, PhaseLutealLight)
}

data class PeriodDayLog(
    val dayOfMonth: Int,
    val flow: PeriodFlow? = null,
    val symptoms: List<String> = emptyList(),
    val mood: MoodType? = null,
    val notes: String = "",
    val isEstimatedFuture: Boolean = false
)

data class CycleHistoryItem(
    val cycleNumber: Int,
    val startDate: String,
    val cycleLengthDays: Int,
    val periodDurationDays: Int,
    val commonSymptoms: List<String>,
    val dominantMood: MoodType
)

// Symptoms
enum class SymptomCategory(val displayName: String) {
    PHYSICAL("Physical"),
    DIGESTIVE("Digestive"),
    GENERAL("General")
}

data class SymptomDefinition(
    val id: String,
    val name: String,
    val icon: String,
    val category: SymptomCategory,
    val description: String,
    val selfCareTip: String,
    val whenToSeekHelp: String
)

// Health Journal
data class JournalEntry(
    val id: String,
    val date: String,
    val mood: MoodType,
    val energyLevel: Int, // 1 to 5
    val sleepHours: Double,
    val waterGlasses: Int,
    val activityMinutes: Int,
    val symptoms: List<String>,
    val notes: String,
    val tags: List<String>,
    val isPrivate: Boolean = false
)

// Hydration & Sleep & Activity
data class HydrationState(
    val currentGlasses: Int = 5,
    val goalGlasses: Int = 8,
    val glassVolumeMl: Int = 250
) {
    val currentMl: Int get() = currentGlasses * glassVolumeMl
    val goalMl: Int get() = goalGlasses * glassVolumeMl
    val progress: Float get() = (currentGlasses.toFloat() / goalGlasses.coerceAtLeast(1)).coerceIn(0f, 1f)
}

data class SleepRecord(
    val bedtime: String = "10:45 PM",
    val wakeTime: String = "6:27 AM",
    val durationHours: Double = 7.7,
    val quality: String = "Restful & Good",
    val deepSleepHours: Double = 2.1
)

enum class ActivityType(val label: String, val icon: String) {
    WALKING("Walking", "🚶‍♀️"),
    YOGA("Yoga", "🧘‍♀️"),
    STRETCHING("Stretching", "🤸‍♀️"),
    RUNNING("Running", "🏃‍♀️"),
    CYCLING("Cycling", "🚴‍♀️"),
    STRENGTH("Strength", "🏋️‍♀️"),
    SPORTS("Sports", "🏸")
}

data class ActivityLog(
    val type: ActivityType,
    val durationMinutes: Int,
    val steps: Int = 0,
    val distanceKm: Double = 0.0,
    val estimatedCalories: Int = 0
)

// Fitness / Workouts
enum class WorkoutCategory(val title: String) {
    BEGINNER("Beginner"),
    MOBILITY("Mobility"),
    STRETCHING("Stretching"),
    YOGA("Yoga"),
    STRENGTH("Strength"),
    CARDIO("Cardio"),
    RELAXATION("Relaxation")
}

data class WorkoutItem(
    val id: String,
    val title: String,
    val category: WorkoutCategory,
    val durationMinutes: Int,
    val difficulty: String,
    val equipment: String,
    val description: String,
    val exercises: List<String>,
    val restIntervalSeconds: Int = 30
)

// Nutrition
data class MealIdea(
    val id: String,
    val title: String,
    val category: String, // Breakfast, Lunch, Dinner, Snack
    val prepTimeMinutes: Int,
    val keyNutrients: String,
    val ingredients: List<String>,
    val benefits: String
)

data class NutrientGuide(
    val name: String,
    val importance: String,
    val foodSources: List<String>,
    val dailyContext: String
)

// Education Articles & Hygiene
enum class ArticleCategory(val title: String) {
    ALL("All"),
    PERIODS("Periods"),
    PUBERTY("Puberty"),
    NUTRITION("Nutrition"),
    FITNESS("Fitness"),
    REPRODUCTIVE("Reproductive"),
    PREGNANCY("Pregnancy"),
    MENOPAUSE("Menopause"),
    SELF_CARE("Self-Care")
}

data class ArticleItem(
    val id: String,
    val title: String,
    val category: ArticleCategory,
    val readingTime: String,
    val date: String,
    val summary: String,
    val content: List<String>,
    val keyPoints: List<String>,
    val questionsForDoctor: List<String>
)

data class HygieneProduct(
    val name: String,
    val icon: String,
    val description: String,
    val howToUse: String,
    val changeFrequency: String,
    val safeDisposal: String
)

data class GlossaryTerm(
    val term: String,
    val simpleExplanation: String,
    val category: String
)

data class FaqItem(
    val question: String,
    val answer: String,
    val category: String
)

// Quizzes & Challenges
data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class Quiz(
    val id: String,
    val title: String,
    val subtitle: String,
    val questions: List<QuizQuestion>
)

data class WellnessChallenge(
    val id: String,
    val title: String,
    val durationDays: Int,
    val completedDays: Int,
    val description: String,
    val dailyAction: String
)

data class HabitItem(
    val id: String,
    val title: String,
    val icon: String,
    val isCompletedToday: Boolean,
    val currentStreak: Int
)

// Appointments & Doctor Directory
enum class DoctorSpecialty(val title: String) {
    GYNECOLOGIST("Gynecologist & Obstetrician"),
    NUTRITIONIST("Nutrition Professional"),
    MENTAL_HEALTH("Mental Health Counselor"),
    GENERAL_PHYSICIAN("General Healthcare Physician")
}

data class DoctorProfile(
    val id: String,
    val name: String,
    val specialty: DoctorSpecialty,
    val credentials: String,
    val location: String,
    val availability: String,
    val languages: List<String>,
    val about: String
)

data class AppointmentItem(
    val id: String,
    val doctorName: String,
    val specialty: String,
    val date: String,
    val time: String,
    val location: String,
    val reason: String,
    val notes: String = "",
    val questions: List<String> = emptyList()
)

// Medications & Documents
data class MedicationReminder(
    val id: String,
    val name: String,
    val dosage: String,
    val time: String,
    val frequency: String,
    val notes: String,
    val isTakenToday: Boolean = false
)

data class HealthDocument(
    val id: String,
    val title: String,
    val category: String, // Lab report, Prescription, Appointment document, Medical note
    val date: String,
    val isLocked: Boolean = true,
    val previewSummary: String
)

data class HealthTimelineEvent(
    val id: String,
    val date: String,
    val title: String,
    val category: String,
    val icon: String,
    val description: String
)

// Privacy & Settings
enum class AppLockType {
    NONE,
    PIN,
    BIOMETRIC
}

enum class NotificationPrivacy(val label: String, val example: String) {
    DETAILED("Detailed", "“Your period tracking reminder is ready.”"),
    NEUTRAL("Neutral", "“You have a reminder.”"),
    HIDDEN("Hidden", "“You have a notification.”")
}

enum class LanguageCode(val displayName: String, val nativeName: String) {
    EN("English", "English"),
    NE("Nepali", "नेपाली"),
    HI("Hindi", "हिन्दी"),
    ES("Spanish", "Español"),
    FR("French", "Français"),
    AR("Arabic", "العربية")
}
