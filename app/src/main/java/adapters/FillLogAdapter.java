package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codemagic.TrackMyMileage.database.dao.FillLog;
import com.codemagic.trackmymileage.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Formatter;

public class FillLogAdapter extends RecyclerView.Adapter<FillLogAdapter.ViewHolder> {

    private ArrayList<FillLog> mData;

    public FillLogAdapter(Context context, int resource, ArrayList<FillLog> data) {
        mData = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mileage_card, parent, false);

        // set the view's size, margins. paddings and layout params

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        DecimalFormat decFormat = new DecimalFormat("##.00");
        double gallons = mData.get(position).getGallons();
        double pricePer = mData.get(position).getPricePerGallon();
        double cost = (gallons * pricePer);

        holder.dateTV.setText(mData.get(position).getFillDate().toString());
        holder.mileageTV.setText(mData.get(position).getCurMiles() + " miles");
        holder.gallonsTV.setText(decFormat.format(gallons) + " gals");
        holder.priceTV.setText("$" + decFormat.format(pricePer) + "/gal");
        holder.totalCostTV.setText("$" + decFormat.format(cost));
        holder.mpgTV.setText(decFormat.format(mData.get(position).getMpg()) + "mpg");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Views for the list rows
        public TextView dateTV, mileageTV, gallonsTV, priceTV, totalCostTV, mpgTV;
        public ViewHolder (View v) {
            super(v);
            dateTV = (TextView) v.findViewById(R.id.dateTV);
            mileageTV = (TextView) v.findViewById(R.id.milesTV);
            gallonsTV = (TextView) v.findViewById(R.id.gallonsTV);
            priceTV = (TextView) v.findViewById(R.id.gallonPriceTV);
            totalCostTV = (TextView) v.findViewById(R.id.totalCostTV);
            mpgTV = (TextView) v.findViewById(R.id.mpgTV);
        }
    }
}
