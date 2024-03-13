package org.example.Repository;

import org.example.WebView;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Saver<T extends WebView> implements Repository{
    private String fileName;
    private String DirectoryWithoutFN;
    private T ObjectFromLoad;

    public Saver(String fileName, String directoryWithoutFN, T objectFromLoad) {
        this.fileName = fileName;
        DirectoryWithoutFN = directoryWithoutFN;
        ObjectFromLoad = objectFromLoad;
    }

    @Override
    public void load() {
        String log = "";
        try(FileInputStream logPuller = new FileInputStream(DirectoryWithoutFN + "/"+ fileName)) {
            int read;
            while ((read = logPuller.read()) != -1){
                log += (String.valueOf((char) read));
            }
        } catch (Exception e) {}

        ObjectFromLoad.loadLog(log);
    }

    @Override
    public void save() {
        File logFile = new File(DirectoryWithoutFN);
        logFile.mkdirs();

        logFile = new File(DirectoryWithoutFN,  fileName);
        try(FileOutputStream serverWriter = new FileOutputStream(logFile, false)) {
            serverWriter.write(ObjectFromLoad.getLog().getBytes());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Failed to save" + fileName, JOptionPane.ERROR_MESSAGE);
        }
    }
}
