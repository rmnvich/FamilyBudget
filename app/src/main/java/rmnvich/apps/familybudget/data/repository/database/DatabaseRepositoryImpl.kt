package rmnvich.apps.familybudget.data.repository.database

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rmnvich.apps.familybudget.data.database.AppDatabase
import rmnvich.apps.familybudget.data.entity.Balance
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository
import java.util.concurrent.TimeUnit

class DatabaseRepositoryImpl(appDatabase: AppDatabase) :
        IDatabaseRepository {

    private val userDao = appDatabase.userDao()
    private val balanceDao = appDatabase.balanceDao()
    private val categoryDao = appDatabase.categoryDao()
    private var expenseDao = appDatabase.expenseDao()

    override fun getAllUsers(): Flowable<List<User>> {
        return userDao.getAllUsers()
                .subscribeOn(Schedulers.io())
                .delay(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getUserById(userId: Int): Single<User> {
        return userDao.getUserById(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getUserByNameAndLastName(name: String, lastname: String): Single<User> {
        return userDao.getUserByName(name, lastname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun insertUser(user: User): Completable {
        return Completable.fromAction {
            userDao.insertUser(user)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getBalance(): Single<Balance> {
        return balanceDao.getBalance(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun insertBalance(balance: Balance): Completable {
        return Completable.fromAction {
            balanceDao.insertBalance(balance)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAllCategories(): Flowable<List<Category>> {
        return categoryDao.getAllCategories()
                .subscribeOn(Schedulers.io())
                .delay(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCategoryById(id: Int): Single<Category> {
        return categoryDao.getCategoryById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun insertCategory(category: Category): Completable {
        return Completable.fromAction {
            categoryDao.insertCategory(category)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteCategory(category: Category): Completable {
        return Completable.fromAction {
            categoryDao.deleteCategory(category)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAllActualExpenses(): Flowable<List<Expense>> {
        return expenseDao.getAllExpenses(false)
                .subscribeOn(Schedulers.io())
                .delay(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAllPlannedExpenses(): Flowable<List<Expense>> {
        return expenseDao.getAllExpenses(true)
                .subscribeOn(Schedulers.io())
                .delay(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getExpenseById(id: Int): Single<Expense> {
        return expenseDao.getExpenseById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun insertExpense(expense: Expense): Completable {
        return Completable.fromAction {
            expenseDao.insertExpense(expense)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteExpense(expense: Expense): Completable {
        return Completable.fromAction {
            expenseDao.deleteExpense(expense)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}