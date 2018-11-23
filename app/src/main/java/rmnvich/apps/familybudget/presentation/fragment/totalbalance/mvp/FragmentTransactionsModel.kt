package rmnvich.apps.familybudget.presentation.fragment.totalbalance.mvp

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository
import rmnvich.apps.familybudget.domain.interactor.local.IFileRepository
import java.io.File
import java.util.*
import java.util.concurrent.Callable

class FragmentTransactionsModel(private val databaseRepository: IDatabaseRepository,
                                private val fileRepository: IFileRepository) :
        FragmentTransactionsContract.Model {

    override fun saveTransactionsToExcel(timeRangeStart: Long, timeRangeEnd: Long): Observable<File> {
        return if (timeRangeStart == 0L && timeRangeEnd == 0L) {
            getAllTransactions()
                    .toObservable()
                    .flatMap {
                        Observable.fromCallable(CallableExcelFileAction(it))
                    }.observeOn(AndroidSchedulers.mainThread())
        } else getSortedTransactions(timeRangeStart, timeRangeEnd)
                .toObservable()
                .flatMap {
                    Observable.fromCallable(CallableExcelFileAction(it))
                }.observeOn(AndroidSchedulers.mainThread())
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

    inner class CallableExcelFileAction(private var data: List<Any>) : Callable<File> {
        override fun call(): File {
            return fileRepository.saveDataToExcelFile(data)!!
        }
    }
}