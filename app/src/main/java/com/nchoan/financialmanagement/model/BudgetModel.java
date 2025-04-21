package com.nchoan.financialmanagement.model;

public class BudgetModel {
    private int userId;
    private int budgetAmount;
    private String creatingDate;

    public BudgetModel(int userId, int budgetAmount, String creatingDate) {
        this.userId = userId;
        this.budgetAmount = budgetAmount;
        this.creatingDate = creatingDate;
    }

    public int getUserId() {
        return userId;
    }

    public int getBudgetAmount() {
        return budgetAmount;
    }

    public String getCreatingDate() {
        return creatingDate;
    }
}
