package com.selcukcihan.android.tahlil;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Selcuk on 16.2.2016.
 */
public class TestResult implements Parcelable {


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

    public TestResult(Parcel in){
        String[] data = new String[5];

        in.readStringArray(data);
        this.mName = data[0];
        this.mUnit = data[1];
        this.mValue = Float.parseFloat(data[2]);
        this.mLowerBound = Float.parseFloat(data[3]);
        this.mUpperBound = Float.parseFloat(data[4]);
    }

    public String getName() {
        return mName;
    }

    public String getValueString() {
        return mValue + " (" + mLowerBound + " - " + mUpperBound + ")";
    }

    public boolean Normal () { return mValue >= mLowerBound && mValue <= mUpperBound; }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TestResult createFromParcel(Parcel in) {
            return new TestResult(in);
        }

        public TestResult[] newArray(int size) {
            return new TestResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.mName,
                this.mUnit,
                this.mValue.toString(),
                this.mLowerBound.toString(),
                this.mUpperBound.toString()
        });
    }
}
