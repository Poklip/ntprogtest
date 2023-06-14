package com.nttest.ntprogresstest

import com.nttest.ntprogresstest.base.MyEvent

data class ViewState(
    val dealsShown: List<Server.Deal>,
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
    data class LoadDeals(val deals: List<Server.Deal>) : DataEvent()
}