package com.example.shkvarla.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.clover.sdk.v1.Intents
import com.clover.sdk.v3.inventory.InventoryConnector
import com.clover.sdk.v3.inventory.Modifier
import com.clover.sdk.v3.inventory.ModifierGroup
import com.clover.sdk.v3.order.OrderConnector
import com.example.shkvarla.database.MyDatabase
import com.example.shkvarla.database.model.UpdateItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*
import kotlin.random.Random

class OrderBroadcastReceiver : BroadcastReceiver(), KoinComponent {
    private val orderConnector: OrderConnector by inject()
    private val inventoryConnector: InventoryConnector by inject()
    private val myDatabase: MyDatabase by inject()

    override fun onReceive(context: Context, intent: Intent?) {
        intent ?: return

        when (intent.action) {
            Intents.ACTION_LINE_ITEM_ADDED -> {
                val orderId = intent.getStringExtra(Intents.EXTRA_CLOVER_ORDER_ID)!!
                val lineItemIds =
                    intent.getStringExtra(Intents.EXTRA_CLOVER_LINE_ITEM_ID)?.let { listOf(it) }
                        ?: intent.getStringArrayListExtra("com.clover.intent.extra.LINE_ITEM_IDS")
                        ?: listOf()
                if (lineItemIds.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        updateItemsPriceByOrderId(Random
                            .nextInt(5, 26), orderId, lineItemIds)
                    }
                }
            }
        }
    }

    private suspend fun updateItemsPriceByOrderId(
        percent: Int, orderId: String, lineItemsId: List<String>)
            = CoroutineScope(Job() + Dispatchers.Default).launch {
        val updateItems = mutableListOf<UpdateItem>()
        val lineItems = orderConnector.getOrder(orderId).lineItems
        lineItems
            .filter{lineItemsId.contains(it.id)}
            .distinctBy { it.taxRates[0].id }
            .forEach {
                val oldPrice = (it.price / 100).toDouble()
                val newPrice = oldPrice + (percent * oldPrice / 100)
                updateItems.add(
                    UpdateItem(oldPrice, newPrice, Date().time, orderId, it.id)
                )
                if (it.modifications == null) {
                    val priceForAddToLineItem = ((percent * it.price / 100) * lineItemsId.size)
                    changePrice(percent, priceForAddToLineItem, orderId, it.id)
                }
                it.price = (newPrice * 100).toLong()
            }
            .also {
                if (updateItems.isNotEmpty()) {
                    addUpdateItemsToDatabase(updateItems)
                }
            }
    }

    private suspend fun addUpdateItemsToDatabase(updateItems: List<UpdateItem>) {
        myDatabase
            .updateItemDao()
            .insert(updateItems)
    }

    private fun changePrice(
        percent: Int,
        price: Long,
        orderId: String,
        lineItemId: String) {
        val name ="Update price by $percent%"
        val modifierGroup = inventoryConnector.createModifierGroup(ModifierGroup()
            .setName(name))
        val modifier: Modifier = inventoryConnector.createModifier(modifierGroup.id,
            Modifier().setName(name).setPrice(price))
        orderConnector.addLineItemModification(orderId, lineItemId, modifier)
    }
}
