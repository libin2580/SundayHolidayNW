package com.sunday.holiday.com;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.google.gson.Gson;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;
import com.multispinner.MultiSelectSpinner;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kotlin.Pair;

import static com.sunday.holiday.com.Constants.BASE_URL;
import static com.sunday.holiday.com.RecyclerViewAdapter.list_n;
import static com.sunday.holiday.com.RecyclerViewAdapter.qm1;

public class MainActivity extends AppCompatActivity implements MultiSelectSpinner.OnMultipleItemsSelectedListener {

    String[] visa_status = {"No Selection", "No Visa ", "Visit Visa", "Work Visa", "Family Visa",};
    String[] notice_status = {"No Selection", "No Notice Period", "1 month", "2 month", "3 month",};

    Spinner spin_visa;
    EditText edt_dob, edt_nationality;
    CountryPicker picker;
    CountryPicker picker1;
    @BindView(R.id.radioButton1)
    RadioButton radioButton1;
    @BindView(R.id.radioButton2)
    RadioButton radioButton2;
    @BindView(R.id.radioButton3)
    RadioButton radioButton3;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.visa_spin)
    Spinner visaSpin;
    @BindView(R.id.edt_country_of_residence)
    EditText edtCountryOfResidence;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_nationality)
    EditText edtNationality;
    @BindView(R.id.radiosingle)
    RadioButton radiosingle;
    @BindView(R.id.radiomarried)
    RadioButton radiomarried;
    @BindView(R.id.radioMartial)
    RadioGroup radioMartial;
    @BindView(R.id.radioNo)
    RadioButton radioNo;
    @BindView(R.id.radioYes)
    RadioButton radioYes;
    @BindView(R.id.radioDriving)
    RadioGroup radioDriving;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.edt_passport)
    EditText edtPassport;
    @BindView(R.id.education_spin)
    Spinner educationSpin;
    @BindView(R.id.edt_cost)
    EditText edtCost;
    @BindView(R.id.edt_qualification)
    EditText edtQualification;
    @BindView(R.id.edt_spclity_certification)
    EditText edtSpclityCertification;
    @BindView(R.id.edt_comapny_name)
    EditText edtComapnyName;
    @BindView(R.id.edt_position)
    EditText edtPosition;
    @BindView(R.id.workingNo)
    RadioButton workingNo;
    @BindView(R.id.workingYes)
    RadioButton workingYes;
    @BindView(R.id.radioworking)
    RadioGroup radioworking;
    @BindView(R.id.edt_dob)
    EditText edtDob;
    @BindView(R.id.edt_religion)
    EditText edtReligion;
    @BindView(R.id.edt_job_tile)
    EditText edtJobTile;
    @BindView(R.id.edt_job_location)
    EditText edtJobLocation;
    @BindView(R.id.edt_salary_range)
    EditText edtSalaryRange;
    @BindView(R.id.edt_reamarks)
    EditText edtReamarks;
    @BindView(R.id.notice_spin)
    Spinner noticeSpin;
    @BindView(R.id.edt_from_date)
    EditText edtFromDate;
    @BindView(R.id.edt_experince)
    EditText edtExperince;
    List<Pair<String, String>> params;
    @BindView(R.id.edt_to_date)
    EditText edtToDate;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.language_spin)
    MultiSelectSpinner languageSpin;
    @BindView(R.id.prof_pic)
    ImageView profPic;
    @BindView(R.id.select)
    RelativeLayout select;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.brows)
    LinearLayout brows;
    @BindView(R.id.pdf_name)
    TextView pdfName;
    @BindView(R.id.edt_qualification1)
    EditText edtQualification1;
    @BindView(R.id.edt_spclity_certification1)
    EditText edtSpclityCertification1;
    @BindView(R.id.edt_qualification2)
    EditText edtQualification2;
    @BindView(R.id.edt_spclity_certification2)
    EditText edtSpclityCertification2;
    @BindView(R.id.edt_qualification3)
    EditText edtQualification3;
    @BindView(R.id.edt_spclity_certification3)
    EditText edtSpclityCertification3;
    @BindView(R.id.edt_comapny_name1)
    EditText edtComapnyName1;
    @BindView(R.id.edt_position1)
    EditText edtPosition1;
    @BindView(R.id.edt_comapny_name2)
    EditText edtComapnyName2;
    @BindView(R.id.edt_position2)
    EditText edtPosition2;
    @BindView(R.id.more)
    TextView more;
    @BindView(R.id.more1)
    TextView more1;
    @BindView(R.id.hsv_qualification)
    HorizontalScrollView hsvQualification;
    @BindView(R.id.hsv_previous)
    HorizontalScrollView hsvPrevious;
    @BindView(R.id.edt_experince1)
    EditText edtExperince1;
    @BindView(R.id.edt_experince2)
    EditText edtExperince2;
    @BindView(R.id.edt_experince3)
    EditText edtExperince3;
    private QualificationModel qm;
    private ArrayList<QualificationModel> qgl = new ArrayList<QualificationModel>();
    ArrayList<String> quallist;
    private LanguageModel lm;
    private ArrayList<LanguageModel> lgl = new ArrayList<LanguageModel>();
    ArrayList<String> langlist;
    MultiSelectSpinner multiSelectSpinner;
    private static final int PICTURE_RESULT4 = 2;
    private static final int CAMERA_REQUEST = 1;
    String selectedFilePath1, selectedFilePath2, selectedFilePath3, selectedFilePath4;
    Uri uri;
    int CROP_PIC1;
    RecyclerView recyclerView;
    ArrayList<String> Number;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout;
    View ChildView;
    int RecyclerViewItemPosition;
    ArrayList<String> qualifications = new ArrayList<>();
    // ArrayList<String> prev_exp=new ArrayList<>();
    ArrayList<String> spcl_qual = new ArrayList<>();
    ArrayList<String> comp_name = new ArrayList<>();
    ArrayList<String> position = new ArrayList<>();
    ArrayList<String> exprnce = new ArrayList<>();
    ArrayList<JsonModel> jsonModelArrayList;
    String csv,csd,csf,csg,cbf,cpt,cty,ctt;
    StringBuffer strBuf=new StringBuffer();
    String   user_id;
    final String[] test = new String[1];
    final ArrayList<String> list_nw1 = new ArrayList<>();
    public  static  String somejson="";
   // String sme_jsn;
