package com.gustavosotnas.saep.persistencia.model;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gustavosotnas.saep.persistencia.controller.SaepGsonCustomConfiguration;

import java.util.List;

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
 * @author gustavosotnas
 */
public class ResolucaoDAO implements ResolucaoRepository {

    private static Gson gson;

    /**
     * Construtor padrão do ParecerDAO. Ele inicializa uma configuração
     * de serialização e desserialização personalizada do JSON.
     */
    public ResolucaoDAO() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Nota.class, new SaepGsonCustomConfiguration());
        gson = gsonBuilder.create();
    }

    /**
     * Recupera a instância de {@code Resolucao} correspondente
     * ao identificador.
     *
     * @param id O identificador único da resolução a
     *           ser recuperada.
     * @return {@code Resolucao} identificada por {@code id}.
     * O retorno {@code null} indica que não existe resolução
     * com o identificador fornecido.
     * @see #persiste(Resolucao)
     */
    @Override
    public Resolucao byId(String id) {
        return null;
    }

    /**
     * Persiste uma resolução.
     *
     * @param resolucao A resolução a ser persistida.
     * @return O identificador único da resolução, conforme
     * fornecido em propriedade do objeto fornecido. Observe que
     * o método retorna {@code null} para indicar que a
     * operação não foi realizada de forma satisfatória,
     * possivelmente por já existir resolução com
     * identificador semelhante.
     * @throws CampoExigidoNaoFornecido Caso o identificador não
     *                                  seja fornecido.
     * @throws IdentificadorExistente   Caso uma resolução com identificador
     *                                  igual àquele fornecido já exista.
     * @see #byId(String)
     * @see #remove(String)
     */
    @Override
    public String persiste(Resolucao resolucao) {
        return null;
    }

    /**
     * Remove a resolução com o identificador
     * fornecido.
     *
     * @param identificador O identificador único da
     *                      resolução a ser removida.
     * @return O valor {@code true} se a operação foi
     * executada de forma satisfatória e {@code false},
     * caso contrário.
     * @see #persiste(Resolucao)
     */
    @Override
    public boolean remove(String identificador) {
        return false;
    }

    /**
     * Recupera a lista dos identificadores das
     * resoluções disponíveis.
     *
     * @return Identificadores das resoluções disponíveis.
     */
    @Override
    public List<String> resolucoes() {
        return null;
    }

    /**
     * Persiste o tipo fornecido.
     *
     * @param tipo O objeto a ser persistido.
     * @throws IdentificadorExistente Caso o tipo já
     *                                esteja persistido no repositório.
     */
    @Override
    public void persisteTipo(Tipo tipo) {

    }

    /**
     * Remove o tipo.
     *
     * @param codigo O identificador do tipo a
     * @throws ResolucaoUsaTipoException O tipo
     *                                   é empregado por pelo menos uma resolução.
     */
    @Override
    public void removeTipo(String codigo) {

    }

    /**
     * Recupera o tipo com o código fornecido.
     *
     * @param codigo O código único do tipo.
     * @return A instância de {@link Tipo} cujo
     * código único é fornecido. Retorna {@code null}
     * caso não exista tipo com o código indicado.
     */
    @Override
    public Tipo tipoPeloCodigo(String codigo) {
        return null;
    }

    /**
     * Recupera a lista de tipos cujos nomes
     * são similares àquele fornecido. Um nome é
     * similar àquele do tipo caso contenha o
     * argumento fornecido. Por exemplo, para o nome
     * "casa" temos que "asa" é similar.
     * <p>
     * Um nome é dito similar se contém a sequência
     * indicada.
     *
     * @param nome Sequência que será empregada para
     *             localizar tipos por nome.
     * @return A coleção de tipos cujos nomes satisfazem
     * um padrão de semelhança com a sequência indicada.
     */
    @Override
    public List<Tipo> tiposPeloNome(String nome) {
        return null;
    }
}
