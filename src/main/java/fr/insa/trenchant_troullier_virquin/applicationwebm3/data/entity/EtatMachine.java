package fr.insa.trenchant_troullier_virquin.applicationwebm3.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class EtatMachine extends AbstractEntity{
    private LocalDateTime debut;
    private LocalDateTime fin;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "etat_possible_machine_id")
    private EtatPossibleMachine etat;

    public LocalDateTime getDebut() {
        return debut;
    }

    public void setDebut(LocalDateTime debut) {
        this.debut = debut;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public EtatPossibleMachine getEtat() {
        return etat;
    }

    public void setEtat(EtatPossibleMachine etat) {
        this.etat = etat;
    }
}