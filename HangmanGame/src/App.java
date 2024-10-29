import java.util.Scanner;
import main.java.br.edu.iff.bancodepalavras.dominio.letra.Letra;
import main.java.br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import main.java.br.edu.iff.jogoforca.Aplicacao;
import main.java.br.edu.iff.jogoforca.dominio.jogador.Jogador;
import main.java.br.edu.iff.jogoforca.dominio.rodada.Rodada;
import main.java.br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import main.java.br.edu.iff.repository.RepositoryException;

public class App {
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        
        Aplicacao aplicacao = Aplicacao.getSoleInstance();
        
        PalavraAppService palavraAppService = PalavraAppService.getSoleInstance();
        TemaRepository temaRepository = aplicacao.getRepositoryFactory().getTemaRepository();
        TemaFactory temaFactory = aplicacao.getTemaFactory();

        inserirTemas(temaRepository, temaFactory);
        inserirPalavras(palavraAppService);

        System.out.println("################ Jogo da Forca ver 0.0.1");

        System.out.print("Digite seu nome: ");
        String jogadorNome = scanner.next();
        System.out.println( jogadorNome + "! Bem-vindo, o jogo está sendo iniciado:\n");


        Jogador jogador = aplicacao.getJogadorFactory().getJogador(jogadorNome);

        try {
            aplicacao.getRepositoryFactory().getJogadorRepository().inserir(jogador);
        } catch (RepositoryException e) {
            throw new RepositoryException(e);
        }

        jogarRodada(jogador);

       
    }

    public static void inserirTemas(TemaRepository temaRepository, TemaFactory temaFactory) throws RepositoryException{
        try {
            temaRepository.inserir(temaFactory.getTema("Linguagens de Programação"));
            
        } catch (RepositoryException e) {
            throw new RepositoryException(e);
        }	
    }

    public static void inserirPalavras(PalavraAppService palavraAppService){
        palavraAppService.novaPalavra("java", 1);
        palavraAppService.novaPalavra("python", 2);
        palavraAppService.novaPalavra("c", 3);
    }

    public static void jogarRodada(Jogador jogador){

        RodadaAppService rodadaAppService = RodadaAppService.getSoleInstace();

        Rodada rodada = rodadaAppService.novaRodada(jogador);
        System.out.println("O tema sorteado foi: " + rodada.getTema().getNome());

        do {
            System.out.println("\nTentativas restantes: " + rodada.getQtdeTentativasRestantes());
            System.out.println("Tentativas anteriores: ");
            for (Letra tentativa : rodada.getTentativas()) {
                tentativa.exibir(null);
                System.out.print(" ");
            }
            System.out.println();

            System.out.println("Palavras:");
            rodada.exibirItens(null);
            System.out.println();
            System.out.println("Corpo: ");
            rodada.exibirBoneco(null);
            System.out.println();

            System.out.println("\nEscolha uma opção:");
            System.out.println("(1) Tentar adivinhar uma letra!");
            System.out.println("(2) Arriscar palavra!");
            System.out.print("Opção: ");
            String escolha = scanner.next();
            switch (escolha){
                case "1":
                    System.out.print("Digite a letra: ");
                    rodada.tentar(scanner.next().charAt(0));
                    break;
                case "2":
                    String[] palavrasArriscadas = new String[rodada.getNumPalavras()];
                    for (int i = 0; i < palavrasArriscadas.length; i++) {
                        System.out.print("Se arrisque e chute a palavra " + (i + 1)  + " :");
                        palavrasArriscadas[i] = scanner.next();
                    }
                    rodada.arriscar(palavrasArriscadas);
                    break;
            }

           if (rodada.encerrou()) {
               if (rodada.descobriu()) { 
                   System.out.println("Parabéns, você descobriu a palavra secreta!");
               } else {
                   System.out.println("Infelizmente você perdeu essa rodada! Jogue novamente!");
               }
               System.out.println("Pontuação final: " + rodada.calcularPontos());
           }
        } while (!rodada.encerrou());

    }
}
