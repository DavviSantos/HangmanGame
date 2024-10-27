package main.java.br.edu.iff.jogoforca.dominio.rodada;

import main.java.br.edu.iff.repository.Repository;
import main.java.br.edu.iff.jogoforca.dominio.jogador.Jogador;
import main.java.br.edu.iff.repository.RepositoryException;

public interface RodadaRepository extends Repository {

	public Rodada getPorId(long id);
	
	public Rodada[] getPorJogador(Jogador jogador);
	
	public void inserir(Rodada rodada) throws RepositoryException;
	
	public void atualizar(Rodada rodada) throws RepositoryException;
	
	public void remover(Rodada rodada) throws RepositoryException;
	
}
