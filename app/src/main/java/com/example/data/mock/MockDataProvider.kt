package com.example.data.mock

import com.example.data.model.*

object MockDataProvider {

    // Symptoms catalog
    val symptomsList = listOf(
        SymptomDefinition(
            id = "cramps",
            name = "Cramps",
            icon = "⚡",
            category = SymptomCategory.PHYSICAL,
            description = "Lower abdominal cramping caused by natural uterine muscle contractions.",
            selfCareTip = "A warm heating pad, gentle lower back stretches, and sipping chamomile or ginger tea often provide comfort.",
            whenToSeekHelp = "If cramps are severe enough to disrupt daily life, do not improve with common remedies, or are accompanied by fever, consult a healthcare provider."
        ),
        SymptomDefinition(
            id = "headache",
            name = "Headache",
            icon = "🤕",
            category = SymptomCategory.PHYSICAL,
            description = "Mild to moderate tension or hormonal headaches occurring near cycle shifts.",
            selfCareTip = "Rest in a quiet, dimly lit room, stay well hydrated, and apply a cool compress to the forehead or neck.",
            whenToSeekHelp = "Seek medical care if headaches are sudden and excruciating, accompanied by vision loss, nausea, or stiffness."
        ),
        SymptomDefinition(
            id = "back_discomfort",
            name = "Back Discomfort",
            icon = "🧘‍♀️",
            category = SymptomCategory.PHYSICAL,
            description = "A dull ache or stiffness in the lower back or lumbar region.",
            selfCareTip = "Cat-cow stretches, gentle pelvic tilts, and avoiding prolonged sitting can help alleviate pressure.",
            whenToSeekHelp = "Contact a doctor if the pain radiates down both legs or is accompanied by numbness."
        ),
        SymptomDefinition(
            id = "bloating",
            name = "Bloating",
            icon = "🎈",
            category = SymptomCategory.PHYSICAL,
            description = "A feeling of fullness or abdominal swelling often linked to progesterone fluctuations.",
            selfCareTip = "Eat smaller, more frequent meals, reduce excess sodium intake, and enjoy peppermint tea or gentle walking.",
            whenToSeekHelp = "Consult a physician if bloating is accompanied by persistent vomiting, severe pain, or unexplained weight changes."
        ),
        SymptomDefinition(
            id = "fatigue",
            name = "Fatigue",
            icon = "🛋️",
            category = SymptomCategory.PHYSICAL,
            description = "A general feeling of low stamina or sleepiness as hormonal levels shift.",
            selfCareTip = "Prioritize an earlier bedtime, take short 15-minute restorative rests, and nourish your body with iron-rich foods.",
            whenToSeekHelp = "Discuss chronic, unexplained fatigue with a healthcare professional to rule out anemia or thyroid variations."
        ),
        SymptomDefinition(
            id = "breast_tenderness",
            name = "Breast Tenderness",
            icon = "🌸",
            category = SymptomCategory.PHYSICAL,
            description = "Sensitivity or heaviness in breast tissue during the luteal phase.",
            selfCareTip = "Wear a supportive, non-wired cotton bra and reduce high caffeine consumption during sensitive days.",
            whenToSeekHelp = "Always consult a doctor if you feel a distinct hard lump, skin changes, or notice unexpected nipple discharge."
        ),
        SymptomDefinition(
            id = "skin_changes",
            name = "Skin Changes",
            icon = "✨",
            category = SymptomCategory.PHYSICAL,
            description = "Mild hormonal breakouts or dry patches during specific cycle phases.",
            selfCareTip = "Maintain a gentle, hydrating skincare routine without harsh scrubbing, and drink plenty of water.",
            whenToSeekHelp = "A dermatologist can provide tailored guidance if you experience cystic or painful inflammatory acne."
        ),
        // Digestive
        SymptomDefinition(
            id = "appetite_changes",
            name = "Appetite Changes",
            icon = "🍎",
            category = SymptomCategory.DIGESTIVE,
            description = "Shifts in hunger cues or food cravings, natural during metabolic fluctuations.",
            selfCareTip = "Focus on balanced meals with protein and complex carbs to keep blood sugar stable.",
            whenToSeekHelp = "Consult a healthcare provider if you experience sudden inability to keep food down or persistent loss of appetite."
        ),
        SymptomDefinition(
            id = "nausea",
            name = "Nausea",
            icon = "🍵",
            category = SymptomCategory.DIGESTIVE,
            description = "Mild queasiness or stomach sensitivity.",
            selfCareTip = "Sip warm ginger tea, nibble on whole-grain crackers, and avoid heavy, greasy meals.",
            whenToSeekHelp = "Seek medical assessment if nausea is prolonged, prevents fluid retention, or is accompanied by severe abdominal pain."
        ),
        SymptomDefinition(
            id = "constipation",
            name = "Constipation",
            icon = "💧",
            category = SymptomCategory.DIGESTIVE,
            description = "Slowed digestion often influenced by elevated progesterone in the luteal phase.",
            selfCareTip = "Increase dietary fiber with chia seeds, leafy greens, berries, and drink warm water in the morning.",
            whenToSeekHelp = "Contact a doctor if bowel movements are absent for more than a few days despite high fluid and fiber intake."
        ),
        SymptomDefinition(
            id = "diarrhea",
            name = "Digestive Sensitivity",
            icon = "🌿",
            category = SymptomCategory.DIGESTIVE,
            description = "Looser stools caused by natural prostaglandins during menstrual onset.",
            selfCareTip = "Replenish electrolytes, sip broth, and stick to gentle foods like bananas, oats, and rice.",
            whenToSeekHelp = "Seek medical care if loose stools persist beyond 48 hours or occur with fever or signs of dehydration."
        ),
        // General
        SymptomDefinition(
            id = "low_energy",
            name = "Low Energy",
            icon = "🔋",
            category = SymptomCategory.GENERAL,
            description = "A gentle lull in motivation or physical vigor.",
            selfCareTip = "Honor your body’s need for rest. Replace intense workouts with a quiet walk or stretching.",
            whenToSeekHelp = "Talk with your doctor if low energy persists throughout all phases of your cycle."
        ),
        SymptomDefinition(
            id = "difficulty_sleeping",
            name = "Difficulty Sleeping",
            icon = "🌙",
            category = SymptomCategory.GENERAL,
            description = "Restlessness or trouble settling into deep sleep.",
            selfCareTip = "Dim lights 1 hour before sleep, avoid screens, and try a soothing lavender or chamomile scent.",
            whenToSeekHelp = "Consult a physician if chronic insomnia affects your ability to function safely during the day."
        ),
        SymptomDefinition(
            id = "temperature_shifts",
            name = "Feeling Warm / Cold",
            icon = "🌡️",
            category = SymptomCategory.GENERAL,
            description = "Subtle basal body temperature shifts that naturally occur after ovulation.",
            selfCareTip = "Dress in breathable, layered natural fabrics so you can adjust your comfort easily.",
            whenToSeekHelp = "Check your temperature with a thermometer; seek immediate care if you have an actual fever over 38°C (100.4°F)."
        )
    )

