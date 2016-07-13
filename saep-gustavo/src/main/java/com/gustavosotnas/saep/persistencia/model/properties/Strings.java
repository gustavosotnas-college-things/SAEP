package com.gustavosotnas.saep.persistencia.model.properties;

/**
 * Reúne mensagens de retorno para usuário, de forma separada do resto do SAEP.
 *
 * @author gustavosotnas
 */
public class Strings {

    /**
     * Gera mensagem de entidade não encontrada.
     *
     * @param nomeEntidade nome da entidade que não foi encontrada.
     * @param idEntidade identificador da entidade.
     * @return uma string gerada dinamicamente que exibe mensagem de "não encontrado".
     */
    public static String getMessage$EntityNotFound (String nomeEntidade, String idEntidade) {
        return "ID da entidade " + nomeEntidade + " " + idEntidade + " não foi encontrada.";
    }

    /**
     * Gera mensagem de entidade já existente.
     *
     * @param nomeEntidade nome da entidade que já existe.
     * @param idEntidade identificador da entidade.
     * @return uma string gerada dinamicamente que exibe mensagem de "já existe".
     */
    public static String getMessage$EntityAlreadyExists (String nomeEntidade, String idEntidade) {
        return "ID da entidade " + nomeEntidade + " " + idEntidade + " já existe.";
    }

    /**
     * Gera mensagem de que existe um parecer relacionado ao Radoc em específico.
     *
     * @param idRadoc identificador do Radoc.
     * @return uma string gerada dinamicamente que exibe mensagem de "existe um parecer relacionado".
     */
    public static String getMessage$ThereIsParecerRelatedRadoc (String idRadoc) {
        return "Existe um parecer relacionado com o radoc " + idRadoc + " no banco de dados. Não é permitido esse radoc.";
    }

}
