package com.xiwi.aide.adapter;

import com.xiwi.aide.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiwi.aide.model.thesaurus;
import java.util.ArrayList;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ListAdapter extends BaseAdapter {

    private LayoutInflater inflater = null;
    private ArrayList<thesaurus> list = null;
    private int currentItem = -1;
    private Context context = null;

    public ListAdapter(Context context, ArrayList<thesaurus> list) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public thesaurus getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        System.out.println("getCount:" + getCount());
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.thesaurus_item, null);
            holder.item_linear = (LinearLayout) convertView.findViewById(R.id.item_linear);
            holder.item_linear_header = (LinearLayout) convertView.findViewById(R.id.item_linear_header);
            holder.item_keyword_text = (TextView) convertView.findViewById(R.id.item_keyword_text);   
            //holder.item_but = (Button) convertView.findViewById(R.id.item_but);
            holder.item_linear_body = (LinearLayout) convertView.findViewById(R.id.item_linear_body);
            holder.item_reoly_text = (TextView) convertView.findViewById(R.id.item_reoly_text);
            //System.out.println("getView is ok");
            convertView.setTag(holder);           
        } else {
            holder = (ViewHolder) convertView.getTag();
            //System.out.println("getTag");
        }
        
        if (currentItem == position) {
            holder.item_linear_body.setVisibility(View.VISIBLE);
        } else {
            holder.item_linear_body.setVisibility(View.GONE);
        }
        
       
            
        
        holder.item_linear_header.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (Integer)v.getTag();
                //System.out.println("tag: " + tag);
                //System.out.println("item: " + currentItem);
                if (tag == currentItem) {
                    currentItem = -1;
                } else {
                    currentItem = tag;
                }
                //System.out.println("okokokok");
                notifyDataSetChanged();
                //System.out.println("over");
                
            }
        });
        
        thesaurus thes = list.get(position);   
        holder.item_keyword_text.setText(thes.getKeyword());
        holder.item_reoly_text.setText(thes.getReply());
        
        
        //System.out.println("position0: " + position);
        holder.item_linear_header.setTag(position);
        
        return convertView;
    }

    class ViewHolder {
        public LinearLayout item_linear;
        public LinearLayout item_linear_header;
        public TextView item_keyword_text;
        public Button item_but;
        public LinearLayout item_linear_body;
        public TextView item_reoly_text;
        

    }

}
