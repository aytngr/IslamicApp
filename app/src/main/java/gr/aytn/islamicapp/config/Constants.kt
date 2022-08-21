package gr.aytn.islamicapp.config

import gr.aytn.islamicapp.model.Chapter

class Constants {
    companion object{
        private val chapterNames = arrayListOf<String>()

        private val chapterList = arrayListOf<Chapter>(Chapter(1,"Fatihə","Məkkə",7),Chapter(2,"Bəqərə","Məkkə",286),
            Chapter(3,"Ali-İmran","Mədinə",200),Chapter(4,"Nisa","Mədinə",176),
            Chapter(5,"Maidə","Mədinə",113),Chapter(6,"Ənam","Məkkə",165),
            Chapter(7,"Əraf","Məkkə",206),Chapter(8,"Ənfal","Mədinə",75),
            Chapter(9,"Tövbə","Mədinə",129),Chapter(10,"Yunus","Məkkə",109),
            Chapter(11,"Hud","Məkkə",123),Chapter(12,"Yusif","Məkkə",111),
            Chapter(13,"Rad","Mədinə",43),Chapter(14,"İbrahim","Məkkə",52),
            Chapter(15,"Hicr","Məkkə",99),Chapter(16,"Nəhl","Məkkə",128),
            Chapter(17,"İsra","Məkkə",111),Chapter(18,"Kəhf","Məkkə",110),
            Chapter(19,"Məryəm","Məkkə",98),Chapter(20,"Taha","Məkkə",135),
            Chapter(21,"Ənbiya","Məkkə",112),Chapter(22,"Həcc","Mədinə",78),
            Chapter(23,"Muminun","Məkkə",118),Chapter(24,"Nur","Mədinə",64),
            Chapter(25,"Furqan","Məkkə",77),Chapter(26,"Şuəra","Məkkə",227),
            Chapter(27,"Nəml","Məkkə",93),Chapter(28,"Qəsəs","Məkkə",88),
            Chapter(29,"Ənkəbut","Məkkə",69),Chapter(30,"Rum","Məkkə",60),
            Chapter(31,"Loğman","Məkkə",34),Chapter(32,"Səcdə","Məkkə",30),
            Chapter(33,"Əhzab","Məkkə",73),Chapter(34,"Səba","Məkkə",54),
            Chapter(35,"Fatir","Məkkə",45),Chapter(36,"Yasin","Məkkə",83),
            Chapter(37,"Saffat","Məkkə",182),Chapter(38,"Sad","Məkkə",88),
            Chapter(39,"Zumər","Məkkə",75),Chapter(40,"Ğafir","Məkkə",85),
            Chapter(41,"Fussilət","Məkkə",54),Chapter(42,"Şura","Məkkə",53),
            Chapter(43,"Zuxruf","Məkkə",89),Chapter(44,"Duxan","Məkkə",59),
            Chapter(45,"Casiyə","Məkkə",37),Chapter(46,"Əhqaf","Məkkə",35),
            Chapter(47,"Muhəmməd","Mədinə",38),Chapter(48,"Fəth","Mədinə",29),
            Chapter(49,"Hucurat","Məkkə",18),Chapter(50,"Qaf","Məkkə",45),
            Chapter(51,"Zariyat","Məkkə",60),Chapter(52,"Tur","Məkkə",49),
            Chapter(53,"Nəcm","Məkkə",62),Chapter(54,"Qəmər","Məkkə",55),
            Chapter(55,"Rəhman","Mədinə",78),Chapter(56,"Vaqiə","Məkkə",96),
            Chapter(57,"Hədid","Mədinə",29),Chapter(58,"Mucadələ","Mədinə",22),
            Chapter(59,"Həşr","Mədinə",24),Chapter(60,"Mumtəhinə","Mədinə",13),
            Chapter(61,"Saff","Mədinə",123),Chapter(62,"Cumuə","Mədinə",11),
            Chapter(63,"Munafiqun","Mədinə",11),Chapter(64,"Təğabun","Mədinə",18),
            Chapter(65,"Təlaq","Mədinə",12),Chapter(66,"Təhrim","Mədinə",12),
            Chapter(67,"Mülk","Məkkə",30),Chapter(68,"Nun","Məkkə",52),
            Chapter(69,"Haqqə","Məkkə",52),Chapter(70,"Məaric","Məkkə",44),
            Chapter(71,"Nuh","Məkkə",28),Chapter(72,"Cinn","Məkkə",28),
            Chapter(73,"Muzzəmmil","Məkkə",20),Chapter(74,"Muddəssir","Məkkə",56),
            Chapter(75,"Qiyamə","Məkkə",40),Chapter(76,"İnsan","Mədinə",31),
            Chapter(77,"Mursəlat","Məkkə",50),Chapter(78,"Nəbə","Məkkə",40),
            Chapter(79,"Naziat","Məkkə",46),Chapter(80,"Əbəs","Məkkə",42),
            Chapter(81,"Təkvir","Məkkə",29),Chapter(82,"İnfitar","Məkkə",19),
            Chapter(83,"Mutəffifin","Məkkə",36),Chapter(84,"İnşiqaq","Məkkə",25),
            Chapter(85,"Buruc","Məkkə",22), Chapter(86,"Tariq","Məkkə",17),
            Chapter(87,"A'lə","Məkkə",19),Chapter(88,"Ğaşiyə","Məkkə",40),
            Chapter(89,"Fəcr","Məkkə",30),Chapter(90,"Bələd","Məkkə",20),
            Chapter(91,"Şəms","Məkkə",15),Chapter(92,"Leyl","Məkkə",21),
            Chapter(93,"Duha","Məkkə",11),Chapter(94,"İnşirah","Məkkə",8),
            Chapter(95,"Tin","Məkkə",8),Chapter(96,"Ələq","Məkkə",19),
            Chapter(97,"Qədr","Məkkə",5),Chapter(98,"Bəyyinə","Mədinə",8),
            Chapter(99,"Zilzal","Mədinə",8),Chapter(100,"Adiyat","Məkkə",11),
            Chapter(101,"Qariə","Məkkə",11),Chapter(102,"Təkasur","Məkkə",8),
            Chapter(103,"Əsr","Məkkə",3),Chapter(104,"Huməzə","Məkkə",9),
            Chapter(105,"Fil","Məkkə",5),Chapter(106,"Qureyş","Məkkə",4),
            Chapter(107,"Maun","Məkkə",7),Chapter(108,"Kəvsər","Məkkə",3),
            Chapter(109,"Kafirun","Məkkə",6),Chapter(110,"Nəsr","Mədinə",3),
            Chapter(111,"Əbu Ləhəb","Məkkə",5),Chapter(112,"İxlas","Məkkə",4),
            Chapter(113,"Fələq","Məkkə",5),Chapter(114,"Nas","Məkkə",6)
        )
        fun getChapterList(): ArrayList<Chapter>{
            return  chapterList
        }

    }

}