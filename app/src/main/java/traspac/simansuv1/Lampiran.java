package traspac.simansuv1;

/**
 * Created by UI-21 on 30/08/2016.
 */
public class Lampiran {


    private String fileName,fileSize;

    public Lampiran(String fileName, String fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public Lampiran() {

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }


}
