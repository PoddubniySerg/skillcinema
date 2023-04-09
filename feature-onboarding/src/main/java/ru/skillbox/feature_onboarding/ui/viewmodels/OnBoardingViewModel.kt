package ru.skillbox.feature_onboarding.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.skillbox.feature_onboarding.domain.usecases.CheckIfAppWasLaunchUseCase
import ru.skillbox.feature_onboarding.domain.usecases.SetOnBoardingLaunchedUseCase
import javax.inject.Inject

class OnBoardingViewModel @Inject constructor(
    private val checkIfAppWasLaunchUseCase: CheckIfAppWasLaunchUseCase,
    private val setOnBoardingLaunchedUseCase: SetOnBoardingLaunchedUseCase
) : ViewModel() {

    private val _exitFromOnBoardingFlow = Channel<Boolean>()
    val exitFromOnBoardingFlow = _exitFromOnBoardingFlow.receiveAsFlow()

    //    проверяем запускалось ли приложение раньше
    suspend fun isFirstLaunch(): Boolean {
        return try {
            checkIfAppWasLaunchUseCase.execute()
        } catch (ex: Exception) {
    //            Todo handle exception
            true
        }
    }

    //    переходим от презентации с загрузке приложения
    fun exit() {
        setOnBoardingLaunched()
        try {
            viewModelScope.launch { _exitFromOnBoardingFlow.send(true) }
        } catch (ex: Exception) {
//            Todo handle exception
        }
    }

    //    сохраняем в репозитории информацию о том, что приложение уже было запущено
    private fun setOnBoardingLaunched() {
        viewModelScope.launch {
            try {
                setOnBoardingLaunchedUseCase.execute()
            } catch (ex: Exception) {
//            Todo handle exception
            }
        }
    }
}