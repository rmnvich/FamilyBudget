package rmnvich.apps.familybudget.data.repository.local

import android.content.Context
import android.content.ContextWrapper
import android.content.CursorLoader
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import rmnvich.apps.familybudget.domain.interactor.local.IFileRepository
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class FileRepositoryImpl(private val context: Context) :
        IFileRepository {

    override fun getRealPathFromUri(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(context, uri, projection,
                null, null, null)
        val cursor = loader.loadInBackground()
        val columnIndex: Int
        try {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        } catch (e: NullPointerException) {
            return uri.path
        }

        cursor.moveToFirst()
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result

    }

    override fun saveToInternalStorage(contentUri: Uri, realPath: String): String {
        try {
            var loadedBitmap: Bitmap? = MediaStore.Images.Media
                    .getBitmap(context.contentResolver, contentUri)

            val originalExif = ExifInterface(realPath)
            val orientation = originalExif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED)
            loadedBitmap = rotateBitmap(loadedBitmap!!, orientation)

            val cw = ContextWrapper(context)
            val directory = cw.getDir("Posters", Context.MODE_PRIVATE)
            if (!directory.exists()) {
                directory.mkdir()
            }

            val imageName = "familybudget_" + Calendar.getInstance().timeInMillis
            val file = File(directory, "$imageName.png")
            val imagePath = directory.absolutePath + "/" + imageName + ".png"

            val fos: FileOutputStream
            fos = FileOutputStream(file)

            loadedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()

            return imagePath
        } catch (e: IOException) {
            Log.d("qwe", e.message)
            return ""
        }

    }

    override fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_NORMAL -> return bitmap
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
            else -> return bitmap
        }
        return try {
            val bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width,
                    bitmap.height, matrix, true)
            bitmap.recycle()
            bmRotated
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            bitmap
        }
    }

}