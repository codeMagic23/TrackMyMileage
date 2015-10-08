package com.codemagic.trackmymileage;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.codemagic.TrackMyMileage.database.dao.DaoMaster;
import com.codemagic.TrackMyMileage.database.dao.DaoSession;
import com.codemagic.TrackMyMileage.database.dao.FillLog;
import com.codemagic.TrackMyMileage.database.dao.FillLogDao;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import adapters.FillLogAdapter;


public class FillLogList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<FillLog> data;

    private List<FillLog> fillLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_log_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        Toolbar toolBar = (Toolbar) findViewById(R.id.myToolBar);
        TextView titleTV = (TextView) toolBar.findViewById(R.id.titleTV);
        titleTV.setText(getClass().getSimpleName());

        android.support.design.widget.FloatingActionButton v = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        ColorStateList csl = new ColorStateList(new int[][]{new int[0]}, new int[]{getResources().getColor(R.color.colorPrimary)});
        v.setBackgroundTintList(csl);
//        setSupportActionBar(toolBar);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        data = getData();

        // specify an adapter
        mAdapter = new FillLogAdapter(this, R.layout.mileage_card, data);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fill_log_list, menu);
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

    private ArrayList<FillLog> getData() {
        ArrayList<FillLog> data = new ArrayList<FillLog>();

        // get data from db
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "filllog-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        FillLogDao logDao = daoSession.getFillLogDao();

        fillLogs = logDao.queryBuilder()
                .where(FillLogDao.Properties.Gallons.isNotNull()).orderDesc(FillLogDao.Properties.FillDate).list();
        DecimalFormat format = new DecimalFormat("$#.##");
        for (FillLog log:fillLogs) {
            data.add(log);
        }

        return data;

    }

    public void newFillup(View v) {
        Intent i = new Intent(v.getContext(), InputMileage.class);
        startActivity(i);
    }

    public void addVehicle(View v) {
        Intent i = new Intent(v.getContext(), VehicleInput.class);
        startActivity(i);
    }
}
