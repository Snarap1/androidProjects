package com.example.sportclubapp.Data

import android.content.ContentResolver
import android.net.Uri
import android.provider.BaseColumns

class  ClubContract private constructor() {

    companion object{
        const val DB_VERSION = 1
        const val DB_NAME = "club"
        const val SCHEME = "content://"
        const val AUTHORITY = "com.example.sportclubapp"
        const val PATH_MEMBERS = "members"

        val BASE_CONTENT_URI = Uri.parse(SCHEME+ AUTHORITY)
    }

 class MemberEntry() : BaseColumns{
       companion object {
           const val TABLE_NAME: String = "members"
           const val KEY_ID: String = BaseColumns._ID
           const val KEY_FIRST_NAME: String = "firstName"
           const val KEY_LAST_NAME: String = "lastName"
           const val KEY_GENDER: String = "gender"
           const val KEY_HEALTH_GROUP: String = "healthGroup"

           const val GENDER_UNKNOWN = 0
           const val GENDER_MALE = 1
           const val GENDER_FEMALE = 2

           val CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBERS)!!

           const val CONTENT_MULTIPLE_ITEMS  = ContentResolver.CURSOR_DIR_BASE_TYPE+ "/" + AUTHORITY +"/"+ PATH_MEMBERS
           const val CONTENT_SINGLE_ITEMS  = ContentResolver.CURSOR_ITEM_BASE_TYPE+ "/" + AUTHORITY + "/" + PATH_MEMBERS
       }
    }

}