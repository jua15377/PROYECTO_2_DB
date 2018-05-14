package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import manejador.ServerSQL;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReportesSceneController {

    ServerSQL serverSQL = new ServerSQL();
    ResultSet rs = null;
    ArrayList<String> ids = new ArrayList();
    @FXML
    ComboBox cb_reporte;

    @FXML
    TableView table;

    @FXML
    javafx.scene.image.ImageView imagen;

    @FXML
    void reporteListener(){
        table.getColumns().clear();

        int numero = cb_reporte.getSelectionModel().getSelectedIndex();

        final ObservableList<IngresosXTienda> dataPrimerR = FXCollections.observableArrayList();
        final ObservableList<TotalCreditos> dataSegundoR = FXCollections.observableArrayList();
        final ObservableList<PersonasPorDepartamento> dataTercerR = FXCollections.observableArrayList();
        final ObservableList<PersonasPorCategoria> dataCuartoR = FXCollections.observableArrayList();
        final ObservableList<EdadPromedio> dataQuintoR = FXCollections.observableArrayList();
        final ObservableList<CreditosPorBanco> dataSextoR = FXCollections.observableArrayList();
        final ObservableList<TopDestacados> dataSeptimoR = FXCollections.observableArrayList();
        final ObservableList<TopDeudores> dataOctavoR = FXCollections.observableArrayList();
        final ObservableList<EdadPorCategoria> dataNvenoR = FXCollections.observableArrayList();
        final ObservableList<UsuariosPorSucursal> dataDecimoR = FXCollections.observableArrayList();


        if(numero == 0){
            String query = "\n" +
                            "SELECT nombre_sucursal as Sucursal, sum(ultima_compra) as Ingresos \n" +
                            "FROM cliente c INNER JOIN sucursal s ON (c.sucursal_favorita = s.id_sucursal)\n" +
                            "GROUP BY nombre_sucursal";
            rs = serverSQL.executeQuery(query);
            try{
                DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
                while(rs.next()){
                    table.getColumns().clear();
                    dataPrimerR.add(new IngresosXTienda(rs.getString("sucursal"), rs.getString("ingresos")));
                    String nombre = rs.getString(1);
                    dataset.addValue(rs.getDouble(2),nombre, nombre);
                }

                table.setItems(dataPrimerR);
                TableColumn<IngresosXTienda, String> sucursalCol = new TableColumn<IngresosXTienda, String>("Sucursal");
                sucursalCol.setCellValueFactory(new PropertyValueFactory<>("sucursal"));
                TableColumn<IngresosXTienda, String> ingresoCol = new TableColumn<IngresosXTienda, String>("Ingresos");
                ingresoCol.setCellValueFactory(new PropertyValueFactory<>("ingresos"));
                table.getColumns().setAll(sucursalCol,ingresoCol);
                table.setVisible(true);

                //grafica
                JFreeChart barChart = ChartFactory.createBarChart3D (
                        "Ventas por tiendas",
                        "Tiendas", "Ventas",
                        dataset, PlotOrientation.VERTICAL,
                        true, true, false);
                CategoryPlot categoryPlot = barChart.getCategoryPlot();
                BarRenderer br = (BarRenderer) categoryPlot.getRenderer();
                br.setMaximumBarWidth(13); // set maximum width to 35% of chart
                int width = 840;    /* Width of the image */
                int height = 480;   /* Height of the image */


                String path1 = System.getProperty("user.dir") + "/resources/ingreso_departamento.png";
                path1 = path1.replace("\\", "/");
                File BarChart = new File( path1 );
                ChartUtilities.saveChartAsJPEG( BarChart , barChart , width , height );

                path1 = "file:"+ path1.replace("\\", "/");
                System.out.println(path1);
                Image img = new Image(path1);
                imagen.setImage(img);

            }catch (Exception e ){
                e.printStackTrace();
            }
        }
        else if (numero == 1){
            table.getColumns().clear();
            String query =  "SELECT  sum(cant_creditos) as creditos_totales\n" +
                            "FROM cliente ";
            rs = serverSQL.executeQuery(query);
            try{
                while(rs.next()){
                    dataSegundoR.add(new TotalCreditos(rs.getString("creditos_totales")));
                }

                table.setItems(dataSegundoR);
                TableColumn<TotalCreditos, String> creditosCol = new TableColumn<TotalCreditos, String>("Creditos Totales");
                creditosCol.setCellValueFactory(new PropertyValueFactory<>("creditos_totales"));
                table.getColumns().setAll(creditosCol);
                table.setVisible(true);
                imagen.setImage(null);
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
        else if (numero == 2){
            table.getColumns().clear();
            String query = "SELECT departamento, count(*) as paisanos\n" +
                            "FROM cliente c INNER JOIN departamento d ON (d.id_departamento = c.id_departamento)\n" +
                            "GROUP BY departamento";
            rs = serverSQL.executeQuery(query);
            try{
                DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
                while(rs.next()){
                    dataTercerR.add(new PersonasPorDepartamento(rs.getString("departamento"),rs.getString("paisanos") ));
                    String nombre = rs.getString(1);
                    dataset.addValue(rs.getDouble(2),nombre, nombre);
                }

                table.setItems(dataTercerR);
                TableColumn<PersonasPorDepartamento, String> departamentoCol = new TableColumn<PersonasPorDepartamento, String>("Departamento");
                departamentoCol.setCellValueFactory(new PropertyValueFactory<>("departamento"));
                TableColumn<PersonasPorDepartamento, String> paisanosCol = new TableColumn<PersonasPorDepartamento, String>("Paisanos");
                paisanosCol.setCellValueFactory(new PropertyValueFactory<>("paisanos"));
                table.getColumns().setAll(departamentoCol, paisanosCol);
                table.setVisible(true);

                JFreeChart barChart = ChartFactory.createBarChart3D (
                        "Usuarios por Departmanetos",
                        "Departamento", "Usuarios",
                        dataset, PlotOrientation.HORIZONTAL,
                        true, true, false);

                int width = 840;    /* Width of the image */
                int height = 480;   /* Height of the image */

                String path1 = System.getProperty("user.dir") + "/resources/cliente_depto.png";
                path1 = path1.replace("\\", "/");
                File BarChart = new File( path1 );
                ChartUtilities.saveChartAsJPEG( BarChart , barChart , width , height );

                path1 = "file:"+ path1.replace("\\", "/");
                System.out.println(path1);
                Image img = new Image(path1);
                imagen.setImage(img);

            }catch (Exception e ){
                e.printStackTrace();
            }
        }
        else if (numero == 3){
            table.getColumns().clear();
            String query = "SELECT nombre_cat as categoria, count(*) as clientes\n" +
                            "FROM cliente c INNER JOIN categoria_producto CP ON (c.id_categoria_favorita = CP.id_categoria)\n" +
                            "GROUP BY nombre_cat\n";
            rs = serverSQL.executeQuery(query);
            try{
                while(rs.next()){
                    dataCuartoR.add(new PersonasPorCategoria(rs.getString("categoria"),rs.getString("clientes") ));
                }

                table.setItems(dataCuartoR);
                TableColumn<PersonasPorCategoria, String> categoriaCol = new TableColumn<PersonasPorCategoria, String>("Categoria");
                categoriaCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));
                TableColumn<PersonasPorCategoria, String> clientesCol = new TableColumn<PersonasPorCategoria, String>("Clientes");
                clientesCol.setCellValueFactory(new PropertyValueFactory<>("clientes"));
                table.getColumns().setAll(categoriaCol, clientesCol);
                table.setVisible(true);
                imagen.setImage(null);
            }catch (Exception e ){
                e.printStackTrace();
            }

        }
        else if (numero == 4){
            table.getColumns().clear();
            String query =  "SELECT avg(2018 - extract(year FROM fecha_nacimiento)) as edad_promedio\n" +
                            "FROM cliente";
            rs = serverSQL.executeQuery(query);
            try{
                while(rs.next()){
                    dataQuintoR.add(new EdadPromedio(rs.getString("edad_promedio")));
                }

                table.setItems(dataQuintoR);
                TableColumn<EdadPromedio, String> edadCol = new TableColumn<EdadPromedio, String>("Edad Promedio");
                edadCol.setCellValueFactory(new PropertyValueFactory<>("edad_promedio"));
                table.getColumns().setAll(edadCol);
                table.setVisible(true);
                imagen.setImage(null);
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
        else if (numero == 5){
            table.getColumns().clear();
            String query =  "SELECT banco, sum(cant_creditos) as creditos_otorgados\n" +
                            "FROM cliente c INNER JOIN banco b ON (b.id_banco = c.id_banco)\n" +
                            "GROUP BY banco";
            rs = serverSQL.executeQuery(query);
            try{
                DefaultPieDataset dataset = new DefaultPieDataset( );
                while(rs.next()){
                    dataSextoR.add(new CreditosPorBanco(rs.getString("banco"), rs.getString("creditos_otorgados")));
                    dataset.setValue(
                            rs.getString( 1 ) ,
                            Double.parseDouble( rs.getString( 2 )));
                }

                table.setItems(dataSextoR);
                TableColumn<CreditosPorBanco, String> bancoCol = new TableColumn<CreditosPorBanco, String>("Banco");
                bancoCol.setCellValueFactory(new PropertyValueFactory<>("banco"));
                TableColumn<CreditosPorBanco, String> creditosCol = new TableColumn<CreditosPorBanco, String>("Creditos Permitidos");
                creditosCol.setCellValueFactory(new PropertyValueFactory<>("creditos_otorgados"));
                table.getColumns().setAll(bancoCol, creditosCol);
                table.setVisible(true);

                JFreeChart chart = ChartFactory.createPieChart(
                        "Ditribucion de los creditos en los bancos",   // chart title
                        dataset,          // data
                        true,             // include legend
                        true,
                        false );

                int width = 560;    /* Width of the image */
                int height = 370;   /* Height of the image */

                String path1 = System.getProperty("user.dir") + "/resources/creditos_bancos.png";
                path1 = path1.replace("\\", "/");
                File pieChart = new File( path1 );
                ChartUtilities.saveChartAsJPEG( pieChart , chart , width , height );

                path1 = "file:"+ path1.replace("\\", "/");
                System.out.println(path1);
                Image img = new Image(path1);
                imagen.setImage(img);

            }catch (Exception e ){
                e.printStackTrace();
            }
        }
        else if (numero == 6){
            table.getColumns().clear();
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
            try{
                while(rs.next()){
                    dataSeptimoR.add(new TopDestacados(
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("edad"),
                            rs.getString("departamento"),
                            rs.getString("ocupacion"),
                            rs.getString("banco"),
                            rs.getString("sucursal_favorita"),
                            rs.getString("categoria_favorita")

                            ));
                }

                table.setItems(dataSeptimoR);
                TableColumn<TopDestacados, String> nombreCol = new TableColumn<TopDestacados, String>("Nombre");
                nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                TableColumn<TopDestacados, String> apellidoCol = new TableColumn<TopDestacados, String>("Apellido");
                apellidoCol.setCellValueFactory(new PropertyValueFactory<>("apellido"));
                TableColumn<TopDestacados, String> edadCol = new TableColumn<TopDestacados, String>("Edad");
                edadCol.setCellValueFactory(new PropertyValueFactory<>("edad"));
                TableColumn<TopDestacados, String> departamentoCol = new TableColumn<TopDestacados, String>("Departamento");
                departamentoCol.setCellValueFactory(new PropertyValueFactory<>("departamento"));
                TableColumn<TopDestacados, String> ocupacionCol = new TableColumn<TopDestacados, String>("Ocupacion");
                ocupacionCol.setCellValueFactory(new PropertyValueFactory<>("ocupacion"));
                TableColumn<TopDestacados, String> bancoCol = new TableColumn<TopDestacados, String>("Banco");
                bancoCol.setCellValueFactory(new PropertyValueFactory<>("banco"));
                TableColumn<TopDestacados, String> sucursalCol= new TableColumn<TopDestacados, String>("Sucursal");
                sucursalCol.setCellValueFactory(new PropertyValueFactory<>("sucursal_favorita"));
                TableColumn<TopDestacados, String> categoriaCol = new TableColumn<TopDestacados, String>("Categoria");
                categoriaCol.setCellValueFactory(new PropertyValueFactory<>("categoria_favorita"));

                table.getColumns().setAll(nombreCol, apellidoCol, edadCol, departamentoCol, ocupacionCol, bancoCol, sucursalCol, categoriaCol);
                table.setVisible(true);
                imagen.setImage(null);
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
        else if (numero == 7){
            table.getColumns().clear();
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
            try{
                while(rs.next()){
                    dataOctavoR.add(new TopDeudores(
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("edad"),
                            rs.getString("departamento"),
                            rs.getString("ocupacion"),
                            rs.getString("banco"),
                            rs.getString("sucursal_favorita"),
                            rs.getString("categoria_favorita")

                    ));
                }

                table.setItems(dataOctavoR);
                TableColumn<TopDeudores, String> nombreCol = new TableColumn<TopDeudores, String>("Nombre");
                nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                TableColumn<TopDeudores, String> apellidoCol = new TableColumn<TopDeudores, String>("Apellido");
                apellidoCol.setCellValueFactory(new PropertyValueFactory<>("apellido"));
                TableColumn<TopDeudores, String> edadCol = new TableColumn<TopDeudores, String>("Edad");
                edadCol.setCellValueFactory(new PropertyValueFactory<>("edad"));
                TableColumn<TopDeudores, String> departamentoCol = new TableColumn<TopDeudores, String>("Departamento");
                departamentoCol.setCellValueFactory(new PropertyValueFactory<>("departamento"));
                TableColumn<TopDeudores, String> ocupacionCol = new TableColumn<TopDeudores, String>("Ocupacion");
                ocupacionCol.setCellValueFactory(new PropertyValueFactory<>("ocupacion"));
                TableColumn<TopDeudores, String> bancoCol = new TableColumn<TopDeudores, String>("Banco");
                bancoCol.setCellValueFactory(new PropertyValueFactory<>("banco"));
                TableColumn<TopDeudores, String> sucursalCol= new TableColumn<TopDeudores, String>("Sucursal");
                sucursalCol.setCellValueFactory(new PropertyValueFactory<>("sucursal_favorita"));
                TableColumn<TopDeudores, String> categoriaCol = new TableColumn<TopDeudores, String>("Categoria");
                categoriaCol.setCellValueFactory(new PropertyValueFactory<>("categoria_favorita"));

                table.getColumns().setAll(nombreCol, apellidoCol, edadCol, departamentoCol, ocupacionCol, bancoCol, sucursalCol, categoriaCol);
                table.setVisible(true);
                imagen.setImage(null);
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
        else if (numero == 8){
            table.getColumns().clear();
            String query =  "SELECT nombre_cat as categoria, avg(2018 - extract(year FROM fecha_nacimiento)) edad_promedio \n" +
                            "FROM cliente c INNER JOIN categoria_producto k ON (c.id_categoria_favorita = k.id_categoria)\n" +
                            "GROUP BY nombre_cat ";
            rs = serverSQL.executeQuery(query);
            try{
                DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
                while(rs.next()){
                    dataNvenoR.add(new EdadPorCategoria(rs.getString("categoria"), rs.getString("edad_promedio")));
                    String nombre = rs.getString(1);
                    dataset.addValue(rs.getDouble(2),nombre, nombre);
                }

                table.setItems(dataNvenoR);
                TableColumn<EdadPorCategoria, String> categoriaCol = new TableColumn<EdadPorCategoria, String>("Categoria");
                categoriaCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));
                TableColumn<EdadPorCategoria, String> edadCol = new TableColumn<EdadPorCategoria, String>("Edad Promedio");
                edadCol.setCellValueFactory(new PropertyValueFactory<>("edad_promedio"));
                table.getColumns().setAll(categoriaCol, edadCol);
                table.setVisible(true);

                JFreeChart barChart = ChartFactory.createBarChart3D (
                        "Edad promedio seegun categoria",
                        "Categorias", "Edades",
                        dataset, PlotOrientation.VERTICAL,
                        true, true, false);
                int width = 1024;    /* Width of the image */
                int height = 480;   /* Height of the image */
                String path1 = System.getProperty("user.dir") + "/resources/edades_categoria.png";
                path1 = path1.replace("\\", "/");
                File BarChart = new File( path1 );
                ChartUtilities.saveChartAsJPEG( BarChart , barChart , width , height );

                path1 = "file:"+ path1.replace("\\", "/");
                System.out.println(path1);
                Image img = new Image(path1);
                imagen.setImage(img);

            }catch (Exception e ){
                e.printStackTrace();
            }


        }
        else{
            table.getColumns().clear();
            String query =  "SELECT nombre_sucursal as sucursal, count (*) as clientes\n" +
                            "FROM cliente c INNER JOIN sucursal k ON (c.sucursal_favorita = k.id_sucursal)\n" +
                            "GROUP BY nombre_sucursal";
            rs = serverSQL.executeQuery(query);
            try{
                DefaultPieDataset dataset = new DefaultPieDataset( );
                while(rs.next()){
                    dataDecimoR.add(new UsuariosPorSucursal(rs.getString("sucursal"), rs.getString("clientes")));
                    dataset.setValue(
                            rs.getString( 1 ) ,
                            Double.parseDouble( rs.getString( 2 )));
                }

                table.setItems(dataDecimoR);
                TableColumn<UsuariosPorSucursal, String> sucursalCol = new TableColumn<UsuariosPorSucursal, String>("Sucursal");
                sucursalCol.setCellValueFactory(new PropertyValueFactory<>("sucursal"));
                TableColumn<UsuariosPorSucursal, String> clienteCol = new TableColumn<UsuariosPorSucursal, String>("Clientes");
                clienteCol.setCellValueFactory(new PropertyValueFactory<>("clientes"));
                table.getColumns().setAll(sucursalCol, clienteCol);
                table.setVisible(true);

                JFreeChart chart = ChartFactory.createPieChart(
                        "Ditribucion de los usuarios la tienda",   // chart title
                        dataset,          // data
                        true,             // include legend
                        true,
                        false );

                int width = 560;    /* Width of the image */
                int height = 370;   /* Height of the image */

                String path1 = System.getProperty("user.dir") + "/resources/usuarios_sucursal.png";
                path1 = path1.replace("\\", "/");
                File pieChart = new File( path1 );
                ChartUtilities.saveChartAsJPEG( pieChart , chart , width , height );

                path1 = "file:"+ path1.replace("\\", "/");
                System.out.println(path1);
                Image img = new Image(path1);
                imagen.setImage(img);

            }catch (Exception e ){
                e.printStackTrace();
            }
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
