package rmnvich.apps.familybudget.data.repository.preferences

import android.content.SharedPreferences
import rmnvich.apps.familybudget.data.common.Constants.EXTRA_USER_ID
import rmnvich.apps.familybudget.domain.interactor.preferences.IPreferenceRepository

class PreferencesRepositoryImpl(private val preferences: SharedPreferences) :
        IPreferenceRepository {

    override fun saveUserIdToPreferences(userId: Int) {
        preferences.edit()
                .putInt(EXTRA_USER_ID, userId)
                .apply()
    }

    override fun getUserId(): Int {
        return preferences.getInt(EXTRA_USER_ID, -1)
    }

    fun deleteUserIdFromPreferences() {
        preferences.edit()
                .putInt(EXTRA_USER_ID, -1)
                .apply()
    }
}