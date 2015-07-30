package com.codemagic.trackmymileage;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class DashBoard extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board);
        Log.i("TAG", "onCreate() of DashBoard...");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dash_board, menu);
        return true;
    }

    public void doAction(View v) {
        int viewId = v.getId();
        String className = "";
        Intent i = null;
        switch (viewId) {
            case R.id.mileageLog:
                i = new Intent(v.getContext(), FillLogList.class);
                break;
            case R.id.enterMileage:
                i = new Intent(v.getContext(), InputMileage.class);
                break;
            case R.id.vehicleList:
                i = new Intent(v.getContext(), VehicleList.class);
                break;
            case R.id.addVehicle:
                i = new Intent(v.getContext(), VehicleInput.class);
                break;
        }
       // try {
       //     Toast.makeText(v.getContext(), className, Toast.LENGTH_SHORT).show();
       //     Intent i = new Intent(v.getContext(), Class.forName(className)); //
            startActivity(i);
      //  } catch (ClassNotFoundException e) {
      //      Toast.makeText(v.getContext(), "That class doesn't exist!!!", Toast.LENGTH_SHORT).show();
      //  }

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
