package org.clear.framework.test;

public enum Gender {
    Male("1","男性"),
    Female("1","女性");
    private final String num;
    private final String gender;
    Gender(String num, String gender) {
        this.num = num;
        this.gender = gender;
    }

    public String getNum() {
        return num;
    }

    public String getGender() {
        return gender;
    }

    public static void main(String[] args) {
        System.out.println(Gender.Female.gender);
    }
}
