package id.example.sisteminformasiakademik.walidosen.krs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.walidosen.DashboardWalidosenActivity;
import id.example.sisteminformasiakademik.walidosen.khs.LihatKhsWalidosenActivity;

public class KrsWalidosenActivity extends AppCompatActivity {

    TextView txtNidnKRS, txtNamaWaldosKRS, txtProdiKRS, txtSemesterKRS, namaProdi, noSemester;
    String url = "https://droomp.tech/walidosen/krs/showkrs_walidosen.php";

    ArrayList<KrsDataWD> list;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krs_walidosen);
        getSupportActionBar().hide();

        txtNidnKRS = findViewById(R.id.txtNIDNkrs);
        txtNamaWaldosKRS = findViewById(R.id.txtNamaWaldosKrs);
        txtProdiKRS = findViewById(R.id.txtProdiWaldosKRS);
        txtSemesterKRS = findViewById(R.id.txtSemesterWaldosKRS);
        namaProdi = findViewById(R.id.namaProdiKRS);
        noSemester = findViewById(R.id.noSemesterKRS);

        listView = findViewById(R.id.listViewKRSWD);
        list = new ArrayList<>();

        Intent i = getIntent();
        String tNim = i.getStringExtra("nidn");
        String tName = i.getStringExtra("namaDosen");
        String tProdi = i.getStringExtra("prodi");
        String tSemester = i.getStringExtra("semester");

        txtNidnKRS.setText(tNim);
        txtNamaWaldosKRS.setText(tName);
        txtProdiKRS.setText(tProdi);
        txtSemesterKRS.setText(tSemester);
        namaProdi.setText(tProdi);
        noSemester.setText(tSemester);

        show_krs();
    }

    public void show_krs() {

        String prodi = txtProdiKRS.getText().toString();
        String semester = txtSemesterKRS.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//
                    JSONArray j = new JSONArray(response);
                    list.clear();
                    for (int i=0; i<j.length(); i++){
                        try {
//                            JSONObject getData = jsonArray.getJSONObject(i);
                            JSONObject object = j.getJSONObject(i);
                            String nim = object.getString("nim");
                            String nama = object.getString("nama");
                            String prodi = object.getString("prodi");
                            String semester = object.getString("semester");

                            KrsDataWD krsDataWD = new KrsDataWD();
                            krsDataWD.setStr_nim(nim);
                            krsDataWD.setStr_nama(nama);
                            krsDataWD.setStr_prodi(prodi);
                            krsDataWD.setStr_semester(semester);
                            list.add(krsDataWD);
                            progressDialog.dismiss();
//                        list.add(new MahasiswaData(nim,nama,email,prodi,semester));
                        } catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                   Adapter adapter = new Adapter(KrsWalidosenActivity.this, list);
                    listView.setAdapter(adapter);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            //                @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("prodi", prodi);
                params.put("semester", semester);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(KrsWalidosenActivity.this);
        queue.add(request);

    }

    public void moveDashboardKRSWaldos(View view){
        String idAdmin = txtNidnKRS.getText().toString().trim();
        String namaAdmin = txtNamaWaldosKRS.getText().toString().trim();
        String prodiKHS = txtProdiKRS.getText().toString().trim();
        String semesterKHS = txtSemesterKRS.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardWalidosenActivity.class);
        intent.putExtra("nidn", idAdmin);
        intent.putExtra("namaDosen", namaAdmin);
        intent.putExtra("prodi", prodiKHS);
        intent.putExtra("semester", semesterKHS);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String idAdmin = txtNidnKRS.getText().toString().trim();
        String namaAdmin = txtNamaWaldosKRS.getText().toString().trim();
        String prodiKHS = txtProdiKRS.getText().toString().trim();
        String semesterKHS = txtSemesterKRS.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardWalidosenActivity.class);
        intent.putExtra("nidn", idAdmin);
        intent.putExtra("namaDosen", namaAdmin);
        intent.putExtra("prodi", prodiKHS);
        intent.putExtra("semester", semesterKHS);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }
}

class Adapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<KrsDataWD> model;
    String urlLihatKrsAdmin = "https://droomp.tech/walidosen/krs/lihatkrs_walidosen.php";

    public Adapter(Context context, ArrayList<KrsDataWD> model){
        inflater=LayoutInflater.from(context);
        this.context = context;
        this.model = model;
    }

