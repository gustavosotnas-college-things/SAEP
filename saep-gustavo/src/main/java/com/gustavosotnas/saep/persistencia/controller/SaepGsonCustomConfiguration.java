package com.gustavosotnas.saep.persistencia.controller;

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Pontuacao;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Classe que define uma configuração personalizada da desserialização do JSON
 * do SAEP. É usada para gerar um Gson personlizado no início do programa.
 *
 * @author gustavosotnas
 */
public class SaepGsonCustomConfiguration implements JsonDeserializer<Nota> {

    /**
     * Função que define a forma customizada de desserializar uma Nota.
     *
     * @param json uma representação de um elemento de JSON na forma de objeto.
     * @param typeOfT um tipo de variável genérica, passível de ser desserializada.
     * @param context um contexto de desserialização de JSON.
     * @return uma instância de Nota usada para a desserialização customizada.
     * @throws JsonParseException quando alguma falha grave acontece durante uma conversão.
     */
    @Override
    public Nota deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonInput = (JsonObject) json; // Converte JsonElement para JsonObject

        JsonObject originalJSON = (JsonObject) jsonInput.get("original");
        JsonObject newJSON = (JsonObject) jsonInput.get("novo");
        String justificativa = jsonInput.get("justificativa").getAsString();

        Avaliavel original = generateAvaliavel(originalJSON);
        Avaliavel novo = generateAvaliavel(newJSON);

        return new Nota(original, novo, justificativa);
    }

    /**
     * Gera um objeto genérico, com um tipo específicado (para Nota).
     *
     * @param jsonObject um objeto serializado em JSON.
     * @return o objeto desserializado para uso.
     */
    private Avaliavel generateAvaliavel(JsonObject jsonObject) {

        Gson gson = new Gson();

        if (jsonObject.has("tipo")) {
            return gson.fromJson(jsonObject, Relato.class);
        }
        else {
            return gson.fromJson(jsonObject, Pontuacao.class);
        }

    }
}
