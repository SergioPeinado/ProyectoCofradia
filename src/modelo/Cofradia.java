package modelo;

public class Cofradia {
	private String nombre;
	private int fundacion;
	private int n_hermanos;
	private int titulares;
	private String h_salida;
	private String h_entrada;
	/**
	 * @param nombre
	 * @param fundacion
	 * @param n_hermanos
	 * @param titulares
	 * @param h_salida
	 * @param h_entrada
	 */
	public Cofradia(String nombre, int fundacion, int n_hermanos, int titulares, String h_salida, String h_entrada) {
		
		this.nombre = nombre;
		this.fundacion = fundacion;
		this.n_hermanos = n_hermanos;
		this.titulares = titulares;
		this.h_salida = h_salida;
		this.h_entrada = h_entrada;
	}
	public Cofradia(){
		this.nombre="";
		this.fundacion=0;
		this.n_hermanos=0;
		this.titulares=0;
		this.h_salida="";
		this.h_entrada="";
	}
	public void setCofradia(Cofradia c){
		this.nombre = c.nombre;
		this.fundacion = c.fundacion;
		this.n_hermanos = c.n_hermanos;
		this.titulares = c.titulares;
		this.h_salida = c.h_salida;
		this.h_entrada = c.h_entrada; 
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the fundacion
	 */
	public int getFundacion() {
		return fundacion;
	}
	/**
	 * @param fundacion the fundacion to set
	 */
	public void setFundacion(int fundacion) {
		this.fundacion = fundacion;
	}
	/**
	 * @return the n_hermanos
	 */
	public int getN_hermanos() {
		return n_hermanos;
	}
	/**
	 * @param n_hermanos the n_hermanos to set
	 */
	public void setN_hermanos(int n_hermanos) {
		this.n_hermanos = n_hermanos;
	}
	/**
	 * @return the titulares
	 */
	public int getTitulares() {
		return titulares;
	}
	/**
	 * @param titulares the titulares to set
	 */
	public void setTitulares(int titulares) {
		this.titulares = titulares;
	}
	/**
	 * @return the h_salida
	 */
	public String getH_salida() {
		return h_salida;
	}
	/**
	 * @param h_salida the h_salida to set
	 */
	public void setH_salida(String h_salida) {
		this.h_salida = h_salida;
	}
	/**
	 * @return the h_entrada
	 */
	public String getH_entrada() {
		return h_entrada;
	}
	/**
	 * @param h_entrada the h_entrada to set
	 */
	public void setH_entrada(String h_entrada) {
		this.h_entrada = h_entrada;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fundacion;
		result = prime * result + ((h_entrada == null) ? 0 : h_entrada.hashCode());
		result = prime * result + ((h_salida == null) ? 0 : h_salida.hashCode());
		result = prime * result + n_hermanos;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + titulares;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cofradia other = (Cofradia) obj;
		if (fundacion != other.fundacion)
			return false;
		if (h_entrada == null) {
			if (other.h_entrada != null)
				return false;
		} else if (!h_entrada.equals(other.h_entrada))
			return false;
		if (h_salida == null) {
			if (other.h_salida != null)
				return false;
		} else if (!h_salida.equals(other.h_salida))
			return false;
		if (n_hermanos != other.n_hermanos)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (titulares != other.titulares)
			return false;
		return true;
	}
	
	
}
