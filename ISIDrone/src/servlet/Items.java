package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Const;
import action.ActionCart;
import action.ActionCategory;
import action.ActionItems;
import java.util.ArrayList;
import manager.MItem;

/**
 * Servlet implementation class Products
 */
@WebServlet(name = "products", urlPatterns = {"/items"})
public class Items extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Items() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Si le paramètre category est présent
        String searchValue = request.getParameter("search");
        if (searchValue != null) {
            ArrayList<Item> items = (ArrayList<Item>) request.getAttribute("items");
            ActionCategory.getCategories(request, response);
            ActionItems.getItems(request, response);
            ActionItems.getItemsByName(searchValue, request, response);
            request.setAttribute("searchValue", searchValue);
        } else {

            ArrayList<Item> items = (ArrayList<Item>) request.getAttribute("items");

            ActionCategory.getCategories(request, response);

            ActionItems.getItems(request, response);
        }

        request.getRequestDispatcher(Const.PATH_PAGE_ITEMS).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strId = request.getParameter("itemId");
        String strIdToDelete = request.getParameter("itemIdToDelete");
        String strQty = request.getParameter("qty");

        if(strIdToDelete != null){
            MItem.deleteItem(Integer.parseInt(strIdToDelete));
            doGet(request, response);
        }
        if(strId != null){
            ActionCart.addItem(request, response, strId, strQty);
            doGet(request, response);
        }
    }
}
