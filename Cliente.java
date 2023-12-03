public class Cliente{
	public static void main(String[] args){
		LineasRectangulosColores.inicializarTablero();
		int i = 0, j = 0;
		while(i < 9){
			while(j < 9){
				System.out.print(LineasRectangulosColores.tablero[i][j] + " ");
				j++;
			}
			System.out.println();
			j = 0;
			i++;
		}
		LineasRectangulosColores.obtenerProximosObjetos();
		LineasRectangulosColores.mostrarEstadoDelJuego();
		LineasRectangulosColores.obtenerJugadaValida();
		LineasRectangulosColores.agregarProximosObjetos();
		LineasRectangulosColores.obtenerProximosObjetos();
		LineasRectangulosColores.actualizarEstadoDelJuego();
	}
}