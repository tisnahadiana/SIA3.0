package id.example.sisteminformasiakademik.walidosen.khs;

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

public class KhsWalidosenActivity extends AppCompatActivity {

    TextView txtNidnKHS, txtNamaWaldosKHS, txtProdiKHS, txtSemesterKHS, namaProdi, noSemester;
    String url = "https://droomp.tech/walidosen/khs/showkhs_walidosen.php";

    ArrayList<KhsDataWD> list;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khs_walidosen);
        getSupportActionBar().hide();

        txtNidnKHS = findViewById(R.id.txtNIDNkhs);
        txtNamaWaldosKHS = findViewById(R.id.txtNamaWaldosKhs);
        txtProdiKHS = findViewById(R.id.txtProdiWaldosKHS);
        txtSemesterKHS = findViewById(R.id.txtSemesterWaldosKHS);
        namaProdi = findViewById(R.id.prodiKHSWD);
        noSemester = findViewById(R.id.semesterKHSWD);

        listView = findViewById(R.id.listViewKhsMHSWD);
        list = new ArrayList<>();

        Intent i = getIntent();
        String tNim = i.getStringExtra("nidn");
        String tName = i.getStringExtra("namaDosen");
        String tProdi = i.getStringExtra("prodi");
        String tSemester = i.getStringExtra("semester");

        txtNidnKHS.setText(tNim);
        txtNamaWaldosKHS.setText(tName);
        txtProdiKHS.setText(tProdi);
        txtSemesterKHS.setText(tSemester);
        namaProdi.setText(tProdi);
        noSemester.setText(tSemester);

        showKhsMHS_WD();
    }


    public void showKhsMHS_WD() {
        String prodi = txtProdiKHS.getText().toString();
        String semester = txtSemesterKHS.getText().toString();
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
                            String img = object.getString("khs_url");

                            KhsDataWD khsData = new KhsDataWD();
                            khsData.setNim(nim);
                            khsData.setNama(nama);
                            khsData.setProdi(prodi);
                            khsData.setSemester(semester);
                            khsData.setImg(img);
                            list.add(khsData);
                            progressDialog.dismiss();
//                        list.add(new MahasiswaData(nim,nama,email,prodi,semester));
                        } catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }

                    Adapter adapter = new Adapter(KhsWalidosenActivity.this,list);
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
        RequestQueue queue = Volley.newRequestQueue(KhsWalidosenActivity.this);
        queue.add(request);
    }


    public void moveDashboardKHSWaldos(View view){
        String nidnData = txtNidnKHS.getText().toString().trim();
        String namaDosen = txtNamaWaldosKHS.getText().toString().trim();
        String prodiKHS = txtProdiKHS.getText().toString().trim();
        String semesterKHS = txtSemesterKHS.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardWalidosenActivity.class);
        intent.putExtra("nidn", nidnData);
        intent.putExtra("namaDosen", namaDosen);
        intent.putExtra("prodi", prodiKHS);
        intent.putExtra("semester", semesterKHS);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String idAdmin = txtNidnKHS.getText().toString().trim();
        String namaAdmin = txtNamaWaldosKHS.getText().toString().trim();
        String prodiKHS = txtProdiKHS.getText().toString().trim();
        String semesterKHS = txtSemesterKHS.getText().toString().trim();
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
    ArrayList<KhsDataWD> model;

    public Adapter(Context context, ArrayList<KhsDataWD> model){
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

    TextView nim, nama,prodi, semester,txtNidnKHSlistview,txtNamaKHSWDlistview,txtProdiKHSWDlistview, txtSemesterKHSWDlistview, txtImageKHSWDlistview;
    Button lihat;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.khs_list_walidosen, parent, false);

        lihat = view.findViewById(R.id.btn_lihat_khswd);

        nim = view.findViewById(R.id.txt_nimkhs_WD);
        nama = view.findViewById(R.id.txt_namakhs_WD);
        prodi = view.findViewById(R.id.txt_prodikhs_WD);
        semester = view.findViewById(R.id.txt_semesterkhs_WD);
        txtImageKHSWDlistview = view.findViewById(R.id.txtImageKHSWDlistview);

        nim.setText(model.get(position).getNim());
        nama.setText(model.get(position).getNama());
        prodi.setText(model.get(position).getProdi());
        semester.setText(model.get(position).getSemester());
        txtImageKHSWDlistview.setText(model.get(position).getImg());

        txtNidnKHSlistview = view.findViewById(R.id.txtNidnKHSlistview);
        txtNamaKHSWDlistview = view.findViewById(R.id.txtNamaKHSWDlistview);
        txtProdiKHSWDlistview = view.findViewById(R.id.txtProdiKHSWDlistview);
        txtSemesterKHSWDlistview = view.findViewById(R.id.txtSemesterKHSWDlistview);

        Intent i = ((Activity) context).getIntent();
        String tNim = i.getStringExtra("nidn");
        String tName = i.getStringExtra("namaDosen");
        String tProdi = i.getStringExtra("prodi");
        String tSemester = i.getStringExtra("semester");

        txtNidnKHSlistview.setText(tNim);
        txtNamaKHSWDlistview.setText(tName);
        txtProdiKHSWDlistview.setText(tProdi);
        txtSemesterKHSWDlistview.setText(tSemester);

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtNidnKHSlistview.getText().toString().trim();
                String namaAdmin = txtNamaKHSWDlistview.getText().toString().trim();
                String prodiKHS = txtProdiKHSWDlistview.getText().toString().trim();
                String semesterKHS = txtSemesterKHSWDlistview.getText().toString().trim();
                Intent intent = new Intent(context, LihatKhsWalidosenActivity.class);
                intent.putExtra("nidn", idAdmin);
                intent.putExtra("namaDosen", namaAdmin);
                intent.putExtra("prodi", prodiKHS);
                intent.putExtra("semester", semesterKHS);
                intent.putExtra("nimLKHS", model.get(position).getNim());
                intent.putExtra("namaLKHS", model.get(position).getNama());
                intent.putExtra("prodiLKHS", model.get(position).getProdi());
                intent.putExtra("semesterLKHS", model.get(position).getSemester());
                intent.putExtra("imgLKHS", model.get(position).getImg());
                context.startActivity(intent);
            }
        });
        return view;
    }


}