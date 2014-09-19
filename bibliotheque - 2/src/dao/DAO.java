
package dao;

public class DAO extends Connection {
	
	//Getter
	private Connection getConnection(){
		return this.connection;
	}
	
	//Setter
	private void setConnection(Connection connection){
		this.connection = connection;
	}
	
	//Retourne la (@link java.sql.Connection) JDBC.
	protected Connection getConnection(){
		return getConnexion().getConnection();
	}
}
