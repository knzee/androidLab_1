package io.pootis.androidlabtsu.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class GameViewModel @Inject constructor (private val repository : GameRepository) : ViewModel(){

    val games = MutableLiveData<MutableList<Game>>()

    fun fetchGame(){
        GlobalScope.launch(Dispatchers.Main) {

            try {
                games.postValue(repository.getGame())
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

}