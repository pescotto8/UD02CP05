public class Usuario extends Thread {
    private final int id;
    private final int origen;
    private final int destino;
    private final Ascensor ascensor;

    public Usuario(int id, int origen, int destino, Ascensor ascensor) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.ascensor = ascensor;
    }

    public int getIdUsuario() {
        return id;
    }

    public int getOrigen() {
        return origen;
    }

    public int getDestino() {
        return destino;
    }

    @Override
    public void run() {
        try {
            ascensor.esperarTurno(this);
            ascensor.entrar(this);
            ascensor.salir(this);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
