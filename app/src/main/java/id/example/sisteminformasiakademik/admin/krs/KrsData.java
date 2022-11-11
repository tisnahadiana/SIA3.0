package id.example.sisteminformasiakademik.admin.krs;

public class KrsData {
    long NoPengiriman;
    String str_nim, str_nama,str_prodi,str_semester;
    long date;

//    public KrsData() {
//    }

    public long getNoPengiriman() {
        return NoPengiriman;
    }

    public void setNoPengiriman(long noPengiriman) {
        NoPengiriman = noPengiriman;
    }

    public String getStr_nim() {
        return str_nim;
    }

    public void setStr_nim(String str_nim) {
        this.str_nim = str_nim;
    }

    public String getStr_nama() {
        return str_nama;
    }

    public void setStr_nama(String str_nama) {
        this.str_nama = str_nama;
    }

    public String getStr_prodi() {
        return str_prodi;
    }

    public void setStr_prodi(String str_prodi) {
        this.str_prodi = str_prodi;
    }

    public String getStr_semester() {
        return str_semester;
    }

    public void setStr_semester(String str_semester) {
        this.str_semester = str_semester;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
