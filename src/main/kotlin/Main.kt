import java.sql.*

fun main() {

    //Los tres parametros para realizar la conexion
    val url = "jdbc:mysql://localhost/"
    val bd = "bddeprueba"
    val user = "root"
    val pass = ""

    val nombre = "Pablo"
    val dni = "23456714D"
    val edad = "20"
    val ciudad = "Puerto de Santa Maria"

    var con: Connection? = null

    try {
        //Ya nos ha hecho de traductor entre MySQL y nuestra apliacion
        Class.forName("com.mysql.cj.jdbc.Driver")

        //Obtenemos un objeto Connection. Este objeto es la conexion que se ha realizado con la base de datos
        //utilizamos la clase DriverManager

        val con: Connection? = DriverManager.getConnection(url + bd, user, pass)

        if (con != null) {
            println("[Conexion realizada]")

            //Con la conexion creada vamos a obtener un objeto Statemen

            //3º Crear objeto Statement

            val st: Statement = con.createStatement()

            //4º Ejecutar sentencia SQL

            val sentencia = "SELECT * FROM alumnos;"
            //val crear = "INSERT INTO `alumnos`(`DNI`, `Nombre`, `Edad`, `Ciudad`) VALUES ('12345678G','Ruth','23','San Fernando');"
            val crearhadc = "INSERT INTO `alumnos`(`DNI`, `Nombre`, `Edad`, `Ciudad`) VALUES ('$dni','$nombre','$edad','$ciudad');"
            //val actualizar = "UPDATE `alumnos` SET `DNI`='98765431Z' WHERE `Nombre`='Rafa';"
            val borrar = "DELETE FROM `alumnos` WHERE `Nombre`='Ruth';"

            //val resultcrear = st.executeUpdate(crear)
            val resultcrearhd = st.executeUpdate(crearhadc)
            //val resultactu = st.executeUpdate(actualizar)
            val resultborrar = st.executeUpdate(borrar)
            val results = st.executeQuery(sentencia)


            //Iteramos sobre los resultados que obtenemos

            while (results.next()){
                println("--------------------------------------------------------------------------")
                println(results.getString("DNI"))
                println(results.getString("Nombre"))
                println(results.getString("Edad"))
                println(results.getString("Ciudad"))
                println("--------------------------------------------------------------------------")
            }

            con.close()
        }


    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    }catch (e: SQLException){
        e.printStackTrace()
    }finally {
        if (con!=null){
            con.close()
        }
    }

}