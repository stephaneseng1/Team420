
package dao;

public class DAO extends Connection {
	private Connection getConnection(){
		return this.connection;
	}
	private void setConnection(Connection connection){
		this.connection = connection;
	}
	
	protected Connection getConnection(){
		return getConnexion().getConnection();
	}
}
