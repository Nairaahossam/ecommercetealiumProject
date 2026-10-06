package utils;

import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    private static final String FILE_PATH = "testdata/TestData.xlsx";
    private static final String TEST_CASE_ID_COLUMN = "TestCaseID";
    private static final String TEST_TYPE_COLUMN = "TestType";

    private static List<Map<String, String>> getSheetData(String sheetName) {

        try (InputStream inputStream =
                     ExcelReader.class
                             .getClassLoader()
                             .getResourceAsStream(FILE_PATH)) {

            if (inputStream == null) {
                throw new RuntimeException(
                        "TestData.xlsx file not found"
                );
            }

            try (Workbook workbook =
                         WorkbookFactory.create(inputStream)) {

                Sheet sheet = workbook.getSheet(sheetName);

                if (sheet == null) {
                    throw new RuntimeException(
                            "Sheet not found: " + sheetName
                    );
                }

                return readRows(sheet);
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read Excel file: " + FILE_PATH,
                    e
            );
        }
    }

    public static Map<String, String> getTestCaseData(
            String sheetName,
            String testCaseId) {

        return getSheetData(sheetName)
                .stream()
                .filter(row -> row.get(TEST_CASE_ID_COLUMN)
                        .equalsIgnoreCase(testCaseId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Test case not found: " + testCaseId
                ));
    }

    public static Object[][] getTestDataByType(
            String sheetName,
            String testType) {

        return getSheetData(sheetName)
                .stream()
                .filter(row -> row.get(TEST_TYPE_COLUMN)
                        .equalsIgnoreCase(testType))
                .map(row -> new Object[]{row})
                .toArray(Object[][]::new);
    }

    private static List<Map<String, String>> readRows(Sheet sheet) {

        Row headerRow = sheet.getRow(0);

        if (headerRow == null) {
            throw new RuntimeException(
                    "Header row not found in sheet: "
                            + sheet.getSheetName()
            );
        }

        DataFormatter formatter =
                new DataFormatter();

        List<Map<String, String>> rows =
                new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);

            if (row == null) {
                continue;
            }

            Map<String, String> rowData =
                    new LinkedHashMap<>();

            for (Cell headerCell : headerRow) {

                Cell cell = row.getCell(headerCell.getColumnIndex());

                rowData.put(
                        formatter.formatCellValue(headerCell),
                        cell == null ? "" : formatter.formatCellValue(cell)
                );
            }

            rows.add(rowData);
        }

        return rows;
    }
}
