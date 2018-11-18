package rmnvich.apps.familybudget.data.repository.preference

interface IPreferenceRepository {

    fun saveUserIdToPreferences(userId: Int)

    fun getUserId(): Int
}