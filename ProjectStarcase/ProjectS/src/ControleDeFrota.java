import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ControleDeFrota {
    static Scanner scan = new Scanner(System.in);
    static List<Nave> naves = new ArrayList<>();
    static int creditos = 100;
    static int rodada = 0;

    public static void main(String[] args) {
        int escolhaCOM = 0;

        while (escolhaCOM != 5) {
            System.out.println("\nBEM-VINDO AO STARCOM! (Rodada: " + rodada + ")");
            System.out.println("1) Iniciar Combate");
            System.out.println("2) Comprar Nave");
            System.out.println("3) Comprar Equipamento");
            System.out.println("4) Rcrutar");
            System.out.println("5) Reparar Nave");
            System.out.println("6) Sair");
            System.out.println("Créditos disponíveis: " + creditos);
            System.out.print("Escolha uma opção: ");
            escolhaCOM = scan.nextInt();

            switch (escolhaCOM) {
                case 1:
                    iniciarCombate();
                    break;
                case 2:
                    comprarNave();
                    break;
                case 3:
                    comprarEquipamento();
                    break;
                case 4:
                    recrutarTripulante();
                    break;
                case 5:
                    repararNave();
                    break;
                case 6:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            if (escolhaCOM != 5) {
                creditos += 100; // Ganha créditos a cada rodada
                rodada++;
            }
        }
    }

    public static void iniciarCombate() {
        if (naves.isEmpty()) {
            System.out.println("Você precisa de naves na frota para iniciar um combate.");
            return;
        }

        for(int i = 0 ; i<naves.size();i++){
            if(naves.get(i).tripulantes.isEmpty()){
                System.out.println("VOCÊ PRECISA DE TRIPULANTES PARA INICIAR UM COMBATE!");
                return;
            }
        }
    
        System.out.println("\n=== INICIANDO COMBATE ===");
        List<Nave> navesInimigas = generateNavesInimigas();
        boolean emCombate = true;
    
        while (emCombate) {
            System.out.println("\n--- Sua Frota ---");
            for (int i = 0; i < naves.size(); i++) {
                Nave nave = naves.get(i);
                System.out.println((i + 1) + ") " + nave.getNome() + " | Fuselagem: " + nave.getSaudeFuselagem() +
                                   " | Motor: " + nave.getSaudeMotor() + " | Equipamentos: " + nave.getEquipamentos() +
                                   " | Tripulantes: " + nave.getTripulantes().size());
            }
    
            System.out.println("\n--- Naves Inimigas ---");
            for (int i = 0; i < navesInimigas.size(); i++) {
                Nave inimiga = navesInimigas.get(i);
                System.out.println((i + 1) + ") " + inimiga.getNome() + " | Fuselagem: " + inimiga.getSaudeFuselagem() +
                                   " | Motor: " + inimiga.getSaudeMotor());
            }
    
            System.out.println("\nAções:");
            System.out.println("1) Atacar");
            System.out.println("2) Usar Equipamento");
            System.out.println("3) Fugir");
            System.out.print("Escolha uma ação: ");
            int acao = scan.nextInt();
    
            switch (acao) {
                case 1:
                    realizarAtaque(naves, navesInimigas);
                    break;
                case 2:
                    realizarUsoEquipamento(naves);
                    break;
                
                case 3:
                    if (naves.get(0).temPiloto()) {
                        System.out.println("Você fugiu do combate!");
                        emCombate = false;
                    } else {
                        System.out.println("Sua nave não tem piloto. Não pode fugir.");
                    }
                    break;
                default:
                    System.out.println("Ação inválida.");
            }
    
            // Ações do inimigo
            for (Nave inimiga : navesInimigas) {
                if (inimiga.getSaudeFuselagem() > 0) {
                    // Inimigo ataca aleatoriamente
                    Random rand = new Random();
                    int alvo = rand.nextInt(naves.size()); // Escolhe uma nave aleatória da frota
                    Nave naveAtacada = naves.get(alvo);
    
                    // Decidir qual parte da nave o inimigo vai atacar
                    int parteAtacada = rand.nextInt(3); // 0 = Fuselagem, 1 = Motor, 2 = Equipamentos
                    if (parteAtacada == 0) {
                        atacarFuselagem(inimiga, naveAtacada);
                    } else if (parteAtacada == 1) {
                        atacarMotor(inimiga, naveAtacada);
                    } else {
                        atacarEquipamentos(inimiga, naveAtacada);
                    }
    
                    // Chance de 10% de morte de tripulante
                    if (rand.nextInt(100) < 10) {
                        morteTripulante(naveAtacada);
                    }
                }
            }
    
            // Verificação de vitória ou derrota
            if (navesInimigas.isEmpty()) {
                System.out.println("Você venceu o combate!");
                emCombate = false;
            } else if (naves.isEmpty()) {
                System.out.println("Você perdeu todas as suas naves...");
                emCombate = false;
            }
        }
    }
    
    public static void recrutarTripulante() {
        int escolha = 0;
        if(naves.isEmpty()){
            System.out.println("NÃO É POSSÍVEL RECRUTAR SEM NAVES!");
            
        }else{
            System.out.println("Escolha o tipo de tripulante para recrutar:");
          System.out.println("1) Piloto");
            System.out.println("2) Engenheiro");
           int tipoTripulante = scan.nextInt();
            
          if (tipoTripulante == 1) {
              System.out.print("Digite o nome do piloto: ");
             String nomePiloto = scan.next();
              Tripulante piloto = new Tripulante(nomePiloto, "Piloto");
              for(int i = 0; i< naves.size(); i++){
                System.out.println(i + ") " + naves.get(i).getNome());
              }
              System.out.println("Qual nave voce deseja adiconar o tripulante:");
              escolha = scan.nextInt();
              naves.get(escolha).recrutarTripulante(piloto);
                System.out.println("Piloto recrutado com sucesso!");
          } else if (tipoTripulante == 2) {
              System.out.print("Digite o nome do engenheiro: ");
              String nomeEngenheiro = scan.next();
              Tripulante engenheiro = new Tripulante(nomeEngenheiro, "Engenheiro");
              for(int i = 0; i< naves.size(); i++){
                System.out.println(i + ") " + naves.get(i).getNome());
              }
              System.out.println("Qual nave voce deseja adiconar o tripulante:");
              escolha = scan.nextInt();
              naves.get(escolha).recrutarTripulante(engenheiro);
                System.out.println("Engenheiro recrutado com sucesso!");
          } else {
              System.out.println("Opção inválida.");
           }
        }
        
    }
    
    public static void morteTripulante(Nave nave) {
        Random rand = new Random();
        if (!nave.getTripulantes().isEmpty()) {
            Tripulante tripulanteMorto = nave.getTripulantes().get(rand.nextInt(nave.getTripulantes().size()));
            tripulanteMorto.morrer();
            System.out.println(tripulanteMorto.getNome() + " (" + tripulanteMorto.getCargo() + ") morreu durante o combate!");
        }
    }
    
    
    public static void realizarAtaque(List<Nave> aliadas, List<Nave> inimigas) {
        System.out.println("\nEscolha uma nave para atacar:");
        for (int i = 0; i < inimigas.size(); i++) {
            Nave inimiga = inimigas.get(i);
            System.out.println((i + 1) + ") " + inimiga.getNome() + " | Fuselagem: " + inimiga.getSaudeFuselagem() +
                               " | Motor: " + inimiga.getSaudeMotor());
        }
    
        int naveEscolhida = scan.nextInt() - 1;
        if (naveEscolhida < 0 || naveEscolhida >= inimigas.size()) {
            System.out.println("Nave inimiga inválida.");
            return;
        }
    
        Nave inimiga = inimigas.get(naveEscolhida);
    
        System.out.println("\nEscolha a parte da nave inimiga para atacar:");
        System.out.println("1) Fuselagem");
        System.out.println("2) Motor");
        System.out.println("3) Equipamentos");
        int parteEscolhida = scan.nextInt();
    
        if (parteEscolhida == 1) {
            atacarFuselagem(aliadas.get(0), inimiga);
        } else if (parteEscolhida == 2) {
            atacarMotor(aliadas.get(0), inimiga);
        } else if (parteEscolhida == 3) {
            atacarEquipamentos(aliadas.get(0), inimiga);
        }
    }
    
    public static void atacarFuselagem(Nave atacante, Nave alvo) {
        Random rand = new Random();
        int danoBase = 50;
        int critico = rand.nextInt(31); // Crítico de 0 a 30
        int danoTotal = danoBase + critico;
    
        if (alvo.getSaudeFuselagem() > 0) {
            alvo.setSaudeFuselagem(alvo.getSaudeFuselagem() - danoTotal);
            System.out.println(atacante.getNome() + " atacou a fuselagem de " + alvo.getNome() + " causando " + danoTotal + " de dano.");
            if (alvo.getSaudeFuselagem() <= 0) {
                System.out.println("A fuselagem de " + alvo.getNome() + " foi destruída! A nave foi perdida.");
                naves.remove(alvo);
            }
        }
    }
    
    public static void atacarMotor(Nave atacante, Nave alvo) {
        Random rand = new Random();
        int danoBase = 50;
        int critico = rand.nextInt(31); // Crítico de 0 a 30
        int danoTotal = danoBase + critico;
    
        if (alvo.getSaudeMotor() > 0) {
            alvo.setSaudeMotor(alvo.getSaudeMotor() - danoTotal);
            System.out.println(atacante.getNome() + " atacou o motor de " + alvo.getNome() + " causando " + danoTotal + " de dano.");
            if (alvo.getSaudeMotor() <= 0) {
                System.out.println("O motor de " + alvo.getNome() + " foi destruído! Não pode mais fugir.");
            }
        }
    }
    
    public static void atacarEquipamentos(Nave atacante, Nave alvo) {
        Random rand = new Random();
        int danoBase = 50;
        int critico = rand.nextInt(31); // Crítico de 0 a 30
        int danoTotal = danoBase + critico;
    
        if (!alvo.getEquipamentos().isEmpty()) {
            alvo.getEquipamentos().clear();
            System.out.println(atacante.getNome() + " destruiu os equipamentos de " + alvo.getNome() + " causando " + danoTotal + " de dano.");
        }
    }
    
    public static void realizarUsoEquipamento(List<Nave> naves) {
        System.out.println("\nEscolha uma nave para usar equipamento:");
        for (int i = 0; i < naves.size(); i++) {
            Nave nave = naves.get(i);
            System.out.println((i + 1) + ") " + nave.getNome() + " | Equipamentos: " + nave.getEquipamentos());
        }
    
        int naveEscolhida = scan.nextInt() - 1;
        if (naveEscolhida < 0 || naveEscolhida >= naves.size()) {
            System.out.println("Nave inválida.");
            return;
        }
    
        Nave nave = naves.get(naveEscolhida);
        if (nave.getEquipamentos().isEmpty()) {
            System.out.println("Esta nave não tem equipamentos disponíveis.");
            return;
        }
    
        System.out.println("\nEscolha um equipamento para usar:");
        for (int i = 0; i < nave.getEquipamentos().size(); i++) {
            System.out.println((i + 1) + ") " + nave.getEquipamentos().get(i));
        }
    
        int equipamentoEscolhido = scan.nextInt() - 1;
        if (equipamentoEscolhido < 0 || equipamentoEscolhido >= nave.getEquipamentos().size()) {
            System.out.println("Equipamento inválido.");
            return;
        }
    
        String equipamento = nave.getEquipamentos().get(equipamentoEscolhido);
        if (equipamento.equals("Gerador de Escudo")) {
            System.out.println(nave.getNome() + " usou o Gerador de Escudo para aumentar sua defesa.");
            // Implementar aumento de defesa ou outro efeito aqui
        } else {
            System.out.println(nave.getNome() + " usou o " + equipamento + "!");
            // Implementar efeito do equipamento de ataque
        }
    
        nave.getEquipamentos().remove(equipamentoEscolhido);
    }
    

    public static void comprarNave() {
        System.out.println("\n=== COMPRA DE NAVES ===");
        System.out.println("1) Fragata Simples (25 Créditos)");
        System.out.println("2) Cruzador (50 Créditos)");
        System.out.print("Escolha o tipo de nave que deseja comprar: ");
        int escolha = scan.nextInt();

        if (escolha == 1 && creditos >= 25) {
            Nave fragata = new Nave("Fragata Simples", new ArrayList<>(), new ArrayList<>(), 50, 30, 20, 100);
            adicionarNaveAFrota(fragata, 25);
        } else if (escolha == 2 && creditos >= 50) {
            Nave cruzador = new Nave("Cruzador", new ArrayList<>(), new ArrayList<>(), 100, 60, 50, 250);
            adicionarNaveAFrota(cruzador, 50);
        } else {
            System.out.println("Créditos insuficientes ou opção inválida.");
        }
    }

    private static void adicionarNaveAFrota(Nave nave, int custo) {
        scan.nextLine(); // Consumir a quebra de linha
        System.out.print("Dê um nome para sua nova nave: ");
        String nome = scan.nextLine();
        nave.setNome(nome);
        naves.add(nave);
        creditos -= custo;
        System.out.println("Nave " + nome + " adicionada à frota!");
    }

    public static void comprarEquipamento() {
        System.out.println("\n=== COMPRA DE EQUIPAMENTOS ===");
        System.out.println("1) Arma (10 Créditos)");
        System.out.println("2) Lança-Mísseis (20 Créditos)");
        System.out.println("3) Gerador de Escudo (15 Créditos)");
        System.out.print("Escolha um equipamento: ");
        int escolha = scan.nextInt();

        int custo;
        String equipamento;
        switch (escolha) {
            case 1:
                custo = 10;
                equipamento = "Arma";
                break;
            case 2:
                custo = 20;
                equipamento = "Lança-Mísseis";
                break;
            case 3:
                custo = 15;
                equipamento = "Gerador de Escudo";
                break;
            default:
                System.out.println("Equipamento inválido.");
                return;
        }

        if (creditos < custo) {
            System.out.println("Créditos insuficientes.");
            return;
        }

        System.out.println("Escolha a nave para adicionar o equipamento (1-" + naves.size() + "): ");
        int naveIndex = scan.nextInt() - 1;

        if (naveIndex < 0 || naveIndex >= naves.size()) {
            System.out.println("Nave inválida.");
            return;
        }

        Nave nave = naves.get(naveIndex);
        int limiteEquipamentos = nave.getNome().equals("Fragata Simples") ? 2 : 4;

        if (nave.getEquipamentos().size() >= limiteEquipamentos) {
            System.out.println("A nave " + nave.getNome() + " atingiu o limite de equipamentos.");
            return;
        }

        nave.getEquipamentos().add(equipamento);
        creditos -= custo;
        System.out.println("Equipamento " + equipamento + " adicionado à nave " + nave.getNome() + "!");
    }

    public static void repararNave() {
        System.out.println("\n=== REPARAR NAVES ===");
        System.out.println("1) Reparar Motor (50 Créditos)");
        System.out.println("2) Reparar Fuselagem (50 Créditos)");
        System.out.println("3) Reparar Equipamentos (50 Créditos)");
        System.out.print("Escolha o reparo: ");
        int escolha = scan.nextInt();

        if (creditos < 50) {
            System.out.println("Créditos insuficientes.");
            return;
        }

        System.out.println("Escolha a nave para realizar o reparo (1-" + naves.size() + "): ");
        int naveIndex = scan.nextInt() - 1;

        if (naveIndex < 0 || naveIndex >= naves.size()) {
            System.out.println("Nave inválida.");
            return;
        }

        Nave nave = naves.get(naveIndex);
        switch (escolha) {
            case 1:
                nave.setSaudeMotor(100);
                System.out.println("Motor reparado com sucesso!");
                break;
            case 2:
                nave.setSaudeFuselagem(100);
                System.out.println("Fuselagem reparada com sucesso!");
                break;
            case 3:
                nave.getEquipamentos().clear();
                System.out.println("Equipamentos reparados com sucesso!");
                break;
            default:
                System.out.println("Opção de reparo inválida.");
        }
        creditos -= 50;
    }

   

    public static List<Nave> generateNavesInimigas() {
        Random rand = new Random();
        List<Nave> navesInimigas = new ArrayList<>();
        int quantidadeNaves = rand.nextInt(3) + 1; // De 1 a 3 naves inimigas

        for (int i = 0; i < quantidadeNaves; i++) {
            int tipoNave = rand.nextInt(2) + 1; // 1 para Fragata, 2 para Cruzador
            Nave nave;
            if (tipoNave == 1) {
                nave = new Nave("Fragata Inimiga", new ArrayList<>(), new ArrayList<>(), 50, 30, 20, 100);
            } else {
                nave = new Nave("Cruzador Inimigo", new ArrayList<>(), new ArrayList<>(), 100, 60, 50, 250);
            }
            navesInimigas.add(nave);
        }
        return navesInimigas;
    }
}


