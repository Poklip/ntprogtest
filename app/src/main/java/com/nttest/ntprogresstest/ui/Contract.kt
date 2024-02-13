package com.nttest.ntprogresstest.ui

import com.nttest.ntprogresstest.base.MyEvent
import com.nttest.ntprogresstest.data.Server

data class ViewState(
    val deals: List<Server.Deal>
)

sealed class UiEvent : MyEvent {
    object OnDateClicked : MyEvent
    object OnInstrumentClicked : MyEvent
    object OnPriceClicked : MyEvent
    object OnAmountClicked : MyEvent
    object OnSideClicked : MyEvent
}

sealed class DataEvent : MyEvent {
    object InitLoadDeals : DataEvent()
    data class LoadPacks(val deals: List<Server.Deal>) : DataEvent()
}