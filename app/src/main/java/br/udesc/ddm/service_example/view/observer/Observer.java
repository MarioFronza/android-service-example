package br.udesc.ddm.service_example.view.observer;

public interface Observer {

    void userListUpdate();

    void showSuccessNotification(String username);

    void showErrorNotification(String username);

}
