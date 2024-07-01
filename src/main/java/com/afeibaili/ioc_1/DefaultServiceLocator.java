package com.afeibaili.ioc_1;

public class DefaultServiceLocator {
    private static ClientServiceImpl clientService = new ClientServiceImpl();

    public ClientServiceImpl createClientService() {
        return clientService;
    }
}
