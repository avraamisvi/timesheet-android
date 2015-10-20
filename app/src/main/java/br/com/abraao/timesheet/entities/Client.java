package br.com.abraao.timesheet.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abraao on 10/20/15.
 */
public class Client {

    public String id;
    public String name;

    public Client(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
