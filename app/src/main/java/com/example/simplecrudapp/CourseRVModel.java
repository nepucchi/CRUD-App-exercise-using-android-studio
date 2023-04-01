package com.example.simplecrudapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CourseRVModel implements Parcelable {
    private String coursename;
    private String courseprice;
    private String coursedesc;
    private String coursefor;
    private String courseimg;
    private String courselink;
    private String courseid;

    public CourseRVModel() {}//biar ga error aja di firebase

    public CourseRVModel(String coursename, String courseprice, String coursedesc, String coursefor, String courseimg, String courselink, String courseid) {
        this.coursename = coursename;
        this.courseprice = courseprice;
        this.coursedesc = coursedesc;
        this.coursefor = coursefor;
        this.courseimg = courseimg;
        this.courselink = courselink;
        this.courseid = courseid;
    }

    protected CourseRVModel(Parcel in) {
        coursename = in.readString();
        courseprice = in.readString();
        coursedesc = in.readString();
        coursefor = in.readString();
        courseimg = in.readString();
        courselink = in.readString();
        courseid = in.readString();
    }

    public static final Creator<CourseRVModel> CREATOR = new Creator<CourseRVModel>() {
        @Override
        public CourseRVModel createFromParcel(Parcel in) {
            return new CourseRVModel(in);
        }

        @Override
        public CourseRVModel[] newArray(int size) {
            return new CourseRVModel[size];
        }
    };

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCourseprice() {
        return courseprice;
    }

    public void setCourseprice(String courseprice) {
        this.courseprice = courseprice;
    }

    public String getCoursedesc() {
        return coursedesc;
    }

    public void setCoursedesc(String coursedesc) {
        this.coursedesc = coursedesc;
    }

    public String getCoursefor() {
        return coursefor;
    }

    public void setCoursefor(String coursefor) {
        this.coursefor = coursefor;
    }

    public String getCourseimg() {
        return courseimg;
    }

    public void setCourseimg(String courseimg) {
        this.courseimg = courseimg;
    }

    public String getCourselink() {
        return courselink;
    }

    public void setCourselink(String courselink) {
        this.courselink = courselink;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(coursename);
        parcel.writeString(courseprice);
        parcel.writeString(coursedesc);
        parcel.writeString(coursefor);
        parcel.writeString(courseimg);
        parcel.writeString(courselink);
        parcel.writeString(courseid);
    }
}
