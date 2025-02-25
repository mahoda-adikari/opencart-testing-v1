package com.opencart.utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviderUtility {

    @DataProvider(name = "loginData")
    public String[][] getLoginData() throws IOException {
        String path = ".\\testdata\\OpenCart_LoginData.xlsx";
        String sheetName = "Sheet1";

        ExcelUtility excelUtils = new ExcelUtility(path);

        int rowCount = excelUtils.getRowCount(sheetName);
        int cellCount = excelUtils.getCellCount(sheetName, 0);

        String loginData[][] = new String[rowCount][cellCount];

        for (int i = 1; i <= rowCount; i++) {
            for (int j = 1; j <= cellCount; j++) {
                loginData[i-1][j] = excelUtils.getCellData(sheetName, i, j);
            }
        }
        return loginData;
    }
}
