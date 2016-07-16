package wb.store.helper;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import wb.store.model.Item;

public class ItemDAO {
	private Connection myConn;
	
	public ItemDAO(Connection myConn){
		this.myConn = myConn;
	}
	
	public Serializable addItem(Item item) throws SQLException{
		String sql = "insert into items "
				+ "(item_name, item_description, item_price) "
				+ "values (?, ?, ?)";
		
		try (PreparedStatement myStmt = myConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
	
			// Set the parameters
			myStmt.setString(1, item.getName());
			myStmt.setString(2, item.getDescription());
			myStmt.setFloat(3, item.getPrice());
			
			// Execute SQL query
			myStmt.executeUpdate();
			try(ResultSet rs=myStmt.getGeneratedKeys()){
				if (rs.next()) {
			       return new Long (rs.getLong(1));
				}
			}
		}
		return null;
	}
	public List<Item> getItems(){
		
		String sql = "select * from items";
		
		ArrayList<Item> items = new ArrayList<>();
		try (Statement myStmt = myConn.createStatement();
			ResultSet myRS = myStmt.executeQuery(sql)){

			//process the result set
			while (myRS.next()) {
				Item item = new Item(myRS.getString("item_name"), 
									myRS.getString("item_description"),
									Float.valueOf(myRS.getString("item_price")));
				items.add(item);
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
		} 
		
		return items;
	}
}
