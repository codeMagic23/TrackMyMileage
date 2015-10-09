package com.codemagic.trackmymileage;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codemagic.TrackMyMileage.database.dao.DaoMaster;
import com.codemagic.TrackMyMileage.database.dao.DaoSession;
import com.codemagic.TrackMyMileage.database.dao.FillLog;
import com.codemagic.TrackMyMileage.database.dao.FillLogDao;
import com.codemagic.TrackMyMileage.database.dao.Vehicle;
import com.codemagic.TrackMyMileage.database.dao.VehicleDao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.support.design.widget.Snackbar.*;


public class InputMileage extends Activity implements View.OnClickListener {

    long vehicleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_mileage);
        TextView dateTV = (TextView) findViewById(R.id.date);
        TextView vehicleNameTV = (TextView) findViewById(R.id.vehicleName);
        vehicleNameTV.setOnClickListener(this);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        dateTV.setText(format.format(c.getTime()));
        vehicleNameTV.setText(getVehicle());

        Toolbar toolBar = (Toolbar) findViewById(R.id.myToolBar);
        TextView titleTV = (TextView) toolBar.findViewById(R.id.titleTV);
        titleTV.setText(getClass().getSimpleName());


        // have the first box grab focus
        if(((EditText) findViewById(R.id.mileage)).requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

    }

    public void saveFillLog(View v) {
        // will show the fill log list screen Activity
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "filllog-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        FillLogDao fillDao = daoSession.getFillLogDao();

        try {
            long mileage = Long.valueOf(((EditText) findViewById(R.id.mileage)).getText().toString());
            double gallons = Double.valueOf(((EditText) findViewById(R.id.gallons)).getText().toString());
            double mpg = 0;

            if (fillDao.count() > 0) {
                // get the last mileage entered to obtain mpg
                List<FillLog> prevEntry = fillDao.queryBuilder().where(FillLogDao.Properties.FillDate.isNotNull()).orderDesc(FillLogDao.Properties.FillDate).limit(1).list();

                long prevMileage = 0;

                if (prevEntry != null) {
                    for (FillLog entry : prevEntry) {
                        prevMileage = entry.getCurMiles();
                    }
                }

                mpg = (mileage - prevMileage) / gallons;
            }

        double price = Double.valueOf(((EditText) findViewById(R.id.pricePerGallon)).getText().toString());
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        Date fillDate = new Date();
        /*
            Date fillDate = new Date(System.currentTimeMillis());
            try {
                fillDate = format.parse(((TextView) findViewById(R.id.date)).getText().toString());
                Toast.makeText(this, "Date: " + fillDate, Toast.LENGTH_SHORT).show();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        */
        TextView tv = (TextView) findViewById(R.id.vehicleName);
        FillLog log = new FillLog(fillDate, gallons, price, mileage, vehicleId, mpg);
        long inserts = fillDao.insert(log);

        // let the calling activity know that a record was added successfully
            Intent i = new Intent();
            int result = (inserts > 0) ? RESULT_OK : RESULT_CANCELED;
            setResult(result, i);
            finish();
    } catch (NumberFormatException e) {

        Toast toast = new Toast(this);
            toast.makeText(this, "Invalid entry(s)", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private String getVehicle() {
        String vehicleName = "";

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "vehicle-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        VehicleDao vehicleDao = daoSession.getVehicleDao();

        List<Vehicle> vehicles = vehicleDao.queryBuilder().where(VehicleDao.Properties.Id.isNotNull()).orderAsc(VehicleDao.Properties.Id).limit(1).list();

        for (Vehicle vehicle : vehicles) {
            vehicleName = vehicle.getVehicleName();
            vehicleId = vehicle.getId();
        }

        return vehicleName;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input_mileage, menu);
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

    public void addVehicle(View v) {
        Intent i = new Intent(v.getContext(), VehicleInput.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vehicleName:
                Toast.makeText(InputMileage.this, "clicked!", Toast.LENGTH_SHORT).show();
        }
    }
}
