package com.gustavosotnas.saep.persistencia.controller;

import com.gustavosotnas.saep.persistencia.model.Collections;
import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoDatabase;

/**
 * Created by gustavosotnas on 10/07/16.
 */
public class DBController {
    public static MongoClient mongoClient =  new MongoClient();
    public static MongoDatabase db = mongoClient.getDatabase("saep");

    /**
     * Função que inicializa o MongoDB com as collections usadas no SAEP.
     */
    public static void initializeDB() {
        try {
            db.createCollection(Collections.PARECER_COLLECTION);
            db.createCollection(Collections.RESOLUCAO_COLLECTION);
            System.out.println("Colletions criadas com sucesso");
        } catch (MongoCommandException mongocmdex) {
            System.out.println("Collections já criadas anteriormente");
        } catch (MongoSocketOpenException mongosockex){
            System.out.println("Não foi possível conectar ao servidor de banco de dados. Ele está rodando?");
        } catch (MongoTimeoutException mongoconnex) {
            System.out.println("O tempo de conexão com o banco de dados expirou. Certifique se é possível "+
                    "conectar à ele.");
        }
    }
}
