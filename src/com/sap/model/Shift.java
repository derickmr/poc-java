package com.sap.model;

public enum Shift {

    DAY("Day"),
    LATE("Late"),
	NONE("None"),
    ANY("Any");

    String shift;

    private Shift (String shift){
        this.shift = shift;
    }

    public String getShift (){
        return shift;
    }

}
