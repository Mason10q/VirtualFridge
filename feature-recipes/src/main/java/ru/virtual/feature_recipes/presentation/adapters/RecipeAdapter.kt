package ru.virtual.feature_recipes.presentation.adapters

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.squareup.picasso.BuildConfig
import com.squareup.picasso.Picasso
import ru.virtual.core_android.ui.BasePagingAdapter
import ru.virtual.core_res.R as resR
import ru.virtual.feature_recipes.databinding.ItemRecipeBinding
import ru.virtual.feature_recipes.domain.entities.Recipe
import javax.inject.Inject

class RecipeAdapter @Inject constructor(private val picasso: Picasso)
    : BasePagingAdapter<Recipe, ItemRecipeBinding>(DIFF_CALLBACK, ItemRecipeBinding::inflate) {


    private var onItemClick: ((Int) -> Unit)? = null

    override fun bindView(binding: ItemRecipeBinding, item: Recipe, position: Int) {
        with(binding) {
            recipeName.text = item.name
            recipeTime.text = item.cookingTime
            recipeCalories.text = item.calories.toString()
            recipeCategory.text = item.difficulty
            picasso.load(item.imageUrl)
                .placeholder(resR.drawable.ic_broken)
                .error(resR.drawable.ic_broken)
                .centerCrop()
                .fit()
                .into(recipeImage)
        }
    }

    override fun onClick(view: View, item: Recipe, position: Int) {
        onItemClick?.invoke(item.id)
    }

    fun setOnItemClick(listener: (Int) -> Unit) {
        onItemClick = listener
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem == newItem
        }
    }
}