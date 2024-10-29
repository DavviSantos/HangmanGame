package main.java.br.edu.iff.jogoforca.dominio.rodada;

import main.java.br.edu.iff.jogoforca.dominio.jogador.Jogador;
import main.java.br.edu.iff.jogoforca.dominio.jogador.JogadorNaoEncontradoException;
import main.java.br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import main.java.br.edu.iff.repository.RepositoryException;

public class RodadaAppService {
    private static RodadaAppService soleInstance;
    private RodadaRepository rodadaRepository;
    private JogadorRepository jogadorRepository;
    private RodadaFactory rodadaFactory;

    private RodadaAppService(RodadaRepository rodadaRepository, JogadorRepository jogadorRepository,
            RodadaFactory rodadaFactory) {
        this.rodadaRepository = rodadaRepository;
        this.jogadorRepository = jogadorRepository;
        this.rodadaFactory = rodadaFactory;
    }

    public static void createSoleInstance(RodadaRepository rodadaRepository, JogadorRepository jogadorRepository,
            RodadaFactory rodadaFactory) {
        if (soleInstance == null) {
            soleInstance = new RodadaAppService(rodadaRepository, jogadorRepository, rodadaFactory);
        }
    }

    public static RodadaAppService getSoleInstance() {
		if(soleInstance==null) {
			throw new RuntimeException("Precisa chamar o createSoleInstance primeiro.");
		}
		return soleInstance;
	}

    // Checar se o jogador existe
    public Rodada novaRodada(long idJogador) {
        if (this.jogadorRepository.getPorId(idJogador).equals(null)) {
            throw new RuntimeException("Jogador não encontrado");
        }
        return this.rodadaFactory.getRodada(this.jogadorRepository.getPorId(idJogador));
    }

    // Checa pelo nome do jogador (faz sentido nenhum um ter exception e outro não,
    // mas não vamos mudar)
    public Rodada novaRodada(String nomeJogador) throws JogadorNaoEncontradoException {
        if (this.jogadorRepository.getPorNome(nomeJogador) == null) {
            throw new JogadorNaoEncontradoException(nomeJogador);
        }
        return this.rodadaFactory.getRodada(this.jogadorRepository.getPorNome(nomeJogador));
    }

    public Rodada novaRodada(Jogador jogador) {
        return this.rodadaFactory.getRodada(jogador);
    }

    public boolean salvarRodada(Rodada rodada) {
        try {
            this.rodadaRepository.inserir(rodada);
            return true;
        } catch (RepositoryException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

}