    // Calendar day logs for current month (September)
    val calendarDays = run {
        val list = mutableMapOf<Int, PeriodDayLog>()
        // Days 1 to 5: Period days
        list[1] = PeriodDayLog(dayOfMonth = 1, flow = PeriodFlow.LIGHT, symptoms = listOf("cramps", "fatigue"), mood = MoodType.TIRED)
        list[2] = PeriodDayLog(dayOfMonth = 2, flow = PeriodFlow.HEAVY, symptoms = listOf("cramps", "bloating"), mood = MoodType.LOW)
        list[3] = PeriodDayLog(dayOfMonth = 3, flow = PeriodFlow.MEDIUM, symptoms = listOf("cramps"), mood = MoodType.OKAY)
        list[4] = PeriodDayLog(dayOfMonth = 4, flow = PeriodFlow.LIGHT, symptoms = listOf("fatigue"), mood = MoodType.CALM)
        list[5] = PeriodDayLog(dayOfMonth = 5, flow = PeriodFlow.SPOTTING, symptoms = emptyList(), mood = MoodType.GOOD)
        // Days 6-11: Follicular
        list[8] = PeriodDayLog(dayOfMonth = 8, mood = MoodType.CALM, notes = "Felt energized, went on a gentle walk.")
        list[11] = PeriodDayLog(dayOfMonth = 11, mood = MoodType.ENERGETIC, notes = "Great focus today.")
        // Day 12: Today
        list[12] = PeriodDayLog(dayOfMonth = 12, mood = MoodType.GREAT, symptoms = listOf("skin_changes"), notes = "Feeling vibrant and motivated.")
        // Days 14-16: Estimated fertile / ovulation window
        list[14] = PeriodDayLog(dayOfMonth = 14, isEstimatedFuture = true)
        list[15] = PeriodDayLog(dayOfMonth = 15, isEstimatedFuture = true)
        list[16] = PeriodDayLog(dayOfMonth = 16, isEstimatedFuture = true)
        list
    }

    // Previous cycle history
    val cycleHistoryList = listOf(
        CycleHistoryItem(
            cycleNumber = 1,
            startDate = "Aug 15, 2026",
            cycleLengthDays = 28,
            periodDurationDays = 5,
            commonSymptoms = listOf("Mild Cramps", "Bloating"),
            dominantMood = MoodType.CALM
        ),
        CycleHistoryItem(
            cycleNumber = 2,
            startDate = "Jul 18, 2026",
            cycleLengthDays = 29,
            periodDurationDays = 5,
            commonSymptoms = listOf("Headache", "Fatigue"),
            dominantMood = MoodType.GOOD
        ),
        CycleHistoryItem(
            cycleNumber = 3,
            startDate = "Jun 19, 2026",
            cycleLengthDays = 27,
            periodDurationDays = 4,
            commonSymptoms = listOf("Cramps"),
            dominantMood = MoodType.OKAY
        )
    )

