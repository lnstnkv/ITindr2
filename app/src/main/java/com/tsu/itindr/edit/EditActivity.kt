package com.tsu.itindr.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.tsu.itindr.*
import com.tsu.itindr.databinding.ActivityEditBinding
import com.tsu.itindr.databinding.ActivityRegistrationBinding

class EditActivity : AppCompatActivity() {
    private lateinit var viewbinding: ActivityEditBinding
    private var controller = ProfileController()
    val controllerTopic = TopicController()
    val updateController = UserController()
    val chips: MutableList<String> = mutableListOf()
    val chooseChip: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreference = SharedPreference(this)
        viewbinding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        viewbinding.imageViewEdit.clipToOutline=true
        viewbinding.materialToolbar.setOnClickListener { finish() }
        controller.profile(
            "Bearer " + sharedPreference.getValueString("accessToken"),
            onSuccess = {
                for (j in it.topics) {
                    chooseChip(j.title)
                }
                viewbinding.textViewAboutEdit.text = it.aboutMyself
                viewbinding.editTextEditName.setText(it.name)
                Glide
                    .with(this)
                    .load(it.avatar)
                    .into(viewbinding.imageViewEdit);

            },
            onFailure = {

            }
        )
        controllerTopic.topic(
            "Bearer " + sharedPreference.getValueString("accessToken"),
            onSuccess = {

                for (i in it) {
                    addChip(i)
                }


            },
            onFailure = {
                Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
            }

        )
        viewbinding.buttonSavEdit.setOnClickListener {
            updateController.update(
                "Bearer " + sharedPreference.getValueString("accessToken"),
                UpdateParams(
                    viewbinding.editTextEditName.text.toString(),
                    viewbinding.textViewAboutEdit.text.toString(),
                    chips.toList()
                ),
                onSuccess = {
                    Toast.makeText(this, R.string.item_save, Toast.LENGTH_LONG).show()
                },
                onFailure = {

                }
            )

        }
    }

    private fun addChip(it: TopicResponse) {
        val text = it.title
        val id = it.id
        val chipWhite = LayoutInflater.from(this).inflate(R.layout.item_chip, null) as Chip
        chipWhite.text = text
        viewbinding.chipGroup.addView(chipWhite)
        chipWhite.setOnClickListener {
            if (chipWhite.isChecked) {
                chips.add(id)
            } else {
                chips.remove(id)
            }
        }
    }

    private fun chooseChip(text: String) {

        val chip = LayoutInflater.from(this).inflate(R.layout.item_chip_pink, null) as Chip
        chip.text = text
        viewbinding.chipGroup.addView(chip)
    }

}

