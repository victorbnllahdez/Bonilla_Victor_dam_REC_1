package beans;

public class MuestraForense {

    private int id;
    private int codigoCaso;
    private String tipoMuestra;
    private String fechaRecogida;
    private String estadoCustodia;

    private CentroForense centro;

    private InformeForense informe;

    public MuestraForense() {
    }

    public MuestraForense(int id, int codigoCaso, String tipoMuestra, String fechaRecogida, String estadoCustodia, CentroForense centro, InformeForense informe) {
        this.id = id;
        this.codigoCaso = codigoCaso;
        this.tipoMuestra = tipoMuestra;
        this.fechaRecogida = fechaRecogida;
        this.estadoCustodia = estadoCustodia;
        this.centro = centro;
        this.informe = informe;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoCaso() {
        return codigoCaso;
    }

    public void setCodigoCaso(int codigoCaso) {
        this.codigoCaso = codigoCaso;
    }

    public String getTipoMuestra() {
        return tipoMuestra;
    }

    public void setTipoMuestra(String tipoMuestra) {
        this.tipoMuestra = tipoMuestra;
    }

    public String getFechaRecogida() {
        return fechaRecogida;
    }

    public void setFechaRecogida(String fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    public String getEstadoCustodia() {
        return estadoCustodia;
    }

    public void setEstadoCustodia(String estadoCustodia) {
        this.estadoCustodia = estadoCustodia;
    }

    public CentroForense getCentro() {
        return centro;
    }

    public void setCentro(CentroForense centro) {
        this.centro = centro;
    }


    public void setInforme(InformeForense informe) {
        this.informe = informe;
    }

    public InformeForense getInforme(){
        if(informe == null) informe = new InformeForense();
        return informe;
    }


    @Override
    public String toString() {
        return "MuestraForense{" +
                "id=" + id +
                ", codigoCaso=" + codigoCaso +
                ", tipoMuestra='" + tipoMuestra + '\'' +
                ", fechaRecogida='" + fechaRecogida + '\'' +
                ", estadoCustodia='" + estadoCustodia + '\'' +
                ", centro=" + centro +
                ", informe=" + informe +
                '}';
    }
}
