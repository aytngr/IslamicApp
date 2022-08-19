package gr.aytn.islamicapp.config

import gr.aytn.islamicapp.model.Chapter

class Constants {
    companion object{
        private val chapterList = arrayListOf<Chapter>(Chapter(1,"Fatihə","Məkkə",7),Chapter(2,"Bəqərə","Məkkə",286) )
        fun getChapterList(): ArrayList<Chapter>{
            return  chapterList
        }
    }

}