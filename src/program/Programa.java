package program;

// Trabalhando com transacao  

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

// Elimina registro da base

import db.DB;
import db.DbException;

public class Programa {

	public static void main(String[] args) {

		Connection conn = null;

		Statement st = null;

		try {

			conn = DB.getConnection();
			
			conn.setAutoCommit(false); // Desabilita a confirmacao automatica da transacao
			
			st = conn.createStatement();
			
			int linha1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2800.0"
					+ " WHERE "
					+ "DepartmentId=2" );
			
			// bloco usado para quebrar o codigo gerando fake error
			int x=1;
			
			if (x<2) {
				
				throw new SQLException (" Fake Error");
			}
			
			int linha2 =st.executeUpdate("UPDATE seller SET BaseSalary = 3000.0"
					+ " WHERE "
					+ " DepartmentId=3");
			
			conn.commit(); // todo o bloco acima esta protegido pela logica de programacao
			
			System.out.println("Linha1" + linha1);
			System.out.println("Linha2" + linha2);
			
				}
		catch ( SQLException e) {

			try {
				
			
			conn.rollback(); // volta ao estado inicial do banco
			throw new DbException ("Transacao nao Efetuada ! executado RoolBack- Causada por " + e.getMessage());
			} 
			catch (SQLException e1) {
				
				throw new DbException(" Erro ao tentar executar RollBack ! causado por " + e1.getMessage());
			}
		}
		finally {
			
			DB.closeStatemant(st);
			DB.closeConnection();
		}
		
}
