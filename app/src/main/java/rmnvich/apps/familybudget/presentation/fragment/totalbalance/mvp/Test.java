package rmnvich.apps.familybudget.presentation.fragment.totalbalance.mvp;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {

    public static Uri saveExcelFile() {
        //New Workbook
        Workbook wb = new HSSFWorkbook();
        Cell c;
        CellStyle cs = wb.createCellStyle();
        Font defaultFont = wb.createFont();
        defaultFont.setBold(true);
        cs.setFont(defaultFont);

        Sheet sheet1;
        sheet1 = wb.createSheet("myOrder");
        Row row = sheet1.createRow(0);
        c = row.createCell(0);
        c.setCellValue("Статья");
        c.setCellStyle(cs);
        c = row.createCell(1);
        c.setCellValue("Кто");
        c.setCellStyle(cs);
        c = row.createCell(2);
        c.setCellValue("Сумма");
        c.setCellStyle(cs);
        c = row.createCell(3);
        c.setCellValue("Дата");
        c.setCellStyle(cs);

        Cell c2;
        for (int i = 1; i < 14; i++) {
            Row row1 = sheet1.createRow(i);
            for (int j = 0; j < 4; j++) {
                c2 = row1.createCell(j);
                c2.setCellValue("row " + i + ", " + j);
            }
        }

        sheet1.setColumnWidth(0, (15 * 500));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(2, (15 * 500));
        sheet1.setColumnWidth(3, (15 * 500));

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "testfile.xls");
        FileOutputStream os;
        try {
            os = new FileOutputStream(file);
            wb.write(os);
            os.close();
        } catch (IOException e) {
            Log.w("qwe", "Failed to save file", e);
        }

        return Uri.fromFile(file);
    }
}
