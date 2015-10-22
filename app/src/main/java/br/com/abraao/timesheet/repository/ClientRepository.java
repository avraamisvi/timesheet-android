package br.com.abraao.timesheet.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.abraao.timesheet.entities.Client;

/**
 * Created by abraao on 10/20/15.
 */
public class ClientRepository extends Repository<Client>{

    public static final String NAME = "name";
    public static final String CODE = "code";
    public static final String CLIENTE = "cliente";

    public static final String CREATE = "CREATE TABLE " + CLIENTE + " (" + KEY_ID + " INTEGER PRIMARY KEY, " + NAME + " TEXT," + CODE + " TEXT);";

    public static void create(SQLiteDatabase db) {
        db.execSQL(CREATE);
    }

    /*public List<Client> getClients() {
        List<Client> ITEMS = new ArrayList<Client>();

        ITEMS.add(new Client("1", "Cli 1", "1"));
        ITEMS.add(new Client("2", "Cli 2", "2"));
        ITEMS.add(new Client("3", "Cli 3", "3"));

        return ITEMS;
    }*/

    @Override
    public String getSelection() {
        return null;
    }

    @Override
    public String[] getSelectionParams() {
        return new String[0];
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public ContentValues getSaveValues(Client entity) {
        return null;
    }

    @Override
    public ContentValues getUpdateValues(Client entity) {
        return null;
    }

    @Override
    public String getUpdateClause(Client entity) {
        return null;
    }

    @Override
    public String[] getUpdateParams(Client entity) {
        return new String[0];
    }

    @Override
    public String getDeleteClause(Client entity) {
        return null;
    }

    @Override
    public String[] getDeleteParams(Client entity) {
        return new String[0];
    }

    @Override
    public Client processEntity(Cursor cursor) {
        return null;
    }
}
