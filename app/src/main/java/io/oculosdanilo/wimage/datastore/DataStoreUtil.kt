package io.oculosdanilo.wimage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreUtil(private val context: Context) {
  companion object {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    
    val MODO_ESCURO = booleanPreferencesKey("modoescuro")
    val MATERIAL_YOU_CUSTOM = booleanPreferencesKey("my")
  }
  
  /* tema escuro */
  fun pegarTema(isSystemDark: Boolean): Flow<Boolean> {
    return context.dataStore.data.map { pref ->
      pref[MODO_ESCURO] ?: isSystemDark
    }
  }
  
  suspend fun setarTema(novoTema: Boolean) {
    context.dataStore.edit { pref ->
      pref[MODO_ESCURO] = novoTema
    }
  }
  
  /* material you cor do dispositivo */
  fun pegarMY(): Flow<Boolean> {
    return context.dataStore.data.map { pref ->
      pref[MATERIAL_YOU_CUSTOM] ?: false
    }
  }
  
  suspend fun setarMY(novoMY: Boolean) {
    context.dataStore.edit { pref ->
      pref[MATERIAL_YOU_CUSTOM] = novoMY
    }
  }
  
  /* apenas testezinho besta */
  suspend fun init() {
    context.dataStore.edit { pref ->
      pref[stringPreferencesKey("testezinhopo")] = "atasim"
    }
  }
}