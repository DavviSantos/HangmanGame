import java.util.Scanner;
import main.java.br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.Tema;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import main.java.br.edu.iff.jogoforca.Aplicacao;
import main.java.br.edu.iff.jogoforca.dominio.jogador.Jogador;
import main.java.br.edu.iff.jogoforca.dominio.rodada.Rodada;
import main.java.br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;

public class App {
    public static void main(String[] args) {
        try {
            // Inicializar a aplicação
            Aplicacao app = Aplicacao.getSoleInstance();

            // Inicializar banco de palavras
            inicializarBancoDePalavras(app);

            Scanner scanner = new Scanner(System.in);

            System.out.println("Bem-vindo ao Jogo da Forca!");
            System.out.print("Digite seu nome: ");
            String nomeJogador = scanner.nextLine();

            // Criar ou obter jogador
            Jogador jogador = app.getJogadorFactory().getJogador(nomeJogador);

            // Iniciar uma nova rodada
            RodadaAppService rodadaAppService = RodadaAppService.getSoleInstance();
            Rodada rodada = rodadaAppService.novaRodada(jogador);

            while (!rodada.encerrou()) {
                System.out.println("\nTema: " + rodada.getTema().getNome());
                System.out.print("Palavra(s): ");
                rodada.exibirItens(System.out);
                System.out.print("\nLetras erradas: ");
                rodada.exibirLetrasErradas(System.out);
                System.out.println("\nTentativas restantes: " + rodada.getQtdeTentativasRestantes());

                System.out.print("Digite uma letra ou 'arriscar' para tentar a palavra completa: ");
                String entrada = scanner.nextLine().toUpperCase();

                if (entrada.equals("ARRISCAR")) {
                    System.out.print("Digite a(s) palavra(s): ");
                    String[] palavras = scanner.nextLine().toUpperCase().split(" ");
                    rodada.arriscar(palavras);
                } else if (entrada.length() == 1) {
                    rodada.tentar(entrada.charAt(0));
                } else {
                    System.out.println("Entrada inválida!");
                }
            }

            // Fim do jogo
            if (rodada.descobriu()) {
                System.out.println("Parabéns! Você descobriu a(s) palavra(s)!");
            } else {
                System.out.println("Que pena! Você não descobriu a(s) palavra(s).");
                System.out.print("A(s) palavra(s) era(m): ");
                rodada.exibirPalavras(System.out);
            }

            System.out.println("Pontuação: " + rodada.calcularPontos());

            // Salvar a rodada
            rodadaAppService.salvarRodada(rodada);

            scanner.close();

        } catch (Exception e) {
            System.err.println("Erro durante a execução do jogo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void inicializarBancoDePalavras(Aplicacao app) throws Exception {
        try {
            TemaFactory temaFactory = app.getTemaFactory();
            TemaRepository temaRepository = app.getRepositoryFactory().getTemaRepository();
            PalavraAppService palavraAppService = PalavraAppService.getSoleInstance();

            // Verificar se já existem temas
            Tema temaFrutas = null;
            Tema temaAnimais = null;
            Tema temaPaises = null;

            // Buscar temas existentes ou criar novos
            Tema[] temasPorNome = temaRepository.getPorNome("FRUTAS");
            if (temasPorNome.length > 0) {
                temaFrutas = temasPorNome[0];
            } else {
                temaFrutas = temaFactory.getTema("FRUTAS");
                temaRepository.inserir(temaFrutas);
            }

            temasPorNome = temaRepository.getPorNome("ANIMAIS");
            if (temasPorNome.length > 0) {
                temaAnimais = temasPorNome[0];
            } else {
                temaAnimais = temaFactory.getTema("ANIMAIS");
                temaRepository.inserir(temaAnimais);
            }

            temasPorNome = temaRepository.getPorNome("PAISES");
            if (temasPorNome.length > 0) {
                temaPaises = temasPorNome[0];
            } else {
                temaPaises = temaFactory.getTema("PAISES");
                temaRepository.inserir(temaPaises);
            }

            // Adicionar palavras para cada tema se ainda não existirem
            // Frutas
            if (app.getRepositoryFactory().getPalavraRepository().getPorTema(temaFrutas).length == 0) {
                palavraAppService.novaPalavra("BANANA", temaFrutas.getId());
                palavraAppService.novaPalavra("MACA", temaFrutas.getId());
                palavraAppService.novaPalavra("UVA", temaFrutas.getId());
                palavraAppService.novaPalavra("PERA", temaFrutas.getId());
                palavraAppService.novaPalavra("LARANJA", temaFrutas.getId());
            }

            // Animais
            if (app.getRepositoryFactory().getPalavraRepository().getPorTema(temaAnimais).length == 0) {
                palavraAppService.novaPalavra("CACHORRO", temaAnimais.getId());
                palavraAppService.novaPalavra("GATO", temaAnimais.getId());
                palavraAppService.novaPalavra("LEAO", temaAnimais.getId());
                palavraAppService.novaPalavra("TIGRE", temaAnimais.getId());
                palavraAppService.novaPalavra("ELEFANTE", temaAnimais.getId());
            }

            // Países
            if (app.getRepositoryFactory().getPalavraRepository().getPorTema(temaPaises).length == 0) {
                palavraAppService.novaPalavra("BRASIL", temaPaises.getId());
                palavraAppService.novaPalavra("FRANCA", temaPaises.getId());
                palavraAppService.novaPalavra("JAPAO", temaPaises.getId());
                palavraAppService.novaPalavra("ITALIA", temaPaises.getId());
                palavraAppService.novaPalavra("CHINA", temaPaises.getId());
            }

            // Verificar se as palavras foram adicionadas corretamente
            for (Tema tema : new Tema[] { temaFrutas, temaAnimais, temaPaises }) {
                int numPalavras = app.getRepositoryFactory().getPalavraRepository().getPorTema(tema).length;
                System.out.println("Tema " + tema.getNome() + " tem " + numPalavras + " palavras.");
                if (numPalavras < 3) {
                    throw new RuntimeException("Tema " + tema.getNome() + " não tem palavras suficientes para o jogo.");
                }
            }

            System.out.println("Banco de palavras inicializado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao inicializar banco de palavras: " + e.getMessage());
            throw e;
        }
    }
}