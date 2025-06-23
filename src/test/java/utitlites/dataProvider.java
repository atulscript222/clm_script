package utitlites;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class dataProvider {

    @DataProvider(name = "logindata")
    public String[][] getdata() throws IOException {

        String path = System.getProperty("user.dir") + "\\123\\testData\\testdatalogin.xlsx";
        ExcelUtility xlutil = new ExcelUtility(path);

        int totalrows = xlutil.getRowCount("Sheet1");
        int totalcols = xlutil.getCellCount("Sheet1", 1);

        String logindata[][] = new String[totalrows][totalcols];
        System.out.println("Total rows: " + totalrows);
        System.out.println("Total columns: " + totalcols);


        for (int i = 1; i <= totalrows; i++) {
            for (int j = 0; j < totalcols; j++) {
                logindata[i - 1][j] = xlutil.getCellValue("Sheet1", i, j);
                System.out.println("Reading cell [" + i + "," + j + "]: " + logindata[i - 1][j]);

            }
        }

        return logindata;
    }
    @DataProvider(name = "logindatawithoutexcel")
    public String[][] logindata() {
        
        String logindt[][] = {
            {"atul@doqfy.in", "Outlook@123"},
            {"ganesh@doqfy.in", "Doqfy@12345"}
            
        };
        
        return logindt;  // ✅ Return the data array here
    }
}
