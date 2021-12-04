package com.tsu.itindr.room.people

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Database

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProfiles(profileEntity: List<ProfileEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProfile(profileEntity: ProfileEntity)

    @Update
    suspend fun updateProfile(profileEntity: ProfileEntity)

    @Delete
    suspend fun deleteProfile(profileEntity: ProfileEntity)

    @Query("SELECT EXISTS(SELECT*FROM profile WHERE id=:id)")
    suspend fun checkUser(id: String): Boolean

    @Query("UPDATE profile SET name= :name WHERE id= :id")
    suspend fun updateProfileName(id: String, name: String)

    @Query("UPDATE profile SET name= :name, about= :about WHERE id= :id")
    suspend fun updateProfiles(id: String, name: String,about:String)

    @Query("DELETE FROM profile")
    suspend fun deleteAll()

    @Query("SELECT * FROM profile where id != :id")
    fun addAll(id: String): LiveData<List<ProfileEntity>>

    @Query("SELECT * FROM profile")
    fun observeAll(): LiveData<List<ProfileEntity>>

    @Query("SELECT* FROM profile WHERE id= :id")
     fun observeProfile(id: String): ProfileEntity

    @Query("SELECT*FROM profile WHERE id= :id")
    fun getUser(id: String): LiveData<ProfileEntity>


}

