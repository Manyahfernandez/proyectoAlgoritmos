import java.awt.Font;
import java.util.Scanner;
public class LineasRectangulosColores{

	/*
	------------Variables--------------
	CUADRADO : int --> Número entero que representa al elemento cuadrado en el juego.
	ESFERAB : int --> Número entero que representa al elemento esfera de color azul(BLUE).
	ESFERAC : int --> Número entero que representa al elemento esfera de color cyan(CYAN).
	ESFERAG : int --> Número entero que representa al elemento esfera de color verde(GREEN).
	ESFERAR : int --> Número entero que representa al elemento esfera de color rojo(RED).
	ESFERAY : int --> Número entero que representa al elemento esfera de color amarillo(YELLOW).
	ESFERAO : int --> Número entero que representa al elemento esfera de color anaranjada(ORANGE).
	VACIO: int --> Número entero que representa a una casilla vacia en el juego.
	COLORESSELECCIONADOS: Colores[] --> Colores de cada uno de los elementos en el tablero, donde cada posicion concuerda con el número del elemento.
	puntaje : int --> Almacena el puntaje del jugador.
	mt : MaquinaDeTrazados --> Establece una nueva pantalla.
	movimientos : int[][]--> Es los movimientos que son validos en el movimiento de un elemento en el tablero.
	proximosObjetos : int[] --> Almacena los proximos objetos a colocar en el tablero.
	cantidadDeElementos : int[] --> Almacena la cantidad de cada uno de los elementos en el tablero, donde la posicion en el arreglo representa al elemento.
	jugada : int[] --> Almacena el elemento a mover y al sitio donde se mueve dicho elemento.
	tablero : int[][] --> Tablero del juego.
	*/

