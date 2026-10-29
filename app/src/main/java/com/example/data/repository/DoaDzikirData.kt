package com.example.data.repository

data class DoaItem(
    val id: String,
    val category: String, // "Pagi-Petang", "Setelah Salat", "Doa Harian", "Doa Quran"
    val title: String,
    val note: String = "",
    val repeat: Int = 1,
    val arabic: String,
    val latin: String,
    val translation: String,
    val source: String = ""
)

object DoaDzikirData {

    val categories = listOf("Semua", "Pagi-Petang", "Setelah Salat", "Doa Harian", "Doa Quran")

    val doaList: List<DoaItem> = listOf(
        // Pagi - Petang
        DoaItem(
            id = "dzikir_pagi_1",
            category = "Pagi-Petang",
            title = "Sayyidul Istighfar (Rajanya Istighfar)",
            note = "Barangsiapa membacanya di pagi hari lalu meninggal sebelum petang, ia termasuk penghuni surga.",
            repeat = 1,
            arabic = "اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ، خَلَقْتَنِي وَأَنَا عَبْدُكَ، وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ، أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ، أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ، وَأَبُوءُ بِذَنْبِي فَاغْفِرْ لِي فَإِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ",
            latin = "Allāhumma anta rabbī lā ilāha illā anta, khalaqtanī wa anā 'abduka, wa anā 'alā 'ahdika wa wa'dika mastaṭa'tu, a'ūżu bika min syarri mā ṣana'tu, abū'u laka bini'matika 'alayya, wa abū'u biżambī fāgfir lī fa innahū lā yagfiruż-żunūba illā anta.",
            translation = "Ya Allah, Engkaulah Tuhanku, tidak ada tuhan selain Engkau. Engkau telah menciptakan aku dan aku adalah hamba-Mu. Aku berada dalam perjanjian dan janji-Mu semampuku. Aku berlindung kepada-Mu dari keburukan apa yang telah kuperbuat. Aku mengakui nikmat-Mu kepadaku dan aku mengakui dosaku, maka ampunilah aku karena sesungguhnya tidak ada yang mengampuni dosa selain Engkau.",
            source = "HR. Bukhari no. 6306"
        ),
        DoaItem(
            id = "dzikir_pagi_2",
            category = "Pagi-Petang",
            title = "Perlindungan Dari Segala Marabahaya",
            note = "Dibaca 3x pagi dan petang. Siapa membacanya, tiada sesuatu pun di bumi dan di langit yang dapat memudaratkannya.",
            repeat = 3,
            arabic = "بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الْأَرْضِ وَلَا فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ",
            latin = "Bismillāhil-lażī lā yaḍurru ma'asmihī syai'un fil-arḍi wa lā fis-samā'i wa huwas-samī'ul-'alīm.",
            translation = "Dengan nama Allah yang bersama nama-Nya tidak ada sesuatu pun di bumi maupun di langit yang dapat mendatangkan mudharat, dan Dia Maha Mendengar lagi Maha Mengetahui.",
            source = "HR. Abu Dawud & Tirmidzi"
        ),
        DoaItem(
            id = "dzikir_pagi_3",
            category = "Pagi-Petang",
            title = "Keridhaan Iman & Islam",
            note = "Dibaca 3x. Allah menjamin meridhai orang yang mengucapkannya.",
            repeat = 3,
            arabic = "رَضِيتُ بِاللَّهِ رَبًّا، وَبِالْإِسْلَامِ دِينًا، وَبِمُحَمَّدٍ صَلَّى اللهُ عَلَيْهِ وَسَلَّمَ نَبِيًّا",
            latin = "Raḍītu billāhi rabban, wa bil-islāmi dīnan, wa bi Muḥammadin ṣallallāhu 'alaihi wa sallama nabiyyā.",
            translation = "Aku rela Allah sebagai Tuhanku, Islam sebagai agamaku, dan Nabi Muhammad SAW sebagai Nabiku.",
            source = "HR. Muslim"
        ),
        DoaItem(
            id = "dzikir_pagi_4",
            category = "Pagi-Petang",
            title = "Tasbih Pembuka Pintu Kebaikan",
            note = "Dibaca 100x setiap pagi. Dosa-dosanya diampuni walau sebanyak buih di lautan.",
            repeat = 100,
            arabic = "سُبْحَانَ اللهِ وَبِحَمْدِهِ",
            latin = "Subḥānallāhi wa biḥamdih.",
            translation = "Maha Suci Allah dan segala puji bagi-Nya.",
            source = "HR. Muslim"
        ),

        // Setelah Salat
        DoaItem(
            id = "sholat_1",
            category = "Setelah Salat",
            title = "Istighfar & Doa Keselamatan",
            note = "Dibaca langsung seusai salam salat fardhu.",
            repeat = 3,
            arabic = "أَسْتَغْفِرُ اللهَ (٣×)\nاللَّهُمَّ أَنْتَ السَّلَامُ وَمِنْكَ السَّلَامُ تَبَارَكْتَ يَا ذَا الْجَلَالِ وَالْإِكْرَامِ",
            latin = "Astaghfirullāh (3x). Allāhumma antas-salām, wa minkas-salām, tabārakta yā żal-jalāli wal-ikrām.",
            translation = "Aku memohon ampun kepada Allah (3x). Ya Allah, Engkaulah Yang Memberi Keselamatan, dan dari-Mulah keselamatan, Maha Berkah Engkau wahai Pemilik Keagungan dan Kemuliaan.",
            source = "HR. Muslim"
        ),
        DoaItem(
            id = "sholat_2",
            category = "Setelah Salat",
            title = "Dzikir Tasbih, Tahmid, & Takbir (33x)",
            note = "Subhanallah (33x), Alhamdulillah (33x), Allahu Akbar (33x), lalu disempurnakan tahlil.",
            repeat = 33,
            arabic = "سُبْحَانَ اللهِ (٣٣×) الْحَمْدُ لِلَّهِ (٣٣×) اللهُ أَكْبَرُ (٣٣×)\nلَا إِلَهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ",
            latin = "Subḥānallāh (33x), Al-ḥamdulillāh (33x), Allāhu Akbar (33x). Lā ilāha illallāhu waḥdahū lā syarīka lah, lahul-mulku wa lahul-ḥamdu wa huwa 'alā kulli syai'in qadīr.",
            translation = "Maha Suci Allah (33x), Segala puji bagi Allah (33x), Allah Maha Besar (33x). Tiada tuhan selain Allah Yang Maha Esa, tiada sekutu bagi-Nya. Bagi-Nya kerajaan dan bagi-Nya segala puji, dan Dia Mahakuasa atas segala sesuatu.",
            source = "HR. Muslim no. 597"
        ),

        // Doa Harian
        DoaItem(
            id = "harian_1",
            category = "Doa Harian",
            title = "Doa Bangun Tidur",
            note = "Dibaca saat terbangun dari tidur.",
            repeat = 1,
            arabic = "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ",
            latin = "Al-ḥamdu lillāhil-lażī aḥyānā ba'da mā amātanā wa ilaihin-nusyūr.",
            translation = "Segala puji bagi Allah yang telah menghidupkan kami setelah mematikan kami, dan hanya kepada-Nya tempat kembali.",
            source = "HR. Bukhari"
        ),
        DoaItem(
            id = "harian_2",
            category = "Doa Harian",
            title = "Doa Untuk Kedua Orang Tua",
            note = "Berbakti kepada ayah dan ibu melalui doa tulus setiap waktu.",
            repeat = 1,
            arabic = "رَبِّ اغْفِرْ لِي وَلِوَالِدَيَّ وَارْحَمْهُمَا كَمَا رَبَّيَانِي صَغِيرًا",
            latin = "Rabbigfir lī wa liwālidayya warḥamhumā kamā rabbayānī ṣagīrā.",
            translation = "Wahai Tuhanku, ampunilah aku dan kedua orang tuaku, serta sayangilah mereka berdua sebagaimana mereka telah mendidikku di waktu kecil.",
            source = "QS. Al-Isra': 24"
        ),
        DoaItem(
            id = "harian_3",
            category = "Doa Harian",
            title = "Doa Masuk Masjid",
            note = "Melangkah dengan kaki kanan.",
            repeat = 1,
            arabic = "اللَّهُمَّ افْتَحْ لِي أَبْوَابَ رَحْمَتِكَ",
            latin = "Allāhummaftaḥ lī abwāba raḥmatik.",
            translation = "Ya Allah, bukakanlah untukku pintu-pintu rahmat-Mu.",
            source = "HR. Muslim"
        ),
        DoaItem(
            id = "harian_4",
            category = "Doa Harian",
            title = "Doa Sapu Jagat (Kebaikan Dunia & Akhirat)",
            note = "Doa yang paling sering dibaca oleh Rasulullah SAW.",
            repeat = 1,
            arabic = "رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ",
            latin = "Rabbanā ātinā fid-dunyā ḥasanatan wa fil-ākhirati ḥasanatan wa qinā 'ażāban-nār.",
            translation = "Wahai Tuhan kami, berikanlah kami kebaikan di dunia dan kebaikan di akhirat, dan lindungilah kami dari siksa api neraka.",
            source = "QS. Al-Baqarah: 201"
        ),
        DoaItem(
            id = "quran_1",
            category = "Doa Quran",
            title = "Doa Keteguhan Iman (Ali 'Imran 8)",
            note = "Agar hati senantiasa teguh dalam hidayah.",
            repeat = 1,
            arabic = "رَبَّنَا لَا تُزِغْ قُلُوبَنَا بَعْدَ إِذْ هَدَيْتَنَا وَهَبْ لَنَا مِن لَّدُنكَ رَحْمَةً ۚ إِنَّكَ أَنتَ الْوَهَّابُ",
            latin = "Rabbanā lā tuzig qulūbanā ba'da iż hadaitanā wa hab lanā mil ladunka raḥmah, innaka antal-wahhāb.",
            translation = "Wahai Tuhan kami, janganlah Engkau condongkan hati kami kepada kesesatan setelah Engkau beri petunjuk kepada kami, dan karuniakanlah kepada kami rahmat dari sisi-Mu; sesungguhnya Engkau Maha Pemberi.",
            source = "QS. Ali 'Imran: 8"
        ),
        DoaItem(
            id = "quran_2",
            category = "Doa Quran",
            title = "Doa Nabi Yunus dalam Perut Paus",
            note = "Doa mustajab penyingkap duka, kesedihan, dan kesulitan hidup.",
            repeat = 1,
            arabic = "لَّا إِلَٰهَ إِلَّا أَنتَ سُبْحَانَكَ إِنِّي كُنتُ مِنَ الظَّالِمِينَ",
            latin = "Lā ilāha illā anta subḥānaka innī kuntu minaẓ-ẓālimīn.",
            translation = "Tidak ada tuhan selain Engkau, Maha Suci Engkau. Sungguh, aku termasuk orang-orang yang zalim.",
            source = "QS. Al-Anbiya': 87"
        )
    )
}
