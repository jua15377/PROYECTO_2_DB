package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import manejador.ServerSQL;
import java.sql.ResultSet;

public class ReportesSceneController {

    ServerSQL serverSQL = new ServerSQL();
    ResultSet rs = null;

    @FXML
    ComboBox cb_reporte;

    @FXML
    TableView table;

    @FXML
    void reporteListener(){
        table.getColumns().clear();

        int numero = cb_reporte.getSelectionModel().getSelectedIndex();



        if(numero == 0){
            String query = "\n" +
                            "SELECT nombre_sucursal as Sucursal, sum(ultima_compra) as Ingresos \n" +
                            "FROM cliente c INNER JOIN sucursal s ON (c.sucursal_favorita = s.id_sucursal)\n" +
                            "GROUP BY nombre_sucursal";
            rs = serverSQL.executeQuery(query);
        }
        else if (numero == 1){
            String query =  "SELECT  sum(cant_creditos) as creditos_totales\n" +
                            "FROM cliente ";
            rs = serverSQL.executeQuery(query);
        }
        else if (numero == 2){
            String query = "SELECT departamento, count(*) as paisanos\n" +
                            "FROM cliente c INNER JOIN departamento d ON (d.id_departamento = c.id_departamento)\n" +
                            "GROUP BY departamento";
            rs = serverSQL.executeQuery(query);
        }
        else if (numero == 3){
            String query = "SELECT nombre_cat as categoria, count(*) as clientes\n" +
                            "FROM cliente c INNER JOIN categoria_producto CP ON (c.id_categoria_favorita = CP.id_categoria)\n" +
                            "GROUP BY nombre_cat\n";
            rs = serverSQL.executeQuery(query);
        }
        else if (numero == 4){
            String query =  "SELECT avg(2018 - extract(year FROM fecha_nacimiento)) as edad_promedio\n" +
                            "FROM cliente";
            rs = serverSQL.executeQuery(query);
        }
        else if (numero == 5){
            String query =  "SELECT banco, sum(cant_creditos) as creditos_otorgados\n" +
                            "FROM cliente c INNER JOIN banco b ON (b.id_banco = c.id_banco)\n" +
                            "GROUP BY banco";
            rs = serverSQL.executeQuery(query);
        }
        else if (numero == 6){
            String query =  "SELECT nombre, apellido, (SELECT 2018 - extract(year FROM fecha_nacimiento)) as edad, departamento, nombre_ocupacion as ocupacion, banco, nombre_sucursal as sucursal_favorita, nombre_cat as categoria_favorita \n" +
                            "FROM cliente c INNER JOIN banco b ON (c.id_banco = b.id_banco)\n" +
                            "INNER JOIN departamento d ON(c.id_departamento = d.id_departamento)\n" +
                            "INNER JOIN ocupacion o ON (c.id_ocupacio = o.id_ocupacion)\n" +
                            "INNER JOIN sucursal s ON (c.sucursal_favorita = s.id_sucursal)\n" +
                            "INNER JOIN categoria_producto cat ON (c.id_categoria_favorita = cat.id_categoria) \n" +
                            "WHERE id IN (SELECT cliente_id\n" +
                            "\t\t\tFROM cliente_destacado\n" +
                            "\t\t\tORDER BY ultima_compra)\n" +
                            "LIMIT 5";
            rs = serverSQL.executeQuery(query);
        }
        else if (numero == 7){
            String query =  "SELECT nombre, apellido, (SELECT 2018 - extract(year FROM fecha_nacimiento)) as edad, departamento, nombre_ocupacion as ocupacion, banco, nombre_sucursal as sucursal_favorita, nombre_cat as categoria_favorita\n" +
                            "FROM cliente c INNER JOIN banco b ON (c.id_banco = b.id_banco)\n" +
                            "INNER JOIN departamento d ON(c.id_departamento = d.id_departamento)\n" +
                            "INNER JOIN ocupacion o ON (c.id_ocupacio = o.id_ocupacion)\n" +
                            "INNER JOIN sucursal s ON (c.sucursal_favorita = s.id_sucursal)\n" +
                            "INNER JOIN categoria_producto cat ON (c.id_categoria_favorita = cat.id_categoria) \n" +
                            "WHERE id IN (SELECT cliente_id\n" +
                            "\t\t\tFROM deudor\n" +
                            "\t\t\tORDER BY cant_credito)\n" +
                            "LIMIT 5\n";
            rs = serverSQL.executeQuery(query);
        }
        else if (numero == 8){
            String query =  "SELECT nombre_cat as categoria, avg(2018 - extract(year FROM fecha_nacimiento)) edad_promedio \n" +
                            "FROM cliente c INNER JOIN categoria_producto k ON (c.id_categoria_favorita = k.id_categoria)\n" +
                            "GROUP BY nombre_cat ";
            rs = serverSQL.executeQuery(query);
        }
        else{
            String query =  "SELECT nombre_sucursal as sucursal, count (*) as clientes\n" +
                            "FROM cliente c INNER JOIN sucursal k ON (c.sucursal_favorita = k.id_sucursal)\n" +
                            "GROUP BY nombre_sucursal";
            rs = serverSQL.executeQuery(query);
        }


    }

    public void fillReportes(String query){
        rs = serverSQL.viewDataFromCatalogo(query);




    }
    public void initialize(){
        table.setVisible(false);
        cb_reporte.getItems().addAll(
                "Ingresos por Tienda",
                "Cantidad Total de Creditos",
                "Cantidad de Personas por Departamento",
                "Cantidad de Usuarios por Categoria",
                "Edad Promedio de Usuarios",
                "Cantidad de Creditos Prestados por Banco",
                "Top 5 clientes mas destacados",
                "Top 5 deudores",
                "Promedio de Edad por Categoria de Compra",
                "Cantidad de Usuarios por Sucursal Favorita"
        );


    }

}
