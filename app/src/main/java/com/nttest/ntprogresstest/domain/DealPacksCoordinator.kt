package com.nttest.ntprogresstest.domain

import com.nttest.ntprogresstest.data.Server

class DealPacksCoordinator {

    private val packsList = mutableListOf<List<Server.Deal>>()

    fun addPack(pack: List<Server.Deal>) {
        packsList.add(pack)
    }

    fun getPacksListSize() = packsList.size

    fun getPacks(count: Int): List<Server.Deal> {
        val startPacks = mutableListOf<Server.Deal>()
        for (pack in 0 until count) {
            startPacks += getPack(pack)
        }
        return startPacks
    }

    private fun getPack(index: Int): List<Server.Deal> {
        return packsList[index]
    }

}