package rmnvich.apps.familybudget.presentation.fragment.plannedexpenses.mvp

import io.reactivex.Completable
import io.reactivex.Flowable
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.domain.helper.DateHelper

class FragmentPlannedExpensesModel(private val databaseRepositoryImpl: DatabaseRepositoryImpl) :
        FragmentPlannedExpensesContract.Model {

    override fun getAllPlannedExpenses(): Flowable<List<Expense>> {
        return databaseRepositoryImpl.getAllPlannedExpenses()
    }

    override fun updateExpense(id: Int): Completable {
        return databaseRepositoryImpl.getExpenseById(id)
                .flatMapCompletable {
                    it.isPlannedExpense = false
                    it.timestamp = DateHelper.getCurrentTimeInMills()
                    databaseRepositoryImpl.insertExpense(it)
                }
    }
}