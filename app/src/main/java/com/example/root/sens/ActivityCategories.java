package com.example.root.sens;

public enum ActivityCategories {
    Søvn(0),
    Stå(1),
    Gang(2),
    Træning(4),
    Cykling(3),
    Skridt(5);

    private int dates;

    public int getValue(){ return this.dates; }

    ActivityCategories(int dates){ this.dates = dates; }
}