//    public void massage(String massage) {
//        Toast.makeText(context.getApplicationContext(), massage, Toast.LENGTH_LONG).show();
//    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TextView nim, nama, email, prodi, semester,txtNIDNkrslist,txtNamaDosenkrslist,txtProdiTnilaiWDlistview,txtSemesterTnilaiWDlistview;
    Button lihat, edit, hapus;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.krs_list_walidosen, parent, false);

        lihat = view.findViewById(R.id.btn_lihat_krsWD);

        nim = view.findViewById(R.id.txt_nimkrs_wd);
        nama = view.findViewById(R.id.txt_namakrs_wd);
        prodi = view.findViewById(R.id.txt_prodikrs_wd);
        semester = view.findViewById(R.id.txt_semesterkrs_wd);

        nim.setText(model.get(position).getStr_nim());
        nama.setText(model.get(position).getStr_nama());
        prodi.setText(model.get(position).getStr_prodi());
        semester.setText(model.get(position).getStr_semester());

        txtNIDNkrslist = view.findViewById(R.id.txtNIDNkrslistWD);
        txtNamaDosenkrslist = view.findViewById(R.id.txtNamaDosenkrslist);
        txtProdiTnilaiWDlistview = view.findViewById(R.id.txtProdiKrsWDlistview);
        txtSemesterTnilaiWDlistview = view.findViewById(R.id.txtSemesterKrsWDlistview);

        Intent i = ((Activity) context).getIntent();
        String tNim = i.getStringExtra("nidn");
        String tName = i.getStringExtra("namaDosen");
        String prodi = i.getStringExtra("prodi");
        String semester = i.getStringExtra("semester");

        txtNIDNkrslist.setText(tNim);
        txtNamaDosenkrslist.setText(tName);
        txtProdiTnilaiWDlistview.setText(prodi);
        txtSemesterTnilaiWDlistview.setText(semester);

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nim = model.get(position).getStr_nim();
                String nama = model.get(position).getStr_nama();

                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Mohon Menunggu.....");

                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, urlLihatKrsAdmin,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String result = jsonObject.getString("status");

                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    if (result.equals("success")) {
                                        for (int i = 0; i < jsonArray.length(); i++) {

                                            JSONObject object = jsonArray.getJSONObject(i);
                                            String nim = object.getString("nim");
                                            String nama = object.getString("nama");
                                            String prodi = object.getString("prodi");
                                            String semester = object.getString("semester");
                                            String matkul1 = object.getString("matkul1");
                                            String sks1 = object.getString("sks1");
                                            String matkul2 = object.getString("matkul2");
                                            String sks2 = object.getString("sks2");
                                            String matkul3 = object.getString("matkul3");
                                            String sks3 = object.getString("sks3");
                                            String matkul4 = object.getString("matkul4");
                                            String sks4 = object.getString("sks4");
                                            String matkul5 = object.getString("matkul5");
                                            String sks5 = object.getString("sks5");
                                            String matkul6 = object.getString("matkul6");
                                            String sks6 = object.getString("sks6");
                                            String matkul7 = object.getString("matkul7");
                                            String sks7 = object.getString("sks7");
                                            String matkul8 = object.getString("matkul8");
                                            String sks8 = object.getString("sks8");
                                            String jumlahsks = object.getString("jumlahsks");
                                            String date = object.getString("date");
                                            String nidn = txtNIDNkrslist.getText().toString().trim();
                                            String namaDosen = txtNamaDosenkrslist.getText().toString().trim();


                                            Intent intent = new Intent(context, LihatKrsWalidosenActivity.class);
                                            intent.putExtra("nidn", nidn);
                                            intent.putExtra("namaDosen", namaDosen);

                                            intent.putExtra("nim", nim);
                                            intent.putExtra("nama", nama);
                                            intent.putExtra("prodi", prodi);
                                            intent.putExtra("semester", semester);
                                            intent.putExtra("matkul1", matkul1);
                                            intent.putExtra("sks1", sks1);
                                            intent.putExtra("matkul2", matkul2);
                                            intent.putExtra("sks2", sks2);
                                            intent.putExtra("matkul3", matkul3);
                                            intent.putExtra("sks3", sks3);
                                            intent.putExtra("matkul4", matkul4);
                                            intent.putExtra("sks4", sks4);
                                            intent.putExtra("matkul5", matkul5);
                                            intent.putExtra("sks5", sks5);
                                            intent.putExtra("matkul6", matkul6);
                                            intent.putExtra("sks6", sks6);
                                            intent.putExtra("matkul7", matkul7);
                                            intent.putExtra("sks7", sks7);
                                            intent.putExtra("matkul8", matkul8);
                                            intent.putExtra("sks8", sks8);
                                            intent.putExtra("jumlahsks", jumlahsks);
                                            intent.putExtra("date", date);
                                            context.startActivity(intent);
                                            progressDialog.dismiss();
                                            Toast.makeText(context.getApplicationContext(), "Melihat Data", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(context.getApplicationContext(), "Data tidak ditemukan", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(context.getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }) {
                    //                @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("nim", nim);
                        params.put("nama", nama);
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(request);
            }
        });


        return view;
    }




}