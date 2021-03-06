package com.zy.mvp.database.manager;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: Created by yong on 2019/9/9 12:22.
 */
public class MigrationHelper {

    private static MigrationHelper instance;

    public static MigrationHelper getInstance() {
        if (instance == null) {
            instance = new MigrationHelper();
        }
        return instance;
    }

    /**
     * 获取列的名字
     *
     * @param db
     * @param tableName
     * @return
     */
    private static List<String> getColumns(Database db, String tableName) {
        List<String> columns = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 1", null);
            if (cursor != null) {
                columns = new ArrayList<>(Arrays.asList(cursor.getColumnNames()));
            }
        } catch (Exception e) {
            Log.e(tableName, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return columns;
    }


    /**
     * 数据库迁移
     *
     * @param db
     * @param daoClasses
     */
    @SafeVarargs
    public final void migrate(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        //生成临时表，复制表数据
        generateTempTables(db, daoClasses);
//        DaoMaster.dropAllTables(db, true);
//        DaoMaster.createAllTables(db, false);
        //恢复数据
        restoreData(db, daoClasses);
    }

    /**
     * 生成临时表，复制表数据
     *
     * @param db
     * @param daoClasses
     */
    @SafeVarargs
    private final void generateTempTables(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String divider = "";
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");

            db.beginTransaction();
            db.execSQL("PRAGMA foreign_keys=off;");
            db.execSQL("ALTER TABLE " + tableName + " RENAME TO " + tempTableName + ";");
            db.execSQL("PRAGMA foreign_keys=on;");
            db.setTransactionSuccessful();
            db.endTransaction();
//            ArrayList<String> properties = new ArrayList<>();
//            StringBuilder createTableStringBuilder = new StringBuilder();
//
//            createTableStringBuilder.append("CREATE TABLE ").append(tempTableName).append(" (");
//            for (int j = 0; j < daoConfig.properties.length; j++) {
//                String columnName = daoConfig.properties[j].columnName;
//                if (getColumns(db, tableName).contains(columnName)) {
//                    properties.add("\"" + columnName + "\"");
//                    String type = null;
//                    try {
//                        type = getTypeByClass(daoConfig.properties[j].type);
//                    } catch (Exception exception) {
//                        exception.printStackTrace();
//                    }
//                    createTableStringBuilder.append(divider).append("\"" + columnName + "\"").append(" ").append(type);
//                    if (daoConfig.properties[j].primaryKey) {
//                        createTableStringBuilder.append(" PRIMARY KEY");
//                    }
//                    divider = ",";
//                }
//            }
//
//            createTableStringBuilder.append(");");
//
//            Log.e("DBMigrationHelper", "generateTempTables: sql:" + createTableStringBuilder.toString());
//            db.execSQL(createTableStringBuilder.toString());
//
//            StringBuilder insertTableStringBuilder = new StringBuilder();
//            insertTableStringBuilder.append("INSERT INTO ").append(tempTableName).append(" (");
//            insertTableStringBuilder.append(TextUtils.join(",", properties));
//            insertTableStringBuilder.append(") SELECT ");
//            insertTableStringBuilder.append(TextUtils.join(",", properties));
//            insertTableStringBuilder.append(" FROM ").append(tableName).append(";");
//
//            db.execSQL(insertTableStringBuilder.toString());
//            Log.e("DBMigrationHelper", "generateTempTables: sql:" + insertTableStringBuilder.toString());
        }
    }

    /**
     * 恢复数据
     *
     * @param db
     * @param daoClasses
     */
    @SafeVarargs
    private final void restoreData(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            List<String> properties = new ArrayList<String>();
            List<String> propertiesQuery = new ArrayList<String>();
            for (int j = 0; j < daoConfig.properties.length; j++) {
                String columnName = daoConfig.properties[j].columnName;
                if (getColumns(db, tempTableName).contains(columnName)) {
                    properties.add("\"" + columnName + "\"");
                    propertiesQuery.add("\"" + columnName + "\"");
                } else {
                    try {
                        if (getTypeByClass(daoConfig.properties[j].type).equals("INTEGER")) {
                            propertiesQuery.add("0 as \"" + columnName + "\"");
                            properties.add("\"" + columnName + "\"");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            StringBuilder insertTableStringBuilder = new StringBuilder();
            insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(") SELECT ");
            insertTableStringBuilder.append(TextUtils.join(",", propertiesQuery));
            insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");

            StringBuilder dropTableStringBuilder = new StringBuilder();
            dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);

            db.execSQL(insertTableStringBuilder.toString());
            db.execSQL(dropTableStringBuilder.toString());

            Log.e("DBMigrationHelper", "restoreData: sql:" + insertTableStringBuilder.toString());
            Log.e("DBMigrationHelper", "restoreData: sql:" + dropTableStringBuilder.toString());
        }
    }

    /**
     * @param type
     * @return
     * @throws Exception
     */
    private String getTypeByClass(Class type) throws Exception {
        if (type.equals(String.class)) {
            return "TEXT";
        }

        if (type.equals(Long.class) || type.equals(Integer.class) || type.equals(Double.class) ||
                type.equals(long.class) || type.equals(int.class) || type.equals(double.class)) {
            return "INTEGER";
        }

        if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            return "BOOLEAN";
        }

        Exception exception = new Exception(("migration helper - class doesn't match with the" +
                " current parameters").concat(" - Class: ").concat(type.toString()));
        exception.printStackTrace();
        throw exception;
    }
}
