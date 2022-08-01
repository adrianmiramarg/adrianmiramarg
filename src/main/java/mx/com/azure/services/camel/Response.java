/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.azure.services.camel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrian
 */
public class Response {

    private Integer resultadoOperacion;
    private String motivoRechazo;
    private List<Saldos> Saldos = new ArrayList();

    /**
     * @return the resultadoOperacion
     */
    public Integer getResultadoOperacion() {
        return resultadoOperacion;
    }

    /**
     * @param resultadoOperacion the resultadoOperacion to set
     */
    public void setResultadoOperacion(Integer resultadoOperacion) {
        this.resultadoOperacion = resultadoOperacion;
    }

    /**
     * @return the motivoRechazo
     */
    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    /**
     * @param motivoRechazo the motivoRechazo to set
     */
    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    /**
     * @return the Saldos
     */
    public List<Saldos> getSaldos() {
        return Saldos;
    }

    /**
     * @param Saldos the Saldos to set
     */
    public void setSaldos(List<Saldos> Saldos) {
        this.Saldos = Saldos;
    }
    
}
