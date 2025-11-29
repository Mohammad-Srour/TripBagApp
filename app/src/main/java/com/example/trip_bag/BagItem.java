package com.example.trip_bag;

public class BagItem {
    private String name;
    private String Category;

    private int quantity;

    private String reminderDate;

    private boolean isPacked;

    private boolean isImportant;

    private String bagType;


    public BagItem(String name, String category, int quantity, String reminderDate, boolean isPacked, boolean isImportant, String bagType) {
        this.name = name;
        Category = category;
        this.quantity = quantity;
        this.reminderDate = reminderDate;
        this.isPacked = isPacked;
        this.isImportant = isImportant;
        this.bagType = bagType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public boolean isPacked() {
        return isPacked;
    }

    public void setPacked(boolean packed) {
        isPacked = packed;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public String getBagType() {
        return bagType;
    }

    public void setBagType(String bagType) {
        this.bagType = bagType;
    }
}
