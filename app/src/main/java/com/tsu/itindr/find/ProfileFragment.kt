package com.tsu.itindr.find

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.tsu.itindr.request.profile.ProfileController
import com.tsu.itindr.R
import com.tsu.itindr.request.SharedPreference
import com.tsu.itindr.databinding.FragmentProfileBinding
import com.tsu.itindr.edit.EditActivity


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    companion object {
        val TAG = ProfileFragment::class.java.simpleName
        fun newInstance() = ProfileFragment()
    }

    private val controller = ProfileController()
    private lateinit var binding: FragmentProfileBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        binding.buttonEdit.setOnClickListener {
            val intent = Intent(activity, EditActivity::class.java)
            binding.buttonEdit.setOnClickListener { startActivity(intent) }
        }
        val sharedPreference = SharedPreference(activity as FindActivity)

        binding.imageViewAvatarProfile.clipToOutline = true
        controller.profile(
            "Bearer " + sharedPreference.getValueString("accessToken"),
            onSuccess = {
                for (j in it.topics) {
                    addChip(j.title)
                }
                binding.textViewNameProfile.text = it.name
                binding.textViewAboutMySelf.text = it.aboutMyself
                if (it.avatar != null) {
                    Glide
                        .with(this)
                        .load(it.avatar)
                        .into(binding.imageViewAvatarProfile);
                }
            },
            onFailure = {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show()
            }
        )

    }


    private fun addChip(text: String) {

        val chip = LayoutInflater.from(activity).inflate(R.layout.item_chip_pink, null) as Chip
        chip.text = text
        binding.chipGroupProfile.addView(chip)
    }
}
