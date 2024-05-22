package ru.virtual.feature_product_list.domain

object PercentUtil {

    fun getPercents(part: Int, amount: Int) = if(amount == 0) 0.0 else (part.toDouble() / amount.toDouble() * 100)

}