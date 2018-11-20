package rmnvich.apps.familybudget.presentation.activity.make.expense.mvp

import io.reactivex.Completable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.preferences.PreferencesRepositoryImpl
import rmnvich.apps.familybudget.domain.helper.DateHelper
import java.math.BigDecimal
import java.math.BigDecimal.ROUND_DOWN

class MakeExpenseActivityModel(private val databaseRepositoryImpl: DatabaseRepositoryImpl,
                               private val preferencesRepositoryImpl: PreferencesRepositoryImpl) :
        MakeExpenseActivityContract.Model {

    override fun getExpenseById(id: Int): Single<Expense> {
        return databaseRepositoryImpl.getExpenseById(id)
    }

    override fun getCategoryById(id: Int): Single<Category> {
        return databaseRepositoryImpl.getCategoryById(id)
    }

    override fun insertExpense(expense: Expense, oldValue: String): Completable {
        if (expense.timestamp == 0L) {
            expense.timestamp = DateHelper.getCurrentTimeInMills()
        } else if (DateHelper.isDateMoreThatToday(expense.timestamp)) {
            expense.isPlannedExpense = true
        }

        //TODO: fix this

        return databaseRepositoryImpl.getUserById(preferencesRepositoryImpl.getUserId())
                .flatMapCompletable {
                    expense.userName = "${it.name} ${it.lastname}"
                    expense.userRelationship = it.relationship
                    databaseRepositoryImpl.insertExpense(expense)
                }.andThen(databaseRepositoryImpl.getBalance())
                .flatMapCompletable {
                    val newValue = BigDecimal(expense.value)

                    if (expense.isPlannedExpense) {
                        val totalPlannedExpense = BigDecimal(it.totalPlannedExpenses)
                        if (oldValue == "0") {
                            it.totalPlannedExpenses = totalPlannedExpense.add(newValue)
                                    .setScale(2, ROUND_DOWN).toString()
                        } else it.totalPlannedExpenses = newValue.subtract(BigDecimal(oldValue))
                                .add(totalPlannedExpense).setScale(2, ROUND_DOWN).toString()

                    } else if (!expense.isPlannedExpense) {
                        val totalActualExpense = BigDecimal(it.totalActualExpenses)
                        if (oldValue == "0") {
                            val totalBalance = BigDecimal(it.balance)
                            it.balance = totalBalance.subtract(newValue)
                                    .setScale(2, ROUND_DOWN).toString()
                            it.totalActualExpenses = totalActualExpense.add(newValue)
                                    .setScale(2, ROUND_DOWN).toString()
                        } else {
                            it.totalActualExpenses = newValue.subtract(BigDecimal(oldValue))
                                    .add(totalActualExpense).setScale(2, ROUND_DOWN).toString()
                            it.balance = BigDecimal(oldValue).subtract(newValue)
                                    .add(BigDecimal(it.balance)).setScale(2, ROUND_DOWN).toString()
                        }
                    }
                    databaseRepositoryImpl.insertBalance(it)
                }
    }

    override fun deleteExpense(id: Int): Completable {
        return databaseRepositoryImpl.getExpenseById(id)
                .flatMapCompletable { databaseRepositoryImpl.deleteExpense(it) }
    }
}