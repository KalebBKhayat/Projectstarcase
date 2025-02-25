public class Equipamento {
    String nome;
    String tipo;
    int saude;
    int energia;

    
    public Equipamento(String tipo, String nome, int saude, int energia) {
        this.nome = nome;
        this.saude = saude;
        this.energia = energia;
        this.tipo = tipo;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public int getSaude() {
        return saude;
    }


    public void setSaude(int saude) {
        this.saude = saude;
    }


    public int getEnergia() {
        return energia;
    }


    public void setEnergia(int energia) {
        this.energia = energia;
    }

    
}
