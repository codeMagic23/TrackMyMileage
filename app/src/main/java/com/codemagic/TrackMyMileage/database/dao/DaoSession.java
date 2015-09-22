package com.codemagic.TrackMyMileage.database.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.codemagic.TrackMyMileage.database.dao.Vehicle;
import com.codemagic.TrackMyMileage.database.dao.FillLog;

import com.codemagic.TrackMyMileage.database.dao.VehicleDao;
import com.codemagic.TrackMyMileage.database.dao.FillLogDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig vehicleDaoConfig;
    private final DaoConfig fillLogDaoConfig;

    private final VehicleDao vehicleDao;
    private final FillLogDao fillLogDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        vehicleDaoConfig = daoConfigMap.get(VehicleDao.class).clone();
        vehicleDaoConfig.initIdentityScope(type);

        fillLogDaoConfig = daoConfigMap.get(FillLogDao.class).clone();
        fillLogDaoConfig.initIdentityScope(type);

        vehicleDao = new VehicleDao(vehicleDaoConfig, this);
        fillLogDao = new FillLogDao(fillLogDaoConfig, this);

        registerDao(Vehicle.class, vehicleDao);
        registerDao(FillLog.class, fillLogDao);
    }
    
    public void clear() {
        vehicleDaoConfig.getIdentityScope().clear();
        fillLogDaoConfig.getIdentityScope().clear();
    }

    public VehicleDao getVehicleDao() {
        return vehicleDao;
    }

    public FillLogDao getFillLogDao() {
        return fillLogDao;
    }

}