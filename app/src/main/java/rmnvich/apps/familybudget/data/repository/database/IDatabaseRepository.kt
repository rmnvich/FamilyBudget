package rmnvich.apps.familybudget.data.repository.database

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Balance
import rmnvich.apps.familybudget.data.entity.User

interface IDatabaseRepository {

    fun getAllUsers(): Flowable<List<User>>

    fun getUserById(userId: Int): Single<User>

    fun getUserByNameAndLastName(name: String, lastname: String): Single<User>

    fun insertUser(user: User): Completable

    fun getBalance(): Single<Balance>

    fun insertBalance(balance: Balance): Completable
}