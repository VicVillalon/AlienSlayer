package math;

// Clase que comprende la posicion a partir de vectores (x) y (y)
public class Vector2D {
	private double x, y;

	// Constructor donde le pasamos por parametro la posicion
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// Constructor por defecto donde nos inicia la posicion en 0,0
	public Vector2D() {
		x = 0;
		y = 0;
	}

	// Genera un vector a partir de otro vector
	public Vector2D add(Vector2D v) {
		return new Vector2D(x + v.getX(), y + v.getY());
	}

	//
	public Vector2D scale(double value) {
		return new Vector2D(x * value, y * value);
	}

	// Limita la velocidad del objeto con la velocidad pasada por parámetro
	public Vector2D limit(double value) {
		if (getMagnitude() > value) {
			return this.normalize().scale(value);
		}
		return this;
	}

	// Fa que la magnitud negativa sigui 1
	public Vector2D normalize() {
		return new Vector2D(x / getMagnitude(), y / getMagnitude());
	}

	// S'agafa la magnitud del vector fen l'hipotenusa amb x y y
	public double getMagnitude() {
		return Math.sqrt(x * x + y * y);
	}

	// Se posiciona la dirección del objeto a partir del seno y el coseno del angulo
	// multiplicado por la magnitud
	public Vector2D setDirection(double angle) {
		return new Vector2D(Math.cos(angle) * getMagnitude(), Math.sin(angle) * getMagnitude());
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
