package rmnvich.apps.familybudget.presentation.activity.make.income.mvp

import io.reactivex.Completable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.common.helper.DateHelper
import rmnvich.apps.familybudget.data.entity.Income
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository
import rmnvich.apps.familybudget.domain.interactor.preferences.IPreferencesRepository
import java.math.BigDecimal

class MakeIncomeActivityModel(private val databaseRepository: IDatabaseRepository,
                              private val preferencesRepository: IPreferencesRepository) :
        MakeIncomeActivityContract.Model {

    override fun getIncomeById(id: Int): Single<Income> {
        return databaseRepository.getIncomeById(id)
    }

    override fun getUserById(): Single<User> {
        return databaseRepository.getUserById(preferencesRepository.getUserId())
    }

    override fun insertIncome(income: Income, oldValue: String): Completable {
        if (income.timestamp == 0L)
            income.timestamp = DateHelper.getCurrentTimeInMills()

        return databaseRepository.getUserById(preferencesRepository.getUserId())
                .flatMapCompletable {
                    if (income.userName.isEmpty() && income.userRelationshipType.isEmpty()) {
                        income.userName = "${it.name} ${it.lastname}"
                        income.userRelationshipType = it.relationship
                    }
                    income.value = BigDecimal(income.value)
                            .setScale(2, BigDecimal.ROUND_DOWN).toString()
                    databaseRepository.insertIncome(income)
                }.andThen(databaseRepository.getBalance())
                .flatMapCompletable {
                    when (oldValue) {
                        "0" -> {
                            it.balance = BigDecimal(it.balance).add(BigDecimal(income.value))
                                    .setScale(2, BigDecimal.ROUND_DOWN).toString()
                            it.totalIncomes = BigDecimal(it.totalIncomes).add(BigDecimal(income.value))
                                    .setScale(2, BigDecimal.ROUND_DOWN).toString()
                        }
                        else -> {
                            it.balance = BigDecimal(it.balance).add(BigDecimal(income.value))
                                    .subtract(BigDecimal(oldValue))
                                    .setScale(2, BigDecimal.ROUND_DOWN).toString()
                            it.totalIncomes = BigDecimal(it.totalIncomes).add(BigDecimal(income.value))
                                    .subtract(BigDecimal(oldValue))
                                    .setScale(2, BigDecimal.ROUND_DOWN).toString()
                        }
                    }
                    databaseRepository.insertBalance(it)
                }
    }

    override fun deleteIncome(id: Int): Completable {
        var value = BigDecimal(0)

        return databaseRepository.getIncomeById(id)
                .flatMapCompletable {
                    value = BigDecimal(it.value)
                    databaseRepository.deleteIncome(it)
                }.andThen(databaseRepository.getBalance())
                .flatMapCompletable {
                    it.balance = BigDecimal(it.balance).subtract(value)
                            .setScale(2, BigDecimal.ROUND_DOWN).toString()
                    it.totalIncomes = BigDecimal(it.totalIncomes).subtract(value)
                            .setScale(2, BigDecimal.ROUND_DOWN).toString()
                    databaseRepository.insertBalance(it)
                }
    }
}