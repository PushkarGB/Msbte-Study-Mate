package com.example.quizapp.Model;

public class Units {
    String unitName;
   String unitNo;
    int weight;

    public Units(String unitName, int weight, int unitNo) {
        this.unitName = unitName;
        this.weight = weight;
        this.unitNo = "unit" + unitNo;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(int unitNo) {
        this.unitNo = "unit" + unitNo;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
