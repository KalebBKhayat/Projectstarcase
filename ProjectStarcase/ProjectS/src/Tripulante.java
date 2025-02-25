public class Tripulante {
    private String nome;
    private String cargo; // Piloto ou Engenheiro
    private boolean vivo;

    // Construtor que aceita nome e cargo
    public Tripulante(String nome, String cargo) {
        this.nome = nome;
        this.cargo = cargo;
        this.vivo = true;  // Por padrão, o tripulante começa vivo
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void morrer() {
        this.vivo = false;
    }

    public void curar() {
        this.vivo = true;
    }
}


