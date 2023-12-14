import java.awt.Font;
import java.util.Scanner;
public class Probar{
	public static final Colores[] COLORESSELECCIONADOS = {Colores.MAGENTA, Colores.BLUE, Colores.CYAN, Colores.GREEN, Colores.RED, Colores.YELLOW, Colores.WHITE};

	public static void main(String[] args) {

		MaquinaDeTrazados mt = new MaquinaDeTrazados(450, 450, "Lineas de colores y rectangulos", Colores.WHITE);
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
		mt.dibujarString("hacia arriba, abajo o diagonal.",0, 105, Colores.BLACK);
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
		mt.dibujarString("desea mover elemento, a la derecha se muestra el numero de cada",0, 333, Colores.BLACK);
		mt.dibujarString("fila y abajo el numero de cada columna",0, 345, Colores.BLACK);
		mt.dibujarString("DIVIERTETE",0, 357, Colores.MAGENTA);
		mt.configurarFuente("SansSerif", Font.BOLD, 12);
		mt.dibujarString("Para regresar al menu anterior ingresa el valor 0",0, 369, Colores.BLACK);

















		 


		mt.mostrar();
	}
}