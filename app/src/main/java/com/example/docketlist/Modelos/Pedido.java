package com.example.docketlist.Modelos;

import java.text.DateFormat;
import java.util.Date;

public class Pedido {
    private int id;
    private String nombre; //del cliente
    private String fecha_registro; //fecha de registro DD/MM
    private String fecha_entrega; // fecha que se entrega el pedido DD/MM
    private Float cantC;
    private Float cantA;
    private Float total; //la suma a cobrar
    private String recorder; //quien registra el pedido
    private int entregado; //si se entregó o no el pedido
    private int pagado; //si se pagó o no el pedido

    // Constructor
    public  Pedido(){}

    public Pedido(int id, String nombre, Float cantC, Float cantA, String recorder, String fecha_entrega) {
        this.id = id;
        this.nombre = nombre;
        this.cantC = cantC;
        this.cantA = cantA;
        this.total = calcularTotal(cantA, cantC);
        this.recorder = recorder;
        this.fecha_entrega = fecha_entrega;
        this.fecha_registro = DateFormat.getDateTimeInstance().format(new Date());
        this.entregado = 0;
        this.pagado = 0;
    }

    /*
     * Getters and Setters
     */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }
    public void setFecha_registro(String fecha) {
        this.fecha_registro = fecha;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }
    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public Float getCantC() {
        return cantC;
    }
    public void setCantC(Float cantC) {
        this.cantC = cantC;
    }

    public Float getCantA() {
        return cantA;
    }
    public void setCantA(Float cantA) {
        this.cantA = cantA;
    }

    public Float getTotal() {
        return total;
    }
    public void setTotal(Float total) {
        this.total = total;
    }

    public String getRecorder() {
        return recorder;
    }
    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public int getEntregado() {
        return entregado;
    }
    public void setEntregado(int entregado) {
        this.entregado = entregado;
    }

    public int getPagado() {
        return pagado;
    }
    public void setPagado(int pagado) {
        this.pagado = pagado;
    }

    /**/
    private Float calcularTotal(Float cantA, Float cantC){
        float total = cantA + cantC;

        if(total>=9) return (total-9) * 200 + 1620;
        if(total>=6) return (total-6) * 200 + 1080;
        if(total>=3) return (total-3) * 200 + 540;
        return total*200;
    }

}
