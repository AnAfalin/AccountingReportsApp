package data;

import records.YearlyRecord;

import java.util.*;

public class YearData {
    private List<YearlyRecord> recordListYear;
    private Map<Byte, MonthData> monthDataMap = new HashMap<>();

    public YearData() {
    }

    public YearData(List<YearlyRecord> recordListYear) {
        this.recordListYear = recordListYear;
    }

    public void setRecordListYear(List<YearlyRecord> recordListYear) {
        this.recordListYear = recordListYear;
    }

    public Map<Byte, MonthData> getMonthDataMap() {
        return monthDataMap;
    }

    public void addRecordMonthData(Byte numberMonth, MonthData monthData){
        monthDataMap.put(numberMonth, monthData);
    }

    public double getAverageIncome() {
        double averageIncome = 0;
        Set<Integer> month = new HashSet<>();
        for (YearlyRecord yearlyRecord : recordListYear) {
            month.add(yearlyRecord.getMonth());
            if (!yearlyRecord.getIsExpense()) {
                averageIncome += yearlyRecord.getAmount();
            }
        }
        return averageIncome / month.size();
    }

    public double getAverageExpense() {
        double averageExpense = 0;
        Set<Integer> month = new HashSet<>();
        for (YearlyRecord yearlyRecord : recordListYear) {
            month.add(yearlyRecord.getMonth());
            if (yearlyRecord.getIsExpense()) {
                averageExpense += yearlyRecord.getAmount();
            }
        }
        return averageExpense / month.size();
    }

    public double getProfitByMonth(int month){
        double sumExpense = 0;
        double sumNotExpense= 0;
        for (YearlyRecord yearlyRecord : recordListYear) {
            if(yearlyRecord.getMonth() == month){
               if(yearlyRecord.getIsExpense()) {
                   sumExpense = yearlyRecord.getAmount();
               }else {
                   sumNotExpense = yearlyRecord.getAmount();
               }
            }
        }
        return sumNotExpense - sumExpense;
    }

    public List<YearlyRecord> getRecordListYear() {
        return recordListYear;
    }
}
