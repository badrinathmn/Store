package wb.store.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import wb.store.model.Item;
import wb.store.service.ItemService;

public class EditItemServlet extends HttpServlet{

	@Resource(name="jdbc/store")
	private DataSource ds;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("item_id"));
		
		try{
			ItemService is = new ItemService(ds);
			Item item = (Item)is.getItem(id);
			request.setAttribute("item", item);
			request.setAttribute("action", "editItem");
			RequestDispatcher view = request.getRequestDispatcher("admin.jsp");
			view.forward(request, response);
			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//retrieve parameters from the request
		System.out.println(request.getParameter("item_id"));
		Integer id = Integer.parseInt(request.getParameter("item_id"));
		String name = request.getParameter("item_name");
		String description = request.getParameter("item_description");
		float price = Float.valueOf(request.getParameter("item_price"));
				
		Item item = new Item(id, name, description, price);
		try{
			ItemService is = new ItemService(ds);
			int updated = (int) is.updateItem(item);	
			request.setAttribute("update", updated);
			RequestDispatcher view = request.getRequestDispatcher("ItemOutput.jsp");
			view.forward(request, response);
				
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
