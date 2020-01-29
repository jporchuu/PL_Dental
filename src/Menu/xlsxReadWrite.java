package Menu;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class xlsxReadWrite{

    public static int patientCount = 1;
    public static int schedCount = 0;
    public static int receiptsGenerated = 0;

    public static int searchedIndex = 0;

    public void writeToExcel(String name, String gender, String age, int dc, int gp, int od, int c, int dfa) throws IOException {

        FileInputStream myXLSXFile = new FileInputStream("ConsolidatedList.xlsx");

        XSSFWorkbook wBook = new XSSFWorkbook(myXLSXFile);

        XSSFSheet wSheet = wBook.getSheetAt(0);

        XSSFRow row = wSheet.createRow(patientCount);

        row.createCell(0).setCellValue(name);
        row.createCell(1).setCellValue(age);
        row.createCell(2).setCellValue(gender);
        row.createCell(3).setCellValue(dc);
        row.createCell(4).setCellValue(gp);
        row.createCell(5).setCellValue(od);
        row.createCell(6).setCellValue(c);
        row.createCell(7).setCellValue(dfa);

        myXLSXFile.close();

        FileOutputStream output_file = new FileOutputStream(new File("ConsolidatedList.xlsx"));
        wBook.write(output_file);
        output_file.close();
    }

    public void writeSchedToExcel(String name, String service, String date, String time) throws IOException{
        FileInputStream myXLSXFile = new FileInputStream("ConsolidatedList.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(myXLSXFile);
        XSSFSheet schedSheet = wb.getSheetAt(1);
        XSSFRow row = schedSheet.createRow(schedSheet.getLastRowNum()+1);
        row.createCell(0).setCellValue(name);
        row.createCell(1).setCellValue(service);
        row.createCell(2).setCellValue(date);
        row.createCell(3).setCellValue(time);

        myXLSXFile.close();

        FileOutputStream output = new FileOutputStream(new File("ConsolidatedList.xlsx"));
        wb.write(output);
        output.close();
    }

    public void deleteRow() throws IOException {

        FileInputStream myXLSXFile = new FileInputStream("ConsolidatedList.xlsx");
        XSSFWorkbook wBook = new XSSFWorkbook(myXLSXFile);
        XSSFSheet wSheet = wBook.getSheetAt(0);

        wSheet.removeRow(wSheet.getRow(searchedIndex+1));

        if( searchedIndex+1 != wSheet.getLastRowNum()+1 ){
            wSheet.shiftRows(searchedIndex+2, wSheet.getLastRowNum(), -1);
        }

        myXLSXFile.close();

        FileOutputStream output_file = new FileOutputStream(new File("ConsolidatedList.xlsx"));
        wBook.write(output_file);
        output_file.close();
    }

    public void readProfileToExcel() throws IOException {
        PatientLinkedList currLinkedList = new PatientLinkedList();

        XSSFWorkbook wb = new XSSFWorkbook("ConsolidatedList.xlsx");

        XSSFSheet schedSheet = wb.getSheetAt(0);

        for (int i = 2; i <= schedSheet.getLastRowNum(); i++) {
            String name = schedSheet.getRow(i).getCell(0).getStringCellValue();
            String age = schedSheet.getRow(i).getCell(1).getStringCellValue();
            String gender = schedSheet.getRow(i).getCell(2).getStringCellValue();
            double dc = schedSheet.getRow(i).getCell(3).getNumericCellValue();
            double gp = schedSheet.getRow(i).getCell(4).getNumericCellValue();
            double od = schedSheet.getRow(i).getCell(5).getNumericCellValue();
            double c = schedSheet.getRow(i).getCell(6).getNumericCellValue();
            double dfa = schedSheet.getRow(i).getCell(7).getNumericCellValue();
            currLinkedList.pushToStaticLinkedList(name, gender, age, (int) dc, (int) gp, (int) od, (int) c, (int) dfa);
        }

        wb.close();
    }

    public void readSchedFromExcel() throws IOException {
        SchedLinkedList currLinkedList = new SchedLinkedList();

        XSSFWorkbook wb = new XSSFWorkbook("ConsolidatedList.xlsx");

        XSSFSheet schedSheet = wb.getSheetAt(1);
        for (int i = 1; i <=schedSheet.getLastRowNum(); i++) {
            System.out.println("Iteration " +i);
            String name = schedSheet.getRow(i).getCell(0).getStringCellValue();
            String service = schedSheet.getRow(i).getCell(1).getStringCellValue();
            String date = schedSheet.getRow(i).getCell(2).getStringCellValue();
            String time = schedSheet.getRow(i).getCell(3).getStringCellValue();
            currLinkedList.pushToStaticLinkedList(name, service, date, time);
        }

        wb.close();
    }

    public void editToExcel(String name, String gender, String age, int dc, int gp, int od, int c, int dfa) throws IOException {

        FileInputStream myXLSXFile = new FileInputStream("ConsolidatedList.xlsx");

        XSSFWorkbook wBook = new XSSFWorkbook(myXLSXFile);

        XSSFSheet wSheet = wBook.getSheetAt(0);

        XSSFRow row = wSheet.createRow(searchedIndex+1);

        row.createCell(0).setCellValue(name);
        row.createCell(1).setCellValue(age);
        row.createCell(2).setCellValue(gender);
        row.createCell(3).setCellValue(dc);
        row.createCell(4).setCellValue(gp);
        row.createCell(5).setCellValue(od);
        row.createCell(6).setCellValue(c);
        row.createCell(7).setCellValue(dfa);

        myXLSXFile.close();

        FileOutputStream output_file = new FileOutputStream(new File("ConsolidatedList.xlsx"));
        wBook.write(output_file);
        output_file.close();
    }

    public void generateReceiptCount() throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook("ConsolidatedList.xlsx");

        XSSFSheet schedSheet = wb.getSheetAt(2);

        receiptsGenerated = (int) schedSheet.getRow(0).getCell(0).getNumericCellValue();

        wb.close();
    }

    public void addReceiptCount() throws IOException {
        FileInputStream myXLSXFile = new FileInputStream("ConsolidatedList.xlsx");

        XSSFWorkbook wBook = new XSSFWorkbook(myXLSXFile);

        XSSFSheet wSheet = wBook.getSheetAt(2);

        XSSFRow row = wSheet.createRow(0);

        row.createCell(0).setCellValue(receiptsGenerated);

        myXLSXFile.close();

        FileOutputStream output_file = new FileOutputStream(new File("ConsolidatedList.xlsx"));
        wBook.write(output_file);
        output_file.close();
    }

}
