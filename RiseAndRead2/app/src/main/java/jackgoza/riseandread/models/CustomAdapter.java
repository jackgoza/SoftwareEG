package jackgoza.riseandread.models;

/**
 * Created by jackg on 3/12/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import jackgoza.riseandread.R;

public class CustomAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    String[] result;
    Context context;
    int[] imageId;

    public CustomAdapter(Context cntx, String[] displayList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result = displayList;
        context = cntx;
        imageId = prgmImages;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_list, null);
        holder.tv = (TextView) rowView.findViewById(R.id.customListText);
        holder.img = (ImageView) rowView.findViewById(R.id.customListImage);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);
        /*
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
            }
        });
        */
        return rowView;
    }

    public class Holder {
        TextView tv;
        ImageView img;
    }

}