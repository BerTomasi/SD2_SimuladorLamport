package SimuladorLamport;

import java.util.*;

/**
 * Esta classe simula o algoritmo de exclusão mútua distribuída de Lamport entre
 * um conjunto de processos.
 */
public class SimuladorLamport {

    /**
     * O método principal para executar a simulação do algoritmo de Lamport.
     *
     * @param args 
     */
    public static void main(String[] args) {

        // Cria um objeto Scanner para ler entrada do teclado
        Scanner scanner = new Scanner(System.in);

        // Criação de um HashMap para armazenar os processos, 
        // usando Integer como chave e um array de inteiros como valor
        HashMap<Integer, int[]> listaProcessos = new HashMap<>();

        System.out.print("\n**********************************************************************************************************\n");
        System.out.print("\nSIMULADOR LAMPORT - SD2 - BERNARDO D TOMASI - 2023/02\n");
        System.out.print("\n**********************************************************************************************************\n");

        // Pergunta quantos processos serão
        System.out.print("Numero de processos: ");
        int numProcessos = scanner.nextInt();

        int acres = 0; // Variável para armazenar um valor aleatório de incremento

        // Loop para criar os processos com base no número fornecido
        for (int i = 0; i < numProcessos; i++) {

            // Cria um array de inteiros para representar os tempos de cada processo
            int[] proces = new int[10];

            proces[0] = 0; // Define o tempo inicial como 0

            // Gera um valor aleatório entre 1 e 10 para o incremento
            acres = (int) (Math.random() * 10) + 1;

            // Loop para calcular os tempos subsequentes do processo
            for (int j = 0; j < proces.length; j++) {
                if (j == 0) {
                    proces[j] = 0; // O primeiro tempo é sempre 0
                } else if (j == 1) {
                    proces[j] = acres; // O segundo tempo é o valor aleatório gerado
                } else {
                    proces[j] = proces[j - 1] + acres; // Calcula os tempos subsequentes baseados no incremento
                }
            }

            // Adiciona o array de tempos do processo ao HashMap
            listaProcessos.put(i, proces);
        }

        System.out.print("\n**********************************************************************************************************\n");

        System.out.print("Processos inicializados\n");

        // Exibe os tempos dos processos criados
        for (int i = 0; i < numProcessos; i++) {

            // Exibe a identificação do processo
            System.out.print("PROCESSO " + i + ":   ");

            // Obtém os tempos do processo atual
            int[] proces = listaProcessos.get(i);

            // Exibe os tempos do processo
            for (int value : proces) {
                System.out.print("\t" + value); // Exibe o tempo atual
            }
            System.out.println(); // Pula uma linha após exibir os tempos do processo
        }

        // Loop das comunicações
        do {

            System.out.print("\n**********************************************************************************************************\n");

            // Pergunta ao usuário se deseja iniciar a comunicação entre processos
            System.out.print("Deseja a comunicação entre processos? (S/N): ");
            String resposta = scanner.next();

            if (resposta.equalsIgnoreCase("N")) {
                break;
            } else if (resposta.equalsIgnoreCase("S")) {

                // Solicita ao usuário o número do processo de origem
                System.out.print("Processo de origem: ");
                int processoOrigem = scanner.nextInt();

                // Solicita ao usuário o número do processo de destino
                System.out.print("Processo de destino: ");
                int processoDestino = scanner.nextInt();

                // Exibe uma linha de separação
                System.out.print("\n***\n");

                // Obtém os tempos do processo de origem e destino da lista de processos
                int[] procesOrigem = listaProcessos.get(processoOrigem);
                int[] procesDestino = listaProcessos.get(processoDestino);

                // Verifica se o processo de origem ou destino não foi encontrado na lista
                if (procesOrigem == null || procesDestino == null) {
                    System.out.println("Processo de origem ou destino não encontrado.");
                    continue; // Volta ao início do loop
                }

                // Solicita ao usuário os tempos de origem e destino
                System.out.print("Tempo de origem: ");
                int tempoOrigem = scanner.nextInt();
                System.out.print("Tempo de destino: ");
                int tempoDestino = scanner.nextInt();

                // Verifica se os tempos fornecidos estão dentro dos limites dos processos
                if (tempoOrigem < 0 || tempoOrigem >= procesOrigem.length
                        || tempoDestino < 0 || tempoDestino >= procesDestino.length) {
                    System.out.println("Tempos fora do intervalo do processo.");
                    continue; // Volta ao início do loop
                }

                // Verifica se o tempo no processo de destino é menor que o tempo no processo de origem
                if (procesDestino[tempoDestino] < procesOrigem[tempoOrigem]) {

                    System.out.println("\n>> REGRA DE LAMPORT APLICADA");

                    // Descobre-se a diferença entre os tempos nos processos
                    int diferenca = procesDestino[1];

                    // Atualiza o tempo no processo de destino com o tempo do processo de origem + 1
                    procesDestino[tempoDestino] = procesOrigem[tempoOrigem] + 1;

                    // Atualiza os tempos subsequentes no processo de destino com base na diferença
                    for (int i = tempoDestino + 1; i < 10; i++) {
                        procesDestino[i] = procesDestino[i - 1] + diferenca;
                    }
                } else {
                    // Caso contrário, atualiza o tempo no processo de destino com o tempo do processo de origem
                    procesDestino[tempoDestino] = procesOrigem[tempoOrigem];
                }

                // Atualiza a lista de processos com o processo de destino modificado
                listaProcessos.put(processoDestino, procesDestino);

                System.out.print("\n**********************************************************************************************************\n");

                System.out.print("Processos modificados:\n");

                // Loop para exibir os tempos dos processos após a modificação
                for (int i = 0; i < numProcessos; i++) {

                    // Exibe a identificação do processo
                    System.out.print("PROCESSO " + i + ":   ");

                    // Obtém os tempos do processo atual
                    int[] proces = listaProcessos.get(i);

                    // Exibe os tempos do processo
                    for (int value : proces) {
                        System.out.print("\t" + value); // Exibe o tempo atual
                    }
                    System.out.println();
                }
            }
        } while (true);
    }
}
