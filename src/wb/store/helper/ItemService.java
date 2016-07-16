package wb.store.helper;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import wb.store.model.Item;

public class ItemService {
	private DataSource ds;
	
	public ItemService(DataSource ds){
		this.ds = ds;

	}
	public Serializable addItem(Item item) throws SQLException{
		
		try(Connection conn = ds.getConnection();){
			ItemDAO dao = new ItemDAO(conn);
			return dao.addItem(item);
		}
	}
	public List<Item> getItems() throws SQLException{
		try(Connection conn = ds.getConnection()){
			ItemDAO dao = new ItemDAO(conn);
			return dao.getItems();
		}
	}
}
