package data;

import records.MonthlyRecord;

import java.util.List;

public class MonthData {
    private final String title;
    private List<MonthlyRecord> monthlyRecordList;

    public MonthData(String title, List<MonthlyRecord> monthlyRecordList) {
        this.title = title;
        this.monthlyRecordList = monthlyRecordList;
    }

    public String getTitle() {
        return title;
    }

    public int getIncome() {
        int income = 0;
        for (MonthlyRecord record : monthlyRecordList) {
            if (!record.isExpense()) {
                income += record.getQuantity() * record.getSumOfOne();
            }
        }
        return income;
    }

    public int getExpense() {
        int expense = 0;
        for (MonthlyRecord record : monthlyRecordList) {
            if (record.isExpense()) {
                expense += record.getQuantity() * record.getSumOfOne();
            }
        }
        return expense;
    }

    public double getProfit() {
        double income = 0;
        double expense = 0;
        for (MonthlyRecord record : monthlyRecordList) {
            if (record.isExpense()) {
                expense += record.getQuantity() * record.getSumOfOne();
            } else {
                income += record.getQuantity() * record.getSumOfOne();
            }
        }
        return income - expense;
    }

    public String getTitleProfitProduct() {
        String title = "";
        double quantityPerSumOfOne = 0;
        for (MonthlyRecord monthlyRecord : monthlyRecordList) {
            if (!monthlyRecord.isExpense()) {
                double profitCurrentProduct = monthlyRecord.getQuantity() * monthlyRecord.getSumOfOne();
                if (profitCurrentProduct > quantityPerSumOfOne) {
                    quantityPerSumOfOne = profitCurrentProduct;
                    title = monthlyRecord.getItemName();
                }
            }
        }
        return title;
    }

    public double getProfitByProduct(String title) {
        for (MonthlyRecord monthlyRecord : monthlyRecordList) {
            if (!monthlyRecord.isExpense() && monthlyRecord.getItemName().equalsIgnoreCase(title)) {
                return monthlyRecord.getQuantity() * monthlyRecord.getSumOfOne();
            }
        }
        return 0.0;
    }

    public String getTitleBigSpend() {
        String title = "";
        double spendSum = 0;
        for (MonthlyRecord monthlyRecord : monthlyRecordList) {
            if (monthlyRecord.isExpense()) {
                double currentSpendSum = monthlyRecord.getQuantity() * monthlyRecord.getSumOfOne();
                if (currentSpendSum > spendSum) {
                    spendSum = currentSpendSum;
                    title = monthlyRecord.getItemName();
                }
            }
        }
        return title;
    }

    public double getSumBigSpend(String title) {
        for (MonthlyRecord monthlyRecord : monthlyRecordList) {
            if (monthlyRecord.isExpense() && monthlyRecord.getItemName().equalsIgnoreCase(title)) {
                return monthlyRecord.getQuantity() * monthlyRecord.getSumOfOne();
            }
        }
        return 0.0;
    }
}
