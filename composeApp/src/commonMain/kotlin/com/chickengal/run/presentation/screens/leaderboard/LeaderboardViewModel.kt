package com.chickengal.run.presentation.screens.leaderboard

import androidx.lifecycle.ViewModel
import com.chickengal.run.domain.leaderboard.LeaderboardEntry

class LeaderboardViewModel : ViewModel() {

    private val allNames = listOf(
        "Alexander", "Emma", "Michael", "Sophia", "William", "Olivia", "James", "Isabella", "Benjamin", "Charlotte",
        "Jacob", "Amelia", "Ethan", "Mia", "Noah", "Harper", "Lucas", "Evelyn", "Mason", "Abigail",
        "Logan", "Emily", "Jackson", "Elizabeth", "David", "Sofia", "Oliver", "Avery", "Jayden", "Ella",
        "Александр", "Анна", "Дмитрий", "Мария", "Максим", "Елена", "Андрей", "Ольга", "Сергей", "Татьяна",
        "Алексей", "Наталья", "Владимир", "Ирина", "Николай", "Светлана", "Игорь", "Людмила", "Артем", "Екатерина",
        "Евгений", "Галина", "Михаил", "Валентина", "Денис", "Нина", "Антон", "Лариса", "Кирилл", "Любовь",
        "Hiroshi", "Yuki", "Takeshi", "Sakura", "Kenji", "Aika", "Ryuu", "Hana", "Akira", "Rei",
        "Satoshi", "Yui", "Kaito", "Rin", "Daisuke", "Mika", "Shin", "Kana", "Ryo", "Saki",
        "MinJun", "SoYoung", "JiHoon", "HyeJin", "SeoJun", "JiYeon", "YuJin", "MinJi", "HyunWoo", "ChaeYoung",
        "DongHyun", "SuBin", "JunSeo", "YeJin", "TaeHyun", "HaYoon", "GeonWoo", "SooMin", "WooJin", "JiWon",
        "Maximilian", "Hannah", "Alexander", "Lea", "Paul", "Anna", "Leon", "Lena", "Lukas", "Marie",
        "Felix", "Sophie", "David", "Laura", "Jonas", "Lisa", "Tim", "Sarah", "Noah", "Julia",
        "Lucas", "Emma", "Hugo", "Jade", "Louis", "Louise", "Adam", "Alice", "Arthur", "Chloe",
        "Mathis", "Lina", "Nathan", "Rose", "Tom", "Anna", "Noah", "Mila", "Theo", "Lea",
        "Hugo", "Lucia", "Daniel", "Paula", "Pablo", "Maria", "Alejandro", "Daniela", "Alvaro", "Carla",
        "Adrian", "Sara", "David", "Carmen", "Diego", "Elena", "Mario", "Irene", "Carlos", "Valeria",
        "Leonardo", "Sofia", "Francesco", "Giulia", "Alessandro", "Aurora", "Lorenzo", "Alice", "Mattia", "Ginevra",
        "Andrea", "Emma", "Gabriele", "Giorgia", "Tommaso", "Beatrice", "Riccardo", "Anna", "Edoardo", "Noemi",
        "Wei", "Li", "Zhang", "Wang", "Liu", "Chen", "Yang", "Huang", "Zhao", "Wu",
        "Zhou", "Xu", "Sun", "Ma", "Zhu", "Hu", "Guo", "He", "Lin", "Luo",
        "Ahmed", "Fatima", "Mohammed", "Aisha", "Omar", "Khadija", "Ali", "Zeinab", "Hassan", "Maryam",
        "Youssef", "Nour", "Amr", "Sara", "Khaled", "Dina", "Mostafa", "Rana", "Tamer", "Hala",
        "Miguel", "Alice", "Arthur", "Sophia", "Bernardo", "Helena", "Heitor", "Valentina", "Davi", "Laura",
        "Lorenzo", "Isabella", "Theo", "Manuela", "Pedro", "Julia", "Gabriel", "Heloisa", "Enzo", "Luiza",
        "Arjun", "Ananya", "Aarav", "Aadhya", "Vivaan", "Saanvi", "Aditya", "Kavya", "Vihaan", "Diya",
        "Aryan", "Pihu", "Sai", "Anvi", "Krishna", "Ira", "Ishaan", "Myra", "Shaurya", "Anika",
        "Erik", "Emma", "Oscar", "Maja", "William", "Astrid", "Lucas", "Ella", "Alexander", "Alva",
        "Viktor", "Saga", "Elias", "Agnes", "Hugo", "Freja", "Axel", "Alice", "Anton", "Wilma",
        "Yusuf", "Zeynep", "Emir", "Elif", "Ömer", "Defne", "Aslan", "Miray", "Kerem", "Azra",
        "Metehan", "Ecrin", "Ali", "Nehir", "Eymen", "Asel", "Çınar", "İpek", "Berat", "Lina"
    )

    val leaderboard: List<LeaderboardEntry> by lazy {
        allNames.shuffled().take(100).mapIndexed { index, name ->
            LeaderboardEntry(
                rank = index + 1,
                name = name,
                score = (2000..5000).random()
            )
        }.sortedByDescending { it.score }
            .mapIndexed { index, entry -> entry.copy(rank = index + 1) }
    }

    val topLeaderboard: List<LeaderboardEntry>
        get() = leaderboard.take(10)
}