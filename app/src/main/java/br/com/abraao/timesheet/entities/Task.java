package br.com.abraao.timesheet.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by abraao on 10/23/15.
 */
@Table(name = "task")
public class Task extends Model {

    @Column(name = "Name")
    public String name;

    @Column(name = "Code")
    public String code;

    @Column(name = "client")
    public Client client;

    @Override
    public String toString() {
        return name;
    }
}
