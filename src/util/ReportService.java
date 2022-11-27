package util;

import data.AccountingData;
import data.MonthData;
import data.YearData;
import records.MonthlyRecord;
import records.YearlyRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportService {

    private static final String[] TITLE_MONTH = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Декабрь"};

    public static void readYearlyReport(AccountingData accountingData, int year) {
        String path = "reportFiles/y." + year + ".csv";
        System.out.println("Считывание файла " + Path.of(path).getFileName());
        String text = readFileContentsOrNull(path);
        if (text == null) {
            return;
        } else {
            System.out.println("\033[0;32mФайл считан\033[0m");
        }
        String[] textStrings = text.split("\r?\r\n?\n");

        List<YearlyRecord> list = new ArrayList<>();

        for (int i = 1; i < textStrings.length; i++) {
            String[] partString = textStrings[i].split(",");

            YearlyRecord newYearlyRecord = new YearlyRecord();

            int month = Integer.parseInt(partString[0]);
            double amount = Double.parseDouble(partString[1]);
            boolean isExpense = Boolean.parseBoolean(partString[2]);

            newYearlyRecord.setMonth(month);
            newYearlyRecord.setAmount(amount);
            newYearlyRecord.setExpense(isExpense);

            list.add(newYearlyRecord);
        }

        YearData yearData;
        if (!accountingData.getYearDataMap().containsKey(year)) {
            yearData = new YearData(list);
        } else {
            yearData = accountingData.getYearDataMap().get(year);
        }

        yearData.setRecordListYear(list);
        accountingData.getYearDataMap().put(year, yearData);
    }

    public static void readMonthlyReports(AccountingData accountingData, int year) {
        for (byte i = 1; i <= 12; i++) {

            String path = i < 10 ? "reportFiles/m." + year + "0" + i + ".csv" : "reportFiles/m." + year + "" + i + ".csv";
            String message = "Считывание файла " + Path.of(path).getFileName();
            System.out.println(message);

            String text = readFileContentsOrNull(path);

            if (text == null) {
                continue;
            } else {
                System.out.println("\033[0;32mФайл считан\033[0m");
            }
            readMonthReport(accountingData, text, year, i);
        }
    }

    public static void readMonthReport(AccountingData accountingData, String text, int year, byte numMonth) {

        String[] textStrings = text.split("\r?\r\n?\n");

        List<MonthlyRecord> list = new ArrayList<>();

        for (int i = 1; i < textStrings.length; i++) {
            String[] partString = textStrings[i].split(",");

            MonthlyRecord newMonthlyRecord = new MonthlyRecord();

            String itemName = partString[0];
            boolean isExpense = Boolean.parseBoolean(partString[1]);
            int quantity = Integer.parseInt(partString[2]);
            int sumOfOne = Integer.parseInt(partString[3]);

            newMonthlyRecord.setItemName(itemName);
            newMonthlyRecord.setExpense(isExpense);
            newMonthlyRecord.setQuantity(quantity);
            newMonthlyRecord.setSumOfOne(sumOfOne);

            list.add(newMonthlyRecord);
        }

        MonthData monthData = new MonthData(TITLE_MONTH[numMonth - 1], list);

        YearData yearData = accountingData.getYearDataMap().get(year);
        if (yearData == null) {
            yearData = new YearData();
        }
        accountingData.addYearData(year, yearData);
        yearData.addRecordMonthData(numMonth, monthData);
    }

    public static void checkCollationDataYearlyMonthly(AccountingData accountingData, int year) {
        Map<Integer, YearData> yearDataMap = accountingData.getYearDataMap();
        if (!yearDataMap.containsKey(year)) {
            System.out.println("По указанному году данных нет");
            return;
        }

        List<Integer> listErrorMonth = new ArrayList<>();

        YearData yearData = yearDataMap.get(year);
        Map<Byte, MonthData> monthDataMap = yearData.getMonthDataMap();
        List<YearlyRecord> recordListYear = yearData.getRecordListYear();

        if (monthDataMap == null || recordListYear == null) {
            System.out.println("\033[0;32mОшибка. Не все отчеты были считаныю\033[0m");
            return;
        }

        for (YearlyRecord yearlyRecord : recordListYear) {
            int month = yearlyRecord.getMonth();
            double amount = yearlyRecord.getAmount();
            boolean isExpense = yearlyRecord.getIsExpense();
            MonthData monthData = monthDataMap.get((byte) month);
            if (isExpense) {
                if (amount != monthData.getExpense()) {
                    listErrorMonth.add(month);
                }
            } else {
                if (amount != monthData.getIncome()) {
                    listErrorMonth.add(month);
                }
            }
            System.out.println(amount + " " + monthData.getExpense() + "/" + monthData.getIncome());
        }

        if (listErrorMonth.isEmpty()) {
            System.out.println("\033[0;32mОшибок в месячных отчетах не обнаружено.\033[0m");
        } else {
            System.out.print("\033[0;31mНайдены ошибки в отчетах следующих месяцах: ");
            for (Integer month : listErrorMonth) {
                System.out.print(TITLE_MONTH[month - 1] + " ");
            }
            System.out.println("\033[0m");
        }
    }

    private static String readFileContentsOrNull(String path) {
        Path pathFile = Path.of(path);
        try {
            return Files.readString(pathFile);
        } catch (IOException e) {
            System.out.println("\033[0;31mОшибка. Невозможно прочитать файл " + Path.of(path).getFileName() + ". Возможно, файл не находится в директории."
                    + pathFile.getParent().toAbsolutePath() + "\033[0m");
            return null;
        }
    }
}
