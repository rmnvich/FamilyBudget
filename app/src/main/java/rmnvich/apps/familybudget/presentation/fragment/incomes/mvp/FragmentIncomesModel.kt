package rmnvich.apps.familybudget.presentation.fragment.incomes.mvp

import io.reactivex.Flowable
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.data.entity.Income
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository

class FragmentIncomesModel(private val databaseRepository: IDatabaseRepository) :
        FragmentIncomesContract.Model {

    override fun getAllIncomes(): Flowable<List<Income>> {
        return databaseRepository.getAllIncomes()
    }

    override fun getSortedIncomes(timeRangeStart: Long, timeRangeEnd: Long): Flowable<List<Income>> {
        return databaseRepository.getSortedIncomes(timeRangeStart, timeRangeEnd)
    }
}