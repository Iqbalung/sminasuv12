package traspac.simansuv1;

/**
 * Created by UI-21 on 24/08/2016.
 */
public class RiwayatDisposisi{
    String disposisi_id,entrysurat_id,dari,kepada,tujuan,tgl_disposisi,tgl_remiten,tindakan_disposisi,isi,jam;



    public RiwayatDisposisi(String disposisi_id, String entrysurat_id, String dari, String kepada, String tujuan, String tgl_disposisi, String tgl_remiten, String tindakan_disposisi, String isi, String format_jam) {
        this.disposisi_id = disposisi_id;
        this.entrysurat_id = entrysurat_id;
        this.dari = dari;
        this.kepada = kepada;
        this.tujuan = tujuan;
        this.tgl_disposisi = tgl_disposisi;
        this.tgl_remiten = tgl_remiten;
        this.tindakan_disposisi = tindakan_disposisi;
        this.isi = isi;
        this.jam = format_jam;
    }

    public RiwayatDisposisi() {

    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getDisposisi_id() {
        return disposisi_id;
    }

    public void setDisposisi_id(String disposisi_id) {
        this.disposisi_id = disposisi_id;
    }

    public String getEntrysurat_id() {
        return entrysurat_id;
    }

    public void setEntrysurat_id(String entrysurat_id) {
        this.entrysurat_id = entrysurat_id;
    }

    public String getDari() {
        return dari;
    }

    public void setDari(String dari) {
        this.dari = dari;
    }

    public String getKepada() {
        return kepada;
    }

    public void setKepada(String kepada) {
        this.kepada = kepada;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getTgl_disposisi() {
        return tgl_disposisi;
    }

    public void setTgl_disposisi(String tgl_disposisi) {
        this.tgl_disposisi = tgl_disposisi;
    }

    public String getTgl_remiten() {
        return tgl_remiten;
    }

    public void setTgl_remiten(String tgl_remiten) {
        this.tgl_remiten = tgl_remiten;
    }

    public String getTindakan_disposisi() {
        return tindakan_disposisi;
    }

    public void setTindakan_disposisi(String tindakan_disposisi) {
        this.tindakan_disposisi = tindakan_disposisi;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }


}
