package rmnvich.apps.familybudget.data.repository.database

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rmnvich.apps.familybudget.data.database.AppDatabase
import rmnvich.apps.familybudget.data.entity.Balance
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository
import java.util.concurrent.TimeUnit

class DatabaseRepositoryImpl(appDatabase: AppDatabase) :
        IDatabaseRepository {

    private val userDao = appDatabase.userDao()
    private val balanceDao = appDatabase.balanceDao()
    private val categoryDao = appDatabase.categoryDao()

    override fun getAllUsers(): Flowable<List<User>> {
        return userDao.getAllUsers()
                .subscribeOn(Schedulers.io())
                .delay(150, TimeUnit.MILLISECONDS)
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
                .delay(150, TimeUnit.MILLISECONDS)
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
}