package rmnvich.apps.familybudget.data.repository.local

import android.content.Context
import android.content.ContextWrapper
import android.content.CursorLoader
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.*
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.common.helper.DateHelper
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.data.entity.Income
import rmnvich.apps.familybudget.domain.interactor.local.IFileRepository
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.math.BigDecimal
import java.math.BigDecimal.ROUND_DOWN
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

    override fun saveDataToExcelFile(data: List<Any>): File? {
        val incomes = LinkedList<Income>()
        val expenses = LinkedList<Expense>()
        var totalIncomes = "0"
        var totalExpenses = "0"
        val totalBalance: String

        for (obj in data) {
            if (obj is Income) incomes.add(obj)
            else expenses.add(obj as Expense)
        }

        val workbook = HSSFWorkbook()
        val defaultFont = workbook.createFont()
        defaultFont.bold = true

        val cellStyleBoldLeft = workbook.createCellStyle()
        val cellStyleLeft = workbook.createCellStyle()
        val cellStyleBoldRight = workbook.createCellStyle()
        val cellStyleRight = workbook.createCellStyle()

        cellStyleBoldLeft.setFont(defaultFont)
        cellStyleBoldLeft.setAlignment(HorizontalAlignment.LEFT)
        cellStyleLeft.setAlignment(HorizontalAlignment.LEFT)

        cellStyleBoldRight.setFont(defaultFont)
        cellStyleBoldRight.setAlignment(HorizontalAlignment.RIGHT)
        cellStyleRight.setAlignment(HorizontalAlignment.RIGHT)

        var cell: Cell
        val sheet: Sheet
        sheet = workbook.createSheet(context.getString(R.string.excel_table_name))

        val titleRow = sheet.createRow(0)
        createCustomCell(titleRow, 3, context.getString(R.string.excel_fourth_column_title), cellStyleBoldLeft)
        createCustomCell(titleRow, 2, context.getString(R.string.excel_third_column_title), cellStyleBoldLeft)
        createCustomCell(titleRow, 1, context.getString(R.string.excel_second_column_title), cellStyleBoldLeft)
        createCustomCell(titleRow, 0, context.getString(R.string.excel_first_column_title), cellStyleBoldLeft)

        createCustomCell(sheet, 0, context.getString(R.string.excel_income_column_title), 1, cellStyleBoldLeft)

        var indexRow = 2
        for (income in incomes) {
            val row = sheet.createRow(indexRow)
            for (j in 0 until 4) {
                cell = row.createCell(j)
                when (j) {
                    0 -> {
                        cell.setCellValue(income.incomeType)
                        cell.setCellStyle(cellStyleRight)
                    }
                    1 -> cell.setCellValue("${income.userName} (${income.userRelationshipType})")
                    2 -> {
                        cell.setCellValue("${income.value} BYN")
                        cell.setCellStyle(cellStyleRight)
                    }
                    3 -> cell.setCellValue(DateHelper.getTimeFroExcel(income.timestamp))
                }
            }
            totalIncomes = BigDecimal(totalIncomes).add(BigDecimal(income.value))
                    .setScale(2, ROUND_DOWN).toString()
            indexRow++
        }

        createCustomCell(sheet, 0, context.getString(R.string.excel_expense_column_title), indexRow, cellStyleBoldLeft)
        indexRow += 1

        for (expense in expenses) {
            val row = sheet.createRow(indexRow)
            for (j in 0 until 4) {
                cell = row.createCell(j)
                when (j) {
                    0 -> {
                        cell.setCellValue(expense.category?.name)
                        cell.setCellStyle(cellStyleRight)
                    }
                    1 -> cell.setCellValue("${expense.userName} (${expense.userRelationship})")
                    2 -> {
                        cell.setCellValue("${expense.value} BYN")
                        cell.setCellStyle(cellStyleRight)
                    }
                    3 -> cell.setCellValue(DateHelper.getTimeFroExcel(expense.timestamp))
                }
            }
            totalExpenses = BigDecimal(totalExpenses).add(BigDecimal(expense.value))
                    .setScale(2, ROUND_DOWN).toString()
            indexRow++
        }
        totalBalance = BigDecimal(totalIncomes).subtract(BigDecimal(totalExpenses))
                .setScale(2, ROUND_DOWN).toString()

        createCustomCell(sheet, 0, context.getString(R.string.excel_total_column_title), indexRow, cellStyleBoldLeft)
        indexRow += 1

        createTotalCells(sheet, context.getString(R.string.excel_income_column_title), "$totalIncomes BYN", indexRow, cellStyleBoldRight)
        indexRow += 1
        createTotalCells(sheet, context.getString(R.string.excel_expense_column_title), "$totalExpenses BYN", indexRow, cellStyleBoldRight)
        indexRow += 1
        createTotalCells(sheet, context.getString(R.string.excel_balance_column_title), "$totalBalance BYN", indexRow, cellStyleBoldRight)

        sheet.setColumnWidth(0, 8 * 500)
        sheet.setColumnWidth(1, 12 * 500)
        sheet.setColumnWidth(2, 7 * 500)
        sheet.setColumnWidth(3, 6 * 500)

        val filePath = Environment.getExternalStorageDirectory().toString() +
                File.separator + "family_budget.xls"
        val file = File(filePath)
        val os: FileOutputStream
        return try {
            os = FileOutputStream(file)
            workbook.write(os)
            os.close()
            file
        } catch (e: IOException) {
            Log.w("qwe", "Failed to save file", e)
            null
        }
    }

    private fun createCustomCell(sheet: Sheet, column: Int, text: String, indexRow: Int,
                                 cellStyle: CellStyle) {
        val row = sheet.createRow(indexRow)
        val cell = row.createCell(column)
        cell.cellStyle = cellStyle
        cell.setCellValue(text)
    }

    private fun createCustomCell(row: Row, column: Int, text: String, cellStyle: CellStyle) {
        val cell = row.createCell(column)
        cell.cellStyle = cellStyle
        cell.setCellValue(text)
    }

    private fun createTotalCells(sheet: Sheet, title: String, message: String, indexRow: Int,
                                 cellStyleBoldRight: CellStyle) {
        val row = sheet.createRow(indexRow)
        var cell = row.createCell(0)
        cell.cellStyle = cellStyleBoldRight
        cell.setCellValue(title)
        cell = row.createCell(2)
        cell.setCellValue(message)
        cell.cellStyle = cellStyleBoldRight
    }

}