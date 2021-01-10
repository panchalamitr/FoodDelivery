package com.demo.fooddelivery.viewmodel

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksState

abstract class MvRxViewModel<S : MavericksState>(initialState: S) : MavericksViewModel<S>(initialState)
