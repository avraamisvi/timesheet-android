package br.com.abraao.timesheet.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by abraao on 10/23/15.
 */
@Table(name = "entry")
public class Entry extends Model {

    @Column(name = "Name")
    public String name;

    @Column(name = "description")
    public String description;

    @Column(name = "task")
    public Task task;

    @Column(name = "startHour")
    public Integer startHour;

    @Column(name = "startMinute")
    public Integer startMinute;

    @Column(name = "endHour")
    public Integer endHour;

    @Column(name = "endMinute")
    public Integer endMinute;

    @Column(name = "date")
    public long date;

    @Column(name = "day")
    public long day;

    @Column(name = "month")
    public long month;

    @Column(name = "year")
    public long year;
}