package com.gustavosotnas.saep.persistencia.model;

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.ParecerRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Radoc;

import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorExistente;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;

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
 * @see br.ufg.inf.es.saep.sandbox.dominio.Parecer
 * @see Radoc
 */
public class ParecerDAO implements ParecerRepository {

    /**
     * Adiciona nota ao parecer.
     *
     * @param id   O identificador único do parecer.
     * @param nota A alteração a ser acrescentada ao
     * @throws IdentificadorDesconhecido Caso o identificador
     *                                   fornecido não identifique um parecer existente.
     */
    @Override
    public void adicionaNota(String id, Nota nota) {

    }

    /**
     * Remove a nota cujo item {@link Avaliavel} original é
     * fornedido.
     *
     * @param id       O identificador único do parecer.
     * @param original Instância de {@link Avaliavel} que participa
     *                 da {@link Nota} a ser removida como origem.
     */
    @Override
    public void removeNota(String id, Avaliavel original) {

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
    public void persisteParecer(br.ufg.inf.es.saep.sandbox.dominio.Parecer parecer) {

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
     * @param id O identificador do parecer.
     * @return O parecer recuperado ou o valor {@code null},
     * caso o identificador não defina um parecer.
     */
    @Override
    public br.ufg.inf.es.saep.sandbox.dominio.Parecer byId(String id) {
        return null;
    }

    /**
     * Remove o parecer.
     * <p>
     * <p>Se o identificador fornecido é inválido
     * ou não correspondente a um parecer existente,
     * nenhuma situação excepcional é gerada.
     *
     * @param id O identificador único do parecer.
     */
    @Override
    public void removeParecer(String id) {

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
        return null;
    }

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
        return null;
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