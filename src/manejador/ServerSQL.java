package manejador;
import org.json.simple.JSONObject;
import org.postgresql.util.PGobject;

import java.sql.*;
import java.util.ArrayList;

public class ServerSQL {
    private Credenciales credenciales = new Credenciales();
    private Connection c;

    public ServerSQL(){
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(credenciales.getUrlPostgres(), credenciales.getUser(), credenciales.getPwd());
            System.out.println("Opened database successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
    public void executeSelectAllCliente(){
        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cliente");
            while (rs.next())
            {
                System.out.print(rs.getString(1)+ "\t");
                System.out.print(rs.getString(2)+ "\t");
                System.out.println(rs.getString(3));
            }
            rs.close();
            st.close();
        }
        catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
        }


    }
    //metodos para llenar los combobox
    public ArrayList<String> getNamesFromCatalog(String catalogName){
        ArrayList<String> result  = new ArrayList<>();
        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM "+catalogName);
            while (rs.next())
            {
                result.add(rs.getString(2));
            }
            rs.close();
            st.close();
        }
        catch (SQLException e){
            System.out.println("Error durante la ejecucion de query");
        }
        //System.out.println(result);
        return result;
    }

    //#INSERT INTO cliente VALUES (1, 'Diego','Calderon','5/6/1996', null,'','', 12, 4, 2, 3, 16 , 150.85,false, 0,null)
    //String nombre, String Apellido, String fechaNacimiento, String twUser
    public void insertCliente(String nombre, String apellido, Date fechaNacimiento, String twUser, String rutaLocal, String rutaTw,
                                String depto, String ocupacion,String banco,String sucursal, String categoria,double ultimaCompra, boolean tieneCredito,  double cantidadeCredito, JSONObject obj){
        try {
            PreparedStatement st = c.prepareStatement ("INSERT INTO cliente VALUES(" +
                    "(NEXTVAL('sec_cliente'))," +
                    "?, ?, ?, ? , ?, ?," +
                    "(select d.id_departamento from departamento d where departamento = ?)," +
                    "(select ocu.id_ocupacion from ocupacion ocu  where nombre_ocupacion = ?)," +
                    "(select b.id_banco from banco b where banco = ?)," +
                    "(select suc.id_sucursal from sucursal suc where nombre_sucursal = ?)," +
                    "(select cp.id_categoria from categoria_producto cp where nombre_cat = ?)," +
                    "?," +
                    "?," +
                    "?," +
                    "?)");
            st.setString(1,nombre);
            st.setString(2,apellido);
            st.setDate(3,fechaNacimiento);
            st.setString(4, twUser);
            st.setString(5, rutaTw);
            st.setString(6, rutaLocal);
            st.setString(7, depto);
            st.setString(8, ocupacion);
            st.setString(9, banco);
            st.setString(10, sucursal);
            st.setString(11, categoria);
            st.setDouble(12, ultimaCompra);
            st.setBoolean(13,tieneCredito);
            st.setDouble(14, cantidadeCredito);

            PGobject jsonObject = new PGobject();
            jsonObject.setType("json");
            jsonObject.setValue(obj.toJSONString());
            st.setObject(15, jsonObject);

            st.executeUpdate();
            st.close();
        }
        catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
            System.out.println(e);
        }
    }

    public void updateCliente(String nombre, String apellido, Date fechaNacimiento, String twUser, String rutaLocal, String rutaTw,
                              String depto, String ocupacion,String banco,String sucursal, String categoria,double ultimaCompra, boolean tieneCredito,  double cantidadeCredito,int id, JSONObject obj ){
        try {
            PreparedStatement st = c.prepareStatement ("UPDATE cliente " +
                    "SET nombre = ?, "+
                    " apellido = ?, " +
                    " fecha_nacimiento = ?," +
                    " twitter_user = ? ," +
                    " twitter_image = ?," +
                    " local_image =  ?," +
                    " id_departamento = (select d.id_departamento from departamento d where departamento = ?)," +
                    " id_ocupacio = (select ocu.id_ocupacion from ocupacion ocu  where nombre_ocupacion = ?)," +
                    " id_banco = (select b.id_banco from banco b where banco = ?)," +
                    " sucursal_favorita = (select suc.id_sucursal from sucursal suc where nombre_sucursal = ?)," +
                    " id_categoria_favorita = (select cp.id_categoria from categoria_producto cp where nombre_cat = ?)," +
                    " ultima_compra = ?," +
                    " tiene_creditos = ?," +
                    " cant_creditos = ? ," +
                    " otros = ?"+
                    "WHERE id = ? ;" );
            st.setString(1,nombre);
            st.setString(2,apellido);
            st.setDate(3,fechaNacimiento);
            st.setString(4, twUser);
            st.setString(5, rutaTw);
            st.setString(6, rutaLocal);
            st.setString(7, depto);
            st.setString(8, ocupacion);
            st.setString(9, banco);
            st.setString(10, sucursal);
            st.setString(11, categoria);
            st.setDouble(12, ultimaCompra);
            st.setBoolean(13,tieneCredito);
            st.setDouble(14, cantidadeCredito);

            PGobject jsonObject = new PGobject();
            jsonObject.setType("json");
            jsonObject.setValue(obj.toJSONString());
            st.setObject(15, jsonObject);
            st.setInt(16,id);

            st.executeUpdate();
            st.close();
        }
        catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
            System.out.println(e);
        }
    }
    public ResultSet getUserbyID(int id){
        try {
            PreparedStatement st = c.prepareStatement("select id, nombre, apellido, fecha_nacimiento as fdate, twitter_user as twuser, twitter_image as twimage, local_image as limage, ultima_compra as ucompra, tiene_creditos, cant_creditos AS creditos," +
                    "depto.departamento AS departamento, b.banco as banco, o.nombre_ocupacion as ocupacion, cat.nombre_cat as categoria, s.nombre_sucursal as sucursal, otros " +
                    "from cliente c INNER JOIN departamento depto on c.id_departamento = depto.id_departamento " +
                    "INNER JOIN banco b ON c.id_banco = b.id_banco " +
                    "INNER JOIN ocupacion o ON c.id_ocupacio = o.id_ocupacion " +
                    "INNER JOIN categoria_producto cat ON c.id_categoria_favorita = cat.id_categoria " +
                    "INNER JOIN sucursal s ON c.sucursal_favorita = s.id_sucursal " +
                    "WHERE id =  ?;");
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            return rs;

        }
        catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
            System.out.println(e);
        }
        return null;
    }

    public ResultSet deleteUserbyID(int id){

        try {
            PreparedStatement st = c.prepareStatement("DELETE FROM cliente WHERE  id = ?");
            st.setInt(1,id);
            st.executeUpdate();
            st.close();

        }
        catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
        }
        return null;
    }

    public  void closeConnectionToServer (){
        try {
            c.close();
        }
        catch (SQLException s){

        }

    }

    public ResultSet viewDataFromTable(String tabla, String filtro, String orden){
        try{
            ResultSet rs;
            if(filtro.equals("-None-")){
                Statement st = c.createStatement();
                rs = st.executeQuery("SELECT * FROM "+tabla);
            }else if(orden.equals("-None-")){
                Statement st = c.createStatement();
                rs = st.executeQuery("SELECT * FROM "+tabla + " ORDER BY " + filtro);
            }else{
                Statement st = c.createStatement();
                System.out.println("Orden: "+orden);
                if(orden.equals("Ascendente")){
                    orden = "ASC";
                }else{
                    orden = "DESC";
                }
                rs = st.executeQuery("SELECT * FROM "+tabla + " ORDER BY " + filtro + " " + orden);
            }
            return rs;
        }catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
            System.out.println(e);
        }
        return null;
    }

    public ResultSet viewDataFromCatalogo(String catalogo){
        try{
            ResultSet rs;
            Statement st = c.createStatement();
            rs = st.executeQuery("SELECT * FROM "+catalogo);
            return rs;
        }catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<String> viewColumnsFromTable(String table){
        try{
            PreparedStatement pstmt = c.prepareStatement("select * from " + table);
            ResultSetMetaData meta = pstmt.getMetaData();
            ArrayList<String> res = new ArrayList<>();
            for (int i=1; i <= meta.getColumnCount(); i++)
            {
                //System.out.println("Column name: " + meta.getColumnName(i) + ", data type: " + meta.getColumnTypeName(i));
                res.add(meta.getColumnName(i));
            }
            return res;
        }catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
            System.out.println(e);
        }
        return null;
    }

    public void insertCatalog(String tabla, String datos){
        try{
            PreparedStatement st = c.prepareStatement("INSERT INTO "+ tabla + " VALUES " + datos);
            st.executeUpdate();
            st.close();
        }catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
            System.out.println(e);
        }
    }

    public void updateCatalog(String tabla, String oldData, String newData, String column, String id){
        try{
            PreparedStatement st = c.prepareStatement("UPDATE "+ tabla + " SET " + oldData + " = '" + newData + "' WHERE "+ column + " = " + id);
            st.executeUpdate();
            st.close();
        }catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
            System.out.println(e);
        }
    }

    public void deleteFromCatalog(String tabla, String columna, String valor){
        try{
            PreparedStatement st = c.prepareStatement("DELETE FROM "+ tabla + " WHERE " + columna + " = " + valor);
            st.executeUpdate();
            st.close();
        }catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
            System.out.println(e);
        }
    }

    public ResultSet executeQuery (String query){
        try{
            ResultSet rs;
            Statement st = c.createStatement();
            rs = st.executeQuery(query);
            return rs;
        }catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
            System.out.println(e);
        }
        return null;
    }


}
