package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.codemagic.trackmymileage.R;

import java.util.ArrayList;

/**
 * Created by root on 7/29/15.
 */
public class FillLogAdapter extends RecyclerView.Adapter<FillLogAdapter.ViewHolder> {

    private ArrayList<String> mData;

    public FillLogAdapter(Context context, int resource, ArrayList<String> data) {
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
        holder.mTextView.setText(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Views for the list rows
        public TextView mTextView;
        public ViewHolder (View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.info_text);
        }
    }
}
