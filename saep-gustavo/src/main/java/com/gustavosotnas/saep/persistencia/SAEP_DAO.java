package com.gustavosotnas.saep.persistencia;

import com.gustavosotnas.saep.persistencia.properties.Collections;
import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoDatabase;

/**
 * Created by gustavosotnas on 10/07/16.
 */
public class SAEP_DAO {
    public static MongoClient mongoClient =  new MongoClient();
    public static MongoDatabase db = mongoClient.getDatabase("saep");

    public static void createCollections(){
        try{
            db.createCollection(Collections.PARECER_COLLECTION);
            db.createCollection(Collections.RESOLUCAO_COLLECTION);
        } catch (MongoCommandException e){
            System.out.println("Collections já criadas");
        }
    }
}
