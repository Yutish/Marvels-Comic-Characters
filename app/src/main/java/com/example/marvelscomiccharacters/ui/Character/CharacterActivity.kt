package com.example.marvelscomiccharacters.ui.Character

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelscomiccharacters.databinding.ActivityCharacterBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *
 * Presents the detailed view of characters
 */

@AndroidEntryPoint
class CharacterActivity : AppCompatActivity() {
    private val viewModelCharacter: CharacterViewModel by viewModels()
    private var id: Int = 0
    private lateinit var binding: ActivityCharacterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent != null) {
            id = intent.getIntExtra("id", 0)
            viewModelCharacter.getCharacterByIdValue(id.toString())

            CoroutineScope(Dispatchers.Main).launch {
                viewModelCharacter.characterValue.collect {
                    when {
                        it.isLoading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        it.error.isNotBlank() -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@CharacterActivity,
                                "Unexpected Error",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        it.characterDetail.isNotEmpty() -> {
                            binding.progressBar.visibility = View.GONE
                            it.characterDetail.map { character ->
                                val url =
                                    "${
                                        character.thumbnail.replace(
                                            "http",
                                            "https"
                                        )
                                    }/portrait_xlarge.${character.thumbnailExt}"
                                Picasso.get().load(url).into(binding.characterImageView)
                                binding.characterNameTextView.text = character.name
                                binding.characterDescriptionTextView.text = character.description
                                Log.d("description", character.description)
                                binding.characterComicTextView.text = character.comics.toString()
                            }
                        }

                    }
                }
            }

        }
    }
}