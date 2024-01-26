package ni.gob.inss.siit.view.bean.backbean.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jjrivera on 11/29/2016.
 */

//public class UploadFilePersonal implements UploadedFile {
//
//    private File file;
//    private String fileName;
//
//    public UploadFilePersonal(File file){
//        this.file = file;
//    }
//
//    @Override
//    public String getFileName() {
//        this.fileName = file.getName();
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }
//
//    @Override
//    public InputStream getInputstream() throws IOException {
//        return new FileInputStream(file);
//    }
//
//    @Override
//    public long getSize() {
//        return file.length();
//    }
//
//    @Override
//    public byte[] getContents() {
//        try {
//            return IOUtils.toByteArray(this.getInputstream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public String getContentType() {
//        return "image/" + FilenameUtils.getExtension(file.getName());
//    }
//
//    @Override
//    public void write(String s) throws Exception {
//
//    }
//
//    public String getPath() {
//        return file.getAbsolutePath();
//    }
//}
