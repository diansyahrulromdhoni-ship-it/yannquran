package com.example.data.repository

import com.example.data.model.Ayat
import com.example.data.model.Surah
import com.example.data.model.SurahDetail

object OfflineQuranData {

    val all114Surahs: List<Surah> = listOf(
        Surah(1, "الفاتحة", "Al-Fatihah", 7, "Mekah", "Pembukaan", "Surat Al-Fatihah (Pembukaan) diturunkan di Mekah dan terdiri dari 7 ayat. Surat ini merupakan Ummul Qur'an."),
        Surah(2, "البقرة", "Al-Baqarah", 286, "Madinah", "Sapi Betina", "Surat Al-Baqarah diturunkan di Madinah dan terdiri dari 286 ayat, merupakan surat terpanjang dalam Al-Qur'an."),
        Surah(3, "آل عمران", "Ali 'Imran", 200, "Madinah", "Keluarga Imran", "Surat Ali 'Imran berisi tentang keturunan dan keluarga Imran."),
        Surah(4, "النساء", "An-Nisa'", 176, "Madinah", "Wanita", "Surat An-Nisa' membahas hak-hak dan kewajiban wanita serta hukum keluarga."),
        Surah(5, "المائدة", "Al-Ma'idah", 120, "Madinah", "Hidangan", "Surat Al-Ma'idah memuat hukum-hukum halal dan haram serta perjanjian."),
        Surah(6, "الأنعام", "Al-An'am", 165, "Mekah", "Binatang Ternak", "Surat Al-An'am menegaskan tentang keesaan Allah dan tauhid."),
        Surah(7, "الأعراف", "Al-A'raf", 206, "Mekah", "Tempat Tertinggi", "Surat Al-A'raf menceritakan kisah-kisah para nabi terdahulu."),
        Surah(8, "الأنفال", "Al-Anfal", 75, "Madinah", "Rampasan Perang", "Surat Al-Anfal diturunkan berkaitan dengan Perang Badar."),
        Surah(9, "التوبة", "At-Taubah", 129, "Madinah", "Pengampunan", "Surat At-Taubah memuat pernyataan pemutusan perjanjian dengan kaum musyrikin."),
        Surah(10, "يونس", "Yunus", 109, "Mekah", "Yunus", "Surat Yunus memuat kisah Nabi Yunus dan kaumnya."),
        Surah(11, "هود", "Hud", 123, "Mekah", "Hud", "Surat Hud memuat kisah nabi-nabi dan keteguhan iman."),
        Surah(12, "يوسف", "Yusuf", 111, "Mekah", "Yusuf", "Surat Yusuf menceritakan kisah terbaik (Ahsanul Qashash)."),
        Surah(13, "الرعد", "Ar-Ra'd", 43, "Madinah", "Guruh", "Surat Ar-Ra'd membahas kebesaran Allah melalui fenomena alam."),
        Surah(14, "إبراهيم", "Ibrahim", 52, "Mekah", "Ibrahim", "Surat Ibrahim memuat doa Nabi Ibrahim untuk keluarganya dan negeri Mekah."),
        Surah(15, "الحجر", "Al-Hijr", 99, "Mekah", "Gunung Al-Hijr", "Surat Al-Hijr memuat pemeliharaan Allah terhadap Al-Qur'an."),
        Surah(16, "النحل", "An-Nahl", 128, "Mekah", "Lebah", "Surat An-Nahl memuat nikmat-nikmat Allah yang tak terhitung."),
        Surah(17, "الإسراء", "Al-Isra'", 111, "Mekah", "Memperjalankan Malam Hari", "Surat Al-Isra' menceritakan perjalanan Isra' Mi'raj Nabi Muhammad SAW."),
        Surah(18, "الكهف", "Al-Kahf", 110, "Mekah", "Gua", "Surat Al-Kahf memuat 4 kisah besar: Ashabul Kahfi, Pemilik Kebun, Musa & Khidir, serta Dzulkarnain."),
        Surah(19, "مريم", "Maryam", 98, "Mekah", "Maryam", "Surat Maryam menceritakan kelahiran Nabi Isa dan keajaiban kekuasaan Allah."),
        Surah(20, "طه", "Taha", 135, "Mekah", "Taha", "Surat Taha memuat kisah Nabi Musa dan kelembutan dakwah."),
        Surah(21, "الأنبياء", "Al-Anbiya'", 112, "Mekah", "Para Nabi", "Surat Al-Anbiya' memuat kisah 16 nabi dan teladan mereka."),
        Surah(22, "الحج", "Al-Hajj", 78, "Madinah", "Haji", "Surat Al-Hajj menjelaskan syariat haji dan keutamaan Baitullah."),
        Surah(23, "المؤمنون", "Al-Mu'minun", 118, "Mekah", "Orang-orang Mukmin", "Surat Al-Mu'minun menjelaskan sifat-sifat orang beriman yang beruntung."),
        Surah(24, "النور", "An-Nur", 64, "Madinah", "Cahaya", "Surat An-Nur memuat ayat cahaya dan adab kesucian diri."),
        Surah(25, "الفرقان", "Al-Furqan", 77, "Mekah", "Pembeda", "Surat Al-Furqan menjelaskan sifat 'Ibadurrahman hamba-hamba Allah Yang Maha Pengasih."),
        Surah(26, "الشعراء", "Asy-Syu'ara'", 227, "Mekah", "Para Penyair", "Surat Asy-Syu'ara' memuat argumentasi para rasul."),
        Surah(27, "النمل", "An-Naml", 93, "Mekah", "Semut", "Surat An-Naml memuat kisah Nabi Sulaiman dan Ratu Balqis."),
        Surah(28, "القصص", "Al-Qasas", 88, "Mekah", "Kisah-kisah", "Surat Al-Qasas memuat kisah lengkap Nabi Musa sejak bayi hingga dewasa."),
        Surah(29, "العنكبوت", "Al-'Ankabut", 69, "Mekah", "Laba-laba", "Surat Al-'Ankabut memuat perumpamaan berhala seperti sarang laba-laba."),
        Surah(30, "الروم", "Ar-Rum", 60, "Mekah", "Bangsa Romawi", "Surat Ar-Rum memuat ramalan kemenangan bangsa Romawi dan tanda kekuasaan Allah."),
        Surah(31, "لقمان", "Luqman", 34, "Mekah", "Luqman", "Surat Luqman memuat nasihat-nasihat emas Luqman kepada putranya."),
        Surah(32, "السجدة", "As-Sajdah", 30, "Mekah", "Sujud", "Surat As-Sajdah dianjurkan dibaca pada shalat Subuh hari Jumat."),
        Surah(33, "الأحزاب", "Al-Ahzab", 73, "Madinah", "Golongan yang Bersekutu", "Surat Al-Ahzab menceritakan Perang Khandaq."),
        Surah(34, "سبإ", "Saba'", 54, "Mekah", "Kaum Saba'", "Surat Saba' menceritakan kemakmuran dan bencana negeri Saba'."),
        Surah(35, "فاطر", "Fatir", 45, "Mekah", "Pencipta", "Surat Fatir memuliakan Allah Sang Maha Pencipta seluruh alam."),
        Surah(36, "يس", "Yasin", 83, "Mekah", "Yasin", "Surat Yasin adalah jantung Al-Qur'an (Qalb al-Qur'an) yang penuh hikmah dan keutamaan."),
        Surah(37, "الصافات", "As-Saffat", 182, "Mekah", "Barisan-barisan", "Surat As-Saffat menceritakan barisan malaikat dan ketauhidan."),
        Surah(38, "ص", "Sad", 88, "Mekah", "Sad", "Surat Sad memuat kisah kesabaran Nabi Ayyub dan kemuliaan Nabi Dawud."),
        Surah(39, "الزمر", "Az-Zumar", 75, "Mekah", "Rombongan", "Surat Az-Zumar memuat ayat harapan ampunan yang luas."),
        Surah(40, "غافر", "Ghafir", 85, "Mekah", "Maha Pengampun", "Surat Ghafir memuat sifat Allah Pengampun dosa dan penerima taubat."),
        Surah(41, "فصلت", "Fussilat", 54, "Mekah", "Yang Dijelaskan", "Surat Fussilat menjelaskan ayat-ayat Al-Qur'an yang diturunkan secara terperinci."),
        Surah(42, "الشورى", "Asy-Syura", 53, "Mekah", "Musyawarah", "Surat Asy-Syura menganjurkan prinsip musyawarah."),
        Surah(43, "الزخرف", "Az-Zukhruf", 89, "Mekah", "Perhiasan", "Surat Az-Zukhruf mengingatkan bahwa perhiasan dunia bersifat fana."),
        Surah(44, "الدخان", "Ad-Dukhan", 59, "Mekah", "Kabut", "Surat Ad-Dukhan memperingatkan tentang tanda-tanda hari kiamat."),
        Surah(45, "الجاثية", "Al-Jasiyah", 37, "Mekah", "Yang Berlutut", "Surat Al-Jasiyah menggambarkan keadaan manusia berlutut di hari pembalasan."),
        Surah(46, "الأحقاف", "Al-Ahqaf", 35, "Mekah", "Bukit Pasir", "Surat Al-Ahqaf menceritakan negeri kaum 'Ad."),
        Surah(47, "محمد", "Muhammad", 38, "Madinah", "Muhammad", "Surat Muhammad memuat petunjuk keteguhan dalam perjuangan menegakkan kebenaran."),
        Surah(48, "الفتح", "Al-Fath", 29, "Madinah", "Kemenangan", "Surat Al-Fath memuat kabar gembira kemenangan nyata pada Perjanjian Hudaibiyah."),
        Surah(49, "الحجرات", "Al-Hujurat", 18, "Madinah", "Kamar-kamar", "Surat Al-Hujurat memuat adab persaudaraan Islam, tabayyun, dan toleransi."),
        Surah(50, "ق", "Qaf", 45, "Mekah", "Qaf", "Surat Qaf memuat peringatan tentang hari kebangkitan."),
        Surah(51, "الذاريات", "Az-Zariyat", 60, "Mekah", "Angin yang Menerbangkan", "Surat Az-Zariyat menegaskan tujuan penciptaan jin dan manusia semata untuk beribadah."),
        Surah(52, "الطور", "At-Tur", 49, "Mekah", "Bukit", "Surat At-Tur memuat sumpah Allah demi bukit Thursina."),
        Surah(53, "النجم", "An-Najm", 62, "Mekah", "Bintang", "Surat An-Najm memuat peristiwa Mi'raj Nabi ke Sidratul Muntaha."),
        Surah(54, "القمر", "Al-Qamar", 55, "Mekah", "Bulan", "Surat Al-Qamar memuat mukjizat terbelahnya bulan dan kemudahan Al-Qur'an untuk dipelajari."),
        Surah(55, "الرحمن", "Ar-Rahman", 78, "Madinah", "Yang Maha Pengasih", "Surat Ar-Rahman adalah pengantin Al-Qur'an (Arusul Qur'an) dengan pengulangan 'Fabiayyi ala-i Rabbikuma tukadzdziban'."),
        Surah(56, "الواقعة", "Al-Waqi'ah", 96, "Mekah", "Hari Kiamat", "Surat Al-Waqi'ah terkenal sebagai surah pembuka pintu rezeki dan penangkal kefakiran."),
        Surah(57, "الحديد", "Al-Hadid", 29, "Madinah", "Besi", "Surat Al-Hadid menerangkan tentang kekuatan besi dan keagungan sedekah."),
        Surah(58, "المجادلة", "Al-Mujadalah", 22, "Madinah", "Gugatan", "Surat Al-Mujadalah memuat hukum zhihar dan adab bermajelis."),
        Surah(59, "الحشر", "Al-Hasyr", 24, "Madinah", "Pengusiran", "Surat Al-Hasyr memuat Asmaul Husna yang mulia di akhir surah."),
        Surah(60, "الممتحنة", "Al-Mumtahanah", 13, "Madinah", "Wanita yang Diuji", "Surat Al-Mumtahanah memuat ujian keimanan kaum wanita muhajirin."),
        Surah(61, "الصف", "As-Saff", 14, "Madinah", "Barisan", "Surat As-Saff mengajak mukminin bersatu dalam barisan yang kokoh."),
        Surah(62, "الجمعة", "Al-Jumu'ah", 11, "Madinah", "Hari Jumat", "Surat Al-Jumu'ah mewajibkan ibadah shalat Jumat dan meninggalkan jual beli saat azan berkumandang."),
        Surah(63, "المنافقون", "Al-Munafiqun", 11, "Madinah", "Orang-orang Munafik", "Surat Al-Munafiqun membongkar kedok sifat kaum munafik."),
        Surah(64, "التغابن", "At-Tagabun", 18, "Madinah", "Hari Ditampakkan Kesalahan", "Surat At-Tagabun menerangkan tentang hari perhitungan hakiki."),
        Surah(65, "الطلاق", "At-Talaq", 12, "Madinah", "Talak", "Surat At-Talaq memuat ayat seribu dinar (jalan keluar bagi orang yang bertakwa)."),
        Surah(66, "التحريم", "At-Tahrim", 12, "Madinah", "Pengharaman", "Surat At-Tahrim memuat perintah menjaga keluarga dari api neraka."),
        Surah(67, "الملك", "Al-Mulk", 30, "Mekah", "Kerajaan", "Surat Al-Mulk adalah pembela dan penolong dari siksa kubur (Tabarak)."),
        Surah(68, "القلم", "Al-Qalam", 52, "Mekah", "Pena", "Surat Al-Qalam menegaskan akhlak agung Nabi Muhammad SAW."),
        Surah(69, "الحاقة", "Al-Haqqah", 52, "Mekah", "Hari Kiamat", "Surat Al-Haqqah menggambarkan kepastian terjadinya kiamat."),
        Surah(70, "المعارج", "Al-Ma'arij", 44, "Mekah", "Tempat-tempat Naik", "Surat Al-Ma'arij memuat sifat sabar yang indah (shabr jamil)."),
        Surah(71, "نوح", "Nuh", 28, "Mekah", "Nuh", "Surat Nuh menceritakan perjuangan dakwah Nabi Nuh yang penuh kesabaran."),
        Surah(72, "الجن", "Al-Jinn", 28, "Mekah", "Jin", "Surat Al-Jinn memuat keimanan golongan jin saat mendengarkan Al-Qur'an."),
        Surah(73, "المزمل", "Al-Muzzammil", 20, "Mekah", "Orang yang Berselimut", "Surat Al-Muzzammil memerintahkan qiyamul lail (shalat malam)."),
        Surah(74, "المدثر", "Al-Muddassir", 56, "Mekah", "Orang yang Berkemul", "Surat Al-Muddassir memerintahkan bangkit untuk memberi peringatan."),
        Surah(75, "القيامة", "Al-Qiyamah", 40, "Mekah", "Hari Kebangkitan", "Surat Al-Qiyamah bersumpah demi hari kiamat dan jiwa yang menyesali diri."),
        Surah(76, "الإنسان", "Al-Insan", 31, "Madinah", "Manusia", "Surat Al-Insan menceritakan kenikmatan surga dan keikhlasan memberi makanan."),
        Surah(77, "المرسلات", "Al-Mursalat", 50, "Mekah", "Malaikat yang Diutus", "Surat Al-Mursalat memuat sumpah para malaikat."),
        Surah(78, "النبإ", "An-Naba'", 40, "Mekah", "Berita Besar", "Awal Juz 'Amma (Juz 30) yang menceritakan berita hari kebangkitan."),
        Surah(79, "النازعات", "An-Nazi'at", 46, "Mekah", "Malaikat yang Mencabut", "Menceritakan malaikat pencabut nyawa dan kisah Nabi Musa menghadapi Fir'aun."),
        Surah(80, "عبس", "Abasa", 42, "Mekah", "Bermuka Masam", "Teguran penuh kasih kepada Nabi SAW terkait sahabat Abdullah bin Ummi Maktum."),
        Surah(81, "التكوير", "At-Takwir", 29, "Mekah", "Menggulung", "Menceritakan dahsyatnya peristiwa kiamat saat matahari digulung."),
        Surah(82, "الانفطار", "Al-Infitar", 19, "Mekah", "Terbelah", "Menceritakan terbelahnya langit di hari kiamat."),
        Surah(83, "المطففين", "Al-Mutaffifin", 36, "Mekah", "Orang-orang Curang", "Peringatan keras bagi orang-orang yang curang dalam takaran dan timbangan."),
        Surah(84, "الانشقاق", "Al-Insyiqaq", 25, "Mekah", "Terbelah", "Keadaan langit saat terbelah dan penyerahan kitab catatan amal."),
        Surah(85, "البروج", "Al-Buruj", 22, "Mekah", "Gugusan Bintang", "Kisah keteguhan Ashabul Ukhdud mempertahankan iman di parit api."),
        Surah(86, "الطارق", "At-Tariq", 17, "Mekah", "Yang Datang di Malam Hari", "Tentang bintang yang menembus kegelapan dan penciptaan manusia."),
        Surah(87, "الأعلى", "Al-A'la", 19, "Mekah", "Yang Mahatinggi", "Perintah bertasbih menyucikan nama Rabb Yang Mahatinggi."),
        Surah(88, "الغاشية", "Al-Ghasyiyah", 26, "Mekah", "Hari Pembalasan", "Menggambarkan dua golongan manusia pada hari kiamat."),
        Surah(89, "الفجر", "Al-Fajr", 30, "Mekah", "Fajar", "Memuat seruan mulia kepada jiwa yang tenang (Nafs al-Muthmainnah)."),
        Surah(90, "البلد", "Al-Balad", 20, "Mekah", "Negeri", "Kemuliaan kota suci Mekah dan jalan terjal mendaki (Al-'Aqabah)."),
        Surah(91, "الشمس", "Asy-Syams", 15, "Mekah", "Matahari", "Sumpah Allah atas ciptaan-Nya dan keberuntungan orang yang menyucikan jiwa."),
        Surah(92, "الليل", "Al-Lail", 21, "Mekah", "Malam", "Perbedaan jalan orang dermawan yang bertakwa dan orang kikir."),
        Surah(93, "الضحى", "Ad-Duha", 11, "Mekah", "Waktu Duha", "Penghiburan hangat bagi Rasulullah bahwa Rabb-nya tiada meninggalkannya."),
        Surah(94, "الشرح", "Asy-Syarh", 8, "Mekah", "Kelapangan", "Kabar gembira: sungguh beserta kesulitan ada kemudahan."),
        Surah(95, "التين", "At-Tin", 8, "Mekah", "Buah Tin", "Penciptaan manusia dalam bentuk sebaik-baik rupa (Fi ahsani taqwim)."),
        Surah(96, "العلق", "Al-'Alaq", 19, "Mekah", "Segumpal Darah", "Lima ayat pertamanya adalah wahyu pertama: Iqra' bismi Rabbika."),
        Surah(97, "القدر", "Al-Qadr", 5, "Mekah", "Kemuliaan", "Kemuliaan malam Lailatul Qadr yang lebih baik dari seribu bulan."),
        Surah(98, "البينة", "Al-Bayyinah", 8, "Madinah", "Bukti Nyata", "Penegasan tentang keikhlasan dalam beragama."),
        Surah(99, "الزلزلة", "Az-Zalzalah", 8, "Madinah", "Guncangan", "Guncangan dahsyat bumi dan balasan kebaikan sekecil zarrah."),
        Surah(100, "العاديات", "Al-'Adiyat", 11, "Mekah", "Kuda Perang yang Berlari Kencang", "Kuda perang yang berderap kencang dan tabiat manusia terhadap harta."),
        Surah(101, "القارعة", "Al-Qari'ah", 11, "Mekah", "Hari Kiamat yang Menggetarkan", "Hari di mana manusia bagaikan anai-anai yang berhamburan."),
        Surah(102, "التكاثر", "At-Takasur", 8, "Mekah", "Bermegah-megahan", "Peringatan agar tidak terlena oleh perlombaan menumpuk kemegahan dunia."),
        Surah(103, "العصر", "Al-'Asr", 3, "Mekah", "Masa/Waktu", "Demi masa! Manusia sungguh merugi kecuali yang beriman, beramal saleh, dan saling menasihati."),
        Surah(104, "الهمزة", "Al-Humazah", 9, "Mekah", "Pengumpat", "Ancaman siksa bagi orang yang gemar mengumpat dan mencela."),
        Surah(105, "الفيل", "Al-Fil", 5, "Mekah", "Gajah", "Kisah kehancuran pasukan bergajah Abrahah oleh burung Ababil."),
        Surah(106, "قريش", "Quraisy", 4, "Mekah", "Suku Quraisy", "Seruan beribadah kepada Rabb Pemilik Ka'bah yang memberi rasa aman."),
        Surah(107, "الماعون", "Al-Ma'un", 7, "Mekah", "Bantuan Bermanfaat", "Ciri-ciri pendusta agama: menghardik anak yatim dan enggan memberi bantuan."),
        Surah(108, "الكوثر", "Al-Kausar", 3, "Mekah", "Nikmat yang Banyak", "Karunia telaga Kausar dan perintah shalat serta berkurban."),
        Surah(109, "الكافرون", "Al-Kafirun", 6, "Mekah", "Orang-orang Kafir", "Penegasan akidah tanpa kompromi: Lakum diinukum wa liya diin."),
        Surah(110, "النصر", "An-Nasr", 3, "Madinah", "Pertolongan", "Kabar datangnya pertolongan Allah dan pembebasan kota Mekah."),
        Surah(111, "اللهب", "Al-Lahab", 5, "Mekah", "Gejolak Api", "Kehancuran Abu Lahab dan istrinya yang memusuhi dakwah Nabi."),
        Surah(112, "الإخلاص", "Al-Ikhlas", 4, "Mekah", "Keesaan Allah", "Inti tauhid: Qul huwallahu ahad. Setara sepertiga Al-Qur'an."),
        Surah(113, "الفلق", "Al-Falaq", 5, "Mekah", "Waktu Subuh", "Permohonan perlindungan dari kejahatan makhluk, malam gelap, sihir, dan kedengkian."),
        Surah(114, "الناس", "An-Nas", 6, "Mekah", "Manusia", "Permohonan perlindungan kepada Rabb manusia dari bisikan jahat setan dan manusia.")
    )

