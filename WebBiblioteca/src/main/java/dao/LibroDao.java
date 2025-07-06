
package dao;
import dao.Conexion;
import java.sql.*;
import java.util.ArrayList;
import model.Libro;

public class LibroDao {
    
    
    public static boolean registrar(Libro l) {
        try {
            String SQL = "INSERT INTO libros (isbn, titulo, descripcion, nombre_autor, publicacion, fecha_registro, codigo_categoria, nit_editorial) VALUES (?, ?, ?, ?, ?, NOW(), ?, ?)";

            // Log para verificar la conexión
            Connection con = Conexion.conectar();
            if (con == null) {
                System.out.println("ERROR: No se pudo establecer conexión a la base de datos");
                return false;
            }

            PreparedStatement st = con.prepareStatement(SQL);

            // Log de los datos que se van a insertar
            System.out.println("=== DATOS A INSERTAR ===");
            System.out.println("ISBN: " + l.getIsbn());
            System.out.println("Título: " + l.getTitulo());
            System.out.println("Descripción: " + l.getDescripcion());
            System.out.println("Autor: " + l.getNombre_autor());
            System.out.println("Publicación: " + l.getPublicacion());
            System.out.println("Categoría: " + l.getCodigo_categoria());
            System.out.println("Editorial: " + l.getNit_editorial());

            st.setString(1, l.getIsbn());
            st.setString(2, l.getTitulo());
            st.setString(3, l.getDescripcion());
            st.setString(4, l.getNombre_autor());
            st.setString(5, l.getPublicacion());
            st.setInt(6, l.getCodigo_categoria());
            st.setString(7, l.getNit_editorial());

            int resultado = st.executeUpdate();
            System.out.println("Resultado de executeUpdate(): " + resultado);

            if (resultado > 0) {
                System.out.println("ÉXITO: Libro registrado correctamente");
                return true;
            } else {
                System.out.println("ERROR: executeUpdate() retornó 0");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("ERROR SQL: " + ex.getMessage());
            System.out.println("Código de error: " + ex.getErrorCode());
            ex.printStackTrace();
            return false;
        } catch (Exception ex) {
            System.out.println("ERROR GENERAL: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    
    
    
    
    public static ArrayList<Libro> listar(){
        try {
            String SQL = "select * from libros";
            Connection con = Conexion.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            //st.setString(1, cat.getNombre());            
            ResultSet resultado = st.executeQuery();
            
            
            ArrayList<Libro> lista = new ArrayList<>();
            
            Libro l;
            
            while(resultado.next()){
                l = new Libro();
                
                l.setIsbn(resultado.getString("isbn"));
                l.setTitulo(resultado.getString("titulo"));
                l.setNombre_autor(resultado.getString("nombre_autor"));
                l.setDescripcion(resultado.getString("descripcion"));
                l.setFecha_registro(resultado.getString("fecha_registro"));
                l.setPublicacion(resultado.getString("publicacion"));
                l.setNit_editorial(resultado.getString("nit_editorial"));
                l.setCodigo_categoria(resultado.getInt("codigo_categoria"));
                
                
                lista.add(l);
            }                                    
            
            return lista;
            
        }catch (SQLException ex) {
            return null;
        }
    }
    
    
    
    
    
    
    public static boolean actualizar(Libro l){
        try {
            String SQL = """
                         
                    update libros set
                    titulo = ?,
                    descripcion = ?,
                    nombre_autor=?,
                    publicacion = ?,                    
                    codigo_categoria=?,
                    nit_editorial=?
                    where isbn = ?
                         """;
            Connection con = Conexion.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            
            
            st.setString(1, l.getTitulo());
            st.setString(2, l.getDescripcion());
            st.setString(3, l.getNombre_autor());            
            st.setString(4, l.getPublicacion());            
            st.setInt(5, l.getCodigo_categoria());
            st.setString(6, l.getNit_editorial());
            
            st.setString(7, l.getIsbn());
            
            if(st.executeUpdate()>0){
                return true;
            }else{
                return false;
            }
            
            
        }catch (SQLException ex) {
            return false;
        }
    }
    
    
//    public static boolean eliminar(Libro l){
//        try {
//            String SQL = "delete from libros where isbn=?";
//                                                                      
//            Connection con = Conexion.conectar();
//            PreparedStatement st = con.prepareStatement(SQL);                        
//            
//            st.setString(1, l.getIsbn());
//            
//            
//            if(st.executeUpdate()>0){
//                return true;
//            }else{
//                return false;
//            }
//            
//            
//        }catch (SQLException ex) {
//            return false;
//        }
//    }
    public static boolean eliminar(String isbn) {
        try {
            String SQL = "DELETE FROM libros WHERE isbn = ?";
            Connection con = Conexion.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, isbn);

            int resultado = st.executeUpdate();
            return resultado > 0; // Retorna true si se eliminó al menos un registro
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    
    
    
    
}
