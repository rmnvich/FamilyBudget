package rmnvich.apps.familybudget.presentation.activity.editprofile.mvp

import android.content.Intent
import android.net.Uri
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository
import rmnvich.apps.familybudget.domain.interactor.local.IFileRepository
import java.util.concurrent.Callable

class EditProfileActivityModel(private val databaseRepository: IDatabaseRepository,
                               private val fileRepository: IFileRepository) :
        EditProfileActivityContract.Model {

    override fun getUserById(userId: Int): Single<User> {
        return databaseRepository.getUserById(userId)
    }

    override fun updateUserIncomeTypes(user: User): Completable {
        return databaseRepository.insertUser(user)
    }

    override fun getFilePath(data: Intent?): Observable<String> {
        return Observable.fromCallable(CallableBitmapAction(data?.data!!, getRealPath(data)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getRealPath(data: Intent?): String {
        return fileRepository.getRealPathFromUri(data?.data!!)
    }

    inner class CallableBitmapAction(private var uri: Uri,
                                     private var realPath: String) : Callable<String> {
        override fun call(): String {
            return fileRepository.saveToInternalStorage(uri, realPath)
        }
    }
}