package com.etuloser.padma.rohit.inclass07;

import java.util.Comparator;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Rohit on 2/27/2017.
 */

public class CustomObject extends RealmObject implements Comparable<CustomObject> {


    private String _id;
    @Ignore
    private String uid;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    String note,priority, update_time, status;

    public CustomObject() {
    }

    public CustomObject(String note, String priority, String update_time, String status) {
        this.note = note;
        this.priority = priority;
        this.update_time = update_time;
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString();
    }


    public static Comparator<CustomObject> comparatortime = new Comparator<CustomObject>() {
        @Override
        public int compare(CustomObject o1, CustomObject o2) {
            return o2.getUpdate_time().compareTo(o1.getUpdate_time());

        }
    };

    public static Comparator<CustomObject> comparatorPrior = new Comparator<CustomObject>() {
        @Override
        public int compare(CustomObject o1, CustomObject o2) {
            return o1.getPriority().compareTo(o2.getPriority());

        }
    };

    @Override
    public int compareTo(CustomObject o) {
        return 0;
    }
}
