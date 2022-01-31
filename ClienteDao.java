
package data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


//import java.sql.Statement;



public class ClienteDao {
    Connection conn;
    //Statement st;
    PreparedStatement st;
    ResultSet rs;

    public boolean conectar () {        
        try { 
            /* Usando SQL SERVER
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDrive");
            conn = DriverManager.getConnection("jdbc:sqlserver://
            localhost;database=
            AdventureWorks         (ou no caso deste projeto)       banco
            ;integratedSecurity=true;");
            */
            
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/banco", "root", "");
            //st = conn.createStatement();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            return false;
        }
    }
    
    /* Este método não expecifica o erro!
    
    public boolean salvar(Cliente cliente) {
        try {
            String inserir;
            //Valores double ou int etc só utilizam aspas duplas.
            inserir = "INSERT INTO cliente VALUES('" +cliente.getMatricula()+ "', '" +cliente.getNome()+ "', '" +cliente.getEmail()+ "', '" +cliente.getCell()+ "', " +cliente.getTelefone()+ ", '" +cliente.getBairro()+ "')";
            st.executeUpdate(inserir);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }*/
    
    public int salvar(Cliente cliente) {
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO cliente VALUES(?,?,?,?,?,?)");
            st.setString(1, cliente.getMatricula());
            st.setString(2, cliente.getNome());
            st.setString(3, cliente.getEmail());
            st.setString(4, cliente.getCell());
            st.setDouble(5, cliente.getTelefone());
            st.setString(6, cliente.getBairro());
            
            //String inserir;
            //Valores double ou int etc só utilizam aspas duplas.
            //inserir = "INSERT INTO cliente VALUES('" +cliente.getMatricula()+ "', '" +cliente.getNome()+ "', '" +cliente.getEmail()+ "', '" +cliente.getCell()+ "', " +cliente.getTelefone()+ ", '" +cliente.getBairro()+ "')";
            status = st.executeUpdate();
            //status = st.executeUpdate(inserir);
            return status; //retorna o valor 1
        } catch (SQLException ex) {
            
            //System.out.println(ex.getErrorCode()); Usado para pegar o NÚMERO do erro
            return ex.getErrorCode();
        }
    }
    
    public void desconectar() {
        try {
            conn.close();
        } catch (SQLException ex) {
            //Não coloque nada porque temos certeza que vamos chamar após conexão.
        }
    }
    
    public Cliente consultar (String matricula) {
        Cliente cliente = new Cliente();
        try {    
            st = conn.prepareStatement("SELECT * FROM cliente WHERE matricula = ?");
            st.setString(1, matricula);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                cliente.setMatricula(rs.getString("matricula"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone((int) rs.getDouble("telefone"));
                cliente.setBairro(rs.getString("bairro"));
                cliente.setCell(rs.getString("cell"));
                return cliente;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
        
}
    
    public boolean excluir(String matricula) {
        try {
            st = conn.prepareStatement("DELETE FROM cliente WHERE matricula = ?");
            st.setString(1, matricula);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
        
    }
    

}