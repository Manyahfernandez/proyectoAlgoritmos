public class LineasRectangulosColores{

	/*
	Se crean constantes para cada tipo de elemento en el juego, se crea una constantes con los colores 
	de cada elemento y se crea una variable para almacenar la cantidad de cada elemento en el tablero.
	*/
	public final int CUADRADO = 0, ESFERAB = 1, ESFERAC = 2, ESFERAG = 3, ESFERAR = 4, ESFERAY = 5, VACIO = 6;
	public static final Colores[] COLORESSELECCIONADOS = {Colores.PINK, Colores.BLUE, Colores.CYAN, Colores.GREEN, Colores.RED, Colores.YELLOW};
	public static int puntaje = 0;
	public static int[] proximosObjetos = {6,6,6};
	public static int[] cantidadDeElementos = {0,0,0,0,0,0};
	public static int[][] tablero = {{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6}};

	public static void inicializarTablero(){
		int k, j, i = 0, siguiente, anterior = 6;

		while(i < 3){
			j = (int)(Math.random()*8);
			k = (int)(Math.random()*8);
			siguiente = (int)(Math.random()*6); 
			if(tablero[j][k] == 6 && siguiente != anterior){
				tablero[j][k] = siguiente;
				sumaObjetos(siguiente);
				i++;	
			}
		}
	}

	public static void sumaObjetos(int posicion){
		cantidadDeElementos[posicion] += 1;
	}

	public static void obtenerProximosObjetos(){
		int menor = Integer.MAX_VALUE, i = 0;
		proximosObjetos[0] = (int)(Math.random()*6);
		proximosObjetos[1] = (int)(Math.random()*6);
		while(i < 6){
			if(menor > cantidadDeElementos[i]){
				menor = cantidadDeElementos[i];
			}
			i++;
		}
		proximosObjetos[2] = menor;
	}

	public static void mostrarEstadoDelJuego(){
		MaquinaDeTrazados mt = new MaquinaDeTrazados(500, 500, "Lineas de colores y rectangulos", Colores.WHITE);
		int longitud = 459, longitudPedazo = longitud/9, i = 0;
		mt.dibujarRectanguloLleno(0, 0, longitud, longitud,Colores.GRAY);
		mt.dibujarRectangulo(0, 0, longitud, longitud);
		while(i < longitud){
			mt.dibujarLinea(i + longitudPedazo,0,i + longitudPedazo,longitud);
			mt.dibujarLinea(0,i + longitudPedazo,longitud,i + longitudPedazo);	
			i = i + longitud/9;
		}

		i = 0;
		int j = 0;

		while(i < 9){
			while(j < 9){
				if(tablero[i][j] != 6){
					if(tablero[i][j] != 0){
						mt.dibujarOvaloLleno(j*longitudPedazo,i*longitudPedazo,longitudPedazo,longitudPedazo,COLORESSELECCIONADOS[tablero[i][j]]);
					}else{
						//mt.dibujarRectanguloLleno();
					}
				}
				j++;
			}
			j = 0;
			i++;
		}
		mt.mostrar();

	}



}