/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentcloud.cloud.app.services.report;

import com.rentcloud.cloud.app.entities.Client;

/**
 *
 * @author Tatiana
 */
public class CountClient {
    
    private Double total;
    private Client client;

    public CountClient(Double total, Client client) {
        this.total = total;
        this.client = client;
    }
    
    public CountClient() {
 
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    
}
