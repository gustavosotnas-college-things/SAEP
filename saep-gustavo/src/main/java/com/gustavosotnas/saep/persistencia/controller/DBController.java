package com.gustavosotnas.saep.persistencia.controller;

import com.gustavosotnas.saep.persistencia.model.properties.Collections;
import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Created by gustavosotnas on 10/07/16.
 */
public class DBController {

    private static MongoClient mongoClient =  new MongoClient();
    private static MongoDatabase db = mongoClient.getDatabase("saep");

    /**
     * Função que inicializa o MongoDB com as collections do SAEP.
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

    /**
     * Salva um objeto Java serializado para JSON em uma collection.
     *
     * @param jsonObject o JSON correspondente à uma classe DAO do SAEP.
     * @param collectionName o nome da collection que se deseja salvar.
     */
    public static Document setCollection(String jsonObject, String collectionName) {
        MongoCollection<Document> collection = getCollection(collectionName);
        Document documentToSave = parseJsonToDocument(jsonObject);
        collection.insertOne(documentToSave);
        return findDocumentById((ObjectId) documentToSave.get("_id"), collectionName);
    }

    /**
     * Faz busca de um objeto de uma collection pelo seu nome (id).
     *
     * @param idName nome do atributo no banco de dados desejado
     * @param idValue valor do atributo que será procurado no banco de dados.
     * @param collectionName nome da collection desejada a se obter.
     * @return a collection desejada.
     */
    public static Document findDocumentById(String idName, String idValue, String collectionName) {
        MongoCollection<Document> collection = getCollection(collectionName);
        return collection.find(eq(idName, idValue)).first();
    }

    /**
     * Faz busca de um objeto de uma collection por uma query no formato Document.
     *
     * @param collectionName nome da collection desejada a se obter.
     * @param query document de padrão para pesquisa no banco de dados.
     * @return a document que "bate" com a Document de query.
     */
    public static Document findDocumentByQuery(String collectionName, Document query) {
        MongoCollection<Document> collection = getCollection(collectionName);
        return collection.find(query).first();
    }

    /**
     * Obtêm todos os Documents de uma determinada collection.
     *
     * @param collectionName nome da collection desejada a se obter.
     * @return um conjunto de Documents da collection desejada.
     */
    public Iterable<Document> getAllDocuments(String collectionName) {
        MongoCollection<Document> collection = getCollection(collectionName);
        return collection.find();
    }

    // *** Métodos privados ***

    /**
     * Faz busca de um objeto de uma collection pelo ObjectId.
     *
     * @param mongoObjectId identificador usado no MongoDB para uma collection.
     * @param collectionName nome da collection desejada a se obter.
     * @return a collection desejada.
     */
    private static Document findDocumentById(ObjectId mongoObjectId, String collectionName) {
        MongoCollection<Document> collection = getCollection(collectionName);
        return collection.find(eq("_id", mongoObjectId)).first();
    }

    /**
     * Converte um JSON para Document.
     *
     * @param jsonObject o JSON correspondente à uma classe DAO do SAEP.
     * @return o Document correspondente ao JSON passado por parâmetro.
     */
    private static Document parseJsonToDocument(String jsonObject) {
        return Document.parse(jsonObject);
    }

    /**
     * Obtêm uma collection do banco de dados do SAEP.
     *
     * @param collectionName o nome da collection que se deseja obter.
     * @return a collection desejada.
     */
    private static MongoCollection<Document> getCollection(String collectionName) {
        return db.getCollection(collectionName);
    }
}
