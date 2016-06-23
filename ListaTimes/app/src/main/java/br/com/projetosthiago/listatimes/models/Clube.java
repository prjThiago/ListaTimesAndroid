package br.com.projetosthiago.listatimes.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suigv on 22/06/2016.
 */
public class Clube {
    public int id;
    public String nome;
    public int id_estado;
    public int id_divisao;
    public String foto;

    public Clube(){}

    public Clube(int id, String nome, int id_estado, int id_divisao, String foto){
        this.id = id;
        this.nome = nome;
        this.id_estado = id_estado;
        this.id_divisao = id_divisao;
        this.foto = foto;
    }
}
