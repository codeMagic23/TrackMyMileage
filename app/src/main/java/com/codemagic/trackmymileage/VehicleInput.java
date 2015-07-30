package com.codemagic.trackmymileage;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.codemagic.TrackMyMileageDB.database.dao.DaoMaster;
import com.codemagic.TrackMyMileageDB.database.dao.DaoSession;
import com.codemagic.TrackMyMileageDB.database.dao.Vehicle;
import com.codemagic.TrackMyMileageDB.database.dao.VehicleDao;


public class VehicleInput extends ActionBarActivity {

    private SQLiteDatabase db;

    EditText makeET;
    EditText yearET;
    EditText engTypeET;
    EditText vehicleID;
    EditText vehicleNameET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_input);

     //   vehicleID = (EditText) findViewById(R.id.vehicleID);
        makeET = (EditText) findViewById(R.id.make);
        yearET = (EditText) findViewById(R.id.year);
        engTypeET = (EditText) findViewById(R.id.engType);
        vehicleNameET = (EditText) findViewById(R.id.vehicleName);

    }

    public void saveVehicleInfo(View v) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "vehicle-db", null);
   //     new DaoMaster.DevOpenHelper(v.getContext(), "vehicle-db", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        VehicleDao vehicleDao = daoSession.getVehicleDao();

      //  String name = vehicleID.getText().toString();
        String make = makeET.getText().toString();
        String vehicleName = vehicleNameET.getText().toString();
        int year = Integer.parseInt(yearET.getText().toString());
        String engType = engTypeET.getText().toString();
        Vehicle vehicle = new Vehicle(null,vehicleName, make, year, engType);
        vehicleDao.insert(vehicle);
        Log.d("TAG", "Inserted new vehicle, ID: " + vehicle.getId());

        Intent i = new Intent(v.getContext(),VehicleList.class);
        startActivity(i);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vehicle_input, menu);
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