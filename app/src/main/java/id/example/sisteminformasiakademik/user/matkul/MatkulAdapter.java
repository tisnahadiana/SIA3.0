package id.example.sisteminformasiakademik.user.matkul;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.example.sisteminformasiakademik.R;

public class MatkulAdapter extends RecyclerView.Adapter<MatkulAdapter.MatkulHolder>{

    Context context;
    List<MatkulData> matkulDataList;

    public MatkulAdapter(Context context, List<MatkulData> matkulDataList) {
        this.context = context;
        this.matkulDataList = matkulDataList;
    }


    @NonNull
    @Override
    public MatkulHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View matkulLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.matkul_list,parent,false);
        return new MatkulHolder(matkulLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull MatkulHolder holder, int position) {
        MatkulData matkulData = matkulDataList.get(position);
        holder.nama_matkul.setText(matkulData.getNama_matkul());
        holder.sks.setText(matkulData.getSks());

    }

    @Override
    public int getItemCount() {
        return matkulDataList.size();
    }

    public class MatkulHolder extends RecyclerView.ViewHolder {
        TextView nama_matkul, sks;
        public MatkulHolder(@NonNull View itemView) {
            super(itemView);

            nama_matkul = itemView.findViewById(R.id.txt_nama_matkul);
            sks = itemView.findViewById(R.id.txt_sks);
        }
    }
}
