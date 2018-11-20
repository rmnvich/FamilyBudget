package rmnvich.apps.familybudget.presentation.fragment.actualexpenses.mvp

import io.reactivex.Flowable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Balance
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl

class FragmentActualExpensesModel(private val databaseRepositoryImpl: DatabaseRepositoryImpl) :
        FragmentActualExpensesContract.Model {

    override fun getBalance(): Single<Balance> {
        return databaseRepositoryImpl.getBalance()
    }

    override fun getAllActualExpenses(): Flowable<List<Expense>> {
        return databaseRepositoryImpl.getAllActualExpenses()
    }
}