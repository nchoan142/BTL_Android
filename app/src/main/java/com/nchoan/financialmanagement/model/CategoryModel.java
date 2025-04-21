package com.nchoan.financialmanagement.model;

public class CategoryModel {
    private String categoryName, categoryType;

    public CategoryModel(String categoryName, String categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }
}