    // Workouts
    val workoutsList = listOf(
        WorkoutItem(
            id = "w1",
            title = "10-Minute Gentle Stretch",
            category = WorkoutCategory.STRETCHING,
            durationMinutes = 10,
            difficulty = "Gentle / Beginner",
            equipment = "Yoga mat or comfortable rug",
            description = "Soothing, restorative poses designed to release lower back tension, open tight hips, and calm the nervous system.",
            exercises = listOf("Child's Pose (2 min)", "Cat-Cow Flow (2 min)", "Reclined Butterfly (2 min)", "Gentle Supine Spinal Twist (2 min)", "Legs-Up-The-Wall (2 min)"),
            restIntervalSeconds = 20
        ),
        WorkoutItem(
            id = "w2",
            title = "Morning Mobility Flow",
            category = WorkoutCategory.MOBILITY,
            durationMinutes = 15,
            difficulty = "All Levels",
            equipment = "None",
            description = "Awaken your joints, improve circulation, and cultivate ease in your shoulders, spine, and pelvis for the day ahead.",
            exercises = listOf("Neck Rolls & Shoulder Shrugs (2 min)", "Standing Side Bends (2 min)", "Torso Rotations (3 min)", "Low Lunge Hip Opener (4 min)", "Deep Breath Centering (4 min)"),
            restIntervalSeconds = 15
        ),
        WorkoutItem(
            id = "w3",
            title = "Restorative Pelvic Core Care",
            category = WorkoutCategory.YOGA,
            durationMinutes = 18,
            difficulty = "Moderate & Mindful",
            equipment = "Mat & Cushion",
            description = "Mindful, breath-led activation of deep core and pelvic floor muscles without straining or stressing your abdomen.",
            exercises = listOf("Diaphragmatic Breathing (3 min)", "Pelvic Clock Tilts (4 min)", "Bird-Dog Extensions (4 min)", "Glute Bridge Pulses (4 min)", "Corpse Pose Relaxation (3 min)"),
            restIntervalSeconds = 30
        ),
        WorkoutItem(
            id = "w4",
            title = "Calming Evening Wind-Down",
            category = WorkoutCategory.RELAXATION,
            durationMinutes = 12,
            difficulty = "Restorative",
            equipment = "Bed or Mat",
            description = "Prepare your body and mind for deep, restorative sleep with passive stretches that lower cortisol.",
            exercises = listOf("Seated Forward Fold (3 min)", "Happy Baby Pose (3 min)", "Supported Bridge (3 min)", "Progressive Muscle Release (3 min)"),
            restIntervalSeconds = 20
        )
    )

    // Meals
    val mealIdeas = listOf(
        MealIdea(
            id = "m1",
            title = "Warm Berry & Chia Oatmeal Bowl",
            category = "Breakfast",
            prepTimeMinutes = 10,
            keyNutrients = "Iron, Fiber, Omega-3",
            ingredients = listOf("Rolled oats", "Almond or oat milk", "Ground flaxseed & chia seeds", "Fresh berries", "Warm honey or cinnamon"),
            benefits = "Sustained morning energy, supportive of gut motility and blood sugar balance."
        ),
        MealIdea(
            id = "m2",
            title = "Quinoa, Spinach & Roasted Chickpea Bowl",
            category = "Lunch",
            prepTimeMinutes = 20,
            keyNutrients = "Plant Protein, Iron, Magnesium",
            ingredients = listOf("Cooked fluffy quinoa", "Baby spinach leaves", "Crispy spiced chickpeas", "Tahini lemon dressing", "Avocado slices"),
            benefits = "Replenishes iron stores and supplies steady plant-powered fuel without heavy sluggishness."
        ),
        MealIdea(
            id = "m3",
            title = "Ginger Steamed Salmon & Sweet Potato",
            category = "Dinner",
            prepTimeMinutes = 25,
            keyNutrients = "Vitamin D, B12, Healthy Fats",
            ingredients = listOf("Wild salmon fillet", "Steamed sweet potato cubes", "Broccoli florets", "Grated ginger & garlic drizzle"),
            benefits = "Rich in anti-inflammatory omega-3 fatty acids and vitamin D to soothe tissues and support mood."
        ),
        MealIdea(
            id = "m4",
            title = "Pumpkin Seed & Dark Chocolate Bark",
            category = "Snack",
            prepTimeMinutes = 5,
            keyNutrients = "Zinc, Magnesium, Antioxidants",
            ingredients = listOf("Raw pumpkin seeds (pepitas)", "70%+ dark chocolate square", "A pinch of sea salt"),
            benefits = "Satisfies natural cravings while delivering magnesium to relax muscles."
        )
    )

