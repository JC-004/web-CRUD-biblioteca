/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.LibroDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Libro;

/**
 *
 * @author MAFOWS21
 */
public class LibroControl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LibroControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LibroControl at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String isbn = request.getParameter("isbn");
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        String nombre_autor = request.getParameter("autor");
        String publicacion = request.getParameter("publicacion");
        String categoriaStr = request.getParameter("categoria");
        String editorial = request.getParameter("editorial");
        String accion = request.getParameter("accion");

        // Logs para verificar los parámetros recibidos
        System.out.println("=== PARÁMETROS RECIBIDOS ===");
        System.out.println("ISBN: " + isbn);
        System.out.println("Título: " + titulo);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Autor: " + nombre_autor);
        System.out.println("Publicación: " + publicacion);
        System.out.println("Categoría (String): " + categoriaStr);
        System.out.println("Editorial: " + editorial);
        System.out.println("Acción: " + accion);

        if (accion != null) {
            accion = accion.toLowerCase();
        }

        // Validar ISBN siempre (necesario para todas las operaciones)
        if (isbn == null || isbn.trim().isEmpty()) {
            request.setAttribute("mensaje", "Error: ISBN es obligatorio");
            request.getRequestDispatcher("registroLibro.jsp").forward(request, response);
            return;
        }

        // Si la acción es eliminar, solo necesitamos el ISBN
        if (accion != null && accion.equals("eliminar")) {
            System.out.println("Intentando eliminar libro con ISBN: " + isbn);
            if (LibroDao.eliminar(isbn)) {
                request.setAttribute("mensaje", "Libro Eliminado");
            } else {
                request.setAttribute("mensaje", "Libro NO Eliminado");
            }
            request.getRequestDispatcher("registroLibro.jsp").forward(request, response);
            return;
        }

        // Para registrar y actualizar, validar campos obligatorios
//        if (titulo == null || titulo.trim().isEmpty()) {
//            request.setAttribute("mensaje", "Error: Título es obligatorio");
//            request.getRequestDispatcher("registroLibro.jsp").forward(request, response);
//            return;
//        }

        // Validar conversión de categoría
        int codigo_categoria;
        try {
            codigo_categoria = Integer.parseInt(categoriaStr);
            System.out.println("Categoría convertida: " + codigo_categoria);
        } catch (NumberFormatException e) {
            request.setAttribute("mensaje", "Error: Categoría inválida");
            request.getRequestDispatcher("registroLibro.jsp").forward(request, response);
            return;
        }

        // Crear objeto Libro para registrar y actualizar
        Libro l = new Libro();
        l.setIsbn(isbn);
        l.setTitulo(titulo);
        l.setDescripcion(descripcion);
        l.setNombre_autor(nombre_autor);
        l.setPublicacion(publicacion);
        l.setCodigo_categoria(codigo_categoria);
        l.setNit_editorial(editorial);

        if (accion == null || accion.equals("registrar")) {
            System.out.println("Intentando registrar libro...");
            if (LibroDao.registrar(l)) {
                request.setAttribute("mensaje", "Libro Registrado");
            } else {
                request.setAttribute("mensaje", "Libro NO Registrado");
            }
        } else if (accion.equals("actualizar")) {
            System.out.println("Intentando actualizar libro...");
            if (LibroDao.actualizar(l)) {
                request.setAttribute("mensaje", "Libro Actualizado");
            } else {
                request.setAttribute("mensaje", "Libro NO Actualizado");
            }
        } else {
            request.setAttribute("mensaje", "Acción desconocida");
        }

        request.getRequestDispatcher("registroLibro.jsp").forward(request, response);
    }




    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
