package com.tsu.itindr.find.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.tsu.itindr.data.profile.ProfileController
import com.tsu.itindr.R
import com.tsu.itindr.databinding.FragmentProfileBinding
import com.tsu.itindr.edit.EditActivity


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    companion object {
        val TAG = ProfileFragment::class.java.simpleName
        fun newInstance() = ProfileFragment()
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    private lateinit var binding: FragmentProfileBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)
        binding.buttonEdit.setOnClickListener {
            val intent = Intent(activity, EditActivity::class.java)
            startActivity(intent)
        }
        initView()
        viewModel.getProfile()
        addData()
    }

    private fun addData() {
        binding.imageViewAvatarProfile.clipToOutline = true
        viewModel.isUser.observe(viewLifecycleOwner) {
            if (it != null) {
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
            }
        }
    }

    private fun initView() = with(binding) {
        viewModel.isErrorUser.observe(viewLifecycleOwner) { isError ->
            if (isError == true) {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show()
            }

        }
    }


    private fun addChip(text: String) {

        val chip = LayoutInflater.from(activity).inflate(R.layout.item_chip_pink, null) as Chip
        chip.text = text
        binding.chipGroupProfile.addView(chip)
    }
}
