package rmnvich.apps.familybudget.presentation.fragment.plannedexpenses.mvp

import io.reactivex.Completable
import io.reactivex.Flowable
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.data.common.helper.DateHelper
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository
import java.math.BigDecimal
import java.math.BigDecimal.ROUND_DOWN

class FragmentPlannedExpensesModel(private val databaseRepository: IDatabaseRepository) :
        FragmentPlannedExpensesContract.Model {

    override fun getAllPlannedExpenses(): Flowable<List<Expense>> {
        return databaseRepository.getAllPlannedExpenses()
    }

    override fun updateExpense(expense: Expense): Completable {
        expense.isPlannedExpense = false
        expense.timestamp = DateHelper.getCurrentTimeInMills()
        return databaseRepository.insertExpense(expense)
                .andThen(databaseRepository.getBalance())
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

                    databaseRepository.insertBalance(it)
                }
    }
}