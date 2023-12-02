public class LineasRectangulosColores{

	/*
	Se crean constantes para cada tipo de elemento en el juego, se crea una constantes con los colores 
	de cada elemento y se crea una variable para almacenar la cantidad de cada elemento en el tablero.
	*/
	public final int CUADRADO = 0, ESFERAB = 1, ESFERAC = 2, ESFERAG = 3, ESFERAR = 4, ESFERAY = 5, VACIO = 6;
	public static final Colores[] COLORESSELECCIONADOS = {Colores.MAGENTA, Colores.BLUE, Colores.CYAN, Colores.GREEN, Colores.RED, Colores.YELLOW};
	public static int puntaje = 0;
	public static int[] proximosObjetos = {6,6,6};
	public static int[] cantidadDeElementos = {0,0,0,0,0,0};
	public static int[] jugada = new int[4];
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
		int menor = Integer.MAX_VALUE, i = 0, posicionMenor = 0;
		proximosObjetos[0] = (int)(Math.random()*6);
		proximosObjetos[1] = (int)(Math.random()*6);
		while(i < 6){
			if(menor > cantidadDeElementos[i]){
				menor = cantidadDeElementos[i];
				posicionMenor = i;
			}
			i++;
		}
		proximosObjetos[2] = posicionMenor;
	}

	public static void mostrarEstadoDelJuego(){
		MaquinaDeTrazados mt = new MaquinaDeTrazados(700, 700, "Lineas de colores y rectangulos", Colores.WHITE);
		int longitud = 459, longitudPedazo = longitud/9, i = 0;
		mt.dibujarRectanguloLleno(0, 0, longitud, longitud,Colores.GRAY);
		mt.dibujarRectangulo(0, 0, longitud, longitud);

		int j = 0;

		while(i < 9){
			while(j < 9){
				if(tablero[i][j] != 6){
					if(tablero[i][j] != 0){
						mt.dibujarOvaloLleno(j*longitudPedazo,i*longitudPedazo,longitudPedazo,longitudPedazo,COLORESSELECCIONADOS[tablero[i][j]]);
					}else{
						mt.dibujarRectanguloLleno(j*longitudPedazo, i*longitudPedazo, longitudPedazo,longitudPedazo, COLORESSELECCIONADOS[tablero[i][j]]);
					}
				}
				j++;
			}
			j = 0;
			i++;
		}
		i = 0;

		while(i < longitud){
			mt.dibujarLinea(i + longitudPedazo,0,i + longitudPedazo,longitud);
			mt.dibujarLinea(0,i + longitudPedazo,longitud,i + longitudPedazo);	
			i = i + longitud/9;
		}

		i = 0;
		
		mt.dibujarRectanguloLleno(9*longitud/8, 0, longitudPedazo,3*longitudPedazo,Colores.LIGHT_GRAY);
		while(i < 3){
			if(proximosObjetos[i] != 0){
				mt.dibujarOvaloLleno(9*longitud/8,i*longitudPedazo,longitudPedazo,longitudPedazo,COLORESSELECCIONADOS[proximosObjetos[i]]);
			}else{
				mt.dibujarRectanguloLleno(9*longitud/8,i*longitudPedazo,longitudPedazo,longitudPedazo,COLORESSELECCIONADOS[proximosObjetos[i]]);
			}
			i++;
		}
		mt.dibujarRectangulo(9*longitud/8, 0, longitudPedazo,3*longitudPedazo);	
		mt.dibujarLinea(9*longitud/8,longitudPedazo,9*longitud/8 + longitudPedazo,longitudPedazo);
		mt.dibujarLinea(9*longitud/8, 2*longitudPedazo,9*longitud/8 + longitudPedazo, 2*longitudPedazo);
		mt.mostrar();
	}

	public static void obtenerJugadaValida(){

	}

}