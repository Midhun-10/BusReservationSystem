package com.connections;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityConnection {

    private static EntityConnection instance = null;

    private EntityManagerFactory emf = null;

    private EntityConnection() {}

    public static EntityConnection getInstance() {
        if (instance == null) {
            instance = new EntityConnection();
        }
        return instance;
    }

    public EntityManagerFactory getEntityConnection() {
        if (this.emf == null) {
            this.emf = Persistence.createEntityManagerFactory("dev");
        }
        return this.emf;
    }

    public void closeConnections() {
        if (this.emf != null && this.emf.isOpen()) this.emf.close();
    }
}
