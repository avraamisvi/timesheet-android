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

    @Column(name = "startminute")
    public Integer startminute;

    @Column(name = "endHour")
    public Integer endHour;

    @Column(name = "endminute")
    public Integer endminute;

    @Column(name = "date")
    public Date date;
}