package rmnvich.apps.familybudget.domain.interactor.preferences

interface IPreferenceRepository {

    fun saveUserIdToPreferences(userId: Int)

    fun getUserId(): Int
}