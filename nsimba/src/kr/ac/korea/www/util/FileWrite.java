package kr.ac.korea.www.util;

import java.io.File;
import java.io.FileOutputStream;

public class FileWrite {
    public FileWrite() {
    }

    public void writeFile(String filename, String content) {
        String dir = "c:\\testresult2\\";
        //dir = dir + filename;
        File file = new File(dir + filename);
        FileOutputStream fos = null;
        try {
            file.createNewFile();
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            //fos.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            try { if(fos != null) fos.close(); } catch(Exception _e) {}
        }


    }
}
