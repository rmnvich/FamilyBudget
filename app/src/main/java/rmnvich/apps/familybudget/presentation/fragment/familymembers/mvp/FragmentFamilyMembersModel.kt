package rmnvich.apps.familybudget.presentation.fragment.familymembers.mvp

import io.reactivex.Flowable
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl

class FragmentFamilyMembersModel(private val databaseRepositoryImpl: DatabaseRepositoryImpl) :
        FragmentFamilyMembersContract.Model {

    override fun getAllUsers(): Flowable<List<User>> {
        return databaseRepositoryImpl.getAllUsers()
    }
}