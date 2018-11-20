package rmnvich.apps.familybudget.presentation.activity.make.expense.mvp

import io.reactivex.Completable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl

class MakeExpenseActivityModel(private val databaseRepositoryImpl: DatabaseRepositoryImpl) :
        MakeExpenseActivityContract.Model {

    override fun getExpenseById(id: Int): Single<Expense> {
        return databaseRepositoryImpl.getExpenseById(id)
    }

    override fun insertExpense(expense: Expense): Completable {
        return databaseRepositoryImpl.inserExpense(expense)
    }

    override fun deleteExpense(id: Int): Completable {
        return databaseRepositoryImpl.getExpenseById(id)
                .flatMapCompletable { databaseRepositoryImpl.deleteExpense(it) }
    }
}