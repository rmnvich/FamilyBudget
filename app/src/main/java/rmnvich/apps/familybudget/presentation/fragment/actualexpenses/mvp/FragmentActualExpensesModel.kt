package rmnvich.apps.familybudget.presentation.fragment.actualexpenses.mvp

import io.reactivex.Flowable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Balance
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository

class FragmentActualExpensesModel(private val databaseRepository: IDatabaseRepository) :
        FragmentActualExpensesContract.Model {

    override fun getBalance(): Single<Balance> {
        return databaseRepository.getBalance()
    }

    override fun getAllActualExpenses(): Flowable<List<Expense>> {
        return databaseRepository.getAllActualExpenses()
    }
}