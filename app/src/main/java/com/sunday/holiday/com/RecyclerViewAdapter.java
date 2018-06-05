package com.sunday.holiday.com;

/**
 * Created by libin on 6/1/2018.
 */
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView> {
    String date1,date;

    private List<String> list;
    Context context;
public static  String qualification;
    public static ArrayList<QualificationModel1> list_n= new ArrayList<QualificationModel1>();
    public static QualificationModel1 qm1;
    public class MyView extends RecyclerView.ViewHolder {
        public EditText edt_qual,edt_position,edt_cntry,edt_from_date1,edt_to_date1,edt_experince1;
String comp_name,position,country,frm_date,to_date,exprnce;
        public MyView(View view) {
            super(view);
            edt_qual = (EditText) view.findViewById(R.id.edt_comapny_name);
            edt_position = (EditText) view.findViewById(R.id.edt_position);
            edt_cntry = (EditText) view.findViewById(R.id.edt_cntry);
            edt_from_date1 = (EditText) view.findViewById(R.id.edt_from_date1);
            edt_to_date1 = (EditText) view.findViewById(R.id.edt_to_date1);
            edt_experince1= (EditText) view.findViewById(R.id.edt_experince1);

        }
    }


    public RecyclerViewAdapter(List<String> horizontalList,Context context) {
        this.list = horizontalList;
        this.context=context;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_work_layout, parent, false);

        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        qm1 = new QualificationModel1();
      /*  holder.edt_from_date1.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                qm1.setFrm_date(holder.edt_from_date1.getText().toString());
                //    System.out.println("<<<<<<<<<<<<<<<libin>>>>>>>>>>>>>" + list_n.get(position).getPosition());

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        holder.edt_to_date1.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                qm1.setTo_date(holder.edt_to_date1.getText().toString());
                //    System.out.println("<<<<<<<<<<<<<<<libin>>>>>>>>>>>>>" + list_n.get(position).getPosition());

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });*/
        holder.edt_qual.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                qm1.setComp_name(holder.edt_qual.getText().toString());
              //  System.out.println("<<<<<<<<<<<<<<<libin>>>>>>>>>>>>>" + list_n.get(position).getComp_name());

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });


        holder.edt_position.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                qm1.setPosition(holder.edt_position.getText().toString());
            //    System.out.println("<<<<<<<<<<<<<<<libin>>>>>>>>>>>>>" + list_n.get(position).getPosition());

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        holder.edt_cntry.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                qm1.setCountry(holder.edt_cntry.getText().toString());
              //  System.out.println("<<<<<<<<<<<<<<<libin>>>>>>>>>>>>>" + list_n.get(position).getCountry());

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        holder.edt_experince1.addTextChangedListener(new TextWatcher() {

    public void afterTextChanged(Editable s) {
        qm1.setExprnce(holder.edt_experince1.getText().toString());
        //  System.out.println("<<<<<<<<<<<<<<<libin>>>>>>>>>>>>>" + list_n.get(position).getCountry());

    }

    public void beforeTextChanged(CharSequence s, int start,
                                  int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start,
                              int before, int count) {

    }
});
        holder.edt_from_date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                     date1 = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1)
                                + "/" + String.valueOf(year);
                        holder.edt_from_date1.setText(date1);
                        qm1.setFrm_date(date1);

                    }

                }, yy, mm, dd);
                datePicker.show();

            }
        });

        holder.edt_to_date1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1)
                                + "/" + String.valueOf(year);
                        holder.edt_to_date1.setText(date);
                        qm1.setTo_date(date);

                    }
                }, yy, mm, dd);
                datePicker.show();

            }
        });
        list_n.add(qm1);

        //  notifyDataSetChanged();
        //System.out.println("<<<<<<<<<<<<<<<libin>>>>>>>>>>>>>" + list_n.get(position).getTo_date());

   // String s= holder.edt_qual.getText().ge

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}