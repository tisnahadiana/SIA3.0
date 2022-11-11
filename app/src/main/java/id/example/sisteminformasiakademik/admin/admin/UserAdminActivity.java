package id.example.sisteminformasiakademik.admin.admin;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.example.sisteminformasiakademik.R;
import id.example.sisteminformasiakademik.admin.DashboardAdminActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.EditMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.HapusMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.InputMahasiswaActivity;
import id.example.sisteminformasiakademik.admin.mahasiswa.Lihat_data_mahasiswa;
import id.example.sisteminformasiakademik.admin.mahasiswa.MahasiswaData;
import id.example.sisteminformasiakademik.admin.mahasiswa.UserMahasiswaAdminActivity;

public class UserAdminActivity extends AppCompatActivity {

    TextView txtidAdminDA, txtNamaAdminDA;
    FloatingActionButton fabAddAdmin;
    String url = "https://droomp.tech/admin/admin/show_data_admin.php";

    ArrayList<AdminData> list;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin);
        getSupportActionBar().hide();

        txtidAdminDA = findViewById(R.id.txtIdAdminDA);
        txtNamaAdminDA = findViewById(R.id.txtNamaAdminDA);
        fabAddAdmin = findViewById(R.id.fab_addAdmin);


        listView = findViewById(R.id.listViewUserAdmin);
        list = new ArrayList<>();

        Intent i = getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtidAdminDA.setText(tNim);
        txtNamaAdminDA.setText(tName);

        fabAddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtidAdminDA.getText().toString().trim();
                String namaAdmin = txtNamaAdminDA.getText().toString().trim();
                Intent intent = new Intent(UserAdminActivity.this, InputAdminActivity.class);
                intent.putExtra("id_admin", idAdmin);
                intent.putExtra("nama", namaAdmin);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        show_admin();
    }

    public void show_admin()  {

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
                            String ID = object.getString("ID");
                            String nama = object.getString("nama");
                            String email = object.getString("email");

                            AdminData adminData = new AdminData();
                            adminData.setId_admin(ID);
                            adminData.setNama(nama);
                            adminData.setEmail(email);
                            list.add(adminData);
                            progressDialog.dismiss();
//                        list.add(new MahasiswaData(nim,nama,email,prodi,semester));
                        } catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                    Adapter adapter = new Adapter(UserAdminActivity.this, list);
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
        });
//        {
//            //                @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                return params;
//            }
//        };
        RequestQueue queue = Volley.newRequestQueue(UserAdminActivity.this);
        queue.add(request);

    }

    public void moveDashboardAdminData(View view){
        String idAdmin = txtidAdminDA.getText().toString().trim();
        String namaAdmin = txtNamaAdminDA.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onBackPressed() {
        String idAdmin = txtidAdminDA.getText().toString().trim();
        String namaAdmin = txtNamaAdminDA.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), DashboardAdminActivity.class);
        intent.putExtra("id_admin", idAdmin);
        intent.putExtra("nama", namaAdmin);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }

}


class Adapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<AdminData> model;

    public Adapter(Context context, ArrayList<AdminData> model){
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

    TextView ID, nama, email, txtidAdmin ,txtNamaAdmin;
    Button lihat, edit, hapus;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.useradmin_list_admin, parent, false);

        lihat = view.findViewById(R.id.btn_lihat_data_admin);
        edit = view.findViewById(R.id.btn_edit_data_admin);
        hapus = view.findViewById(R.id.btn_hapus_data_admin);

        ID = view.findViewById(R.id.txt_ID_admin);
        nama = view.findViewById(R.id.txt_nama_admin);
        email = view.findViewById(R.id.txt_email_admin);

        ID.setText(model.get(position).getId_admin());
        nama.setText(model.get(position).getNama());
        email.setText(model.get(position).getEmail());

        txtidAdmin = view.findViewById(R.id.txtIdAdminlist);
        txtNamaAdmin = view.findViewById(R.id.txtNamaAdminlist);

        Intent i = ((Activity) context).getIntent();
        String tNim = i.getStringExtra("id_admin");
        String tName = i.getStringExtra("nama");

        txtidAdmin.setText(tNim);
        txtNamaAdmin.setText(tName);

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtidAdmin.getText().toString().trim();
                String namaAdmin = txtNamaAdmin.getText().toString().trim();
                Intent intent = new Intent(context, Lihat_data_admin.class);
                intent.putExtra("id_admin", idAdmin);
                intent.putExtra("nama", namaAdmin);
                intent.putExtra("id_adminLihat", model.get(position).getId_admin());
                intent.putExtra("namaLihat", model.get(position).getNama());
                intent.putExtra("emailLihat", model.get(position).getEmail());
                context.startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtidAdmin.getText().toString().trim();
                String namaAdmin = txtNamaAdmin.getText().toString().trim();
                Intent intented = new Intent(context, EditAdminActivity.class);
                intented.putExtra("id_admin", idAdmin);
                intented.putExtra("nama", namaAdmin);
                intented.putExtra("id_adminED", model.get(position).getId_admin());
                intented.putExtra("namaEDadmin", model.get(position).getNama());
                intented.putExtra("emailEDadm", model.get(position).getEmail());
                context.startActivity(intented);
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAdmin = txtidAdmin.getText().toString().trim();
                String namaAdmin = txtNamaAdmin.getText().toString().trim();
                Intent intenth = new Intent(context, HapusAdminActivity.class);
                intenth.putExtra("id_admin", idAdmin);
                intenth.putExtra("nama", namaAdmin);
                intenth.putExtra("id_adminHP", model.get(position).getId_admin());
                intenth.putExtra("namaHP", model.get(position).getNama());
                intenth.putExtra("emailHP", model.get(position).getEmail());
                context.startActivity(intenth);
            }
        });

        return view;
    }


}