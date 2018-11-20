package rmnvich.apps.familybudget.domain.interactor.local

import android.graphics.Bitmap
import android.net.Uri

interface IFileRepository {

    fun getRealPathFromUri(uri: Uri): String

    fun saveToInternalStorage(contentUri: Uri, realPath: String): String

    fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap
}