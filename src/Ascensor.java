import java.util.LinkedList;

public class Ascensor extends Thread {
    private static final int PISO_FINAL = -1;
    private static final long TIEMPO_PISO = 300;

    private final LinkedList<Usuario> cola = new LinkedList<>();
    private int piso = 0;
    private Usuario atendido = null;
    private Usuario dentro = null;
    private boolean usuarioListo = false;

    public synchronized void agregar(Usuario usuario) {
        cola.add(usuario);
    }

    public synchronized void esperarTurno(Usuario usuario) throws InterruptedException {
        while (atendido != usuario) {
            wait();
        }
        System.out.println("Usuario " + usuario.getIdUsuario() + " espera en piso " + usuario.getOrigen());
        usuarioListo = true;
        notifyAll();
    }

    public synchronized void entrar(Usuario usuario) throws InterruptedException {
        while (atendido != usuario || piso != usuario.getOrigen()) {
            wait();
        }
        dentro = usuario;
        System.out.println("Usuario " + usuario.getIdUsuario() + " sube al ascensor.");
        notifyAll();
    }

    public synchronized void salir(Usuario usuario) throws InterruptedException {
        while (dentro != usuario || piso != usuario.getDestino()) {
            wait();
        }
        dentro = null;
        System.out.println("Usuario " + usuario.getIdUsuario() + " baja en piso " + piso);
        cola.remove(usuario);
        atendido = null;
        usuarioListo = false;
        notifyAll();
    }

    private void moverHasta(int objetivo) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (piso == objetivo) {
                    return;
                }
            }
            Thread.sleep(TIEMPO_PISO);
            synchronized (this) {
                piso += piso < objetivo ? 1 : -1;
                System.out.println("Ascensor en piso " + piso);
                notifyAll();
            }
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Usuario usuario;
                synchronized (this) {
                    usuario = cola.peekFirst();
                    if (usuario == null) {
                        break;
                    }
                    atendido = usuario;
                    notifyAll();
                    while (!usuarioListo) {
                        wait();
                    }
                }
                moverHasta(usuario.getOrigen());
                synchronized (this) {
                    notifyAll();
                    while (dentro != usuario) {
                        wait();
                    }
                }
                moverHasta(usuario.getDestino());
                synchronized (this) {
                    notifyAll();
                    while (atendido != null) {
                        wait();
                    }
                }
            }
            moverHasta(PISO_FINAL);
            synchronized (this) {
                System.out.println("Ascensor llegó a piso " + piso + ". Fin del programa.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
