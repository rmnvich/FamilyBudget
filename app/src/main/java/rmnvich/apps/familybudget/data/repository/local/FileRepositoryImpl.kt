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
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.Sheet
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

    fun saveDataToExcelFile(data: List<Any>) {
        val incomes = LinkedList<Income>()
        val expenses = LinkedList<Expense>()
        var totalIncomes = "0"
        var totalExpenses = "0"
        val totalBalance: String

        for (obj in data) {
            if (obj is Income) incomes.add(obj)
            else expenses.add(obj as Expense)
        }

        val wb = HSSFWorkbook()
        val defaultFont = wb.createFont()
        defaultFont.bold = true

        val cellStyleBoldLeft = wb.createCellStyle()
        val cellStyleLeft = wb.createCellStyle()
        val cellStyleBoldRight = wb.createCellStyle()
        val cellStyleRight = wb.createCellStyle()

        cellStyleBoldLeft.setFont(defaultFont)
        cellStyleBoldLeft.setAlignment(HorizontalAlignment.LEFT)
        cellStyleLeft.setAlignment(HorizontalAlignment.LEFT)

        cellStyleBoldRight.setFont(defaultFont)
        cellStyleBoldRight.setAlignment(HorizontalAlignment.RIGHT)
        cellStyleRight.setAlignment(HorizontalAlignment.RIGHT)

        var cell: Cell
        val sheet: Sheet
        sheet = wb.createSheet("Family budget")
        val row = sheet.createRow(0)
        cell = row.createCell(0)
        cell.setCellValue("Статья")
        cell.setCellStyle(cellStyleBoldLeft)
        cell = row.createCell(1)
        cell.setCellValue("Кто")
        cell.setCellStyle(cellStyleBoldLeft)
        cell = row.createCell(2)
        cell.setCellValue("Сумма")
        cell.setCellStyle(cellStyleBoldLeft)
        cell = row.createCell(3)
        cell.setCellValue("Дата")
        cell.setCellStyle(cellStyleBoldLeft)

        val incomeTitleRow = sheet.createRow(1)
        val incomeTitleCell = incomeTitleRow.createCell(0)
        incomeTitleCell.setCellValue("Доход")
        incomeTitleCell.setCellStyle(cellStyleBoldLeft)

        var indexRow = 2
        var incomeCell: Cell
        for (income in incomes) {
            val incomeRow = sheet.createRow(indexRow)
            for (j in 0 until 4) {
                incomeCell = incomeRow.createCell(j)
                when (j) {
                    0 -> {
                        incomeCell.setCellValue(income.incomeType)
                        incomeCell.setCellStyle(cellStyleRight)
                    }
                    1 -> incomeCell.setCellValue("${income.userName} (${income.userRelationshipType})")
                    2 -> {
                        incomeCell.setCellValue("${income.value} BYN")
                        incomeCell.setCellStyle(cellStyleRight)
                    }
                    3 -> incomeCell.setCellValue(DateHelper.getTimeFroExcel(income.timestamp))
                }
            }
            totalIncomes = BigDecimal(totalIncomes).add(BigDecimal(income.value))
                    .setScale(2, ROUND_DOWN).toString()
            indexRow++
        }

        val expenseTitleRow = sheet.createRow(indexRow)
        val expenseTitleCell = expenseTitleRow.createCell(0)
        expenseTitleCell.setCellValue("Расход")
        expenseTitleCell.setCellStyle(cellStyleBoldLeft)

        indexRow += 1
        var expenseCell: Cell
        for (expense in expenses) {
            val expenseRow = sheet.createRow(indexRow)
            for (j in 0 until 4) {
                expenseCell = expenseRow.createCell(j)
                when (j) {
                    0 -> {
                        expenseCell.setCellValue(expense.category?.name)
                        expenseCell.setCellStyle(cellStyleRight)
                    }
                    1 -> expenseCell.setCellValue("${expense.userName} (${expense.userRelationship})")
                    2 -> {
                        expenseCell.setCellValue("${expense.value} BYN")
                        expenseCell.setCellStyle(cellStyleRight)
                    }
                    3 -> expenseCell.setCellValue(DateHelper.getTimeFroExcel(expense.timestamp))
                }
            }
            totalExpenses = BigDecimal(totalExpenses).add(BigDecimal(expense.value))
                    .setScale(2, ROUND_DOWN).toString()
            indexRow++
        }

        val totalRow = sheet.createRow(indexRow)
        val totalCell = totalRow.createCell(0)
        totalCell.setCellStyle(cellStyleBoldLeft)
        totalCell.setCellValue("Итого")
        indexRow += 1

        val totalIncomesRow = sheet.createRow(indexRow)
        val totalIncomesTitleCell = totalIncomesRow.createCell(0)
        val totalIncomesCell = totalIncomesRow.createCell(2)
        totalIncomesTitleCell.setCellStyle(cellStyleBoldRight)
        totalIncomesTitleCell.setCellValue("Доход")
        totalIncomesCell.setCellValue("$totalIncomes BYN")
        totalIncomesCell.setCellStyle(cellStyleBoldRight)
        indexRow += 1

        val totalExpensesRow = sheet.createRow(indexRow)
        val totalExpensesTitleCell = totalExpensesRow.createCell(0)
        val totalExpensesCell = totalExpensesRow.createCell(2)
        totalExpensesTitleCell.setCellStyle(cellStyleBoldRight)
        totalExpensesTitleCell.setCellValue("Расход")
        totalExpensesCell.setCellValue("$totalExpenses BYN")
        totalExpensesCell.setCellStyle(cellStyleBoldRight)
        indexRow += 1

        val totalBalanceRow = sheet.createRow(indexRow)
        val totalBalanceTitleCell = totalBalanceRow.createCell(0)
        val totalBalanceCell = totalBalanceRow.createCell(2)
        totalBalanceTitleCell.setCellStyle(cellStyleBoldRight)
        totalBalanceTitleCell.setCellValue("Баланс")

        totalBalance = BigDecimal(totalIncomes).subtract(BigDecimal(totalExpenses))
                .setScale(2, ROUND_DOWN).toString()
        totalBalanceCell.setCellValue("$totalBalance BYN")
        totalBalanceCell.setCellStyle(cellStyleBoldRight)

        sheet.setColumnWidth(0, 8 * 500)
        sheet.setColumnWidth(1, 12 * 500)
        sheet.setColumnWidth(2, 7 * 500)
        sheet.setColumnWidth(3, 6 * 500)

        val file = File(Environment.getExternalStorageDirectory().toString() + File.separator + "testfile.xls")
        val os: FileOutputStream
        try {
            os = FileOutputStream(file)
            wb.write(os)
            os.close()
        } catch (e: IOException) {
            Log.w("qwe", "Failed to save file", e)
        }
    }

}