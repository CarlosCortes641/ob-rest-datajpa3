package com.example.obrestdatajpa.entitites;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "laptops")
public class Laptop {

    // atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marca;
    private String modelo;
    private Integer version;
    private Double precio;
    private Boolean online;

    // constructores

    public Laptop() {
    }

    public Laptop(Long id, String marca, String modelo, Integer version, Double precio, Boolean online) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.version = version;
        this.precio = precio;
        this.online = online;
    }
// getter y setter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }
}
