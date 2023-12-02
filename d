[1mdiff --git a/lineasRectangulosColores.java b/lineasRectangulosColores.java[m
[1mindex 5ed0f38..7c5b5bf 100644[m
[1m--- a/lineasRectangulosColores.java[m
[1m+++ b/lineasRectangulosColores.java[m
[36m@@ -1,8 +1,15 @@[m
 public class lineasRectangulosColores{[m
[31m-	public final int CUADRADO = 0, [m
[31m-	public final Colores[] COLORESSELECCIONADOS = {};[m
[32m+[m
[32m+[m	[32m/*[m
[32m+[m	[32mSe crean constantes para cada tipo de elemento en el juego, se crea una constantes con los colores[m[41m [m
[32m+[m	[32mde cada elemento y se crea una variable para almacenar la cantidad de cada elemento en el tablero.[m
[32m+[m	[32m*/[m
[32m+[m
[32m+[m	[32mpublic final int CUADRADO = 0, ESFERAB = 1, ESFERAC = 2, ESFERAG = 3, ESFERAR = 4, ESFERAY = 5;[m
[32m+[m	[32mpublic final Colores[] COLORESSELECCIONADOS = {Colores.PINK, Colore.BLUE, Colores.CYAN, Colores.GREEN, Colores.RED, Colores.YELLOW};[m
[32m+[m	[32mpublic int[] cantidadDeElementos = {0,0,0,0,0,0};[m
 [m
 	public static void inicializarJuego(){[m
[31m-		[m
[32m+[m
 	}[m
 }[m
\ No newline at end of file[m
