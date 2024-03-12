package org.example.Client;

import org.example.LogView;

public interface ClientView extends LogView {
    void changeLogin();
    void showMessage(String message);
}
