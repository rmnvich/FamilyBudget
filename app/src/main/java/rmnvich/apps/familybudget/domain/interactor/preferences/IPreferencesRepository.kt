package rmnvich.apps.familybudget.domain.interactor.preferences

interface IPreferencesRepository {

    fun saveUserIdToPreferences(userId: Int)

    fun getUserId(): Int

    fun deleteUserIdFromPreferences()
}