    // Nutrients
    val nutrientGuides = listOf(
        NutrientGuide(
            name = "Iron",
            importance = "Crucial for producing hemoglobin, which transports oxygen throughout your tissues. Vital for replenishing natural monthly losses.",
            foodSources = listOf("Lentils, beans & chickpeas", "Dark leafy greens (spinach, kale)", "Tofu & tempeh", "Pumpkin seeds", "Lean poultry & fish"),
            dailyContext = "Pair plant-based iron with vitamin C (like lemon juice or bell peppers) to boost absorption by up to 3x."
        ),
        NutrientGuide(
            name = "Calcium",
            importance = "Supports bone density, smooth muscle contractions, and nerve signaling throughout all life stages.",
            foodSources = listOf("Fortified plant milks", "Plain yogurt or kefir", "Sesame seeds & tahini", "Tofu set with calcium", "Bok choy & broccoli"),
            dailyContext = "Calcium and Vitamin D work as partners—Vitamin D helps your gut absorb calcium efficiently."
        ),
        NutrientGuide(
            name = "Vitamin D",
            importance = "A cornerstone hormone-like vitamin that regulates immunity, mood synthesis, calcium uptake, and reproductive balance.",
            foodSources = listOf("Gentle sunshine exposure", "Fatty fish (salmon, sardines)", "Egg yolks", "Fortified cereals and milks"),
            dailyContext = "Since food sources are limited, many healthcare providers recommend a simple seasonal blood test."
        ),
        NutrientGuide(
            name = "Vitamin B12",
            importance = "Vital for red blood cell formation, brain function, and mental energy.",
            foodSources = listOf("Eggs & dairy products", "Nutritional yeast", "Fortified plant foods", "Fish and poultry"),
            dailyContext = "Vegetarians and vegans benefit from a reliable fortified source or gentle daily B12 supplement."
        ),
        NutrientGuide(
            name = "Folate (Vitamin B9)",
            importance = "Essential for cellular division, tissue growth, healthy blood production, and critical in pregnancy.",
            foodSources = listOf("Edamame & lentils", "Avocados", "Asparagus & Brussels sprouts", "Oranges & citrus", "Sunflower seeds"),
            dailyContext = "Folate is the naturally occurring food form, while folic acid is the shelf-stable form used in fortification."
        ),
        NutrientGuide(
            name = "Protein",
            importance = "The building block for neurotransmitters, hormone receptors, muscle repair, and immune cells.",
            foodSources = listOf("Eggs", "Greek yogurt", "Lentils & beans", "Hemp hearts", "Fish & poultry"),
            dailyContext = "Including a palm-sized portion of protein with each meal helps stabilize energy and reduce sugar crashes."
        ),
        NutrientGuide(
            name = "Dietary Fiber",
            importance = "Aids digestive regularity, feeds beneficial microbiome flora, and assists your liver in processing circulating estrogen.",
            foodSources = listOf("Chia and ground flax seeds", "Berries & apples", "Whole oats", "Beans & legumes", "Artichokes"),
            dailyContext = "Increase fiber gradually while drinking plenty of water to give your digestive tract time to adapt."
        )
    )

    // Period Hygiene products
    val hygieneProducts = listOf(
        HygieneProduct(
            name = "Pads",
            icon = "🩲",
            description = "External absorbent liners worn inside underwear. Available in disposable and washable reusable cloth varieties.",
            howToUse = "Peel adhesive strip, place firmly against underwear crotch, wrap wings around sides.",
            changeFrequency = "Change every 3 to 6 hours, or sooner if flow is heavy.",
            safeDisposal = "Wrap securely in toilet paper or original wrapper and place in a rubbish bin. Never flush down the toilet."
        ),
        HygieneProduct(
            name = "Tampons",
            icon = "🧴",
            description = "Soft compressed cotton or rayon cylinder inserted internally into the vaginal canal to absorb fluid.",
            howToUse = "Relax pelvic muscles, gently insert using applicator or finger until positioned comfortably. String remains outside.",
            changeFrequency = "Change every 4 to 8 hours. Never leave a single tampon in place longer than 8 hours.",
            safeDisposal = "Dispose in sanitary bin. Do not flush. Choose the lowest absorbency suitable for your flow to reduce TSS risk."
        ),
        HygieneProduct(
            name = "Menstrual Cups",
            icon = "🍷",
            description = "Flexible medical-grade silicone bell-shaped cup that collects fluid internally rather than absorbing it.",
            howToUse = "Fold in half (C-fold or punch-down), insert into vaginal canal, allow it to open and create a gentle seal.",
            changeFrequency = "Empty, wash with clean water and mild soap, and reinsert every 8 to 12 hours.",
            safeDisposal = "Reusable for multiple years. Sterilize in boiling water between cycles."
        ),
        HygieneProduct(
            name = "Period Underwear",
            icon = "🩱",
            description = "Washable fabric briefs with multi-layered moisture-wicking, leak-resistant absorbent gussets.",
            howToUse = "Wear directly like regular underwear; can be used alone or as backup protection.",
            changeFrequency = "Change every 8 to 12 hours depending on absorbency rating and flow intensity.",
            safeDisposal = "Rinse in cold water until water runs clear, then machine wash on gentle cycle. Hang dry."
        )
    )