ArrayList<QualificationModel2> list_n2= new ArrayList<QualificationModel2>();
  static QualificationModel2 qm2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                    (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }


        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences preferencesuser_id = getSharedPreferences("MyPref", MODE_PRIVATE);
           user_id = preferencesuser_id.getString("user_id", null);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview1);

        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // Adding items to RecyclerView.
        AddItemsToRecyclerViewArrayList();

        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(Number,MainActivity.this);

        HorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);

        recyclerView.setAdapter(RecyclerViewHorizontalAdapter);

        edtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1)
                                + "/" + String.valueOf(year);
                        edtDob.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });
        more1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hsvPrevious.scrollTo((int) hsvPrevious.getScrollX() + 700, (int) hsvPrevious.getScrollY());
            }
        });
        more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hsvQualification.scrollTo((int) hsvQualification.getScrollX() + 700, (int) hsvQualification.getScrollY());
            }
        });
        params = new ArrayList<Pair<String, String>>() {{
            add(new Pair<String, String>("userId", "2"));
            add(new Pair<String, String>("device", "android"));


        }};
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "hiiiiiiiiiiii ", Toast.LENGTH_LONG).show();

                Intent chooseImageIntent4 = ImagePicker3.getPickImageIntent(MainActivity.this);
                startActivityForResult(chooseImageIntent4, PICTURE_RESULT4);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                preferences.edit().clear().commit();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);

                finish();
            }
        });
        brows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, 1212);
            }
        });
        edtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1)
                                + "/" + String.valueOf(year);
                        edtFromDate.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });
        edtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1)
                                + "/" + String.valueOf(year);
                        edtToDate.setText(date);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });
        language();


        picker = CountryPicker.newInstance("Select Country");  // dialog title
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {

                edtNationality.setText(name);
                picker.dismiss();
            }
        });
        edtNationality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });
        picker1 = CountryPicker.newInstance("Select Country");  // dialog title
        picker1.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {

                edtCountryOfResidence.setText(name);
                picker1.dismiss();
            }
        });
        edtCountryOfResidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker1.show(getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });
        noticeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //qualification
                qm2 = new QualificationModel2();
