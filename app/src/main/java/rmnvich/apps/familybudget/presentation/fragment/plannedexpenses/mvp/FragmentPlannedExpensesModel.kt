package rmnvich.apps.familybudget.presentation.fragment.plannedexpenses.mvp

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Balance
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.domain.helper.DateHelper
import java.math.BigDecimal
import java.math.BigDecimal.ROUND_DOWN

class FragmentPlannedExpensesModel(private val databaseRepositoryImpl: DatabaseRepositoryImpl) :
        FragmentPlannedExpensesContract.Model {

    override fun getAllPlannedExpenses(): Flowable<List<Expense>> {
        return databaseRepositoryImpl.getAllPlannedExpenses()
    }

    override fun updateExpense(expense: Expense): Completable {
        expense.isPlannedExpense = false
        expense.timestamp = DateHelper.getCurrentTimeInMills()
        return databaseRepositoryImpl.insertExpense(expense)
                .andThen(databaseRepositoryImpl.getBalance())
                .flatMapCompletable {
                    it.totalPlannedExpenses = BigDecimal(it.totalPlannedExpenses)
                            .subtract(BigDecimal(expense.value))
                            .setScale(2, ROUND_DOWN).toString()
                    it.balance = BigDecimal(it.balance)
                            .subtract(BigDecimal(expense.value))
                            .setScale(2, ROUND_DOWN).toString()
                    it.totalActualExpenses = BigDecimal(it.totalActualExpenses)
                            .add(BigDecimal(expense.value))
                            .setScale(2, ROUND_DOWN).toString()

                    databaseRepositoryImpl.insertBalance(it)
                }
    }
}