    // Educational Articles
    val articles = listOf(
        ArticleItem(
            id = "a1",
            title = "Demystifying Your Menstrual Cycle: The Four Phases",
            category = ArticleCategory.PERIODS,
            readingTime = "4 min read",
            date = "Updated Sep 2026",
            summary = "A friendly, educational walk-through of the menstrual, follicular, ovulation, and luteal phases.",
            content = listOf(
                "A menstrual cycle is much more than just the days you bleed. From the first day of your period to the day before your next, your body experiences an intricate, rhythmic conversation between your brain and ovaries.",
                "Phase 1: Menstruation (Days 1–5). Estrogen and progesterone are at their lowest baseline. The uterine lining gently sheds. Rest, hydration, and gentle movement are especially nourishing now.",
                "Phase 2: Follicular Phase (Days 6–13). Follicle-stimulating hormone (FSH) prompts follicles in the ovary to mature. Estrogen begins its climb, which often brings a gradual lift in physical energy and mental focus.",
                "Phase 3: Ovulation (Days 14–16). A surge in luteinizing hormone triggers the release of an egg. Cervical fluid becomes clear and stretchy (like egg whites). You may notice heightened libido and vitality.",
                "Phase 4: Luteal Phase (Days 17–28). Progesterone rises to support the uterine lining. Metabolic rate increases slightly. As progesterone dips toward the end of this phase, pre-menstrual symptoms like tenderness or mood sensitivity can arise."
            ),
            keyPoints = listOf(
                "Day 1 is always the first day of full menstrual bleeding.",
                "Cycle lengths normally range between 21 and 35 days in healthy adults.",
                "Hormonal fluctuations naturally influence mood, energy, and appetite throughout the month."
            ),
            questionsForDoctor = listOf(
                "Is my cycle length within a healthy expected range for my age?",
                "Could my premenstrual symptoms benefit from specific lifestyle adjustments?",
                "What changes should I watch out for over time?"
            )
        ),
        ArticleItem(
            id = "a2",
            title = "Gentle Movement Across Your Cycle: Working with Your Body",
            category = ArticleCategory.FITNESS,
            readingTime = "5 min read",
            date = "Updated Sep 2026",
            summary = "How aligning movement with your natural rhythm can help you feel refreshed instead of depleted.",
            content = listOf(
                "Fitness doesn't have to mean intense exhaustion every single day. Women’s bodies naturally experience varying levels of stamina, joint laxity, and recovery capacity across the month.",
                "During menstruation, gentler forms of movement like Yin yoga, mobility stretches, or unhurried walks help increase circulation without adding stress.",
                "As estrogen climbs in the follicular phase, strength training and cardio often feel more effortless. Recovery is faster, and muscle repair is supported.",
                "In the late luteal phase, core temperature is slightly higher and progesterone encourages rest. Opt for resistance training with longer rest intervals or restorative walks."
            ),
            keyPoints = listOf(
                "Consistency is about showing up appropriately, not pushing to extremes every day.",
                "Rest days are an active part of strength and wellness.",
                "Listen to your body's cues rather than rigid workout regimens."
            ),
            questionsForDoctor = listOf(
                "Are there any movement modifications recommended for my personal health history?",
                "How can I maintain healthy bone density through weight-bearing exercises?"
            )
        ),
        ArticleItem(
            id = "a3",
            title = "Puberty & Body Changes: What Everyone Should Know",
            category = ArticleCategory.PUBERTY,
            readingTime = "6 min read",
            date = "Updated Sep 2026",
            summary = "An empathetic, age-appropriate guide for adolescents and young adults discovering their changing bodies.",
            content = listOf(
                "Puberty is your body’s natural transformation into adulthood. It is completely normal for everyone to start at different ages—some start as early as 9, while others begin around 14.",
                "Common early signs include breast budding, growth spurts, skin changes, increased perspiration, and the appearance of fine body hair.",
                "Vaginal discharge is a completely healthy sign that your reproductive tract is cleaning and lubricating itself. Healthy discharge is typically clear to milky white without an unpleasant odor.",
                "Menarche (your very first period) usually happens around 2 years after breasts begin developing. Initial periods are often irregular for the first year or two as hormone pathways mature."
            ),
            keyPoints = listOf(
                "Everyone's timeline is unique—there is no 'correct' speed.",
                "Normal vaginal discharge is healthy and self-cleaning.",
                "Talking with a trusted parent, school nurse, or doctor can make transitions feel safe and clear."
            ),
            questionsForDoctor = listOf(
                "Are my developmental milestones progressing normally?",
                "What hygiene products are easiest to start with?"
            )
        ),
        ArticleItem(
            id = "a4",
            title = "Pregnancy Journey: Understanding Trimesters & Wellbeing",
            category = ArticleCategory.PREGNANCY,
            readingTime = "6 min read",
            date = "Updated Sep 2026",
            summary = "Key educational insights into fetal growth, maternal comfort, and preparing for prenatal checkups.",
            content = listOf(
                "Pregnancy is typically divided into three distinct trimesters spanning approximately 40 weeks from the last menstrual period.",
                "First Trimester (Weeks 1–12): Major organs and neural pathways begin forming. Fatigue and morning sickness are common as progesterone surges.",
                "Second Trimester (Weeks 13–27): Often called the 'golden phase' as energy returns and nausea subsides. At around 20–24 weeks, fetal kicks become palpable.",
                "Third Trimester (Weeks 28–40): Rapid fetal weight gain and maternal body preparation. Rest, hydration, and pelvic floor care take center stage."
            ),
            keyPoints = listOf(
                "Prenatal appointments are essential for monitoring both parent and baby.",
                "Always consult your midwife or obstetrician before taking any medications or supplements.",
                "Gentle walking and prenatal yoga can relieve lumbar strain."
            ),
            questionsForDoctor = listOf(
                "What prenatal vitamins best support my dietary preferences?",
                "What symptoms warrant an immediate call to the maternity triage unit?",
                "What birth classes or newborn preparation resources do you recommend?"
            )
        ),
        ArticleItem(
            id = "a5",
            title = "Sleep & Hormones: Cultivating Restful Nights",
            category = ArticleCategory.SELF_CARE,
            readingTime = "4 min read",
            date = "Updated Sep 2026",
            summary = "How nighttime habits, bedroom temperature, and calming rituals support restorative sleep.",
            content = listOf(
                "Sleep is your brain’s dedicated time for hormonal calibration, cellular repair, and emotional processing.",
                "Elevated progesterone in the luteal phase can slightly raise body temperature, which sometimes disrupts sleep continuity. Keeping the bedroom cool (around 18°C or 65°F) can make a world of difference.",
                "Creating a predictable 20-minute wind-down ritual signals to your nervous system that it is safe to transition from alert wakefulness to calm sleep."
            ),
            keyPoints = listOf(
                "A cooler bedroom temperature supports deeper slow-wave sleep.",
                "Limit bright blue light from phones at least 45 minutes before sleep.",
                "Gentle evening stretching can release muscle tension before bed."
            ),
            questionsForDoctor = listOf(
                "Could my sleep disturbances be connected to hormonal fluctuations?",
                "When is an in-depth sleep study warranted?"
            )
        )
    )

