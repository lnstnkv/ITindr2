package com.tsu.itindr.find

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.Fragment
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ActivityFindBinding

class FindActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityFindBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFindBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        if(savedInstanceState==null){
            selectScreen(SearchFragment.TAG,SearchFragment.newInstance())
        }
        viewBinding.bottomNavigation.setOnItemSelectedListener {
            revealFragment(it.itemId)
        }
    }
    private fun revealFragment(itemId :Int):Boolean{

        when(itemId){
            R.id.navigation_search -> {
                selectScreen(SearchFragment.TAG,SearchFragment.newInstance())
                return true
            }
            R.id.navigation_chat -> {
                selectScreen(ChatFragment.TAG,ChatFragment.newInstance())
                return true
            }
            R.id.navigation_people -> {
                selectScreen(PeopleFragment.TAG,PeopleFragment.newInstance())
                return true
            }
            R.id.navigation_profile -> {
                selectScreen(ProfileFragment.TAG,ProfileFragment.newInstance())
                return true
            }
            else -> return false
        }

    }


    private fun selectScreen(tag:String, fragment: Fragment){
        supportFragmentManager.commit {
            val active= findActiveFragment()
            val target = supportFragmentManager.findFragmentByTag(tag)

            if(active != null && target != null && active== target) return@commit

            if (active!= null){
                hide(active)
            }
            if (target == null){
                add (R.id.fragmentContainerView, fragment, tag)
            } else {
                show(target)
            }
        }
    }
    private  fun findActiveFragment()= supportFragmentManager.fragments.find { it.isVisible }
}
