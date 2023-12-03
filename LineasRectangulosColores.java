import java.util.Scanner;
import java.awt.Font;

public class LineasRectangulosColores{

	/*
	------------Variables--------------
	CUADRADO : int --> Número entero que representa al elemento cuadrado en el juego.
	ESFERAB : int --> Número entero que representa al elemento esfera de color azul(BLUE).
	ESFERAC : int --> Número entero que representa al elemento esfera de color cyan(CYAN).
	ESFERAG : int --> Número entero que representa al elemento esfera de color verde(GREEN).
	ESFERAR : int --> Número entero que representa al elemento esfera de color rojo(RED).
	ESFERAY : int --> Número entero que representa al elemento esfera de color amarillo(YELLOW).
	VACIO: int --> Número entero que representa a una casilla vacia en el juego.
	COLORESSELECCIONADOS: Colores[] --> Colores de cada uno de los elementos en el tablero, donde cada posicion concuerda con el número del elemento.
	puntaje : int --> Almacena el puntaje del jugador.
	mt : MaquinaDeTrazados --> Establece una nueva pantalla.
	movimientos : int[][]--> Es los movimientos que son validos en el movimiento de un elemento en el tablero.
	proximosObjetos : int[] --> Almacena los proximos objetos a colocar en el tablero.
	cantidadDeElementos : int[] --> Almacena la cantidad de cada uno de los elementos en el tablero, donde la posicion en el arreglo representa al elemento.
	jugada : int[] --> Almacena el elemento a mover y al sitio donde se mueve dicho elemento.
	caminoAbierto : boolean --> Verifica si es posible mover el elemento a la posicion indicada.
	finalDelJuego : boolean --> Indica si el juego termino o no;
	tablero : int[][] --> Tablero del juego.
	*/

	public final int CUADRADO = 0, ESFERAB = 1, ESFERAC = 2, ESFERAG = 3, ESFERAR = 4, ESFERAY = 5, VACIO = 6;
	public static final Colores[] COLORESSELECCIONADOS = {Colores.MAGENTA, Colores.BLUE, Colores.CYAN, Colores.GREEN, Colores.RED, Colores.YELLOW};
	public static int puntaje;
	public static MaquinaDeTrazados mt;
	public static int[][] movimientos = {{0,0},{-1,0},{1,0},{0,1},{0,-1}};
	public static int[] proximosObjetos = {6,6,6};
	public static int[] cantidadDeElementos = {0,0,0,0,0,0};
	public static int[] jugada;
	public static boolean caminoAbierto;
	public static boolean finalDelJuego;
	public static int[][] tablero = {{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6},
									{6, 6, 6, 6, 6, 6, 6, 6, 6}};

	/*----Método que inicializa algunas variables del juego----*/

	public static void inicializarJuego(){
		mt = new MaquinaDeTrazados(700, 700, "Lineas de colores y rectangulos", Colores.WHITE);
		puntaje = 0;
		jugada = new int[4];
		caminoAbierto = false;
		finalDelJuego = false;
	}

	/*----Método que inicializa el tablero de juego----*/

	public static void inicializarTablero(){
		int k, j, i = 0, siguiente, anterior = 6;

		while(i < 3){
			j = (int)(Math.random()*9);
			k = (int)(Math.random()*9);
			siguiente = (int)(Math.random()*6); 
			if(tablero[j][k] == 6 && siguiente != anterior){
				tablero[j][k] = siguiente;
				anterior = siguiente;
				sumaObjetos(siguiente);
				i++;	
			}
		}
	}

	/*----Método que incrementa en uno el numero de un elemento en la posicion indicada----*/

	//@ requires 0 <= posicion < 5;
	//@ ensures cantidadDeElementos[posicion] == \old(cantidadDeElementos[posicion]) + 1;
	public static void sumaObjetos(int posicion){
		cantidadDeElementos[posicion] += 1;
	}

	/*----Método para obtener los proximos objetos---*/

