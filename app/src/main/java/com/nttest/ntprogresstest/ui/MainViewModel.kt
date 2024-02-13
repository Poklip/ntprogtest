package com.nttest.ntprogresstest.ui

import android.util.Log
import com.nttest.ntprogresstest.base.BaseViewModel
import com.nttest.ntprogresstest.base.MyEvent
import com.nttest.ntprogresstest.data.Server
import com.nttest.ntprogresstest.domain.DealPacksCoordinator
import com.nttest.ntprogresstest.domain.SortDirection

class MainViewModel : BaseViewModel<ViewState>() {

    private val dealsCoordinator = DealPacksCoordinator()
    private val server = Server()
    private var dateSortBy = true
    private var instrumentSortBy = true
    private var priceSortBy = true
    private var amountSortBy = true
    private var sideSortBy = true
    private var sortOwner = SortDirection.TimeDown

    init {
        processDataEvent(DataEvent.InitLoadDeals)
    }

    override fun initialViewState() = ViewState(dealsShown = emptyList(), deals = emptyList())

    override fun reduce(event: MyEvent, previousState: ViewState): ViewState? {
        when (event) {
            is DataEvent.InitLoadDeals -> {
                server.subscribeToDeals {
                    dealsCoordinator.addPack(it)
                    if (dealsCoordinator.getPacksListSize() == 1 || dealsCoordinator.getPacksListSize() % 100 == 0 && dealsCoordinator.getPacksListSize() != 0) {
                        processDataEvent(DataEvent.LoadPacks(deals = dealsCoordinator.getPacks(dealsCoordinator.getPacksListSize())))
                    }
                    Log.e("MAIN_DEBUG", "${dealsCoordinator.getPacksListSize()}")
                }
                return null
            }

            is DataEvent.LoadPacks -> {
                return when (sortOwner) {
                    SortDirection.TimeUp -> previousState.copy(dealsShown = event.deals.sortedBy { it.timeStamp })
                    SortDirection.TimeDown -> previousState.copy(dealsShown = event.deals.sortedByDescending { it.timeStamp })
                    SortDirection.InstrumentUp -> previousState.copy(dealsShown = event.deals.sortedBy { it.instrumentName })
                    SortDirection.InstrumentDown -> previousState.copy(dealsShown = event.deals.sortedByDescending { it.instrumentName })
                    SortDirection.PriceUp -> previousState.copy(dealsShown = event.deals.sortedBy { it.price })
                    SortDirection.PriceDown -> previousState.copy(dealsShown = event.deals.sortedByDescending { it.price })
                    SortDirection.AmountUp -> previousState.copy(dealsShown = event.deals.sortedBy { it.amount })
                    SortDirection.AmountDown -> previousState.copy(dealsShown = event.deals.sortedByDescending { it.amount })
                    SortDirection.SideUp -> previousState.copy(dealsShown = event.deals.sortedBy { it.side })
                    SortDirection.SideDown -> previousState.copy(dealsShown = event.deals.sortedByDescending { it.side })
                }
            }

            is UiEvent.OnDateClicked -> {
                return if (dateSortBy) {
                    dateSortBy = !this.dateSortBy
                    sortOwner = SortDirection.TimeDown
                    previousState.copy(dealsShown = previousState.dealsShown.sortedByDescending { it.timeStamp })
                } else {
                    dateSortBy = !this.dateSortBy
                    sortOwner = SortDirection.TimeUp
                    previousState.copy(dealsShown = previousState.dealsShown.sortedBy { it.timeStamp })
                }
            }

            is UiEvent.OnInstrumentClicked -> {
                return if (instrumentSortBy) {
                    instrumentSortBy = !this.instrumentSortBy
                    sortOwner = SortDirection.InstrumentDown
                    previousState.copy(dealsShown = previousState.dealsShown.sortedByDescending { it.instrumentName })
                } else {
                    instrumentSortBy = !this.instrumentSortBy
                    sortOwner = SortDirection.InstrumentUp
                    previousState.copy(dealsShown = previousState.dealsShown.sortedBy { it.instrumentName })
                }
            }

            is UiEvent.OnPriceClicked -> {
                return if (priceSortBy) {
                    priceSortBy = !this.priceSortBy
                    sortOwner = SortDirection.PriceDown
                    previousState.copy(dealsShown = previousState.dealsShown.sortedByDescending { it.price })
                } else {
                    priceSortBy = !this.priceSortBy
                    sortOwner = SortDirection.PriceUp
                    previousState.copy(dealsShown = previousState.dealsShown.sortedBy { it.price })
                }
            }

            is UiEvent.OnAmountClicked -> {
                return if (amountSortBy) {
                    amountSortBy = !this.amountSortBy
                    sortOwner = SortDirection.AmountDown
                    previousState.copy(dealsShown = previousState.dealsShown.sortedByDescending { it.amount })
                } else {
                    amountSortBy = !this.amountSortBy
                    sortOwner = SortDirection.AmountUp
                    previousState.copy(dealsShown = previousState.dealsShown.sortedBy { it.amount })
                }
            }

            is UiEvent.OnSideClicked -> {
                return if (sideSortBy) {
                    sideSortBy = !this.sideSortBy
                    sortOwner = SortDirection.PriceDown
                    previousState.copy(dealsShown = previousState.dealsShown.sortedByDescending { it.side })
                } else {
                    sideSortBy = !this.sideSortBy
                    sortOwner = SortDirection.PriceUp
                    previousState.copy(dealsShown = previousState.dealsShown.sortedBy { it.side })
                }
            }

            else -> return null
        }
    }
}