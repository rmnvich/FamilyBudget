package rmnvich.apps.familybudget.presentation.activity.make.expense.mvp

import io.reactivex.Completable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.domain.helper.DateHelper
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository
import rmnvich.apps.familybudget.domain.interactor.preferences.IPreferencesRepository
import java.math.BigDecimal
import java.math.BigDecimal.ROUND_DOWN

class MakeExpenseActivityModel(private val databaseRepository: IDatabaseRepository,
                               private val preferencesRepository: IPreferencesRepository) :
        MakeExpenseActivityContract.Model {

    override fun getExpenseById(id: Int): Single<Expense> {
        return databaseRepository.getExpenseById(id)
    }

    override fun getCategoryById(id: Int): Single<Category> {
        return databaseRepository.getCategoryById(id)
    }

    override fun insertExpense(expense: Expense, oldValue: String): Completable {
        val oldIsPlannedExpense = expense.isPlannedExpense
        if (expense.timestamp == 0L) {
            expense.timestamp = DateHelper.getCurrentTimeInMills()
        } else if (DateHelper.isDateMoreThatToday(expense.timestamp)) {
            expense.isPlannedExpense = true
        }

        return databaseRepository.getUserById(preferencesRepository.getUserId())
                .flatMapCompletable {
                    if (expense.userName.isEmpty() && expense.userRelationship.isEmpty()) {
                        expense.userName = "${it.name} ${it.lastname}"
                        expense.userRelationship = it.relationship
                    }
                    expense.value = BigDecimal(expense.value)
                            .setScale(2, ROUND_DOWN).toString()

                    databaseRepository.insertExpense(expense)
                }.andThen(databaseRepository.getBalance())
                .flatMapCompletable {
                    val newValue = BigDecimal(expense.value)

                    //If not transferring from planned to actual else if transferring
                    when (oldIsPlannedExpense) {
                        expense.isPlannedExpense ->
                            when {
                                expense.isPlannedExpense -> {
                                    val totalPlannedExpense = BigDecimal(it.totalPlannedExpenses)
                                    //If adding expense, else if update
                                    when (oldValue) {
                                        "0" -> it.totalPlannedExpenses = totalPlannedExpense.add(newValue)
                                                .setScale(2, ROUND_DOWN).toString()
                                        else -> it.totalPlannedExpenses = newValue.subtract(BigDecimal(oldValue))
                                                .add(totalPlannedExpense).setScale(2, ROUND_DOWN).toString()
                                    }
                                }
                                else -> {
                                    val totalActualExpense = BigDecimal(it.totalActualExpenses)
                                    when (oldValue) {
                                        "0" -> {
                                            it.balance = BigDecimal(it.balance).subtract(newValue)
                                                    .setScale(2, ROUND_DOWN).toString()
                                            it.totalActualExpenses = totalActualExpense.add(newValue)
                                                    .setScale(2, ROUND_DOWN).toString()
                                        }
                                        else -> {
                                            it.totalActualExpenses = newValue.subtract(BigDecimal(oldValue))
                                                    .add(totalActualExpense).setScale(2, ROUND_DOWN).toString()
                                            it.balance = BigDecimal(oldValue).subtract(newValue)
                                                    .add(BigDecimal(it.balance)).setScale(2, ROUND_DOWN).toString()
                                        }
                                    }
                                }
                            }
                        else -> {
                            it.balance = BigDecimal(it.balance).add(BigDecimal(oldValue))
                                    .setScale(2, ROUND_DOWN).toString()
                            it.totalPlannedExpenses = BigDecimal(it.totalPlannedExpenses)
                                    .add(newValue).setScale(2, ROUND_DOWN).toString()
                            it.totalActualExpenses = BigDecimal(it.totalActualExpenses)
                                    .subtract(BigDecimal(oldValue))
                                    .setScale(2, ROUND_DOWN).toString()
                        }
                    }
                    databaseRepository.insertBalance(it)
                }
    }

    override fun deleteExpense(id: Int): Completable {
        var value = BigDecimal(0)
        var isPlannedExpense = false

        return databaseRepository.getExpenseById(id)
                .flatMapCompletable {
                    value = BigDecimal(it.value)
                    isPlannedExpense = it.isPlannedExpense
                    databaseRepository.deleteExpense(it)
                }.andThen(databaseRepository.getBalance())
                .flatMapCompletable {
                    if (isPlannedExpense) {
                        it.totalPlannedExpenses = BigDecimal(it.totalPlannedExpenses)
                                .subtract(value).setScale(2, ROUND_DOWN).toString()
                    } else {
                        it.totalActualExpenses = BigDecimal(it.totalActualExpenses)
                                .subtract(value).setScale(2, ROUND_DOWN).toString()
                        it.balance = BigDecimal(it.balance).add(value)
                                .setScale(2, ROUND_DOWN).toString()
                    }
                    databaseRepository.insertBalance(it)
                }
    }
}