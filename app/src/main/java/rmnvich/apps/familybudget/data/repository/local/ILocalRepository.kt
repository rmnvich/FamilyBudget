package rmnvich.apps.familybudget.data.repository.local

import android.graphics.Bitmap
import android.net.Uri

interface ILocalRepository {

    fun getRealPathFromUri(uri: Uri): String

    fun saveToInternalStorage(contentUri: Uri, realPath: String): String

    fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap
}