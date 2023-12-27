/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.trenchant_troullier_virquin.applicationwebm3.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author laelt
 */

@Entity
public class Operation_Effectuee extends AbstractEntity{
    
    @OneToOne
    @JoinColumn(name = "exemplaire_id")
    private Exemplaires exemplaire;
    
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;
    
    @ManyToOne
    @JoinColumn(name = "operation_id")
    private Operation operation;
    
    @NotNull
    private float début;
    
    private float fin;

    public Operation_Effectuee() {
    }

    public Exemplaires getExemplaire() {
        return exemplaire;
    }

    public void setExemplaire(Exemplaires exemplaire) {
        this.exemplaire = exemplaire;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public float getDébut() {
        return début;
    }

    public void setDébut(float début) {
        this.début = début;
    }

    public float getFin() {
        return fin;
    }

    public void setFin(float fin) {
        this.fin = fin;
    }
    
    
}