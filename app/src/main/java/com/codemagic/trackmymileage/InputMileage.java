package com.codemagic.trackmymileage;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codemagic.TrackMyMileageDB.database.dao.DaoMaster;
import com.codemagic.TrackMyMileageDB.database.dao.DaoSession;
import com.codemagic.TrackMyMileageDB.database.dao.FillLog;
import com.codemagic.TrackMyMileageDB.database.dao.FillLogDao;
import com.codemagic.TrackMyMileageDB.database.dao.VehicleDao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;


public class InputMileage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_mileage);

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

        long mileage = Long.valueOf(((EditText) findViewById(R.id.mileage)).getText().toString());
        double gallons = Double.valueOf(((EditText) findViewById(R.id.gallons)).getText().toString());
        double mpg = 0;

        if (fillDao.count() > 0) {
            Log.i("TAG", "Count: " + fillDao.count());
            // ToDo get the mileage from the last log
            // get the last mileage entered to obtain mpg
            List<FillLog> prevEntry = fillDao.queryBuilder().where(FillLogDao.Properties.CurMiles.isNotNull()).limit(1).list();
            long prevMileage = 0;

            if (prevEntry != null) {
                for (FillLog entry : prevEntry) {
                    prevMileage = entry.getCurMiles();
                }

                mpg = Math.round((mileage - prevMileage) / gallons);
                Toast.makeText(this, "MPG: " + mpg, Toast.LENGTH_SHORT).show();
            }
        }

        //get values from EditTexts to insert to db


        double price = Double.valueOf(((EditText) findViewById(R.id.pricePerGallon)).getText().toString());
        SimpleDateFormat format = new SimpleDateFormat("yy/M/d", Locale.US);
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

        FillLog log = new FillLog(fillDate, gallons, price, mileage, ((TextView) findViewById(R.id.vehicleName)).getText().toString(), mpg);
        Long inserted = fillDao.insert(log);

     //   Toast.makeText(v.getContext(), "Inserted: " + inserted.toString(), Toast.LENGTH_SHORT).show();

        Intent i = new Intent(v.getContext(), FillLogList.class);
        startActivity(i);
        finish();

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
}
