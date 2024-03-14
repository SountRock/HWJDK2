package org.example.Repository;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;

public interface Repository {
    String load();
    void save(String value);
    void setWorkDirectory(String fileName, String directoryWithoutFN);
}
