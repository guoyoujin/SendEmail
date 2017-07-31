package com.trycatch.sendemail.vo;

/**
 * 在此写用途
 *
 * @FileName: com.trycatch.sendemail.vo.UserEmail.java
 * @author: guoyoujin
 * @mail: guoyoujin123@gmail.com
 * @date: 2017-07-26 17:34
 * @version: V1.0 <描述当前版本功能>
 */

public class UserEmail {
    
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String addrerss;
    private boolean isChecked;
    private int sendState;
    private int row;
    private int sheetIndex;

    @Override
    public String toString() {
        return "UserEmail{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", addrerss='" + addrerss + '\'' +
                ", isChecked=" + isChecked +
                ", sendState=" + sendState +
                ", row=" + row +
                ", sheetIndex=" + sheetIndex +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddrerss() {
        return addrerss;
    }

    public void setAddrerss(String addrerss) {
        this.addrerss = addrerss;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getSendState() {
        return sendState;
    }

    public void setSendState(int sendState) {
        this.sendState = sendState;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }
}
