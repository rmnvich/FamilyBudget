package rmnvich.apps.familybudget.presentation.fragment.totalbalance.mvp

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository
import java.util.*

class FragmentTransactionsModel(private val databaseRepository: IDatabaseRepository) :
        FragmentTransactionsContract.Model {

    override fun getAllTransactions(): Flowable<List<Any>> {
        return Flowable.zip(databaseRepository.getAllIncomes(),
                databaseRepository.getAllActualExpenses(), BiFunction { incomes, expenses ->
            val list = LinkedList<Any>()
            list.addAll(incomes)
            list.addAll(expenses)
            list
        })
    }

    override fun getSortedTransactions(timeRangeStart: Long, timeRangeEnd: Long): Flowable<List<Any>> {
        return Flowable.zip(databaseRepository.getSortedIncomes(timeRangeStart, timeRangeEnd),
                databaseRepository.getSortedActualExpenses(timeRangeStart, timeRangeEnd),
                BiFunction { incomes, expenses ->
                    val list = LinkedList<Any>()
                    list.addAll(incomes)
                    list.addAll(expenses)
                    list
                })
    }
}