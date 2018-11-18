package rmnvich.apps.familybudget.presentation.activity.register.mvp

import android.content.Intent
import android.net.Uri
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.local.LocalRepositoryImpl
import java.util.concurrent.Callable

class RegisterActivityModel(private val databaseRepository: DatabaseRepositoryImpl,
                            private val localRepository: LocalRepositoryImpl) :
        RegisterActivityContract.Model {

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