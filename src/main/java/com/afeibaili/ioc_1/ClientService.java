package com.afeibaili.ioc_1;

public class ClientService {
    public static ClientService clientService = new ClientService();

    private ClientService() {
    }

    public static ClientService createClientService() {
        return clientService;
    }
}
