package traspac.simansuv1;

/**
 * Created by UI-21 on 12/08/2016.
 */
public class SuratMasuk {


    String surat_id;
    String jenis_id;
    String revisi_id;
    String sifat;
    String dari;
    String tanggal;
    String jam;
    String nosurat;
    String hal;
    String disposisi;
    int visible;
    int dibaca;


    public SuratMasuk(String surat_id, String sifat, String dari, String tanggal, String jam, String nosurat, String hal, String disposisi, int visible, int dibaca,String jenis_id,String revisi_id) {
        this.surat_id = surat_id;
        this.sifat = sifat;
        this.dari = dari;
        this.tanggal = tanggal;
        this.jam = jam;
        this.nosurat = nosurat;
        this.hal = hal;
        this.disposisi = disposisi;
        this.visible = visible;
        this.dibaca = dibaca;
        this.jenis_id = jenis_id;
        this.revisi_id = revisi_id;
    }

    public SuratMasuk() {

    }


    public String getJenis_id() {
        return jenis_id;
    }

    public void setJenis_id(String jenis_id) {
        this.jenis_id = jenis_id;
    }

    public String getRevisi_id() {
        return revisi_id;
    }

    public void setRevisi_id(String revisi_id) {
        this.revisi_id = revisi_id;
    }

    public String getDisposisi() {
        return disposisi;
    }

    public void setDisposisi(String disposisi) {
        this.disposisi = disposisi;
    }

    public String getSurat_id() {
        return surat_id;
    }

    public void setSurat_id(String surat_id) {
        this.surat_id = surat_id;
    }

    public String getSifat() {
        return sifat;
    }

    public void setSifat(String sifat) {
        this.sifat = sifat;
    }

    public String getDari() {
        return dari;
    }

    public void setDari(String dari) {
        this.dari = dari;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getDibaca() {
        return dibaca;
    }

    public void setDibaca(int dibaca) {
        this.dibaca = dibaca;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getNosurat() {
        return nosurat;
    }

    public void setNosurat(String nosurat) {
        this.nosurat = nosurat;
    }

    public String getHal() {
        return hal;
    }

    public void setHal(String hal) {
        this.hal = hal;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getVisible() {
        return visible;
    }
}
