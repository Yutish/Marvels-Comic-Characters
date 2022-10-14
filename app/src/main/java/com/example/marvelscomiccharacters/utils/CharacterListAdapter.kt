package com.example.marvelscomiccharacters.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelscomiccharacters.R
import com.example.marvelscomiccharacters.domain.model.CharacterModel
import com.example.marvelscomiccharacters.ui.Character.CharacterActivity
import java.util.*

/**
 * [CharacterListAdapter] sets the viewholder for MainActivity's recyclerview to hold data.
 */
class CharacterListAdapter(private val context: Context, var itemList: ArrayList<CharacterModel>) :
    RecyclerView.Adapter<
            CharacterListAdapter.CharacterListViewHolder>() {
    inner class CharacterListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val characterName: TextView = view.findViewById(R.id.txt_character_name)
        val thumbnail: ImageView = view.findViewById(R.id.img_character_image)
        val cardCharacter: LinearLayout = view.findViewById(R.id.characters_linear_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_grid, parent, false)
        return CharacterListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        val list = itemList[position]
        holder.characterName.text = list.name
        val imageUrl =
            "${list.thumbnail.replace("http", "https")}/landscape_medium.${list.thumbnailExt}"

        Glide.with(context).load(imageUrl).into(holder.thumbnail)
        holder.cardCharacter.setOnClickListener {
            val intent = Intent(context, CharacterActivity::class.java)
            intent.putExtra("id", list.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(characterList: ArrayList<CharacterModel>) {
        this.itemList.addAll(characterList)
        notifyDataSetChanged()
    }
}