    // Pre-loaded offline complete Surah Al-Fatihah
    val surahAlFatihah = SurahDetail(
        nomor = 1,
        nama = "الفاتحة",
        namaLatin = "Al-Fatihah",
        jumlahAyat = 7,
        tempatTurun = "Mekah",
        arti = "Pembukaan",
        deskripsi = "Surat Al-Fatihah (Pembukaan) yang diturunkan di Mekah dan terdiri dari 7 ayat. Merupakan induk Al-Qur'an (Ummul Kitab).",
        audioFull = mapOf(
            "01" to "https://equran.nos.wjv-1.neo.id/audio-full/Abdullah-Al-Juhany/001.mp3",
            "05" to "https://equran.nos.wjv-1.neo.id/audio-full/Misyari-Rasyid-Al-Afasi/001.mp3"
        ),
        ayat = listOf(
            Ayat(1, "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", "Bismillāhir-raḥmānir-raḥīm(i)", "Dengan nama Allah Yang Maha Pengasih, Maha Penyayang.", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/001001.mp3")),
            Ayat(2, "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ", "Al-ḥamdu lillāhi rabbil-'ālamīn(a)", "Segala puji bagi Allah, Tuhan seluruh alam,", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/001002.mp3")),
            Ayat(3, "الرَّحْمَٰنِ الرَّحِيمِ", "Ar-raḥmānir-raḥīm(i)", "Yang Maha Pengasih, Maha Penyayang,", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/001003.mp3")),
            Ayat(4, "مَالِكِ يَوْمِ الدِّينِ", "Māliki yaumid-dīn(i)", "Pemilik hari pembalasan.", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/001004.mp3")),
            Ayat(5, "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ", "Iyyāka na'budu wa iyyāka nasta'īn(u)", "Hanya kepada Engkaulah kami menyembah dan hanya kepada Engkaulah kami memohon pertolongan.", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/001005.mp3")),
            Ayat(6, "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ", "Ihdinaṣ-ṣirāṭal-mustaqīm(a)", "Tunjukilah kami jalan yang lurus,", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/001006.mp3")),
            Ayat(7, "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ", "Ṣirāṭal-lażīna an'amta 'alaihim, gairil-magḍūbi 'alaihim walal-ḍāllīn(a)", "(yaitu) jalan orang-orang yang telah Engkau beri nikmat kepadanya; bukan (jalan) mereka yang dimurkai, dan bukan (pula jalan) mereka yang sesat.", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/001007.mp3"))
        )
    )

    // Pre-loaded offline Al-Ikhlas
    val surahAlIkhlas = SurahDetail(
        nomor = 112,
        nama = "الإخلاص",
        namaLatin = "Al-Ikhlas",
        jumlahAyat = 4,
        tempatTurun = "Mekah",
        arti = "Keesaan Allah",
        deskripsi = "Surat Al-Ikhlas menegaskan keesaan Allah yang mutlak.",
        audioFull = mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-full/Misyari-Rasyid-Al-Afasi/112.mp3"),
        ayat = listOf(
            Ayat(1, "قُلْ هُوَ اللَّهُ أَحَدٌ", "Qul huwallāhu aḥad(un)", "Katakanlah (Muhammad), \"Dialah Allah, Yang Maha Esa.\"", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/112001.mp3")),
            Ayat(2, "اللَّهُ الصَّمَدُ", "Allāhuṣ-ṣamad(u)", "Allah tempat meminta segala sesuatu.", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/112002.mp3")),
            Ayat(3, "لَمْ يَلِدْ وَلَمْ يُولَدْ", "Lam yalid wa lam yūlad", "(Allah) tidak beranak dan tidak pula diperanakkan,", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/112003.mp3")),
            Ayat(4, "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ", "Wa lam yakul lahū kufuwan aḥad(un)", "dan tidak ada sesuatu yang setara dengan Dia.\"", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/112004.mp3"))
        )
    )

    // Pre-loaded offline Al-Falaq
    val surahAlFalaq = SurahDetail(
        nomor = 113,
        nama = "الفلق",
        namaLatin = "Al-Falaq",
        jumlahAyat = 5,
        tempatTurun = "Mekah",
        arti = "Waktu Subuh",
        deskripsi = "Surat Al-Falaq adalah surat perlindungan.",
        audioFull = mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-full/Misyari-Rasyid-Al-Afasi/113.mp3"),
        ayat = listOf(
            Ayat(1, "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ", "Qul a'ūżu birabbil-falaq(i)", "Katakanlah, \"Aku berlindung kepada Tuhan yang menguasai subuh (fajar),\"", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/113001.mp3")),
            Ayat(2, "مِن شَرِّ مَا خَلَقَ", "Min syarri mā khalaq(a)", "dari kejahatan (makhluk yang) Dia ciptakan,", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/113002.mp3")),
            Ayat(3, "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ", "Wa min syarri gāsiqin iżā waqab(a)", "dan dari kejahatan malam apabila telah gelap gulita,", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/113003.mp3")),
            Ayat(4, "وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ", "Wa min syarrin-naffāṡāti fil-'uqad(i)", "dan dari kejahatan (perempuan-perempuan) penyihir yang meniup pada buhul-buhul (talinya),", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/113004.mp3")),
            Ayat(5, "وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ", "Wa min syarri ḥāsidin iżā ḥasad(a)", "dan dari kejahatan orang yang dengki apabila dia dengki.\"", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/113005.mp3"))
        )
    )

    // Pre-loaded offline An-Nas
    val surahAnNas = SurahDetail(
        nomor = 114,
        nama = "الناس",
        namaLatin = "An-Nas",
        jumlahAyat = 6,
        tempatTurun = "Mekah",
        arti = "Manusia",
        deskripsi = "Surat An-Nas adalah surat penutup Al-Qur'an memohon perlindungan dari bisikan jahat.",
        audioFull = mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-full/Misyari-Rasyid-Al-Afasi/114.mp3"),
        ayat = listOf(
            Ayat(1, "قُلْ أَعُوذُ بِرَبِّ النَّاسِ", "Qul a'ūżu birabbin-nās(i)", "Katakanlah, \"Aku berlindung kepada Tuhannya manusia,\"", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/114001.mp3")),
            Ayat(2, "مَلِكِ النَّاسِ", "Malikin-nās(i)", "Raja manusia,", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/114002.mp3")),
            Ayat(3, "إِلَٰهِ النَّاسِ", "Ilāhin-nās(i)", "Sembahan manusia,", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/114003.mp3")),
            Ayat(4, "مِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ", "Min syarril-waswāsil-khannās(i)", "dari kejahatan (bisikan) setan yang bersembunyi,", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/114004.mp3")),
            Ayat(5, "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ", "Allażī yuwaswisu fī ṣudūrin-nās(i)", "yang membisikkan (kejahatan) ke dalam dada manusia,", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/114005.mp3")),
            Ayat(6, "مِنَ الْجِنَّةِ وَالنَّاسِ", "Minal-jinnati wan-nās(i)", "dari (golongan) jin dan manusia.\"", mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/114006.mp3"))
        )
    )

    // Pre-loaded offline Ayat Kursi
    val ayatKursi = Ayat(
        nomorAyat = 255,
        teksArab = "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ ۚ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ ۗ مَن ذَا الَّذِي يَشْفَعُ عِندَهُ إِلَّا بِإِذْنِهِ ۚ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ ۖ وَلَا يُحِيطُونَ بِشَيْءٍ مِّنْ عِلْمِهِ إِلَّا بِمَا شَاءَ ۚ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ ۖ وَلَا يَئُودُهُ حِفْظُهُمَا ۚ وَهُوَ الْعَلِيُّ الْعَظِيمُ",
        teksLatin = "Allāhu lā ilāha illā huwal-ḥayyul-qayyūm(u), lā ta'khużuhū sinatuw wa lā naum(un), lahū mā fis-samāwāti wa mā fil-arḍ(i), man żal-lażī yasyfa'u 'indahū illā bi'iżnih(ī), ya'lamu mā baina aidīhim wa mā khalfahum, wa lā yuḥīṭūna bisyai'im min 'ilmihī illā bimā syā'(a), wasi'a kursiyyuhus-samāwāti wal-arḍ(a), wa lā ya'ūduhū ḥifẓuhumā, wa huwal-'aliyyul-'aẓīm(u).",
        teksIndonesia = "Allah, tidak ada tuhan selain Dia. Yang Mahahidup, Yang terus-menerus mengurus (makhluk-Nya), tidak mengantuk dan tidak tidur. Milik-Nya apa yang ada di langit dan apa yang ada di bumi. Tidak ada yang dapat memberi syafaat di sisi-Nya tanpa izin-Nya. Dia mengetahui apa yang ada di hadapan mereka dan apa yang di belakang mereka, dan mereka tidak mengetahui sesuatu apa pun dari ilmu-Nya melainkan apa yang Dia kehendaki. Kursi-Nya (ilmu dan kekuasaan-Nya) meliputi langit dan bumi. Dan Dia tidak merasa berat memelihara keduanya, dan Dia Mahatinggi, Mahabesar.",
        audio = mapOf("05" to "https://equran.nos.wjv-1.neo.id/audio-partial/Misyari-Rasyid-Al-Afasi/002255.mp3")
    )
}
