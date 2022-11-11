package id.example.sisteminformasiakademik.admin.mahasiswa;

public class MahasiswaData {
    String nim, nama, email, prodi, semester;

//    public MahasiswaData(String nim, String nama, String email, String prodi, String semester) {
//        this.nim = nim;
//        this.nama = nama;
//        this.email = email;
//        this.prodi = prodi;
//        this.semester = semester;
//    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}