	public static void obtenerProximosObjetos(){
		int menor = Integer.MAX_VALUE, i = 0, posicionMenor = 0;
		proximosObjetos[0] = (int)(Math.random()*6);
		proximosObjetos[1] = (int)(Math.random()*6);

		//@ maintaining 0 <= i <= 6;
		//@ maintaining menor == (\min int j; 0 <= j && j < i; cantidadDeElementos[i]);
		//@ decreases 6 - i;
		while(i < 6){
			if(menor > cantidadDeElementos[i]){
				menor = cantidadDeElementos[i];
				posicionMenor = i;
			}
			i++;
		}
		proximosObjetos[2] = posicionMenor;
	}

	/*----Método para mostrar el estado del juego manera grafica----*/

	public static void mostrarEstadoDelJuego(){
		int longitud = 459, longitudPedazo = longitud/9, i = 0;
		mt.dibujarRectanguloLleno(0, 0, longitud, longitud,Colores.GRAY);
		mt.dibujarRectangulo(0, 0, longitud, longitud);

		int j = 0;

		while(i < 9){
			while(j < 9){
				if(tablero[i][j] != 6){
					if(tablero[i][j] != 0){
						mt.dibujarOvaloLleno(j*longitudPedazo,i*longitudPedazo,longitudPedazo,longitudPedazo,COLORESSELECCIONADOS[tablero[i][j]]);
						mt.dibujarOvalo(j*longitudPedazo,i*longitudPedazo,longitudPedazo,longitudPedazo);
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
				mt.dibujarOvalo(9*longitud/8,i*longitudPedazo,longitudPedazo,longitudPedazo);

			}else{
				mt.dibujarRectanguloLleno(9*longitud/8,i*longitudPedazo,longitudPedazo,longitudPedazo,COLORESSELECCIONADOS[proximosObjetos[i]]);
			}
			i++;
		}
		mt.dibujarRectangulo(9*longitud/8, 0, longitudPedazo,3*longitudPedazo);	
		mt.dibujarLinea(9*longitud/8,longitudPedazo,9*longitud/8 + longitudPedazo,longitudPedazo);
		mt.dibujarLinea(9*longitud/8, 2*longitudPedazo,9*longitud/8 + longitudPedazo, 2*longitudPedazo);
		mt.configurarFuente("SansSerif", Font.PLAIN + Font.BOLD, 16);
		mt.dibujarString("Proximos", 9*longitud/8, 3*longitudPedazo + longitudPedazo/2, Colores.BLACK);
		mt.dibujarString("Elementos", 9*longitud/8, 3*longitudPedazo + longitudPedazo/2 + 16, Colores.BLACK);
		mt.configurarFuente("SansSerif", Font.BOLD, 20);
		mt.dibujarString("Puntaje:" + puntaje,0, longitud + longitudPedazo/2, Colores.BLACK);
		mt.mostrar();
	}



	/*----Método que verifica si el movimiento es valido----*/
	//@ requires 0 <= posX < 9;
	//@ requires 0 <= posY < 9;
	//@ ensures \result == (posX >= 0 && posX < 9 && posY >= 0 && posY < 9 && tablero[posX][posY] == 6);
	public static boolean esValido(int posX, int posY){
		if(posX >= 0 && posX < 9 && posY >= 0 && posY < 9 && tablero[posX][posY] == 6){
			return true;
		}

		return false;
	}

	//RECURSIVIDAD
	public static void caminoPosicion(int posX, int posY, int finalX, int finalY){
		if(posX == finalX && posY == finalY){
			caminoAbierto = true;
		}
		int dir = 0;
		while(!caminoAbierto && dir < 4){
			dir = dir + 1;
			if(esValido(posX + movimientos[dir][0], posY + movimientos[dir][1])){
				tablero[posX + movimientos[dir][0]][posY + movimientos[dir][1]] = tablero[posX][posY];
				tablero[posX][posY] = 7;
				caminoPosicion(posX + movimientos[dir][0], posY + movimientos[dir][1], finalX, finalY);
				tablero[posX][posY] = tablero[posX + movimientos[dir][0]][posY + movimientos[dir][1]];
				tablero[posX + movimientos[dir][0]][posY + movimientos[dir][1]] = 6;
			}
		}
	}


	public static void obtenerJugadaValida(){
		boolean valida = false;
		Scanner usuario = new Scanner(System.in);
		caminoAbierto = false;

		do{
			System.out.println("Ingresar movimiento:");
			jugada[0] = usuario.nextInt();
			jugada[1] = usuario.nextInt();
			jugada[2] = usuario.nextInt();
			jugada[3] = usuario.nextInt();

			if(tablero[jugada[2]][jugada[3]] == 6 && tablero[jugada[0]][jugada[1]] != 6){
				caminoPosicion(jugada[0], jugada[1], jugada[2], jugada[3]);
			}
			if(caminoAbierto){
				valida = true;
				tablero[jugada[2]][jugada[3]] = tablero[jugada[0]][jugada[1]];
				tablero[jugada[0]][jugada[1]] = 6; 
			}else{
				System.out.println("Movimiento invalido");
			}

		}while(!valida);

		int i = 0, j = 0;

		while(i < 9){
			while(j < 9){
				if(tablero[i][j] == 7){
					tablero[i][j] = 6;
				}
				j++;
			}
			j = 0;
			i++;
		}
	}

	/*----Método que agrega los proximos objetos al tablero----*/

	public static void agregarProximosObjetos(){
		int  i = 0, j, k;

		while(i < 3){
			j = (int)(Math.random()*9);
			k = (int)(Math.random()*9);
			if(tablero[j][k] == 6){
				tablero[j][k] = proximosObjetos[i];
				sumaObjetos(proximosObjetos[i]);
				i++;	
			}
		}
	}

	/*----Método que actualiza el tablero de juego----*/

	public static void actualizarEstadoDelJuego(){
		int longitud = 459, longitudPedazo = longitud/9, i = 0;
		mt.dibujarRectanguloLleno(0, 0, longitud, longitud,Colores.GRAY);
		mt.dibujarRectangulo(0, 0, longitud, longitud);

		int j = 0;

		while(i < 9){
			while(j < 9){
				if(tablero[i][j] != 6){
					if(tablero[i][j] != 0){
						mt.dibujarOvaloLleno(j*longitudPedazo,i*longitudPedazo,longitudPedazo,longitudPedazo,COLORESSELECCIONADOS[tablero[i][j]]);
						mt.dibujarOvalo(j*longitudPedazo,i*longitudPedazo,longitudPedazo,longitudPedazo);

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
				mt.dibujarOvalo(9*longitud/8,i*longitudPedazo,longitudPedazo,longitudPedazo);
			}else{
				mt.dibujarRectanguloLleno(9*longitud/8,i*longitudPedazo,longitudPedazo,longitudPedazo,COLORESSELECCIONADOS[proximosObjetos[i]]);
			}
			i++;
		}
		mt.dibujarRectangulo(9*longitud/8, 0, longitudPedazo,3*longitudPedazo);	
		mt.dibujarLinea(9*longitud/8,longitudPedazo,9*longitud/8 + longitudPedazo,longitudPedazo);
		mt.dibujarLinea(9*longitud/8, 2*longitudPedazo,9*longitud/8 + longitudPedazo, 2*longitudPedazo);
		mt.repintar();
	}

	/*----Funcion que indica que si el juego termino----*/

	//@ requires true;
	//@ ensures \result == (\forall int i; 0 <= i && i < 9; \forall int j;  0 <= j && j < 9; tablero[i][j] != 6);
	public static boolean determinarFinaldeJuego(){
		int i = 0, j = 0;

		//@ maintaining 0 <= i <= 9;
		//@ maintaining (\forall int h; 0 <= h && h < i; \forall int k;  0 <= k && k < 9; tablero[h][k] != 6);
		//@ decreases 9-i;
		while(i < 9){
			//@ maintaining 0 <= j <= 9;
			//@ maintaining (\forall int k;  0 <= k && k < j; tablero[i][k] != 6);
			//@ decreases 9-j;
			while(j < 9){
				if(tablero[i][j] == 6){
					return false;
				}
				j++;
			}
			j = 0;
			i++;
		} 

		return true;
	}

}