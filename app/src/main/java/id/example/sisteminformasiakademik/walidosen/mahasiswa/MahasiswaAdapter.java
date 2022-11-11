package id.example.sisteminformasiakademik.walidosen.mahasiswa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.example.sisteminformasiakademik.R;


public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaHolder> {
    Context context;
    List<MahasiswaData> mahasiswaDataList;

    public MahasiswaAdapter(Context context, List<MahasiswaData> mahasiswaDataList) {
        this.context = context;
        this.mahasiswaDataList = mahasiswaDataList;
    }


    @NonNull
    @Override
    public MahasiswaAdapter.MahasiswaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mahasiswaLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.mahasiswa_list_waldos,parent,false);
        return new MahasiswaAdapter.MahasiswaHolder(mahasiswaLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaAdapter.MahasiswaHolder holder, int position) {
        MahasiswaData mahasiswaData = mahasiswaDataList.get(position);
        holder.nim.setText(mahasiswaData.getNim());
        holder.nama.setText(mahasiswaData.getNama());
        holder.prodi.setText(mahasiswaData.getProdi());
        holder.semester.setText(mahasiswaData.getSemester());
    }

    @Override
    public int getItemCount() {
        return mahasiswaDataList.size();
    }

    public class MahasiswaHolder extends RecyclerView.ViewHolder {
        TextView nim, nama, prodi, semester;
        public MahasiswaHolder(@NonNull View itemView) {
            super(itemView);

            nim = itemView.findViewById(R.id.txt_nim_mahasiswa);
            nama = itemView.findViewById(R.id.txt_nama_mahasiswa);
            prodi = itemView.findViewById(R.id.txt_prodi_mahasiswa);
            semester = itemView.findViewById(R.id.txt_semester_mahasiswa);
        }
    }
}
