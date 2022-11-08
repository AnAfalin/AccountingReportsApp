package application;

import data.AccountingData;
import util.ReportService;
import java.util.Scanner;

public class AccountingApplication {
    private final AccountingData accountingData;
    private static final String MENU = "Выберите какую операцию необходимо выполнить: " + System.lineSeparator() +
            "1.	Считать все месячные отчёты" + System.lineSeparator() +
            "2.	Считать годовой отчёт" + System.lineSeparator() +
            "3.	Сверить отчёты" + System.lineSeparator() +
            "4.	Вывести информацию о всех месячных отчётах" + System.lineSeparator() +
            "5.	Вывести информацию о годовом отчёте" + System.lineSeparator() +
            "6. Выйти из приложения";

    private static final Scanner SCANNER = new Scanner(System.in);

    public AccountingApplication(AccountingData accountingData) {
        this.accountingData = accountingData;
    }

    public void start() {
        int userInput;
        while (true) {
            System.out.println(MENU);
            userInput = SCANNER.nextInt();

            switch (userInput) {
                case 1 -> readAllMonthReport();
                case 2 -> readYearReport();
                case 3 -> checkReports();
                case 4 -> printInfoYearReportByMonth();
                case 5 -> printInfoYearReport();
                case 6 -> {
                    System.out.println("Приложение закрыто");
                    return;
                }
                default -> System.out.println("Некорректный ввод. Повторите попытку");
            }
        }
    }

    private void checkReports(){
        System.out.println("Введите год, по которому необходимо провести сверку отчетов");
        int year = SCANNER.nextInt();

        ReportService.checkCollationDataYearlyMonthly(accountingData, year);
    }

    private void readAllMonthReport(){
        System.out.println("Введите год, по которому необходимо считать месячные отчеты");
        int year = SCANNER.nextInt();

        ReportService.readMonthlyReports(accountingData, year);
    }

    private void readYearReport(){
        System.out.println("Введите год, по которому необходимо считать отчеты");
        int year = SCANNER.nextInt();

        ReportService.readYearlyReport(accountingData, year);
    }


    private void printInfoYearReportByMonth() {
        System.out.println("Введите год, по которому необходимо вывести отчет по месяцам");
        int year = SCANNER.nextInt();

        accountingData.printInfoAllMonthReports(year);
    }

    private void printInfoYearReport() {
        System.out.println("Введите год, по которому необходимо вывести годовой отчет");
        int year = SCANNER.nextInt();

        accountingData.printInfoYearReport(year);
    }
}
