package com.example.webserviceapplication.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.webserviceapplication.local.model.LocalCharacter
import com.example.webserviceapplication.local.model.LocalMovie


@Database(entities = [LocalCharacter::class], version = 2)

abstract class CharacterDataBase : RoomDatabase(){

    abstract fun CharacterDAO(): CharacterDAO

}