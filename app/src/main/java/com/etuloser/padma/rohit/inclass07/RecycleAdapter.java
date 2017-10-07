package com.etuloser.padma.rohit.inclass07;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rohit on 4/24/2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder>
{//extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    Context context;
    List<CustomObject> flist;

    public RecycleAdapter(Context context,List<CustomObject> flist) {
        this.context = context;
        this.flist=flist;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.childlayout, parent, false);
        RecycleAdapter.ViewHolder cv = new RecycleAdapter.ViewHolder(v);
        return cv;

    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final CustomObject g=flist.get(position);

        RecycleAdapter.ViewHolder item = (RecycleAdapter.ViewHolder)holder;
        // String time=fc.getDate().substring(0,19);

        DateFormat originalFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("dd, MMM ''yy ");
        Date date = null;
        try {
            date = originalFormat.parse(g.getUpdate_time().substring(0,10));
        } catch (ParseException e) {
            e.printStackTrace();
        }
       if(date!=null) {
           String formattedDate = targetFormat.format(date);
       }
        holder.tv1.setText(g.getNote());


        holder.tv2.setText(g.getPriority()+" priority");

        if(g.getPriority().equals("1"))
        {
            holder.tv2.setText("High priority");
        }
        else  if(g.getPriority().equals("2"))
        {
            holder.tv2.setText("Medium priority");
        }
        else  if(g.getPriority().equals("3"))
        {
            holder.tv2.setText("Low priority");
        }
        PrettyTime pt=new PrettyTime();
        final AlertDialog.Builder builder = new AlertDialog.Builder((MainActivity)context);

        //tv3.setText(pt.format(new Date(Long.valueOf(g.getUpdate_time()))));
        holder.tv3.setText(pt.format(new Date(Long.valueOf(g.getUpdate_time()))));
        holder.cbox.setChecked(g.getStatus().equals("0")?false:true);
        holder.cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ) {

                    builder.setTitle(R.string.completemsg);
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(
                                        DialogInterface dialog,
                                        int whichButton) {

                                   // g.setStatus("1");
                                    ((MainActivity)context).updateStatus(g,"1");                                }
                            });

                    builder.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(
                                        DialogInterface dialog,
                                        int whichButton) {
                                    dialog.cancel();
                                    dialog.dismiss();
                                }
                            });

                    builder.create().show();

                }
                else
                {
                    builder.setTitle(R.string.pendinemsg);
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(
                                        DialogInterface dialog,
                                        int whichButton) {

                                  //  g.setStatus("0");
                                    ((MainActivity) context).updateStatus(g,"0");
                                }
                            });

                    builder.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(
                                        DialogInterface dialog,
                                        int whichButton) {
                                    dialog.cancel();
                                    dialog.dismiss();
                                }
                            });

                    builder.create().show();


                }

            }
        });




        ((ViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                builder.setTitle(R.string.deletemsg);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int whichButton) {
                                ((MainActivity)(context)).deletenote(g,position);
                            }
                        });

                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int whichButton) {
                                dialog.cancel();
                                dialog.dismiss();
                            }
                        });

                builder.create().show();

                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return flist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {



        TextView tv1;
        TextView tv2;
        TextView tv3;
        CheckBox cbox;
        public ViewHolder(View v) {

            super(v);
            tv1=(TextView)v.findViewById(R.id.text1);
            tv2=(TextView)v.findViewById(R.id.text2);
            tv3=(TextView)v.findViewById(R.id.text3);
            cbox=(CheckBox)v.findViewById(R.id.checkBox);
        }
    }
}