list_n2.clear();
                //list_n.add(qm1);

                System.out.println("<<<<<<<<<<<<<<<size>>>>>>>>>>>>>" + list_n.size());

for(int i=0;i<list_n.size();i++){
    StringBuffer strBuf2 = null;
    System.out.println("<<<<<<<<<<<<<<<aaaaaaaaaaa>>>>>>>>>>>>>" + list_n.get(i).getComp_name());
    System.out.println("<<<<<<<<<<<<<<<bbbbbbbbbbbbbbb>>>>>>>>>>>>>" + list_n.get(i).getPosition());
    System.out.println("<<<<<<<<<<<<<<<cccccccccccccccc>>>>>>>>>>>>>" + list_n.get(i).getComp_name());
    System.out.println("<<<<<<<<<<<<<<<dddddddddddd>>>>>>>>>>>>>" + list_n.get(i).getExprnce());
    System.out.println("<<<<<<<<<<<<<<<frm>>>>>>>>>>>>>" + list_n.get(i).getFrm_date());
    System.out.println("<<<<<<<<<<<<<<<tooooooo>>>>>>>>>>>>>" + list_n.get(i).getTo_date());

//    strBuf2.append("{"+"\"company_name\""+":"+"\""+list_n.get(i).getComp_name()+"\""+"#"+"\"position\""+":"+"\""+list_n.get(i).getPosition()+"\""+"},");
    qm2.setComp_name(list_n.get(i).getComp_name());
    qm2.setPosition(list_n.get(i).getPosition());
    qm2.setCountry(list_n.get(i).getCountry());
    qm2.setFrm_date(list_n.get(i).getFrm_date());
    qm2.setTo_date(list_n.get(i).getTo_date());
    qm2.setExprnce(list_n.get(i).getExprnce());
    list_n2.add(qm2);


}
                Gson gson = new Gson();
                somejson=gson.toJson(list_n2);

                System.out.println("<<<<<<<<<<<<<<<string_apnd>>>>>>>>>>>>>" + somejson);
                list_n.clear();
                UploadVideo uv = new UploadVideo();
                uv.execute();
            }
        });

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, notice_status);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        noticeSpin.setAdapter(aa1);
        spin_visa = findViewById(R.id.visa_spin);
        spin_visa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, visa_status);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin_visa.setAdapter(aa);
    }

    private void AddItemsToRecyclerViewArrayList() {
        Number = new ArrayList<>();
        Number.add("ONE");
        Number.add("TWO");
        Number.add("THREE");
        Number.add("FOUR");

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1212 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        pdfName.setText(displayName);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                pdfName.setText(displayName);
            }
        }
        if (requestCode == PICTURE_RESULT4 && resultCode == RESULT_OK) {

            Bitmap bitmap = ImagePicker3.getImageFromResult(this, resultCode, data);
            getImageUri(getApplicationContext(), bitmap);
            startCropImageActivity(uri);
            CROP_PIC1 = 5;


        }
        if (CROP_PIC1 == 5 && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                profPic.setImageURI(result.getUri());
                selectedFilePath1 = Vis_FilePath.getPath(getApplicationContext(), result.getUri());

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: ", Toast.LENGTH_LONG).show();
            }
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        uri = Uri.parse(path);
        return uri;
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    public class UploadVideo extends AsyncTask<Void, Void, String> {
        String Serverresponse = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // progressWheel.setVisibility(View.GONE);

            try {
                JSONObject object = new JSONObject(s);
                String status = object.getString("status");
                String msg = object.getString("message");
                if (!status.equals("true")) {
                    /*SweetAlertDialog dialog = new SweetAlertDialog(Order_medicine.this, SweetAlertDialog.ERROR_TYPE);
                    dialog.setTitleText(msg)
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();

                                }
                            })
                            .show();
                    dialog.findViewById(R.id.confirm_button).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#23a6f8")));*/

                } else {


                  /*  Intent intent = new Intent(Order_medicine.this, Thnkyou.class);
                    intent.putExtra("msg", msg);
                    startActivity(intent);
                    finish();*/
                }


            } catch (Exception e) {
                e.printStackTrace();
                // progressWheel.setVisibility(View.GONE);
            }


        }

        @Override
        protected String doInBackground(Void... params) {

            String charset = "UTF-8";
            String requestURL = "";
            try {

                Vis_Upload multipart = new Vis_Upload(requestURL, charset);
                multipart.addHeaderField("User-Agent", "CodeJava");
                multipart.addHeaderField("Test-Header", "Header-Value");
                // multipart.addFormField("user_id", Reg_id);
                multipart.addFormField("delivery_type", "room_delivery");
                //   multipart.addFormField("room_no", roomNo.getText().toString());
                multipart.addFormField("patient_type", "in_patient");
                multipart.addFormField("device_type", "android");
                multipart.addFilePart("prescription", selectedFilePath1);


                System.out.println("SERVER REPLIED:" + Serverresponse);

            } catch (IOException ex) {
                System.err.println(ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Serverresponse:" + Serverresponse);

            return Serverresponse;
        }
    }

    private void language() {

/*
        progress_bar_explore.setVisibility(View.VISIBLE);
*/
        Fuel.post(BASE_URL + "getFormData.php", params).responseString(new Handler<String>() {
            @Override
            public void success(Request request, Response response, String s) {
                // progress_bar_explore.setVisibility(View.GONE);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    System.out.println("s**********" + s);
                    String baseUrl = jsonObj.getString("baseUrl");
                    //String qualification = jsonObj.getString("qualification");
                    // String languages = jsonObj.getString("languages");

                    JSONArray qualificationArray = jsonObj.getJSONArray("qualification");

                    JSONArray dataArray = jsonObj.getJSONArray("languages");
                    for (int i = 0; i < dataArray.length(); i++) {
                        // JSONObject obj = dataArray.getJSONObject(i);
                        lm = new LanguageModel();
                        String lg = dataArray.getString(i);
                        System.out.println("<<<<<<<<<<<<<<<>>>>>>>>>>>>>" + lg);
                        lm.setLanguages(lg);
                        lgl.add(lm);

                        //  Log.e("##########", String.valueOf(lgl));

                    }

                    for (int j = 0; j < qualificationArray.length(); j++) {
                        JSONObject objqual = qualificationArray.getJSONObject(j);
                        qm = new QualificationModel();
                        String id, qlfn, cost;
                        id = objqual.getString("id");
                        qlfn = objqual.getString("qlfn");
                        cost = objqual.getString("cost");
                        qm.setId(id);
                        qm.setQlfn(qlfn);
                        qm.setCost(cost);
                        qgl.add(qm);
                        System.out.println("<<<<<<<<<<<<<<<qlfn>>>>>>>>>>>>>" + qlfn);


                    }
                    langlist = new ArrayList<String>();

                    for (LanguageModel lm : lgl) {
                        langlist.add(lm.getLanguages());
                        //  divisonlist.add(dv.getBranch_id());
                    }
                    if (langlist != null) {
                        multiSelectSpinner = findViewById(R.id.language_spin);
                        multiSelectSpinner.setItems(langlist);
                        multiSelectSpinner.hasNoneOption(true);
                        multiSelectSpinner.setSelection(new int[]{0});
                        multiSelectSpinner.setListener(MainActivity.this);

                    }

                    quallist = new ArrayList<String>();

                    for (QualificationModel qm : qgl) {
                        quallist.add(qm.getQlfn());
                        //  divisonlist.add(dv.getBranch_id());
                    }
                    ArrayAdapter<String> spinnerAdapter0 = new ArrayAdapter<String>(MainActivity.this,
                            R.layout.language_spinner_item, R.id.txt, quallist);

                    educationSpin.setAdapter(spinnerAdapter0);
                    spinnerAdapter0.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(Request request, Response response, FuelError fuelError) {

            }
        });


    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
