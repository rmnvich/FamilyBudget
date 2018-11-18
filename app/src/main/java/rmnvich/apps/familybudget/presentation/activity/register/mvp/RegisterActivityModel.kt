package rmnvich.apps.familybudget.presentation.activity.register.mvp

import android.content.Intent
import android.net.Uri
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.local.FileRepositoryImpl
import java.util.concurrent.Callable

class RegisterActivityModel(private val databaseRepository: DatabaseRepositoryImpl,
                            private val localRepository: FileRepositoryImpl) :
        RegisterActivityContract.Model {

    override fun checkIfExists(user: User): Single<User> {
        return databaseRepository.getUserByNameAndLastname(user.name, user.lastname)
    }

    override fun insertUser(user: User): Completable {
        return databaseRepository.insertUser(user)
    }

    override fun getFilePath(data: Intent?): Observable<String> {
        return Observable.fromCallable(CallableBitmapAction(data?.data!!, getRealPath(data)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getRealPath(data: Intent?): String {
        return localRepository.getRealPathFromUri(data?.data!!)
    }

    inner class CallableBitmapAction(private var uri: Uri,
                                     private var realPath: String) : Callable<String> {
        override fun call(): String {
            return localRepository.saveToInternalStorage(uri, realPath)
        }
    }
}