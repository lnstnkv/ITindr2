package com.tsu.itindr.room.people

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.tsu.itindr.data.SharedPreference
import com.tsu.itindr.databinding.ActivityAuthorizationBinding
import com.tsu.itindr.find.people.model.PeopleProfile
import com.tsu.itindr.room.Database

class ProfileRepository(context: Context) {
    private val profileDao = Database.getInstance(context).getProfileDao()
    private val sharedPreference =SharedPreference(context)
    private val userId=sharedPreference.getValueString("userId")

    fun observeAllProfiles(): LiveData<List<PeopleProfile>> =
        profileDao.observeAll().map { list -> list.map { it.toDomain().toDomainData() } }

    fun observeProfile(id: String): PeopleProfile =
        profileDao.observeProfile(id).toDomain().toDomainData()

    fun observeMyProfile(): PeopleProfile =
        profileDao.observeProfile(userId ?: "").toDomain().toDomainData()

    suspend fun addNew(profileEntity: List<ProfileEntity>) {
        profileDao.addProfiles(profileEntity)
    }

    fun getProfile(id: String): LiveData<ProfileEntity> {
        return profileDao.getUser(id)
    }
    suspend fun addNewUser(profileEntity: ProfileEntity) {
        profileDao.addProfile(profileEntity)
    }
    suspend fun checkProfile():Boolean{
        return profileDao.checkUser(userId?: "")
    }
    suspend fun updateProfile(profileEntity: ProfileEntity){
        profileDao.updateProfile(profileEntity)
    }
}

