package rmnvich.apps.familybudget.data.repository.database

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rmnvich.apps.familybudget.data.database.AppDatabase
import rmnvich.apps.familybudget.data.entity.User

class DatabaseRepositoryImpl(appDatabase: AppDatabase) :
        IDatabaseRepository {

    private val userDao = appDatabase.userDao()

    override fun getAllUsers(): Flowable<List<User>> {
        return userDao.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getUserById(userId: Int): Single<User> {
        return userDao.getUserById(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getUserByNameAndLastname(name: String, lastname: String): Single<User> {
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

}