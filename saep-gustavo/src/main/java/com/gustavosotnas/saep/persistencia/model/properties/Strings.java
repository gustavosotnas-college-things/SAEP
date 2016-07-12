package com.gustavosotnas.saep.persistencia.model.properties;

/**
 * Created by gustavosotnas on 12/07/16.
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
}
