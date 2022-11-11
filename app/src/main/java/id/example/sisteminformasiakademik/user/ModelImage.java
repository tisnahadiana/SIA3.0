package id.example.sisteminformasiakademik.user;

public class ModelImage {
    private String nim, imageurl;

    public ModelImage() {
    }

    public ModelImage(String nim, String imageurl) {
        this.nim = nim;
        this.imageurl = imageurl;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
