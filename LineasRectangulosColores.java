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
	//@(*Precondicion: Ninguna*);
	//@(*Postcondicion: Se debieron inicializar las variables mt, puntaje y jugada*);
	public static void inicializarJuego(){
		mt = new MaquinaDeTrazados(500, 500, "Lineas de colores y rectangulos", Colores.WHITE);
		puntaje = 0;
		jugada = new int[4];
	}

	/*----Método que inicializa el tablero de juego----*/
	/*
		Descripcion: Inicializa el tablero de juego, con los primeros 3 elementos en el tablero.
	*/
	//@(*Precondicion: Haber inicializado el juego con iniciarJuego()*)
	//@(*Postcondicion: Se colocaron en el tablero 3 elemetos de manera aleatoria*)
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
	//@(*Precondicion: Haber colocado los elementos el el tablero con agregarProximosObjetos()*);
	//@(*Postcondicion: Haber seleccionado 3 nuevos elementos a agregar al tablero*);
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
	//@(*Precondicion: Haber inicializado el tablero con inicializarTablero()*);
	//@(*Postcondicion: Mostrar de maner grafica el tablero de juego inicial*);
	public static void mostrarEstadoDelJuego(){
		int longitud = 351, longitudPedazo = longitud/9, i = 0;
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
	//@(*Precondicion: Haber inicializado el tablero de juego y mostrar el tablero de forma grafica*);
	//@(*Postcondicion: Mover un elemento a una casilla vacia*);
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
	//@(*Precondicion: No haber eliminado ninguna linea o cuadrado de tablero*);
	//@(*Postcondicion: Colocá tres nuevos elementos en el tablero de forma aleatoria*);
	public static void agregarProximosObjetos(){
		int  i = 0, j, k;

		//@ maintaining 0 <= i <= 3;

		while(i < 3 && !determinarFinaldeJuego()){
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
	//@(*Precondicion: Haber agregado o movido algun elemento en el tablero*);
	//@(*Postconficion: Tablero de juego actualizado*);
	public static void actualizarEstadoDelJuego(){
		int longitud = 351, longitudPedazo = longitud/9, i = 0;
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
		Retonar : boolean --> que indica si queda alguna casilla vacia en el tablero.
	*/
	//@(*Precondicion: Haber inicializado el juego*);
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

	/*----Método que suma puntaje del usuario---*/
	/*
		Descripcion: Aumenta el puntaje del usuario.
			Parametros: cantidad : int --> cantidad de elementos eliminados.
	*/
	//@ requires cantidad >= 0;
	//@ ensures \old(puntaje) > puntaje;
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

	/*----Método que modifica los elemetos eliminados del tablero---*/
	/*
		Descripcion: Determina si es el final del juego.
		Parametros: posX : int --> elemento a eliminar o colocar en la matriz una vez validado si existe o no una linea o cuadrado.
	*/
	//@ requires 0 <= objeto <= 7;
	//@(*Postcondicion: Modifica el tablero colocando el elemento en las casillas con -1*);
	public static void matrizProcesados(int objeto){
		int i = 0, j = 0;
		//@ maintaining 0 <= i <= 9;
		//@ maintaining (\forall int k; 0 <= k < i; (\forall int h; 0 <= h && h < 9; tablero[k][h] != -1));
		//@ decreases 9-i;
		while(i < 9){
			//@ maintaining 0 <= j <= 9;
			//@ maintaining (\forall int h; 0 <= h && h < j; tablero[i][h] != -1);
			//@ decreases 9-j;
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


	/*----Método que valida la cantidad de elementos para llamar al metodo sumarPuntaje()----*/
	/*
		Descripcion: validad la cantidad de elementos eliminada.
		Parametros: cantidad : int --> cantidad de elemetos eliminados.
					objeto : int --> tipo de elemento eliminado.
	*/
	//@ requires cantidad >= 0;
	//@ requires 0 <= objeto <= 6;
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

	/*----Funcion que valida el siguiente objeto----*/
	/*
		Descripcion: Determina si el siguiente objeto es del mismo tipo.
		Parametros: posX : int --> posicion de la fila en la que se encuentra el elemento.
					posY : int --> posicion de la columna en la que se encuentra el elemento.
					objeto : int --> tipo de elemento a validar
		Retonar : boolean --> que indica si el siguiente elemento es del mismo tipo.
	*/
	//@ requires 0 <= posX < 9;
	//@ requires 0 <= posY < 9;
	//@ requires 0 <= objeto <= 6;
	//@ ensures \result == (posX >= 0 && posX < 9 && posY >= 0 && posY < 9 && tablero[posX][posY] == objeto);
	public static /*@ pure @*/ boolean validarSiquienteObjeto(int posX, int posY, int objeto){
		if(posX >= 0 && posX < 9 && posY >= 0 && posY < 9 && tablero[posX][posY] == objeto){
			return true;
		}else{
			return false;
		}
	}

	/*----Metodo que verifica si se formo alguna linea hacia la derecha----*/
	/*
		Descripcion: Determina si se formo una linea hacia la derecha.
		Parametros: posX : int --> posicion de la fila en la que se encuentra el elemento
					posY : int --> posicion de la columna en la que se encuentra el elemento
	*/
	//@ requires 0 <= posX < 9;
	//@ requires 0 <= posY < 9;
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

	/*----Metodo que verifica si se formo alguna linea hacia abajo---*/
	/*
		Descripcion: Determina si se formo una linea hacia abajo.
	*/
	//@ requires 0 <= posX < 9;
	//@ requires 0 <= posY < 9;
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

	/*----Metodo que verifica si se formo alguna linea diagonal---*/
	/*
		Descripcion: Determina si se formo una linea diagonal.
		Parametros: posX : int --> posicion de la fila en la que se encuentra el elemento
					posY : int --> posicion de la columna en la que se encuentra el elemento
	*/
	//@ requires 0 <= posX < 9;
	//@ requires 0 <= posY < 9;
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

	/*----Metodo que verifica si se formo alguna diagonal contraria----*/
	/*
		Descripcion: Determina si se formo una linea diagonal contraria
		Parametros: posX : int --> posicion de la fila en la que se encuentra el elemento
					posY : int --> posicion de la columna en la que se encuentra el elemento
	*/
	//@ requires 0 <= posX < 9;
	//@ requires 0 <= posY < 9;
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

	/*----Metodo que verifica si existe un cuadrado---*/
	/*
		Descripcion: Determina si se formo un cuadrado
		Parametros: posX : int --> posicion de la fila en la que se encuentra el elemento
					posY : int --> posicion de la columna en la que se encuentra el elemento
	*/
	//@ requires 0 <= posX < 9;
	//@ requires 0 <= posY < 9;
	public static /*@ pure @*/ int validarCuadrado(int posX, int posY){
		int posicionY = posY, i = posX, j = posY, cantidady = 1, cantidad, cantidadx = 1, cantidadRetornada = 0, posicionX;
		posicionX = posX + 1;
		posicionY = posY + 1;

		while(validarSiquienteObjeto(posX, posicionY, tablero[posX][posY])){
			cantidady++;
			posicionY++;
		}

		while(validarSiquienteObjeto(posicionX, posY, tablero[posX][posY])){
			cantidadx++;
			posicionX++;
		}

		if(cantidadx == 1 || cantidady == 1 || cantidadx == 2 || cantidady == 2){
			return -1;
		}

		if(cantidadx >= cantidady){
			cantidad = cantidadx;
		}else{
			cantidad = cantidady;
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

	/*----Metodo que llama a la funcion validarCuadrado()----*/
	/*
		Descripcion: Determina si se formo una linea haca la derecha.
		Parametros: posX : int --> posicion de la fila en la que se encuentra el elemento
					posY : int --> posicion de la columna en la que se encuentra el elemento
	*/
	//@ requires 0 <= posX < 9;
	//@ requires 0 <= posY < 9;
	public static void cuadradoVerificar(int posX, int posY){
		int cantidad = validarCuadrado(posX, posY);
		if(cantidad != -1){
			validarCantidad(cantidad,0);
			seElimino = true;
		}else{
			matrizProcesados(0);
		}
	}


	public static /*@ pure @*/ boolean procesarObjetosDelTablero(){
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

	public static int seleccion;

	public static void iniciarElJuego(){
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
			}
			LineasRectangulosColores.actualizarEstadoDelJuego();
		}

		LineasRectangulosColores.procesarFinalJuego();
		System.out.println("\t Juego Terminado");
		System.out.println("Puntaje: " + puntaje);
	}

	public static void instruccionesGrafico(){
		mt = new MaquinaDeTrazados(450, 450, "Lineas de colores y rectangulos", Colores.WHITE);
		mt.dibujarRectanguloLleno(0,0,15,15,COLORESSELECCIONADOS[0]);
		mt.dibujarRectangulo(0,0,15,15);
		mt.dibujarOvaloLleno(15,0,15,15,COLORESSELECCIONADOS[1]);
		mt.dibujarOvalo(15,0,15,15);
		mt.dibujarOvaloLleno(30,0,15,15,COLORESSELECCIONADOS[2]);
		mt.dibujarOvalo(30,0,15,15);
		mt.dibujarOvaloLleno(45,0,15,15,COLORESSELECCIONADOS[3]);
		mt.dibujarOvalo(45,0,15,15);
		mt.dibujarOvaloLleno(60,0,15,15,COLORESSELECCIONADOS[4]);
		mt.dibujarOvalo(60,0,15,15);
		mt.dibujarOvaloLleno(75,0,15,15,COLORESSELECCIONADOS[5]);
		mt.dibujarOvalo(75,0,15,15);
		mt.dibujarOvaloLleno(90,0,15,15,COLORESSELECCIONADOS[6]);
		mt.dibujarOvalo(90,0,15,15);
		mt.configurarFuente("SansSerif", Font.BOLD, 12);
		mt.dibujarString("Instrucciones del juego",150, 15, Colores.BLACK);
		mt.dibujarOvaloLleno(345,0,15,15,COLORESSELECCIONADOS[6]);
		mt.dibujarOvalo(345,0,15,15);
		mt.dibujarOvaloLleno(360,0,15,15,COLORESSELECCIONADOS[5]);
		mt.dibujarOvalo(360,0,15,15);
		mt.dibujarOvaloLleno(375,0,15,15,COLORESSELECCIONADOS[4]);
		mt.dibujarOvalo(375,0,15,15);
		mt.dibujarOvaloLleno(390,0,15,15,COLORESSELECCIONADOS[3]);
		mt.dibujarOvalo(390,0,15,15);
		mt.dibujarOvaloLleno(405,0,15,15,COLORESSELECCIONADOS[2]);
		mt.dibujarOvalo(405,0,15,15);
		mt.dibujarOvaloLleno(420,0,15,15,COLORESSELECCIONADOS[1]);
		mt.dibujarOvalo(420,0,15,15);
		mt.dibujarRectanguloLleno(435,0,15,15,COLORESSELECCIONADOS[0]);
		mt.dibujarRectangulo(435,0,15,15);

		mt.configurarFuente("SansSerif", Font.BOLD, 12);
		mt.dibujarString("En el juego 'Lineas de colores y rectangulos' el jugador",5, 45, Colores.BLACK);
		mt.dibujarString("debera formar lineas de esferas o formar cuadrados, con las",0, 57, Colores.BLACK);
		mt.dibujarString("siguientes instrucciones: ",0, 69, Colores.BLACK);

		mt.dibujarString("1. Formar lineas de 5 o mas esferas del mismo color ya sean",5, 93, Colores.BLACK);
		mt.dibujarString("hacia arriba, abajo,hacia un lado o diagonal.",0, 105, Colores.BLACK);
		mt.dibujarString("2. Formar cuadrados de 3 x 3 o mas.",5, 117, Colores.BLACK);
		mt.dibujarString("La consola podra arrojar alguno de los siguientes mensajes: ",5, 141, Colores.BLACK);
		mt.dibujarString("al ingresar un movimiento",0, 153, Colores.BLACK);
		mt.dibujarString("1.'Elemento movido a la casilla: ' si el elemento seleccionado",5, 177, Colores.BLACK);
		mt.dibujarString("por el usuario se movio a la casilla de manera exitosa",0, 189, Colores.BLACK);
		mt.dibujarString("2. 'Elemento seleccionado no puede ser movido' si el elemento",5, 201, Colores.BLACK);
		mt.dibujarString("no posee a su alrededor alguna casilla vacia.",0, 213, Colores.BLACK);
		mt.dibujarString("3. 'Casilla [i] [j] no se encuentra vacia' si la casilla [i][j] a la que",5, 225, Colores.BLACK);
		mt.dibujarString("se quiere mover un elemento no se encuentra vacia.",0, 237, Colores.BLACK);
		mt.dibujarString("4. 'La casilla [i] [j] se encuentra vacia' si la casilla de la cual",5, 249, Colores.BLACK);
		mt.dibujarString("se quiere mover un elemento se encuentra vacia.",0, 261, Colores.BLACK);
		mt.dibujarString("5. 'Movimiento invalido, posicion se sale del tablero' si alguna",5, 273, Colores.BLACK);
		mt.dibujarString("fila o columna es mayor a los valores permitidos",0, 285, Colores.BLACK);

		mt.dibujarString("El usuario primero ingresara la fila y la columna del elemento",5,309, Colores.BLACK);
		mt.dibujarString("a mover y despues la fila y columna de la casilla a la cual se",0, 321, Colores.BLACK);
		mt.dibujarString("desea mover el elemento, a la derecha se muestra el numero",0, 333, Colores.BLACK);
		mt.dibujarString("de cada fila y abajo el numero de cada columna",0, 345, Colores.BLACK);
		mt.dibujarString("DIVIERTETE...!!! PARA INICIAR EL JUEGO INGRESA 1",50, 381, Colores.MAGENTA);
		mt.mostrar();
	}

	public static void procesarFinalJuego(){
		mt.dibujarRectanguloLleno(0,150,500,100,Colores.WHITE);
		mt.dibujarRectangulo(0,150,500,100);
		mt.configurarFuente("Monospaced", Font.BOLD, 24);
		mt.dibujarString("Juego Terminado",150,210, Colores.BLACK);
		mt.configurarFuente("Monospaced", Font.BOLD, 21);
		mt.dibujarRectanguloLleno(0, 260, 500, 50, Colores.BLACK);
		mt.dibujarString("Puntaje: " + puntaje,185,295, Colores.WHITE);
	}

	public static void main(String[] args) {
		Scanner usuario = new Scanner(System.in);
		System.out.println("\t Lineas de Rectangulos y Colores: ");
		System.out.println("1. Iniciar Juego");
		System.out.println("2. Ver instrucciones del juego");
		System.out.println("3. Salir del juego");
		System.out.println("Bienvenido al juego. Seleccione una opcion: ");
		do{
			seleccion = usuario.nextInt();
			if(seleccion != 1 && seleccion != 2 && seleccion != 3){
				System.out.println("Seleccione una opcion valida: ");
				seleccion = -1;
			}
		}while(seleccion == -1);
		
		if(seleccion == 1){
			LineasRectangulosColores.iniciarElJuego();
		}
		if(seleccion == 2){
			LineasRectangulosColores.instruccionesGrafico();
			System.out.println("Ingresa 1 para comenzar el juego: ");
			do{
				seleccion = usuario.nextInt();
				if(seleccion != 1){
					System.out.println("Opcion invalida. Ingresa 1 para comenzar el juego: ");
					seleccion = -1;
				}
			}while(seleccion == -1);
			mt.terminar();
			LineasRectangulosColores.iniciarElJuego();
		}
		if(seleccion == 3){
			System.exit(0);
		}
	}
}