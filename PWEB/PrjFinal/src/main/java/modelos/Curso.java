package modelos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Curso {
	@Id
	private int codigo;
	private String nome;
	private boolean presencial;
	
	@OneToMany
	private List<Aluno> alunos = new ArrayList<Aluno>();
	
	@ManyToMany
	private List<Professor> professores = new ArrayList<Professor>();
	
	public Curso() {
		
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void addAluno(Aluno aluno) {
		alunos.add(aluno);
		
	}
	public List<Aluno> getAlunos() {
		return alunos;
	}
	public List<Professor> getProfessores() {
		return professores;
	}
	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	public boolean isPresencial() {
		return presencial;
	}
	public void setPresencial(boolean presencial) {
		this.presencial = presencial;
	}
	
	public void addProfessor(Professor p){
		professores.add(p);
	}
	
	public void removeProfessor(Professor p){
		professores.remove(p);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Curso [codigo=" + codigo + ", nome=" + nome + ", presencial=" + presencial + ", alunos=" + alunos
				+ ", professores=" + professores + "]";
	}
	
	
}
