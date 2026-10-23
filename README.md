Práctica ascensor con hilos

Práctica UD02CP05 de Programación de Servicios y Procesos. Es una simulación de un ascensor que va atendiendo a varios usuarios usando hilos en Java.

Clases
Main: crea el ascensor y los usuarios, los mete en la cola y arranca los hilos.
Ascensor: es un hilo que atiende a los usuarios por orden. Sube y baja piso a piso y cuando no quedan usuarios vuelve al piso -1.
Usuario: es otro hilo. Espera su turno, se sube en su piso y se baja en el piso al que va.
Cómo lo he hecho

Cada usuario y el ascensor son hilos distintos, así que los he sincronizado con synchronized, wait() y notifyAll() sobre el objeto Ascensor. De esta forma el usuario espera hasta que lo atienden, hasta que el ascensor llega a su piso y hasta que llega a su destino.

Salida de ejemplo
Usuario 1 espera en piso 0
Usuario 1 sube al ascensor.
Ascensor en piso 1
Ascensor en piso 2
Ascensor en piso 3
Usuario 1 baja en piso 3
...
Ascensor llegó a piso -1. Fin del programa.
