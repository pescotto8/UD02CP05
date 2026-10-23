public class Main {
    public static void main(String[] args) throws InterruptedException {
        Ascensor ascensor = new Ascensor();

        Usuario[] usuarios = {
            new Usuario(1, 0, 3, ascensor),
            new Usuario(2, 2, -1, ascensor),
            new Usuario(3, 1, 5, ascensor)
        };

        for (Usuario usuario : usuarios) {
            ascensor.agregar(usuario);
        }

        for (Usuario usuario : usuarios) {
            usuario.start();
        }
        ascensor.start();

        ascensor.join();
    }
}
