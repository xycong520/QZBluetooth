package com.qz.qizhi;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by Administrator on 2016/1/4.
 */
public class DBUtils implements DbManager.DbUpgradeListener {
    private final String DBName = "DATABASE";
    private DbManager db;
    private int newVersion = 1;
    DbManager.DaoConfig config = new DbManager.DaoConfig();

    public DBUtils() {
        config.setDbName(DBName);
        config.setDbUpgradeListener(this);
        db = x.getDb(config);
    }

    public DbManager getDB(){
        return db;
    }
    @Override
    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {

        }
    }
}