    // Doctors directory
    val doctorsList = listOf(
        DoctorProfile(
            id = "d1",
            name = "Dr. Maya Sharma, MD",
            specialty = DoctorSpecialty.GYNECOLOGIST,
            credentials = "Board Certified OB/GYN • 14 Years Experience",
            location = "City Women’s Health Pavilion, Suite 402",
            availability = "Mon, Wed, Fri (9:00 AM – 4:00 PM)",
            languages = listOf("English", "Nepali", "Hindi"),
            about = "Dedicated to compassionate, empowering adolescent and adult gynecological care, reproductive planning, and menstrual disorders with a patient-centered approach."
        ),
        DoctorProfile(
            id = "d2",
            name = "Dr. Elena Vance, MS, RD",
            specialty = DoctorSpecialty.NUTRITIONIST,
            credentials = "Registered Dietitian & Women’s Health Specialist",
            location = "Integrative Wellness Clinic, Suite 210",
            availability = "Tue, Thu, Sat (10:00 AM – 3:00 PM)",
            languages = listOf("English", "Spanish"),
            about = "Specializes in non-restrictive, wholesome nutritional therapy supporting hormonal balance, iron deficiency recovery, and sustainable metabolic wellness."
        ),
        DoctorProfile(
            id = "d3",
            name = "Priya Patel, LMFT",
            specialty = DoctorSpecialty.MENTAL_HEALTH,
            credentials = "Licensed Clinical Counselor • Perinatal Mental Health",
            location = "Mindful Horizons Center, 3rd Floor",
            availability = "Mon – Thu (1:00 PM – 7:00 PM)",
            languages = listOf("English", "Hindi"),
            about = "Provides safe, empathetic psychotherapy for life transitions, reproductive anxieties, mood fluctuations, and mindful stress reduction."
        ),
        DoctorProfile(
            id = "d4",
            name = "Dr. Sarah Jenkins, MD",
            specialty = DoctorSpecialty.GENERAL_PHYSICIAN,
            credentials = "Family Medicine & Preventive Healthcare Physician",
            location = "Oakview Community Medical Center",
            availability = "Mon – Fri (8:30 AM – 4:30 PM)",
            languages = listOf("English", "French"),
            about = "Passionate about comprehensive annual health checks, preventive screenings, thyroid wellness, and personalized health roadmaps."
        )
    )

