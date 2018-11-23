package rmnvich.apps.familybudget.presentation.fragment.totalbalance.mvp

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import rmnvich.apps.familybudget.data.repository.local.FileRepositoryImpl
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository
import java.util.*

class FragmentTransactionsModel(private val databaseRepository: IDatabaseRepository,
                                private val fileRepository: FileRepositoryImpl) :
        FragmentTransactionsContract.Model {

    override fun saveTransactionsToExcel(timeRangeStart: Long, timeRangeEnd: Long): Completable {
        return if (timeRangeStart == 0L && timeRangeEnd == 0L) {
            getAllTransactions()
                    .flatMapCompletable {
                        Completable.fromAction {
                            fileRepository.saveDataToExcelFile(it)
                        }
                    }
        } else getSortedTransactions(timeRangeStart, timeRangeEnd)
                .flatMapCompletable {
                    Completable.fromAction {
                        fileRepository.saveDataToExcelFile(it)
                    }
                }
    }

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