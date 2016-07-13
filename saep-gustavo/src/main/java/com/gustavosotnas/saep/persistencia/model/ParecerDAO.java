package com.gustavosotnas.saep.persistencia.model;

import br.ufg.inf.es.saep.sandbox.dominio.*;

import com.gustavosotnas.saep.persistencia.controller.DBController;
import com.gustavosotnas.saep.persistencia.model.properties.Collections;
import com.gustavosotnas.saep.persistencia.model.properties.Entities;
import static com.gustavosotnas.saep.persistencia.model.properties.Strings.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.Document;

/**
 * Created by gustavosotnas on 11/07/16.
 */

/**
 * Oferece noção de coleções de pareceres em memória.
 *
 * <p>Um parecer é o resultado produzido pela avaliação
 * de um conjunto de relatos (RADOC) conforme uma dada
 * resolução. O parecer pode ser produzido pela Comissão
 * de Avaliação Docente (CAD) ou automaticamente pelo
 * SAEP.
 *
 * @see Parecer
 * @see Radoc
 */
public class ParecerDAO implements ParecerRepository {

    private static Gson gson;

    public ParecerDAO() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Nota.class, new NotaDeserialize());
        gson = gsonBuilder.create();
    }

    /**
     * Adiciona nota ao parecer.
     *
     * @param idParecer   O identificador único do parecer.
     * @param nota A alteração a ser acrescentada ao
     * @throws IdentificadorDesconhecido Caso o identificador
     *                                   fornecido não identifique um parecer existente.
     */
    @Override
    public void adicionaNota(String idParecer, Nota nota) {

        Document parecer = DBController.findDocument("id", idParecer, Collections.PARECER_COLLECTION);
        if (parecer != null) { // Se deu tudo certo...
            // Remove a nota existente e adiciona a nova nota
            removeNota(idParecer, nota.getItemOriginal());
            String notaJson = gson.toJson(nota);
            Document notaToAdd = new Document("notas", Document.parse(notaJson));
            DBController.updateDocumentByQuery("id", idParecer, Collections.PARECER_COLLECTION, new Document("$push", notaToAdd));
        } else {
            throw new IdentificadorDesconhecido(getMessage$EntityNotFound(Entities.PARECER_ENTITY, idParecer));
        }
    }

    /**
     * Remove a nota cujo item {@link Avaliavel} original é
     * fornedido.
     *
     * @param idParecer       O identificador único do parecer.
     * @param original Instância de {@link Avaliavel} que participa
     *                 da {@link Nota} a ser removida como origem.
     */
    @Override
    public void removeNota(String idParecer, Avaliavel original) {

    }

    /**
     * Acrescenta o parecer ao repositório.
     *
     * @param parecer O parecer a ser persistido.
     * @throws IdentificadorExistente Caso o
     *                                identificador seja empregado por parecer
     *                                existente (já persistido).
     */
    @Override
    public void persisteParecer(Parecer parecer) {

        String idParecer = parecer.getId();
        Document document = DBController.findDocument("id", idParecer, Collections.PARECER_COLLECTION);
        if (document == null) { // se não existe o determinado identificador...
            // ... salva o parecer no banco de dados.
            String parecerJson = gson.toJson(parecer);
            DBController.setCollection(parecerJson, Collections.PARECER_COLLECTION);
        }
        else {
            throw new IdentificadorExistente(getMessage$EntityAlreadyExists(Entities.PARECER_ENTITY, idParecer));
        }
    }

    /**
     * Altera a fundamentação do parecer.
     * <p>
     * <p>Fundamentação é o texto propriamente dito do
     * parecer. Não confunda com as alterações de
     * valores (dados de relatos ou de pontuações).
     * <p>
     * <p>Após a chamada a esse método, o parecer alterado
     * pode ser recuperado pelo método {@link #byId(String)}.
     * Observe que uma instância disponível antes dessa chamada
     * torna-se "inválida".
     *
     * @param parecer       O identificador único do parecer.
     * @param fundamentacao Novo texto da fundamentação do parecer.
     * @throws IdentificadorDesconhecido Caso o identificador
     *                                   fornecido não identifique um parecer.
     */
    @Override
    public void atualizaFundamentacao(String parecer, String fundamentacao) {

    }

    /**
     * Recupera o parecer pelo identificador.
     *
     * @param idParecer O identificador do parecer.
     * @return O parecer recuperado ou o valor {@code null},
     * caso o identificador não defina um parecer.
     */
    @Override
    public Parecer byId(String idParecer) {
        Document parecer = DBController.findDocument("id", idParecer, Collections.PARECER_COLLECTION);
        if (parecer != null) {
            String parecerJson = gson.toJson(parecer);
            return gson.fromJson(parecerJson, Parecer.class);
        }
        else {
            return null;
        }
    }

    /**
     * Remove o parecer.
     * <p>
     * <p>Se o identificador fornecido é inválido
     * ou não correspondente a um parecer existente,
     * nenhuma situação excepcional é gerada.
     *
     * @param idParecer O identificador único do parecer.
     */
    @Override
    public void removeParecer(String idParecer) {
        DBController.deleteDocument("id", idParecer, Collections.PARECER_COLLECTION);
    }

    // *** RADOC ***

    /**
     * Conjunto de relatos de atividades e produtos
     * associados a um docente.
     * <p>
     * <p>Um conjunto de relatos é extraído de fonte
     * externa de informação. Uma cópia é mantida pelo
     * SAEP para consistência de pareceres efetuados ao
     * longo do tempo. Convém ressaltar que informações
     * desses relatórios podem ser alteradas continuamente.
     *
     * @param radoc O conjunto de relatos a ser persistido.
     * @return O identificador único do RADOC.
     * @throws IdentificadorExistente Caso o identificador
     *                                do objeto a ser persistido seja empregado por
     *                                RADOC existente.
     */
    @Override
    public String persisteRadoc(Radoc radoc) {

        String idRadoc = radoc.getId();
        Document radocExisting = DBController.findDocument("id", idRadoc, Collections.RADOC_COLLECTION);


        if (radocExisting == null) { // se não existe esse radoc no DB...
            String radocJSON = gson.toJson(radoc);
            Document existingRadoc = DBController.setCollection(radocJSON, Collections.RADOC_COLLECTION);
            // Se deu tudo certo...
            if (existingRadoc != null) {
                // ... verifica se o Radoc está mesmo no banco de dados
                String savedRadoc = gson.toJson(existingRadoc);
                Radoc foundRadoc = gson.fromJson(savedRadoc, Radoc.class);
                return foundRadoc.getId();
            }
            else {
                return null;
            }
        }
        else {
            throw new IdentificadorExistente(getMessage$EntityAlreadyExists(Entities.RADOC_ENTITY, idRadoc));
        }
    }

    /**
     * Recupera o RADOC identificado pelo argumento.
     *
     * @param identificador O identificador único do
     *                      RADOC.
     * @return O {@code Radoc} correspondente ao
     * identificador fornecido.
     */
    @Override
    public Radoc radocById(String identificador) {

        Document radoc = DBController.findDocument("id", identificador, Collections.RADOC_COLLECTION);
        if (radoc != null) { // se deu tudo certo...
            // ... desserializa o radoc desejado do banco de dados
            String radocJson = gson.toJson(radoc);
            return gson.fromJson(radocJson, Radoc.class);
        }
        else {
            return null;
        }
    }

    /**
     * Remove o RADOC.
     * <p>
     * <p>Após essa operação o RADOC correspondente não
     * estará disponível para consulta.
     * <p>
     * <p>Não é permitida a remoção de um RADOC para o qual
     * há pelo menos um parecer referenciando-o.
     *
     * @param identificador O identificador do RADOC.
     */
    @Override
    public void removeRadoc(String identificador) {

    }
}