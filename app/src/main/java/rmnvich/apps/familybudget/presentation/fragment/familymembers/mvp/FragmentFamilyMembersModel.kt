package rmnvich.apps.familybudget.presentation.fragment.familymembers.mvp

import io.reactivex.Flowable
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository

class FragmentFamilyMembersModel(private val databaseRepository: IDatabaseRepository) :
        FragmentFamilyMembersContract.Model {

    override fun getAllUsers(): Flowable<List<User>> {
        return databaseRepository.getAllUsers()
    }
}