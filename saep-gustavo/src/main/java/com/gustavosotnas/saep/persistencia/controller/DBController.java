package com.gustavosotnas.saep.persistencia.controller;

import com.gustavosotnas.saep.persistencia.model.properties.Collections;
import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.regex.Pattern;

/**
 * Classe que reúne funções úteis para manipular o banco de dados MongoDB no SAEP.
 *
 * @author gustavosotnas
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
     * Salva um documento em uma collection no banco de dados.
     *
     * @implNote Se não quiser o Document criado de retorno, simplesmente ignore
     * o retorno não setando o mesmo para variável alguma.
     *
     * @param JSONdocument o JSON correspondente à uma classe DAO do SAEP.
     * @param collectionName o nome da collection que se deseja salvar.
     */
    public static Document setCollection(String JSONdocument, String collectionName) {
        MongoCollection<Document> collection = getCollection(collectionName);
        Document documentToSave = Document.parse(JSONdocument);
        collection.insertOne(documentToSave);
        return findDocumentById((ObjectId) documentToSave.get("_id"), collectionName);
    }

    /**
     * Faz busca de um objeto de uma collection pelo seu nome (id).
     *
     * @param atributeName nome do atributo no banco de dados desejado
     * @param atributeValue valor do atributo que será procurado no banco de dados.
     * @param collectionName nome da collection desejada a se obter.
     * @return a collection desejada.
     */
    public static Document findDocument(String atributeName, String atributeValue, String collectionName) {
        MongoCollection<Document> collection = getCollection(collectionName);
        return collection.find(eq(atributeName, atributeValue)).first();
    }

    /**
     * Faz busca de um Document de uma collection por uma query.
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
    public static Iterable<Document> getAllDocuments(String collectionName) {
        MongoCollection<Document> collection = getCollection(collectionName);
        return collection.find();
    }

    /**
     * Obtêm Document(s) que possuem valor igual ou parecido ao desejado.
     *
     * @param atributeName nome do atributo no banco de dados desejado.
     * @param atributeValue valor do atributo que será procurado no banco de dados.
     * @param collectionName nome da collection desejada a se obter.
     * @return Um ou mais Documents que batem com o desejado.
     */
    public static Iterable<Document> getDocumentsSimilarTo(String atributeName, String atributeValue, String collectionName) {
        MongoCollection<Document> collection = getCollection(collectionName);
        Document query = new Document(atributeName, Pattern.compile(atributeValue));
        return collection.find(query);
    }

    /**
     * Atualiza um Document em uma collection existente no banco de dados (formato JSON).
     *
     * @param atributeName nome do atributo no banco de dados desejado.
     * @param atributeValue valor do atributo que será atualizado no banco de dados.
     * @param collectionName nome da collection a ser atualizada.
     * @param JSONdocument o conteúdo do document a ser atualizado.
     */
    public static void updateDocument(String atributeName, String atributeValue, String collectionName, String JSONdocument) {
        MongoCollection<Document> collection = getCollection(collectionName);
        Document documentToUpdate = Document.parse(JSONdocument);
        collection.replaceOne(eq(atributeName, atributeValue), documentToUpdate);
    }

    /**
     * Atualiza um Document em uma collection existente no banco de dados (formato Document).
     *
     * @param atributeName nome do atributo no banco de dados desejado.
     * @param atributeValue valor do atributo que será atualizado no banco de dados.
     * @param collectionName nome da collection a ser atualizada.
     * @param documentToUpdate o conteúdo do document a ser atualizado.
     */
    public static void updateDocumentByQuery(String atributeName, String atributeValue, String collectionName, Document documentToUpdate){
        MongoCollection<Document> collection = getCollection(collectionName);
        collection.updateOne(eq(atributeName, atributeValue), documentToUpdate);
    }

    /**
     * Exclui um Document do banco de dados.
     *
     * @param atributeName nome do atributo no banco de dados desejado.
     * @param atributeValue valor do atributo que será excluído no banco de dados.
     * @param collectionName nome da collection que terá um document excluído.
     * @return {@code true} se foi excluído com sucesso, {@code false} caso contrário.
     */
    public static boolean deleteDocument(String atributeName, String atributeValue, String collectionName) {
        MongoCollection<Document> collection = getCollection(collectionName);
        DeleteResult result = collection.deleteOne(eq(atributeName, atributeValue));
        return result.getDeletedCount() > 0;
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
     * Obtêm uma collection do banco de dados do SAEP.
     *
     * @param collectionName o nome da collection que se deseja obter.
     * @return a collection desejada.
     */
    private static MongoCollection<Document> getCollection(String collectionName) {
        return db.getCollection(collectionName);
    }
}
