package ru.virtual.feature_recipes.presentation.adapters

import ru.virtual.core_android.ui.BaseAdapter
import ru.virtual.feature_recipes.databinding.ItemIngredientBinding
import ru.virtual.feature_recipes.domain.entities.RecipeProduct

class RecipeProductAdapter: BaseAdapter<RecipeProduct, ItemIngredientBinding>(ItemIngredientBinding::inflate) {

    override fun bindView(binding: ItemIngredientBinding, item: RecipeProduct, position: Int) {
        with(binding) {
            ingredientName.text = item.productName
            ingredientAmount.text = item.amount
            indicator.isSelected = item.isInFridge
        }
    }
}