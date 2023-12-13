import java.awt.Font;
import java.util.Scanner;
public class Probar{
	public static void main(String[] args) {
		MaquinaDeTrazados mt = new MaquinaDeTrazados(500, 500, "Lineas de colores y rectangulos", Colores.WHITE);
		mt.dibujarRectanguloLleno(0,150,500,100,Colores.WHITE);
		mt.dibujarRectangulo(0,150,500,100);
		mt.configurarFuente("Monospaced", Font.BOLD, 24);
		mt.dibujarString("Juego Terminado",150,210, Colores.BLACK);

		mt.configurarFuente("Monospaced", Font.BOLD, 21);
		mt.dibujarRectanguloLleno(0, 260, 500, 50, Colores.BLACK);
		mt.dibujarRectangulo(0, 260, 500, 50, Colores.ORANGE);
		mt.dibujarString("Puntaje",185,295, Colores.WHITE);
		mt.mostrar();
	}
}