    // Upcoming appointments
    val initialAppointments = listOf(
        AppointmentItem(
            id = "apt1",
            doctorName = "Dr. Maya Sharma, MD",
            specialty = "Gynecologist & Obstetrician",
            date = "Oct 14, 2026",
            time = "10:30 AM",
            location = "City Women’s Health Pavilion, Suite 402",
            reason = "Annual Wellness & Cycle Checkup",
            notes = "Discuss recent cramp tracking and iron levels.",
            questions = listOf("Is my cycle length expected for my age?", "Should I re-check serum ferritin?", "Recommendations for lower back discomfort during luteal phase?")
        )
    )

    // Medication reminders
    val initialMedications = listOf(
        MedicationReminder(
            id = "med1",
            name = "Iron & Vitamin C",
            dosage = "65 mg Elemental Iron",
            time = "08:30 AM",
            frequency = "Daily with breakfast",
            notes = "Take with orange juice for enhanced absorption; avoid coffee within 1 hour.",
            isTakenToday = true
        ),
        MedicationReminder(
            id = "med2",
            name = "Vitamin D3",
            dosage = "1,000 IU",
            time = "01:00 PM",
            frequency = "Daily with lunch",
            notes = "Fat-soluble vitamin; best absorbed with meals containing healthy fats.",
            isTakenToday = false
        )
    )

    // Health documents
    val initialDocuments = listOf(
        HealthDocument(
            id = "doc1",
            title = "Complete Blood Count (CBC) & Ferritin",
            category = "Lab Report",
            date = "Sep 12, 2026",
            isLocked = false,
            previewSummary = "Hemoglobin 13.2 g/dL (Normal) • Ferritin 42 ng/mL (Normal healthy range) • Platelets normal."
        ),
        HealthDocument(
            id = "doc2",
            title = "Annual Gynecological Visit Summary",
            category = "Appointment Document",
            date = "Aug 20, 2026",
            isLocked = true,
            previewSummary = "Routine pelvic exam and cervical screening performed. Vital signs optimal. Scheduled follow-up in 12 months."
        ),
        HealthDocument(
            id = "doc3",
            title = "Prescription - Iron Supplementation",
            category = "Prescription",
            date = "Jul 10, 2026",
            isLocked = true,
            previewSummary = "Ferrous bisglycinate 65mg. Take once daily. 90-day supply with 2 refills remaining."
        )
    )

    // Health Timeline
    val initialTimeline = listOf(
        HealthTimelineEvent(
            id = "t1",
            date = "Sep 27, 2026",
            title = "Doctor Consultation Prepared",
            category = "Appointment",
            icon = "🩺",
            description = "Prepared 3 questions for upcoming annual wellness visit."
        ),
        HealthTimelineEvent(
            id = "t2",
            date = "Sep 20, 2026",
            title = "Personal Health Journal Note",
            category = "Health Note",
            icon = "📝",
            description = "Recorded gentle improvement in sleep and steady daytime energy."
        ),
        HealthTimelineEvent(
            id = "t3",
            date = "Sep 12, 2026",
            title = "Laboratory Results Received",
            category = "Lab Document",
            icon = "📄",
            description = "CBC and Iron panel verified within normal baseline parameters."
        ),
        HealthTimelineEvent(
            id = "t4",
            date = "Sep 01, 2026",
            title = "Menstrual Period Logged",
            category = "Period Log",
            icon = "🌸",
            description = "5-day cycle started on time; flow logged as medium to light."
        )
    )

    // Gentle Habits
    val initialHabits = listOf(
        HabitItem("h1", "Drink 8 glasses of water", "💧", true, 4),
        HabitItem("h2", "Sleep 7.5+ hours on time", "🌙", true, 3),
        HabitItem("h3", "10-minute gentle stretch", "🧘‍♀️", true, 5),
        HabitItem("h4", "Daily mindful walk outdoors", "🚶‍♀️", false, 2),
        HabitItem("h5", "Write in health journal", "📓", true, 4),
        HabitItem("h6", "2-minute deep breathing", "🫁", false, 1),
        HabitItem("h7", "Enjoy balanced wholesome meal", "🥗", true, 6)
    )

    // Challenges
    val wellnessChallenges = listOf(
        WellnessChallenge(
            id = "c1",
            title = "7-Day Hydration Habit",
            durationDays = 7,
            completedDays = 5,
            description = "Nourish your cells and support smooth digestion with regular water breaks.",
            dailyAction = "Keep a filled water bottle nearby and enjoy 8 mindful glasses daily."
        ),
        WellnessChallenge(
            id = "c2",
            title = "5-Day Sleep Routine",
            durationDays = 5,
            completedDays = 3,
            description = "Wind down calmly 30 minutes before bed with dim lighting and relaxing stretches.",
            dailyAction = "Disconnect from digital screens by 10:15 PM and practice 5 deep belly breaths."
        ),
        WellnessChallenge(
            id = "c3",
            title = "Gentle Movement Week",
            durationDays = 7,
            completedDays = 4,
            description = "Honor your daily energy without strain—a 15-minute walk, stretch, or yoga flow.",
            dailyAction = "Engage in any joyful, unhurried movement of your choice."
        )
    )

