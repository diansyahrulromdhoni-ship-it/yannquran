package com.example.data.repository

import com.example.data.model.Ayat

data class TahlilStep(
    val id: Int,
    val title: String,
    val note: String = "",
    val repeatCount: Int = 1,
    val arabic: String,
    val latin: String,
    val translation: String
)

object TahlilData {

    val keutamaanYasin = """
        Rasulullah SAW bersabda:
        "Sesungguhnya setiap sesuatu memiliki hati, dan hatinya Al-Qur'an adalah surat Yasin. Siapa yang membacanya, maka Allah mencatat baginya seperti membaca Al-Qur'an sepuluh kali." (HR. At-Tirmidzi)
        
        "Bacakanlah surat Yasin kepada orang-orang yang menghadapi kematian di antara kalian." (HR. Abu Dawud & Ibnu Majah)
    """.trimIndent()

    val tahlilSequence: List<TahlilStep> = listOf(
        TahlilStep(
            id = 1,
            title = "1. Pengantar Hadhoroh Fatihah",
            note = "Membaca Al-Fatihah dihadiahkan untuk Rasulullah SAW, keluarga, dan para sahabat",
            repeatCount = 1,
            arabic = "إِلَى حَضْرَةِ النَّبِيِّ الْمُصْطَفَى مُحَمَّدٍ صَلَّى اللهُ عَلَيْهِ وَسَلَّمَ وَآلِهِ وَأَزْوَاجِهِ وَذُرِّيَّاتِهِ وَأَهْلِ بَيْتِهِ الْكِرَامِ، الْفَاتِحَةُ...",
            latin = "Ilā ḥaḍratin-nabiyyil-muṣṭafā Muḥammadin ṣallallāhu 'alaihi wa sallama wa ālihī wa azwājihī wa żurriyyātihī wa ahli baitihil-kirām, Al-Fātiḥah...",
            translation = "Kepada hadirat Nabi pilihan Muhammad SAW, keluarga, istri-istri, keturunan, dan segenap ahli baitnya yang mulia, (mari kita baca) Al-Fatihah..."
        ),
        TahlilStep(
            id = 2,
            title = "2. Hadhoroh Para Ulama & Ahli Kubur",
            note = "Dihadiahkan kepada para nabi, syuhada, shalihin, dan para leluhur",
            repeatCount = 1,
            arabic = "ثُمَّ إِلَى أَرْوَاحِ إِخْوَانِهِ مِنَ الأَنْبِيَاءِ وَالْمُرْسَلِينَ وَالأَوْلِيَاءِ وَالشُّهَدَاءِ وَالصَّالِحِينَ وَالصَّحَابَةِ وَالتَّابِعِينَ، وَإِلَى أَرْوَاحِ جَمِيعِ أَهْلِ الْقُبُورِ مِنَ الْمُسْلِمِينَ وَالْمُسْلِمَاتِ، الْفَاتِحَةُ...",
            latin = "Ṡumma ilā arwāḥi ikhwānihī minal-ambiyā'i wal-mursalīn wal-auliyā'i wasy-syuhadā'i waṣ-ṣāliḥīn waṣ-ṣaḥābati wat-tābi'īn, wa ilā arwāḥi jamī'i ahlil-qubūri minal-muslimīna wal-muslimāt, Al-Fātiḥah...",
            translation = "Kemudian kepada ruh para nabi, rasul, para wali, syuhada, shalihin, sahabat, tabiin, dan seluruh ahli kubur muslimin dan muslimat, Al-Fatihah..."
        ),
        TahlilStep(
            id = 3,
            title = "3. Membaca Surat Al-Ikhlas",
            note = "Dibaca 3 kali",
            repeatCount = 3,
            arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\nقُلْ هُوَ اللَّهُ أَحَدٌ ۝ اللَّهُ الصَّمَدُ ۝ لَمْ يَلِدْ وَلَمْ يُولَدْ ۝ وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ",
            latin = "Bismillāhir-raḥmānir-raḥīm. Qul huwallāhu aḥad. Allāhuṣ-ṣamad. Lam yalid wa lam yūlad. Wa lam yakul lahū kufuwan aḥad.",
            translation = "Katakanlah (Muhammad), 'Dialah Allah, Yang Maha Esa. Allah tempat bergantung segala sesuatu. Dia tidak beranak dan tidak pula diperanakkan, dan tidak ada sesuatu yang setara dengan Dia.'"
        ),
        TahlilStep(
            id = 4,
            title = "4. Tahlil & Takbir",
            repeatCount = 1,
            arabic = "لَا إِلَهَ إِلَّا اللهُ وَاللهُ أَكْبَرُ وَلِلَّهِ الْحَمْدُ",
            latin = "Lā ilāha illallāhu wallāhu akbar, wa lillāhil-ḥamd.",
            translation = "Tiada Tuhan selain Allah, Allah Maha Besar, dan segala puji hanya bagi Allah."
        ),
        TahlilStep(
            id = 5,
            title = "5. Membaca Surat Al-Falaq",
            repeatCount = 1,
            arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\nقُلْ أَعُوذُ بِرَبِّ الْفَلَقِ ۝ مِن شَرِّ مَا خَلَقَ ۝ وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ ۝ وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ ۝ وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ",
            latin = "Bismillāhir-raḥmānir-raḥīm. Qul a'ūżu birabbil-falaq. Min syarri mā khalaq. Wa min syarri gāsiqin iżā waqab. Wa min syarrin-naffāṡāti fil-'uqad. Wa min syarri ḥāsidin iżā ḥasad.",
            translation = "Katakanlah, 'Aku berlindung kepada Tuhan yang menguasai subuh dari kejahatan makhluk yang Dia ciptakan, dari kejahatan malam apabila telah gelap gulita, dari kejahatan perempuan penyihir yang meniup pada buhul-buhul, dan dari kejahatan orang dengki apabila ia dengki.'"
        ),
        TahlilStep(
            id = 6,
            title = "6. Tahlil & Takbir",
            repeatCount = 1,
            arabic = "لَا إِلَهَ إِلَّا اللهُ وَاللهُ أَكْبَرُ وَلِلَّهِ الْحَمْدُ",
            latin = "Lā ilāha illallāhu wallāhu akbar, wa lillāhil-ḥamd.",
            translation = "Tiada Tuhan selain Allah, Allah Maha Besar, dan segala puji hanya bagi Allah."
        ),
        TahlilStep(
            id = 7,
            title = "7. Membaca Surat An-Nas",
            repeatCount = 1,
            arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\nقُلْ أَعُوذُ بِرَبِّ النَّاسِ ۝ مَلِكِ النَّاسِ ۝ إِلَٰهِ النَّاسِ ۝ مِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ ۝ الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ ۝ مِنَ الْجِنَّةِ وَالنَّاسِ",
            latin = "Bismillāhir-raḥmānir-raḥīm. Qul a'ūżu birabbin-nās. Malikin-nās. Ilāhin-nās. Min syarril-waswāsil-khannās. Allażī yuwaswisu fī ṣudūrin-nās. Minal-jinnati wan-nās.",
            translation = "Katakanlah, 'Aku berlindung kepada Tuhannya manusia, Raja manusia, Sembahan manusia, dari kejahatan bisikan setan yang bersembunyi, yang membisikkan ke dalam dada manusia, dari golongan jin dan manusia.'"
        ),
        TahlilStep(
            id = 8,
            title = "8. Awal Surat Al-Baqarah",
            repeatCount = 1,
            arabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\nالم ۝ ذَٰلِكَ الْكِتَابُ لَا رَيْبَ ۛ فِيهِ ۛ هُدًى لِّلْمُتَّقِينَ ۝ الَّذِينَ يُؤْمِنُونَ بِالْغَيْبِ وَيُقِيمُونَ الصَّلَاةَ وَمِمَّا رَزَقْنَاهُمْ يُنفِقُونَ ۝ وَالَّذِينَ يُؤْمِنُونَ بِمَا أُنزِلَ إِلَيْكَ وَمَا أُنزِلَ مِن قَبْلِكَ وَبِالْآخِرَةِ هُمْ يُوقِنُونَ ۝ أُولَٰئِكَ عَلَىٰ هُدًى مِّن رَّبِّهِمْ ۖ وَأُولَٰئِكَ هُمُ الْمُفْلِحُونَ",
            latin = "Alif Lām Mīm. Żālikal-kitābu lā raiba fīh, hudal lil-muttaqīn. Allażīna yu'minūna bil-gaibi wa yuqīmūnaṣ-ṣalāta wa mimmā razaqnāhum yunfiqūn. Wallażīna yu'minūna bimā unzila ilaika wa mā unzila min qablik, wa bil-ākhirati hum yūqinūn. Ulā'ika 'alā hudam mir rabbihim wa ulā'ika humul-mufliḥūn.",
            translation = "Kitab ini tidak ada keraguan padanya; petunjuk bagi mereka yang bertakwa..."
        ),
        TahlilStep(
            id = 9,
            title = "9. Ayat Kursi",
            repeatCount = 1,
            arabic = "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ ۚ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ ۗ مَن ذَا الَّذِي يَشْفَعُ عِندَهُ إِلَّا بِإِذْنِهِ ۚ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ ۖ وَلَا يُحِيطُونَ بِشَيْءٍ مِّنْ عِلْمِهِ إِلَّا بِمَا شَاءَ ۚ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ ۖ وَلَا يَئُودُهُ حِفْظُهُمَا ۚ وَهُوَ الْعَلِيُّ الْعَظِيمُ",
            latin = "Allāhu lā ilāha illā huwal-ḥayyul-qayyūm, lā ta'khużuhū sinatuw wa lā naum...",
            translation = "Allah, tidak ada tuhan selain Dia. Yang Mahahidup, Yang terus-menerus mengurus makhluk-Nya..."
        ),
        TahlilStep(
            id = 10,
            title = "10. Istighfar",
            note = "Dibaca 3 kali",
            repeatCount = 3,
            arabic = "أَسْتَغْفِرُ اللهَ الْعَظِيمَ الَّذِي لَا إِلَهَ إِلَّا هُوَ الْحَيَّ الْقَيُّومَ وَأَتُوبُ إِلَيْهِ",
            latin = "Astaghfirullāhal-'aẓīm, allażī lā ilāha illā huwal-ḥayyul-qayyūma wa atūbu ilaih.",
            translation = "Aku memohon ampunan kepada Allah Yang Maha Agung, tiada tuhan selain Dia Yang Mahahidup lagi Maha Mengurus, dan aku bertaubat kepada-Nya."
        ),
        TahlilStep(
            id = 11,
            title = "11. Kalimat Tahlil (Inti)",
            note = "Dibaca 33x atau 100x",
            repeatCount = 33,
            arabic = "لَا إِلَهَ إِلَّا اللهُ",
            latin = "Lā ilāha illallāh.",
            translation = "Tiada Tuhan yang berhak disembah selain Allah."
        ),
        TahlilStep(
            id = 12,
            title = "12. Penutup Tahlil & Sholawat",
            repeatCount = 1,
            arabic = "لَا إِلَهَ إِلَّا اللهُ مُحَمَّدٌ رَسُولُ اللهِ صَلَّى اللهُ عَلَيْهِ وَسَلَّمَ، كَلِمَةُ حَقٍّ عَلَيْهَا نَحْيَا وَعَلَيْهَا نَمُوتُ وَبِهَا نُبْعَثُ إِنْ شَاءَ اللهُ تَعَالَى مِنَ الآمِنِينَ.",
            latin = "Lā ilāha illallāhu Muḥammadur Rasūlullāh ṣallallāhu 'alaihi wa sallam, kalimatu ḥaqqin 'alaihā naḥyā wa 'alaihā namūtu wa bihā nub'aṡu in syā'allāhu ta'ālā minal-āminīn.",
            translation = "Tiada Tuhan selain Allah, Nabi Muhammad adalah utusan Allah semoga sholawat dan salam tercurah kepadanya. Kalimat haq yang di atasnya kami hidup, dengannya kami mati, dan dengannya kami dibangkitkan insya Allah termasuk golongan orang yang aman."
        ),
        TahlilStep(
            id = 13,
            title = "13. Tasbih & Tahmid",
            repeatCount = 33,
            arabic = "سُبْحَانَ اللهِ وَبِحَمْدِهِ سُبْحَانَ اللهِ الْعَظِيمِ",
            latin = "Subḥānallāhi wa biḥamdihī subḥānallāhil-'aẓīm.",
            translation = "Maha Suci Allah dengan memuji-Nya, Maha Suci Allah Yang Maha Agung."
        ),
        TahlilStep(
            id = 14,
            title = "14. Doa Lengkap Tahlil & Arwah",
            repeatCount = 1,
            arabic = """
بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ.
الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ حَمْدَ الشَّاكِرِينَ حَمْدَ النَّاعِمِينَ حَمْدًا يُوَافِي نِعَمَهُ وَيُكَافِئُ مَزِيدَهُ.
اللَّهُمَّ صَلِّ وَسَلِّمْ عَلَى سَيِّدِنَا مُحَمَّدٍ وَعَلَى آلِ سَيِّدِنَا مُحَمَّدٍ.
اللَّهُمَّ تَقَبَّلْ وَأَوْصِلْ ثَوَابَ مَا قَرَأْنَاهُ مِنْ كِتَابِكَ الْعَزِيزِ، وَمَا هَلَّلْنَاهُ وَمَا سَبَّحْنَاهُ وَمَا اسْتَغْفَرْنَاهُ، وَمَا صَلَّيْنَاهُ عَلَى سَيِّدِنَا مُحَمَّدٍ صَلَّى اللهُ عَلَيْهِ وَسَلَّمَ، هَدِيَّةً وَاصِلَةً، وَرَحْمَةً نَازِلَةً، وَبَرَكَةً شَامِلَةً، إِلَى حَضْرَةِ حَبِيبِنَا وَشَفِيعِنَا رَسُولِ اللهِ صَلَّى اللهُ عَلَيْهِ وَسَلَّمَ.
ثُمَّ إِلَى أَرْوَاحِ آبَائِنَا وَأُمَّهَاتِنَا وَأَجْدَادِنَا وَجَدَّاتِنَا وَمَشَايِخِنَا وَمُعَلِّمِينَا، وَخُصُوصًا إِلَى رُوحِ مَنِ اجْتَمَعْنَا هَهُنَا بِسَبَبِهِ وَلِأَجْلِهِ.
اللَّهُمَّ اغْفِرْ لَهُمْ وَارْحَمْهُمْ وَعَافِهِمْ وَاعْفُ عَنْهُمْ، اللَّهُمَّ اجْعَلْ قُبُورَهُمْ رَوْضَةً مِنْ رِيَاضِ الْجِنَانِ، وَلَا تَجْعَلْ قُبُورَهُمْ حُفْرَةً مِنْ حُفَرِ النِّيرَانِ.
رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ.
وَصَلَّى اللهُ عَلَى سَيِّدِنَا مُحَمَّدٍ وَعَلَى آلِهِ وَصَحْبِهِ وَسَلَّمَ، وَالْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ.
            """.trimIndent(),
            latin = "Bismillāhir-raḥmānir-raḥīm. Al-ḥamdu lillāhi rabbil-'ālamīn... Allāhumma taqabbal wa auṣil ṡawāba mā qara'nāhu... Allāhummagfir lahum warḥamhum wa 'āfihim wa'fu 'anhum... Rabbanā ātinā fid-dunyā ḥasanatan wa fil-ākhirati ḥasanatan wa qinā 'ażāban-nār.",
            translation = "Dengan nama Allah Yang Maha Pengasih, Maha Penyayang. Segala puji bagi Allah Tuhan semesta alam... Ya Allah, terimalah dan sampaikanlah pahala ayat-ayat Al-Qur'an yang kami baca, tahlil, tasbih, istighfar, dan sholawat kami sebagai hadiah dan rahmat untuk Rasulullah SAW, keluarga, leluhur, orang tua kami, dan khususnya arwah yang kami sebut namanya. Ya Allah, ampunilah mereka, rahmatilah mereka, selamatkanlah mereka, dan maafkanlah kesalahan mereka. Jadikan kubur mereka taman surga dan jangan jadikan jurang neraka. Wahai Tuhan kami, berilah kami kebaikan di dunia dan kebaikan di akhirat serta peliharalah kami dari siksa neraka. Amin ya Rabbal 'Alamin."
        )
    )

