package br.com.abraao.timesheet.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.abraao.timesheet.entities.AbstractEntity;

/**
 * Created by abraao on 10/22/15.
 */
public abstract class Repository<T extends AbstractEntity> {

    public static final String KEY_ID = "ID";

    public T save(T entity) {

        SQLiteDatabase db = ConnectionPool.instance.getConnection().getWritableDatabase();

        ContentValues values = getSaveValues(entity);

        entity.setRowId(db.insert(getTableName(), null, values));

        return entity;
    }

    public void update(T entity) {

        SQLiteDatabase db = ConnectionPool.instance.getConnection().getWritableDatabase();

        db.update(getTableName(), getUpdateValues(entity), getUpdateClause(entity), getUpdateParams(entity));

    }

    public void delete(T entity) {

        SQLiteDatabase db = ConnectionPool.instance.getConnection().getWritableDatabase();

        db.delete(getTableName(), getDeleteClause(entity), getDeleteParams(entity));
    }

    public RepositoryCursor<T> list() {
        SQLiteDatabase db = ConnectionPool.instance.getConnection().getReadableDatabase();

        //db.query (getTableName(), getColumnsName(), getSelectClause(), getSelectParams(), getGroupBy(), getHaving(), getOrderBy(), getLimit());
        Cursor cursor = db.rawQuery(getSelection(), getSelectionParams());
        if(cursor != null)
            cursor.moveToFirst();

        return new RepositoryCursor<T>(this, cursor);
    }

    public T get(long rowId) {
        SQLiteDatabase db = ConnectionPool.instance.getConnection().getReadableDatabase();

        //db.query (getTableName(), getColumnsName(), getSelectClause(), getSelectParams(), getGroupBy(), getHaving(), getOrderBy(), getLimit());
        Cursor cursor = db.rawQuery("select * from " + getTableName() + " where " + KEY_ID + " = ?", new String[]{""+rowId});
        if(cursor != null)
            cursor.moveToFirst();

        return processEntity(cursor);
    }

    public abstract String getSelection();
    public abstract String[] getSelectionParams();

    public abstract String getTableName();
    public abstract ContentValues getSaveValues(T entity);
    public abstract ContentValues getUpdateValues(T entity);
    public abstract String getUpdateClause(T entity);
    public abstract String[] getUpdateParams(T entity);
    public abstract String getDeleteClause(T entity);
    public abstract String[] getDeleteParams(T entity);

    public abstract T processEntity(Cursor cursor);

    class RepositoryCursor<T extends AbstractEntity> {

        Cursor cursor;
        Repository<T> repo;

        public RepositoryCursor(Repository<T> repo, Cursor cursor) {
            this.cursor = cursor;
            this.repo = repo;
        }

        public T next() {
            cursor.moveToNext();
            return repo.processEntity(cursor);
        }
    }
}
