package com.tsu.itindr.room.people

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Database

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProfile(profileEntity: ProfileEntity)

    @Update
    suspend fun updateProfile(profileEntity: ProfileEntity)

    @Delete
    suspend fun deleteProfile(profileEntity: ProfileEntity)

    @Query("UPDATE profile SET name= :name WHERE id= :id")
    suspend fun updateProfileName(id:String,name:String)


    @Query("SELECT * FROM profile")
   fun observeAll():LiveData<List<ProfileEntity>>
}