    // Glossary
    val glossaryTerms = listOf(
        GlossaryTerm("Menstruation", "The natural monthly shedding of the uterine lining through the cervix and vagina.", "Periods"),
        GlossaryTerm("Ovulation", "The release of a mature egg from an ovary into the fallopian tube, typically occurring midway through a cycle.", "Fertility"),
        GlossaryTerm("Follicular Phase", "The phase of the menstrual cycle between the first day of your period and ovulation, marked by rising estrogen.", "Cycle"),
        GlossaryTerm("Luteal Phase", "The second half of the menstrual cycle following ovulation, characterized by increased progesterone production.", "Cycle"),
        GlossaryTerm("Estrogen", "A primary reproductive hormone that builds the uterine lining, influences bone density, mood, and skin elasticity.", "Hormones"),
        GlossaryTerm("Progesterone", "A calming hormone produced after ovulation to maintain the uterine lining and prepare the body for possible pregnancy.", "Hormones"),
        GlossaryTerm("Basal Body Temperature", "Your body's resting temperature immediately upon waking, which subtly increases by about 0.2–0.5°C after ovulation.", "Tracking"),
        GlossaryTerm("Cervical Mucus", "Fluid secreted by the cervix that naturally changes in texture and volume across your cycle to reflect fertility windows.", "Fertility")
    )

    // FAQs
    val faqs = listOf(
        FaqItem("How do I know what cycle day I am on?", "Day 1 is always the first calendar day of your full menstrual bleed. Count each day consecutively until your next period begins.", "Periods"),
        FaqItem("Is it normal for cycle lengths to vary?", "Yes. Variations of 2 to 4 days from one cycle to the next are very common and often influenced by stress, travel, sleep, and natural shifts.", "Periods"),
        FaqItem("What is the difference between spotting and a light period?", "Spotting refers to very light pink or brown drops that barely require a pad or pantyliner. A period has a continuous flow.", "Periods"),
        FaqItem("How often should I change menstrual hygiene products?", "Pads and tampons should generally be changed every 4–6 hours (tampons never longer than 8 hours). Menstrual cups and period underwear can often be worn for 8–12 hours.", "Hygiene"),
        FaqItem("What if I experience sudden severe pain?", "Severe, incapacitating pain is not something you have to endure. Always reach out to a healthcare professional or urgent clinic for an evaluation.", "Health")
    )

    // Educational Quizzes
    val quizzes = listOf(
        Quiz(
            id = "q1",
            title = "Menstrual Health Basics",
            subtitle = "Learn the foundations of your cycle and hormones",
            questions = listOf(
                QuizQuestion(
                    question = "Which day is officially considered 'Cycle Day 1'?",
                    options = listOf("The day after your period stops", "The first day of full menstrual bleeding", "The day you feel ovulation cramps", "The 14th day of the month"),
                    correctIndex = 1,
                    explanation = "Day 1 is universally counted as the very first day of your menstrual bleed."
                ),
                QuizQuestion(
                    question = "Which hormone rises after ovulation to prepare and support the uterine lining?",
                    options = listOf("Progesterone", "Adrenaline", "Melatonin", "Insulin"),
                    correctIndex = 0,
                    explanation = "Progesterone is released by the corpus luteum after ovulation, stabilizing the uterine lining."
                ),
                QuizQuestion(
                    question = "What is a healthy average cycle length range for adults?",
                    options = listOf("Exactly 28 days only", "21 to 35 days", "10 to 15 days", "40 to 60 days"),
                    correctIndex = 1,
                    explanation = "A normal healthy cycle can comfortably range anywhere from 21 to 35 days."
                )
            )
        ),
        Quiz(
            id = "q2",
            title = "Everyday Wellness & Sleep",
            subtitle = "Test your knowledge on hydration, rest, and vitality",
            questions = listOf(
                QuizQuestion(
                    question = "What helps plant-based iron absorb more effectively?",
                    options = listOf("Drinking black coffee", "Pairing it with Vitamin C", "Taking it with high calcium milk", "Skipping water"),
                    correctIndex = 1,
                    explanation = "Vitamin C (such as citrus or bell peppers) significantly boosts non-heme plant iron absorption."
                ),
                QuizQuestion(
                    question = "Why might you feel slightly warmer during the luteal phase?",
                    options = listOf("Progesterone naturally elevates resting body temperature", "Dehydration", "High sugar consumption", "Lack of sleep"),
                    correctIndex = 0,
                    explanation = "Progesterone subtly raises basal body temperature by approximately 0.2°C to 0.5°C after ovulation."
                )
            )
        )
    )
}
