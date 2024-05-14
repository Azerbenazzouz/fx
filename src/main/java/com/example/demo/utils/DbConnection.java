package com.example.demo.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection con;

    public static Connection seConnecter(){
        if(con == null ){
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacyjava", "root", "");
                System.out.println("Connexion etablie....");
            }catch(SQLException ex){
                System.out.println("db non trouve ..."+ex.getMessage());
            }
        }
        return con;
    }
}