	public final int CUADRADO = 0, ESFERAB = 1, ESFERAC = 2, ESFERAG = 3, ESFERAR = 4, ESFERAY = 5, ESFERAO = 6, VACIO = 7;
	public static final Colores[] COLORESSELECCIONADOS = {Colores.MAGENTA, Colores.BLUE, Colores.CYAN, Colores.GREEN, Colores.RED, Colores.YELLOW, Colores.WHITE};
	public static int puntaje;
	public static MaquinaDeTrazados mt;
	public static int[][] movimientos = {{0,0},{-1,0},{1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
	public static int[] proximosObjetos = {7,7,7};
	public static int[] cantidadDeElementos = {0,0,0,0,0,0,0};
	public static boolean seElimino = false;
	public static int[] jugada;
	public static int[][] tablero = {{7, 7, 7, 7, 7, 7, 7, 7, 7},
									{7, 7, 7, 7, 7, 7, 7, 7, 7},
									{7, 7, 7, 7, 7, 7, 7, 7, 7},
									{7, 7, 7, 7, 7, 7, 7, 7, 7},
									{7, 7, 7, 7, 7, 7, 7, 7, 7},
									{7, 7, 7, 7, 7, 7, 7, 7, 7},
									{7, 7, 7, 7, 7, 7, 7, 7, 7},
									{7, 7, 7, 7, 7, 7, 7, 7, 7},
									{7, 7, 7, 7, 7, 7, 7, 7, 7}};

	/*----Método que inicializa algunas variables del juego----*/
	/*
		Descripcion: Inicializa la variable mt, puntaje,  jugada.
	*/
	//@ requires true;
	//@ ensures true;
	public static void inicializarJuego(){
		mt = new MaquinaDeTrazados(700, 700, "Lineas de colores y rectangulos", Colores.WHITE);
		puntaje = 0;
		jugada = new int[4];
	}

	/*----Método que inicializa el tablero de juego----*/
	/*
		Descripcion: Inicializa el tablero de juego, con los primeros 3 elementos en el tablero.
	*/
	//@ requires true;
	//@ ensures true;
	public static void inicializarTablero(){
		int k, j, i = 0, siguiente, anterior = 7;

		while(i < 3){
			j = (int)(Math.random()*9);
			k = (int)(Math.random()*9);
			siguiente = (int)(Math.random()*7); 
			if(tablero[j][k] == 7 && siguiente != anterior){
				tablero[j][k] = siguiente;
				anterior = siguiente;
				sumaObjetos(siguiente);
				i++;	
			}
		}
	}

	/*----Método que incrementa en uno el numero de un elemento en la posicion indicada----*/
	/*
		Descricion: Incrementa en uno la cantidad del elemento.
		Parametros: posicion : int--> Es la posicion en el arreglo cantidadDeElementos.
	*/
	//@ requires 0 <= posicion < 7;
	//@ ensures cantidadDeElementos[posicion] == \old(cantidadDeElementos[posicion]) + 1;
	public static void sumaObjetos(int posicion){
		cantidadDeElementos[posicion] += 1;
	}

	/*----Método para obtener los proximos objetos---*/
	/*
		Descripcion: Obtiene los proximos 3 elementos a colocar en el tablero.
	*/
	//@ requires true;
	//@ ensures true;
	public static void obtenerProximosObjetos(){
		int menor = Integer.MAX_VALUE, i = 0, posicionMenor = 0;
		proximosObjetos[0] = (int)(Math.random()*7);
		proximosObjetos[1] = (int)(Math.random()*7);

		//@ maintaining 0 <= i <= 7;
		//@ maintaining menor == (\min int j; 0 <= j && j < i; cantidadDeElementos[i]);
		//@ decreases 7 - i;
		while(i < 7){
			if(menor > cantidadDeElementos[i]){
				menor = cantidadDeElementos[i];
				posicionMenor = i;
			}
			i++;
		}
		proximosObjetos[2] = posicionMenor;
	}

	/*----Método para mostrar el estado del juego manera grafica----*/
	/*
		Descripcion: Muestra  de forma grafica el estado del juego. Se eligio 459 ya que primero es multiplo de 9 por tanto es mucho mas facil
					 hacer la division de las columnas y filas.
	*/
	//@ requires true;
	//@ ensures true;
	public static void mostrarEstadoDelJuego(){
		int longitud = 459, longitudPedazo = longitud/9, i = 0;
		mt.dibujarRectanguloLleno(0, 0, longitud, longitud,Colores.GRAY);
		mt.dibujarRectangulo(0, 0, longitud, longitud);

		int j = 0;

		while(i < 9){
			while(j < 9){
				if(tablero[i][j] != 7){
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
		mt.dibujarString("Proximos", 9*longitud/8, 7*longitudPedazo/2, Colores.BLACK);
		mt.dibujarString("Elementos", 9*longitud/8, 7*longitudPedazo/2 + 16, Colores.BLACK);
		mt.configurarFuente("SansSerif", Font.BOLD, 20);
		mt.dibujarString("Puntaje:" + puntaje,0, longitud + 20 + longitudPedazo, Colores.BLACK);
		mt.configurarFuente("SansSerif", Font.PLAIN, 14);

		i = 0;

		while(i < 9){
			mt.dibujarString(""+i,longitud + 14, longitudPedazo/2 + i*longitudPedazo + 7, Colores.BLACK);
			i++;
		}

		i = 0;

		while(i < 9){
			mt.dibujarString(""+i,longitudPedazo/2 + i*longitudPedazo - 7,longitud + 20,Colores.BLACK);
			i++;
		}

		mt.mostrar();
	}



	/*----Método que verifica si el movimiento es valido----*/
	/*
		Descripcion: Verifica si se posee una casilla vacia alrededor, comprobando que no se salga del tablero.
		Parametros: posX : int --> posicion de la fila a la cual se va a mover.
					posY : int --> posicion de la columna a la cual se va a mover.
	*/
	//@ requires 0 <= posX < 9;
	//@ requires 0 <= posY < 9;
	//@ ensures \result == (posX >= 0 && posX < 9 && posY >= 0 && posY < 9 && tablero[posX][posY] == 7);
	public static /*@ pure @*/ boolean esValido(int posX, int posY){
		if(posX >= 0 && posX < 9 && posY >= 0 && posY < 9 && tablero[posX][posY] == 7){
			return true;
		}

		return false;
	}

	/*----Funcion que verifica si existe casilla vacia----*/
	/*
		Descripcion: Verifica alrededor de la posicion del elemento si existe una casilla vacia.
		Parametros: posX : int --> posicion de la fila en la que se encuentra el elemento
					posY : int --> posicion de la columna en la que se encuentra el elemento
	*/
	//@ requires 0 <= posX < 9;
	//@ requires 0 <= posY < 9;
	//@ ensures \result == (\exists int i; 0 <= i && i < 9; esValido(posX + movimientos[i][0], posY + movimientos[i][1]));
	public static /*@ pure @*/ boolean caminoPosicion(int posX, int posY){
		int dir = 0;
		//@ maintaining 0 <= dir <= 8;
		//@ maintaining  (\forall int j; 0 <= j && j < dir; !esValido(posX + movimientos[dir][0], posY + movimientos[dir][1]));
		//decreases 8 - dir;
		while(dir < 8){
			dir = dir + 1;

			if(esValido(posX + movimientos[dir][0], posY + movimientos[dir][1])){
				return true;
			}
		}
		return false;
	}

	/*----Método que verifica la jugada ----*/
	/*
		Descripcion: Verifica si la jugada que quiere hacer el jugador es valida
	*/
	
	public static void obtenerJugadasValida(){
		Scanner usuario = new Scanner(System.in);
		boolean valida = false;
		do{	
			System.out.println("Ingrese movimiento: ");
			jugada[0] = usuario.nextInt();
			jugada[1] = usuario.nextInt();
			jugada[2] = usuario.nextInt();
			jugada[3] = usuario.nextInt();
 			//@ assert 0 <= jugada[0] < 9;
 			//@ assert 0 <= jugada[1] < 9;
 			//@ assert 0 <= jugada[2] < 9;
 			//@ assert 0 <= jugada[3] < 9;
			if(jugada[0] >= 0 && jugada[0] < 9 && jugada[1] >= 0 && jugada[1] < 9 && jugada[2] >= 0 && jugada[2] < 9 && jugada[3] >= 0 && jugada[3] < 9){	
				if(tablero[jugada[0]][jugada[1]] != 7){
					if(tablero[jugada[2]][jugada[3]] == 7){
						if(caminoPosicion(jugada[0], jugada[1])){
							tablero[jugada[2]][jugada[3]] = tablero[jugada[0]][jugada[1]];
							tablero[jugada[0]][jugada[1]] = 7; 
							valida = true;
							System.out.println("ELemento movido a la casilla: " + jugada[2] + " " + jugada[3]);
						}else{
						System.out.println("Elemento seleccionado no puede ser movido");	
						}
					}else{
						System.out.println("Casilla " + jugada[2] + " " + jugada[3] + " no se encuentra vacia");
					}
				}
				else{
					System.out.println("La casilla " + jugada[0] + " " + jugada[1] + " se encuentra vacia");
				}
			}	
			else{
				System.out.println("Movimiento invalido, posicion se sale del tablero de juego");
			}
		}while(!valida);
	}

	/*----Método que agrega los proximos objetos al tablero----*/
	/*
		Descripcion: Agrega en el tablero los objetos que se encuentra en proximosObjetos.
	*/
	public static void agregarProximosObjetos(){
		int  i = 0, j, k;

		//@ maintaining 0 <= i <= 3;

		while(i < 3){
			j = (int)(Math.random()*9);
			k = (int)(Math.random()*9);
			if(tablero[j][k] == 7){
				tablero[j][k] = proximosObjetos[i];
				sumaObjetos(proximosObjetos[i]);
				i++;	
			}
		}
	}

	/*----Método que actualiza el tablero de juego----*/
	/*
		Descripcion: Actualiza y repinta la pantalla grafica del juego, colocando los nuevos elementos en este.
	*/
	public static void actualizarEstadoDelJuego(){
		int longitud = 459, longitudPedazo = longitud/9, i = 0;
		mt.dibujarRectanguloLleno(0, 0, longitud, longitud,Colores.GRAY);
		mt.dibujarRectangulo(0, 0, longitud, longitud);

		int j = 0;

		while(i < 9){
			while(j < 9){
				if(tablero[i][j] != 7){
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
		mt.configurarFuente("SansSerif", Font.BOLD, 20);
		mt.dibujarRectanguloLleno(0, longitud + longitudPedazo, longitud, 48, Colores.WHITE);
		mt.dibujarString("Puntaje:" + puntaje,0, longitud + 20 + longitudPedazo, Colores.BLACK);
		mt.repintar();
	}

	/*----Funcion que indica que si el juego termino----*/
	/*
		Descripcion: Determina si es el final del juego.
		Retona : boolean --> que indica si queda alguna casilla vacia en el tablero.
	*/
	//@ requires true;
	//@ ensures \result == (\forall int i; 0 <= i && i < 9; \forall int j;  0 <= j && j < 9; tablero[i][j] != 7);
	public static /*@ pure @*/boolean determinarFinaldeJuego(){
		int i = 0, j = 0;

		//@ maintaining 0 <= i <= 9;
		//@ maintaining (\forall int h; 0 <= h && h < i; \forall int k;  0 <= k && k < 9; tablero[h][k] != 7);
		//@ decreases 9-i;
		while(i < 9){
			//@ maintaining 0 <= j <= 9;
			//@ maintaining (\forall int k;  0 <= k && k < j; tablero[i][k] != 7);
			//@ decreases 9-j;
			while(j < 9){
				if(tablero[i][j] == 7){
					return false;
				}
				j++;
			}
			j = 0;
			i++;
		} 

		return true;
	}

	public static void sumarPuntaje(int cantidad){
		if(cantidad == 4){
			puntaje += 5;
		}
		if(cantidad == 5){
			puntaje += 10;
		}
		if(cantidad == 6){
			puntaje +=12;
		}
		if(cantidad == 7){
			puntaje +=18;
		}
		if(cantidad >= 8){
			puntaje += 40;
		}
	}

	public static void matrizProcesados(int objeto){
		int i = 0, j = 0;
		while(i < 9){
			while(j < 9){
				if(tablero[i][j] == -1){
					tablero[i][j] = objeto;
				}
				j++;
			}
			j = 0;
			i++;
		}
	}

	public static void validarCantidad(int cantidad, int objeto){
		int i = 0, j = 0;
		if(cantidad >= 5 || (cantidad == 4 && objeto == 0)){
			matrizProcesados(7);
			sumarPuntaje(cantidad);
			cantidadDeElementos[objeto] -= cantidad;
			seElimino = true;

		}else{
			matrizProcesados(objeto);
		}
	}

	public static /*@ pure @*/ boolean validarSiquienteObjeto(int posX, int posY, int objeto){
		if(posX >= 0 && posX < 9 && posY >= 0 && posY < 9 && tablero[posX][posY] == objeto){
			return true;
		}else{
			return false;
		}
	}

	public static void validarLineaDerecha(int posX, int posY){
		int cantidad  = 1, posicionY= posY;
		posicionY = posY + 1;

		while(validarSiquienteObjeto(posX, posicionY, tablero[posX][posY])){
			cantidad++;
			tablero[posX][posicionY] = -1;
			posicionY ++;
		}
		validarCantidad(cantidad, tablero[posX][posY]);
	}

	public static void validarLineaAbajo(int posX, int posY){
		int cantidad  = 1, posicionX= posX;
		posicionX = posX + 1;

		while(validarSiquienteObjeto(posicionX, posY, tablero[posX][posY])){
			cantidad++;
			tablero[posicionX][posY] = -1;
			posicionX ++;
		}
		validarCantidad(cantidad, tablero[posX][posY]);
	}

	public static void validarLineaDiagonal(int posX, int posY){
		int cantidad  = 1, posicionX = posX, posicionY = posY;
		posicionX = posX + 1;
		posicionY = posY + 1;

		while(validarSiquienteObjeto(posicionX, posicionY, tablero[posX][posY])){
			cantidad++;
			tablero[posicionX][posicionY] = -1;
			posicionX ++;
			posicionY ++;
		}
		validarCantidad(cantidad, tablero[posX][posY]);
	}

	public static void validarLineaDiagonalContraria(int posX, int posY){
		int cantidad  = 1, posicionX = posX, posicionY = posY;
		posicionX = posX - 1;
		posicionY = posY + 1;

		while(validarSiquienteObjeto(posicionX, posicionY, tablero[posX][posY])){
			cantidad++;
			tablero[posicionX][posicionY] = -1;
			posicionX--;
			posicionY++;
		}
		validarCantidad(cantidad, tablero[posX][posY]);
	}

	public static int validarCuadrado(int posX, int posY){
		int posicionY = posY, i = posX, j = posY, cantidad = 1, cantidadRetornada = 0;

		posicionY = posY + 1;

		while(validarSiquienteObjeto(posX, posicionY, tablero[posX][posY])){
			cantidad++;
			posicionY++;
		}

		if(cantidad == 1){
			return -1;
		}

		while(i < posX + cantidad){
			while(j <  posY + cantidad){
				if(validarSiquienteObjeto(i,j,0)){
					tablero[i][j] = -1;
					cantidadRetornada++;
				}else{
					return -1;
				}
				j++;
			}
			j = posY;
			i++;
		}

		return cantidadRetornada;
	}

	public static void cuadradoVerificar(int posX, int posY){
		int cantidad = validarCuadrado(posX, posY);
		if(cantidad != -1){
			validarCantidad(cantidad,0);
			System.out.println("Elimine algo");
			System.out.println(cantidad);
			seElimino = true;
		}else{
			matrizProcesados(0);
		}
	}


	public static boolean procesarObjetosDelTablero(){
		int i = 0, j = 0;

		while(i < 9){
			while(j < 9){
				if(tablero[i][j] != 7){
					if(tablero[i][j] != 0){
						validarLineaDerecha(i,j);
						validarLineaAbajo(i,j);
						validarLineaDiagonal(i,j);
						validarLineaDiagonalContraria(i,j);
						if(seElimino){
							System.out.println("Se elimino una linea. Nuevo Puntaje: " + puntaje);
							System.out.println("Ganaste una nueva movida");
							tablero[i][j] = 7;
							seElimino = false;
							return true;
						}
					}else{
						cuadradoVerificar(i,j);
						if(seElimino){
							System.out.println("Se elimino un cuadrado. Nuevo Puntaje: " + puntaje);
							System.out.println("Ganaste una nueva movida");
							seElimino = false;
							return true;
						}
					}
				}
				j++;
			}
			j = 0;
			i++;
		}

		return false;
	}

	public static void main(String[] args) {
		int i = 0;

		System.out.println("\t Lineas de Rectangulos y Colores: ");
		LineasRectangulosColores.inicializarJuego();
		LineasRectangulosColores.inicializarTablero();
		LineasRectangulosColores.obtenerProximosObjetos();
		LineasRectangulosColores.mostrarEstadoDelJuego();

		while(!LineasRectangulosColores.determinarFinaldeJuego()){
			LineasRectangulosColores.obtenerJugadasValida();
			LineasRectangulosColores.actualizarEstadoDelJuego();
			if(!LineasRectangulosColores.procesarObjetosDelTablero()){
				LineasRectangulosColores.agregarProximosObjetos();
				LineasRectangulosColores.obtenerProximosObjetos();
				while(i < 7){
				System.out.println(cantidadDeElementos[i]);
				i++;
			}
			i = 0;
			}
			LineasRectangulosColores.actualizarEstadoDelJuego();
		}
	}
}