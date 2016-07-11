package com.gustavosotnas.saep.persistencia;

import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoDatabase;

/**
 * Created by gustavosotnas on 10/07/16.
 */
public class SAEP_DAO {
    public static MongoClient mongoClient =  new MongoClient();
    public static MongoDatabase db = mongoClient.getDatabase("saep");
    public static String PARECER_COLLECTION = "parecer_collection";
    public static String RADOC_COLLECTION = "radoc_collection";
    public static String RESOLUCAO_COLLECTION = "resolucao_collection";

    public static void createCollections(){
        try{
            db.createCollection(SAEP_DAO.PARECER_COLLECTION);
            db.createCollection(SAEP_DAO.RESOLUCAO_COLLECTION);
        }catch(MongoCommandException e ){
            System.out.println("Collections já criadas");
        }

    }
}
