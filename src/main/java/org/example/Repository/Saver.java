package org.example.Repository;

import org.example.WebView;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Saver implements Repository {
    private String fileName;
    private String DirectoryWithoutFN;

    @Override
    public String load() {
        String log = "";
        try(FileInputStream logPuller = new FileInputStream(DirectoryWithoutFN + "/"+ fileName)) {
            int read;
            while ((read = logPuller.read()) != -1){
                log += (String.valueOf((char) read));
            }
        } catch (Exception e) {}

        return log;
    }

    @Override
    public void save(String value) {
        File logFile = new File(DirectoryWithoutFN);
        logFile.mkdirs();

        logFile = new File(DirectoryWithoutFN,  fileName);
        try(FileOutputStream serverWriter = new FileOutputStream(logFile, false)) {
            serverWriter.write(value.getBytes());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Failed to save" + fileName, JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void setWorkDirectory(String fileName, String directoryWithoutFN) {
            this.fileName = fileName;
            DirectoryWithoutFN = directoryWithoutFN;
    }
}
