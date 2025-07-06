package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Editorial;
import dao.EditorialDao;

public class EditorialControl extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditorialControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditorialControl at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // registro de editoriales
        
        String nit = request.getParameter("nit");
        String nombre= request.getParameter("nombre") ;
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String email = request.getParameter("email");
        String sitioweb = request.getParameter("sitioweb");        
        
        //Editorial e = new Editorial(nit, nombre, telefono, direccion, email, sitioweb);
        Editorial e = new Editorial();
        e.setNit(nit);
        e.setNombre(nombre);
        e.setTelefono(telefono);
        e.setDireccion(direccion);
        e.setEmail(email);
        e.setSitioweb(sitioweb);
                        
        if(EditorialDao.registrar(e)){
            request.setAttribute("mensaje", "La editorial fue registrada");
        }else{
            request.setAttribute("mensaje", "La editorial NO fue registrada");
        }
                    
        request.getRequestDispatcher("registroEditorial.jsp").forward(request, response); 
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