    // Complete Surah Yasin (Key verses and structured offline full text for immediate smooth reading)
    val yasinVerses: List<Ayat> = listOf(
        Ayat(1, "يس", "Yā Sīn", "Ya Sin.", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/036001.mp3")),
        Ayat(2, "وَالْقُرْآنِ الْحَكِيمِ", "Wal-qur'ānil-ḥakīm(i)", "Demi Al-Qur'an yang penuh hikmah,", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/036002.mp3")),
        Ayat(3, "إِنَّكَ لَمِنَ الْمُرْسَلِينَ", "Innaka laminal-mursalīn(a)", "sungguh, engkau (Muhammad) benar-benar salah seorang dari rasul-rasul,", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/036003.mp3")),
        Ayat(4, "عَلَىٰ صِرَاطٍ مُسْتَقِيمٍ", "'Alā ṣirāṭim mustaqīm(in)", "(yang berada) di atas jalan yang lurus,", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/036004.mp3")),
        Ayat(5, "تَنزِيلَ الْعَزِيزِ الرَّحِيمِ", "Tanzīlal-'azīzir-raḥīm(i)", "(sebagai wahyu) yang diturunkan oleh (Allah) Yang Mahaperkasa, Maha Penyayang,", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/036005.mp3")),
        Ayat(6, "لِتُنذِرَ قَوْمًا مَّا أُنذِرَ آبَاؤُهُمْ فَهُمْ غَافِلُونَ", "Litunżira qaumam mā unżira ābā'uhum fahum gāfilūn(a)", "agar engkau memberi peringatan kepada suatu kaum yang nenek moyangnya belum pernah diberi peringatan, karena itu mereka lalai.", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/036006.mp3")),
        Ayat(7, "لَقَدْ حَقَّ الْقَوْلُ عَلَىٰ أَكْثَرِهِمْ فَهُمْ لَا يُؤْمِنُونَ", "Laqad ḥaqqal-qaulu 'alā akṡarihim fahum lā yu'minūn(a)", "Sungguh, pasti berlaku perkataan (hukuman) terhadap kebanyakan mereka, karena mereka tidak beriman.", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/036007.mp3")),
        Ayat(8, "إِنَّا جَعَلْنَا فِي أَعْنَاقِهِمْ أَغْلَالًا فَهِيَ إِلَى الْأَذْقَانِ فَهُم مُّقْمَحُونَ", "Innā ja'alnā fī a'nāqihim aglālan fahiya ilal-ażqāni fahum muqmaḥūn(a)", "Sungguh, Kami telah memasang belenggu di leher mereka, lalu tangan mereka (diangkat) ke dagu, karena itu mereka tertengadah.", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/036008.mp3")),
        Ayat(9, "وَجَعَلْنَا مِن بَيْنِ أَيْدِيهِمْ سَدًّا وَمِنْ خَلْفِهِمْ سَدًّا فَأَغْشَيْنَاهُمْ فَهُمْ لَا يُبْصِرُونَ", "Wa ja'alnā mim baini aidīhim saddaw wa min khalfihim saddan fa agsyaināhum fahum lā yubṣirūn(a)", "Dan Kami pasang sekat di hadapan mereka dan di belakang mereka, dan Kami tutup (mata) mereka sehingga mereka tidak dapat melihat.", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/036009.mp3")),
        Ayat(10, "وَسَوَاءٌ عَلَيْهِمْ أَأَنذَرْتَهُمْ أَمْ لَمْ تُنذِرْهُمْ لَا يُؤْمِنُونَ", "Wa sawā'un 'alaihim a'anżartahum am lam tunżirhum lā yu'minūn(a)", "Dan sama saja bagi mereka, apakah engkau memberi peringatan kepada mereka atau engkau tidak memberi peringatan kepada mereka, mereka tidak akan beriman juga.", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/036010.mp3")),
        Ayat(11, "إِنَّمَا تُنذِرُ مَنِ اتَّبَعَ الذِّكْرَ وَخَشِيَ الرَّحْمَٰنَ بِالْغَيْبِ ۖ فَبَشِّرْهُ بِمَغْفِرَةٍ وَأَجْرٍ كَرِيمٍ", "Innamā tunżiru manittaba'aż-żikra wa khasyiyar-raḥmāna bil-gaib(i), fabasysyirhu bimāgfiratiw wa ajrin karīm(in)", "Sesungguhnya engkau hanya memberi peringatan kepada orang-orang yang mau mengikuti peringatan dan yang takut kepada Tuhan Yang Maha Pengasih walaupun mereka tidak melihat-Nya. Maka berilah mereka kabar gembira dengan ampunan dan pahala yang mulia.", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/036011.mp3")),
        Ayat(12, "إِنَّا نَحْنُ نُحْيِي الْمَوْتَىٰ وَنَكْتُبُ مَا قَدَّمُوا وَآثَارَهُمْ ۚ وَكُلَّ شَيْءٍ أَحْصَيْنَاهُ فِي إِمَامٍ مُّبِينٍ", "Innā naḥnu nuḥyil-mautā wa naktubu mā qaddamū wa āṡārahum, wa kulla syai'in aḥṣaināhu fī imāmim mubīn(in)", "Sungguh, Kamilah yang menghidupkan orang-orang yang mati, dan Kamilah yang mencatat apa yang telah mereka kerjakan dan bekas-bekas yang mereka (tinggalkan). Dan segala sesuatu Kami kumpulkan dalam Kitab yang jelas (Lauh Mahfuzh).", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/036012.mp3")),
        Ayat(58, "سَلَامٌ قَوْلًا مِّن رَّبٍّ رَّحِيمٍ", "Salām(un), qaulam mir rabbir raḥīm(in)", "(Kepada mereka dikatakan), \"Salam,\" sebagai ucapan selamat dari Tuhan Yang Maha Penyayang.", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/036058.mp3")),
        Ayat(82, "إِنَّمَا أَمْرُهُ إِذَا أَرَادَ شَيْئًا أَن يَقُولَ لَهُ كُن فَيَكُونُ", "Innamā amruhū iżā arāda syai'an ay yaqūla lahū kun fayakūn(u)", "Sesungguhnya urusan-Nya apabila Dia menghendaki sesuatu Dia hanya berkata kepadanya, \"Jadilah!\" Maka jadilah sesuatu itu.", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/036082.mp3")),
        Ayat(83, "فَسُبْحَانَ الَّذِي بِيَدِهِ مَلَكُوتُ كُلِّ شَيْءٍ وَإِلَيْهِ تُرْجَعُونَ", "Fa subḥānal-lażī biyadihī malakūtu kulli syai'iw wa ilaihi turja'ūn(a)", "Maka Mahasuci (Allah) yang di tangan-Nya kekuasaan atas segala sesuatu dan kepada-Nya kamu dikembalikan.", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/036083.mp3"))
    )
}
