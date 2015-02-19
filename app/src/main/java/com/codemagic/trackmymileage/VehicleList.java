package com.codemagic.trackmymileage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.codemagic.TrackMyMileage.database.dao.DaoMaster;
import com.codemagic.TrackMyMileage.database.dao.DaoSession;
import com.codemagic.TrackMyMileage.database.dao.Vehicle;
import com.codemagic.TrackMyMileage.database.dao.VehicleDao;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;


public class VehicleList extends ActionBarActivity {

    ListView lv;
    ArrayList<Vehicle>  list = new ArrayList<Vehicle>();
    private List<Vehicle> vehicles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_list);
        lv = (ListView) findViewById(R.id.vehicleList);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "vehicle-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        VehicleDao vehicleDao = daoSession.getVehicleDao();

        this.vehicles = vehicleDao.queryBuilder()
                .where(VehicleDao.Properties.Id.isNotNull()).list();
        Toast.makeText(this, "List size: " + String.valueOf(this.vehicles.size()), Toast.LENGTH_LONG).show();

        ArrayList<String> ids = new ArrayList<>();

        for (Vehicle vehicle:vehicles) {
            ids.add("ID: " + String.valueOf(vehicle.getId()) + "Vehicle: " + vehicle.getVehicleName());
        }

        Adapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ids);
        lv.setAdapter((android.widget.ListAdapter) adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vehicle_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
