package com.work.entity;

public class Account {
    private int accountId;
    private int account;
    private int userId;

    public Account() {
    }

    public Account(int account, int userId) {
        this.account = account;
        this.userId = userId;
    }

    public Account(int accountId, int account, int userId) {
        this.accountId = accountId;
        this.account = account;
        this.userId = userId;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getAccount() {
        return account;
    }

    public int getUserId() {
        return userId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return accountId + " " + account + " " + userId;
    }
}
