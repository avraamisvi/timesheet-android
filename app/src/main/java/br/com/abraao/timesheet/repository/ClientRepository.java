package br.com.abraao.timesheet.repository;

import java.util.ArrayList;
import java.util.List;

import br.com.abraao.timesheet.entities.Client;

/**
 * Created by abraao on 10/20/15.
 */
public class ClientRepository {

    public List<Client> getClients() {
        List<Client> ITEMS = new ArrayList<Client>();

        ITEMS.add(new Client("1", "Cli 1", "1"));
        ITEMS.add(new Client("2", "Cli 2", "2"));
        ITEMS.add(new Client("3", "Cli 3", "3"));

        return ITEMS;
    }
}
