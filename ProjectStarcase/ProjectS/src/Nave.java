
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Nave {
    String nome;
    private List<String> equipamentos;
    ArrayList<Tripulante>  tripulantes = new ArrayList<>();
    int saudeMotor;
    int energiaMotor;
    int capacidade;
    int saudeFuselagem;
    public Nave(String nome, List<String> equipamentos, ArrayList<Tripulante> tripulantes, int saudeMotor,
            int energiaMotor, int capacidade, int saudeFuselagem) {
        this.nome = nome;
        this.equipamentos = equipamentos;
        this.tripulantes =  new ArrayList<>();
        this.saudeMotor = saudeMotor;
        this.energiaMotor = energiaMotor;
        this.capacidade = capacidade;
        this.saudeFuselagem = saudeFuselagem;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public List<String> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<String> equipamentos) {
        this.equipamentos = equipamentos;
    }

    public ArrayList<Tripulante> getTripulantes() {
        return tripulantes;
    }
    public void setTripulantes(ArrayList<Tripulante> tripulantes) {
        this.tripulantes = tripulantes;
        
    }
    public int getSaudeMotor() {
        return saudeMotor;
    }
    public void setSaudeMotor(int saudeMotor) {
        this.saudeMotor = saudeMotor;
    }
    public int getEnergiaMotor() {
        return energiaMotor;
    }
    public void setEnergiaMotor(int energiaMotor) {
        this.energiaMotor = energiaMotor;
    }
    public int getCapacidade() {
        return capacidade;
    }
    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }
    public int getSaudeFuselagem() {
        return saudeFuselagem;
    }
    public void setSaudeFuselagem(int saudeFuselagem) {
        this.saudeFuselagem = saudeFuselagem;
    }
    
    public void recrutarTripulante(Tripulante tripulante) {
        this.tripulantes.add(tripulante);
    }

    public boolean temPiloto() {
        return tripulantes.stream().anyMatch(t -> t.getCargo().equals("Piloto") && t.isVivo());
    }

    public void repararParte() {
        // Repara uma parte da nave com valor entre 10 e 50
        Random rand = new Random();
        int reparo = rand.nextInt(41) + 10;
        System.out.println("Um engenheiro reparou " + reparo + " pontos de saúde na nave.");
        // Reparar na fuselagem ou motor, por exemplo:
        if (saudeFuselagem < 100) {
            saudeFuselagem = Math.min(saudeFuselagem + reparo, 100);
        } else if (saudeMotor < 100) {
            saudeMotor = Math.min(saudeMotor + reparo, 100);
        }
    }

    
    




    
}
