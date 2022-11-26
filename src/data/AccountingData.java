package data;

import java.time.Month;
import java.util.*;

public class AccountingData {
    private Map<Integer, YearData> yearDataMap  = new HashMap<>();

    public void addYearData(int year, YearData yearData){
        yearDataMap.put(year, yearData);
    }

    public Map<Integer, YearData> getYearDataMap() {
        return yearDataMap;
    }

    public void printInfoAllMonthReports(int year) {
        if(!yearDataMap.containsKey(year)){
            System.out.println("По указанному году данных нет");
            return;
        }

        YearData yearData = yearDataMap.get(year);

        Map<Byte, MonthData> monthDataMap = yearData.getMonthDataMap();
        if(monthDataMap == null || monthDataMap.isEmpty()){
            System.out.println("Данные по месяцам отсутствуют");
            return;
        }

        for (MonthData monthData : monthDataMap.values()) {
            System.out.println("Месяц:" + monthData.getTitle());

            String titleProfitProduct = monthData.getTitleProfitProduct();
            double profitProduct = monthData.getProfitByProduct(titleProfitProduct);
            System.out.println("Самый прибыльный товар: " + titleProfitProduct + ", " + profitProduct);

            String titleBigSpend = monthData.getTitleBigSpend();
            double sumBigSpend = monthData.getSumBigSpend(titleBigSpend);
            System.out.println("Самая большая трата: " + titleBigSpend + ", " + sumBigSpend);

            System.out.println("Прибыль в месяце: " + monthData.getProfit());
        }
    }

    public void printInfoYearReport(int year) {
        if(!yearDataMap.containsKey(year)){
            System.out.println("По указанному году данных нет");
            return;
        }

        YearData yearData = yearDataMap.get(year);

        if(yearData.getMonthDataMap() == null){
            System.out.println("Данные по месяцам отсутствуют");
            return;
        }

        System.out.println("Рассматриваемый год: " + year );

        System.out.println("Прибыль по месяцам: ");

        for (int i = 0; i < yearData.getMonthDataMap().size(); i++) {
            System.out.println(Month.of(i + 1) + " : " + yearData.getMonthProfit(i + 1));
        }

        System.out.println("Средний расход за все месяцы в году: " + yearData.getAverageExpense());
        System.out.println("Средний доход за все месяцы в году: " + yearData.getAverageIncome());
    }

}
