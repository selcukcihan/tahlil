package com.selcukcihan.android.tahlil;

/**
 * Created by Selcuk on 16.2.2016.
 */
public class TestResult {


    private String mName;
    private String mUnit;
    private Float mValue;
    private Float mLowerBound;
    private Float mUpperBound;

    public TestResult(String name, String unit, Float value, Float lowerBound, Float upperBound) {
        mName = name;
        mUnit = unit;
        mValue = value;
        mLowerBound = lowerBound;
        mUpperBound = upperBound;
    }

    public boolean Normal () { return mValue >= mLowerBound && mValue <= mUpperBound; }

    @Override
    public String toString() {
        return "todo";
    }
}
