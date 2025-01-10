package interfaces;

public enum EnumCombustible {

	RESERVA("1/8"), CUARTO("1/4"), MEDIO("1/2"),TRESCUARTOS("3/4"), COMPLETO("f");

    private String descripcion;

    // Constructor
    EnumCombustible(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

