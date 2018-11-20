package rmnvich.apps.familybudget.presentation.activity.make.expense.mvp

import io.reactivex.Completable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.preferences.PreferencesRepositoryImpl
import rmnvich.apps.familybudget.domain.helper.DateHelper

class MakeExpenseActivityModel(private val databaseRepositoryImpl: DatabaseRepositoryImpl,
                               private val preferencesRepositoryImpl: PreferencesRepositoryImpl) :
        MakeExpenseActivityContract.Model {

    override fun getExpenseById(id: Int): Single<Expense> {
        return databaseRepositoryImpl.getExpenseById(id)
    }

    override fun getCategoryById(id: Int): Single<Category> {
        return databaseRepositoryImpl.getCategoryById(id)
    }

    override fun insertExpense(expense: Expense, expenseId: Int): Completable {
        if (expense.timestamp == 0L) {
            expense.timestamp = DateHelper.getCurrentTimeInMills()
        } else if (DateHelper.isDateMoreThatToday(expense.timestamp)) {
            expense.isPlannedExpense = true
        }

        return if (expenseId == -1) {
            databaseRepositoryImpl.getUserById(preferencesRepositoryImpl.getUserId())
                    .flatMapCompletable {
                        expense.userName = "${it.name} ${it.lastname}"
                        expense.userRelationship = it.relationship
                        databaseRepositoryImpl.inserExpense(expense)
                    }
        } else databaseRepositoryImpl.inserExpense(expense)
    }

    override fun deleteExpense(id: Int): Completable {
        return databaseRepositoryImpl.getExpenseById(id)
                .flatMapCompletable { databaseRepositoryImpl.deleteExpense(it) }
    }
}