package id.example.sisteminformasiakademik.walidosen.tnilai;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.DashboardAdminActivity;
import id.example.sisteminformasiakademik.admin.tnilaiAdmin.LihatTnilaiAdminActivity;
import id.example.sisteminformasiakademik.walidosen.DashboardWalidosenActivity;

public class TnilaiWalidosenActivity extends AppCompatActivity {

    TextView txtIdWDTnilai, txtNamaWDTnilai,txtprodiWDTnilai, txtSemesterWDTnilai, namaProdi, noSemester;
    String url = "https://droomp.tech/walidosen/tnilai/show_tnilai.php";

    ArrayList<TnilaiDataWD> list;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tnilai_walidosen);
        getSupportActionBar().hide();

        txtIdWDTnilai = findViewById(R.id.txtIdWDTnilai);
        txtNamaWDTnilai = findViewById(R.id.txtNamaWDTnilai);
        txtprodiWDTnilai = findViewById(R.id.txtprodiWDTnilai);
        txtSemesterWDTnilai = findViewById(R.id.txtSemesterWDTnilai);
        namaProdi = findViewById(R.id.ProdiWaldosTnilai);
        noSemester = findViewById(R.id.noSemesterTnilaiWaldos);

        listView = findViewById(R.id.listViewTnilaiMHSWD);
        list = new ArrayList<>();

        Intent i = getIntent();
        String tNim = i.getStringExtra("nidn");
        String tName = i.getStringExtra("namaDosen");
        String prodi = i.getStringExtra("prodi");
        String semester = i.getStringExtra("semester");

        txtIdWDTnilai.setText(tNim);
        txtNamaWDTnilai.setText(tName);
        txtprodiWDTnilai.setText(prodi);
        txtSemesterWDTnilai.setText(semester);
        namaProdi.setText(prodi);
        noSemester.setText(semester);

        showTnilai_WD();

    }

    public void showTnilai_WD() {
        String prodi = txtprodiWDTnilai.getText().toString().trim();
        String semester = txtSemesterWDTnilai.getText().toString().trim();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Menunggu.....");

        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
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
                            String img = object.getString("tnilai_url");

                            TnilaiDataWD tnilaiDataWD = new TnilaiDataWD();
                            tnilaiDataWD.setNim(nim);
                            tnilaiDataWD.setNama(nama);
                            tnilaiDataWD.setProdi(prodi);
                            tnilaiDataWD.setSemester(semester);
                            tnilaiDataWD.setTnilai_url(img);
                            list.add(tnilaiDataWD);
                            progressDialog.dismiss();
//                        list.add(new MahasiswaData(nim,nama,email,prodi,semester));
                        } catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                    Adapter adapter = new Adapter(TnilaiWalidosenActivity.this, list);
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
        RequestQueue queue = Volley.newRequestQueue(TnilaiWalidosenActivity.this);
        queue.add(request);
    }

    public void moveDashboardTnilaiShow(View view) {
        String idAdmin = txtIdWDTnilai.getText().toString().trim();
        String namaAdmin = txtNamaWDTnilai.getText().toString().trim();
        String prodiData = txtprodiWDTnilai.getText().toString().trim();
        String semesterData = txtSemesterWDTnilai.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardWalidosenActivity.class);
        intent.putExtra("nidn", idAdmin);
        intent.putExtra("namaDosen", namaAdmin);
        intent.putExtra("prodi", prodiData);
        intent.putExtra("semester", semesterData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String idAdmin = txtIdWDTnilai.getText().toString().trim();
        String namaAdmin = txtNamaWDTnilai.getText().toString().trim();
        String prodiData = txtprodiWDTnilai.getText().toString().trim();
        String semesterData = txtSemesterWDTnilai.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardWalidosenActivity.class);
        intent.putExtra("nidn", idAdmin);
        intent.putExtra("namaDosen", namaAdmin);
        intent.putExtra("prodi", prodiData);
        intent.putExtra("semester", semesterData);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }
}



class Adapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<TnilaiDataWD> model;

    public Adapter(Context context, ArrayList<TnilaiDataWD> model){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.model = model;
    }

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

    TextView nim, nama,prodi, semester,txtIdTnilaiWDlistview,txtNamaTnilaiWDlistview,txtProdiTnilaiWDlistview,txtSemesterTnilaiWDlistview, txtImageTnilaiWDlistview;
    Button lihat, edit, hapus;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.tnilai_list_walidosen, parent, false);

        lihat = view.findViewById(R.id.btn_lihat_TnilaiWD);

        nim = view.findViewById(R.id.txt_nimTnilai_WD);
        nama = view.findViewById(R.id.txt_namaTnilai_WD);
        prodi = view.findViewById(R.id.txt_prodiTnilai_WD);
        semester = view.findViewById(R.id.txt_semesterTnilai_WD);
        txtImageTnilaiWDlistview = view.findViewById(R.id.txtImageTnilaiWDlistview);

        nim.setText(model.get(position).getNim());
        nama.setText(model.get(position).getNama());
        prodi.setText(model.get(position).getProdi());
        semester.setText(model.get(position).getSemester());
        txtImageTnilaiWDlistview.setText(model.get(position).getTnilai_url());

        txtIdTnilaiWDlistview = view.findViewById(R.id.txtIdTnilaiWDlistview);
        txtNamaTnilaiWDlistview = view.findViewById(R.id.txtNamaTnilaiWDlistview);
        txtProdiTnilaiWDlistview = view.findViewById(R.id.txtProdiTnilaiWDlistview);
        txtSemesterTnilaiWDlistview = view.findViewById(R.id.txtSemesterTnilaiWDlistview);

        Intent i = ((Activity) context).getIntent();
        String tNim = i.getStringExtra("nidn");
        String tName = i.getStringExtra("namaDosen");
        String prodi = i.getStringExtra("prodi");
        String semester = i.getStringExtra("semester");

        txtIdTnilaiWDlistview.setText(tNim);
        txtNamaTnilaiWDlistview.setText(tName);
        txtProdiTnilaiWDlistview.setText(prodi);
        txtSemesterTnilaiWDlistview.setText(semester);

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtIdTnilaiWDlistview.getText().toString().trim();
                String namaAdmin = txtNamaTnilaiWDlistview.getText().toString().trim();
                String prodi = txtProdiTnilaiWDlistview.getText().toString().trim();
                String semester = txtSemesterTnilaiWDlistview.getText().toString().trim();
                Intent intent = new Intent(context, LihatTnilaiWalidosenActivity.class);
                intent.putExtra("nidn", idAdmin);
                intent.putExtra("namaDosen", namaAdmin);
                intent.putExtra("prodi", prodi);
                intent.putExtra("semester", semester);
                intent.putExtra("nimLTNL", model.get(position).getNim());
                intent.putExtra("namaLTNL", model.get(position).getNama());
                intent.putExtra("prodiLTNL", model.get(position).getProdi());
                intent.putExtra("semesterLTNL", model.get(position).getSemester());
                intent.putExtra("imgLTNL", model.get(position).getTnilai_url());
                context.startActivity(intent);
            }
        });

        return view;
    }


}
