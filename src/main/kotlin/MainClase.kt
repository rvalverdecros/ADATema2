import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

fun main(){

    val url = "jdbc:mysql://localhost/"
    val bd = "bddeprueba"
    val user = "root"
    val pass = ""

    //Cargar el Driver de la BBDD

    Class.forName("com.mysql.cj.jdbc.Driver")
    //Una vez cargado el Driver, abrimos la conexion

    var con: Connection = DriverManager.getConnection(url+bd,user,pass)

    //Vamos a ver algunas clases mas ademas de las que vimos

    /*
    DatabaseMetadata
    Infromacion relativa a la base de datos
     */

    val dbMeta:DatabaseMetaData = con.metaData

    println("Nombre del SGBD: ${dbMeta.databaseProductName}")
    println("Version del SGBD: ${dbMeta.databaseProductVersion}")

    println("Nombre del Driver: ${dbMeta.driverName}")
    println("Version del Driver: ${dbMeta.driverVersion}")

    /*
    Statement

    executeQuery(sql:String)
    -Para sentencias Select
    -Devuleve un objeto ResultSet

    executeUpdate(sql:String)
    -Para sentencias Insert/Update/delete
    -Devuelve el nº de filas afectadas

    execute(sql:String)
    -Para cuando no sabemos lo que nos viene
    -Devuelve True o Fase
    -true -> Se ha ejecutado un SELECT -> Tenemos un ResultSet
        - .getResultSet()
    -false -> Se ha eujecutado un executeUpdate -> El nº de filas afectadas
        - .getUpdateCount()
     */

    val st: Statement = con.createStatement()

    val rs1 : ResultSet = st.executeQuery(Setencias.selectAll)

    while (rs1.next()){
        println("--------------------------------------------------------------------------")
        println(rs1.getString(1))
        println(rs1.getString(2))
        println(rs1.getString(3))
        println(rs1.getString(4))
        println("--------------------------------------------------------------------------")
    }
    val st2: Statement = con.createStatement()

    var rs2 = st2.executeUpdate(Setencias.crear)

    if (rs2 > 0){
        println("${rs2} afectada(s)")
    }else{
        println("Ninguna Fila afectada(s)")
    }

    var rs3 = st.executeUpdate(Setencias.borrar)

    if (rs3 > 0){
        println("${rs3} afectada(s)")
    }else{
        println("Ninguna Fila afectada(s)")
    }

    //Vemos el Execute

    val st4 = con.createStatement()

    var bool = st4.execute(Setencias.selectAll)

    if (bool){
        val rs4 = st4.resultSet
        //iteramos sobre el Rset como siempre lo hemos hecho
        while (rs4.next()){
            println("--------------------------------------------------------------------------")
            println(rs4.getString(1))
            println(rs4.getString(2))
            println(rs4.getString(3))
            println(rs4.getString(4))
            println("--------------------------------------------------------------------------")
        }
    }else{
        var nFilas = st4.updateCount
        if (nFilas > 0){
            println("${nFilas} afectada(s)")
        }else{
            println("Ninguna Fila afectada(s)")
        }
    }

    //Tambien, podemos explorar la clase ResultSetMetadata
    /*
    ResultSetMetaData
    Contiene informacion relativa  alas columnas o las filas que obtenemos en una consulta
     */

    val st5 = con.createStatement()
    val rs5 = st4.executeQuery(Setencias.selectAll)
    val rsMetaData = rs5.metaData //<-- getMetaData()

    //Podemos por ejemplo, conoocer el numero de columnas que tienen nuestro conjunto de resultado

    val nColumnas = rsMetaData.columnCount // <-- getColumnCount()

    //Si se el nº de columnas, puedo itera sobre ellas para conocer sus nombres

    for (i in 1..nColumnas){
        println("Col(\"${i}\"): ${rsMetaData.getColumnName(i)}, con tipo: ${rsMetaData.getColumnTypeName(i)}")
    }

    //Por ultimo, vamos a ver las clases PreparedStatement
    val ps = con.prepareStatement("INSERT INTO alumnos VALUES (?,?,?,?)")
    ps.setString(1,"12345678Z")
    ps.setString(2,"Roberto")
    ps.setInt(3,16)
    ps.setString(4,"Madrid")

    val filasafectadas = ps.executeUpdate()

    if (filasafectadas > 0){
        println("${filasafectadas} afectada(s)")
    }else{
        println("Ninguna Fila afectada(s)")
    }

    con.close()
}