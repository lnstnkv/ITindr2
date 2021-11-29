package com.tsu.itindr.room.people

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tsu.itindr.edit.model.Avatar
import com.tsu.itindr.find.people.model.PeopleProfile

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val avatar: String
){
    fun toDomain()=PeopleProfile(
        id=id,
        username= name,
        avatar=avatar
    )
}



