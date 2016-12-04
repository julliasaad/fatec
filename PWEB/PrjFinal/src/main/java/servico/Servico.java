package servico;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import modelos.Aluno;
import modelos.Curso;
import modelos.Professor;

@ManagedBean(name = "servico", eager = true)
@ApplicationScoped
public class Servico {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PrjFinal");

	public Servico() {

	}
	// operacoes Aluno

	public void saveAluno(Aluno aluno) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(aluno);
		em.getTransaction().commit();
		em.close();
	}

	public void removeAluno(Aluno aluno) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Aluno a = em.find(Aluno.class, aluno.getMatricula());
		em.remove(a);
		em.getTransaction().commit();
		em.close();
	}

	public List<Aluno> getAlunos() {
		List<Aluno> alunos;
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("Select a From Aluno a", Aluno.class);
		alunos = q.getResultList();
		em.close();
		return alunos;
	}

	public void upDateAluno(Aluno a) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(a);
		em.getTransaction().commit();
		em.close();

	}

	//opercoes Curso

	public List<Curso> getCursos() {

		List<Curso> cursos;
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("Select c From Curso c", Curso.class);
		cursos = q.getResultList();
		em.close();
		return cursos;
	}

	public void saveCurso(Curso curso) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(curso);
		em.getTransaction().commit();
		em.close();
	}

	public void upDateCurso(Curso c) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(c);
		em.getTransaction().commit();
		em.close();

	}

	public boolean removeCurso(Curso curso) {

		boolean sucesso = false;
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Curso f = em.find(Curso.class, curso.getCodigo());
			em.remove(f);
			em.getTransaction().commit();
			em.close();
			sucesso = true;
		} catch (Exception e) {

		}

		return sucesso;

	}

	//operacoes professor
	
	public List<Professor> getProfessores() {

		List<Professor> professores;
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("Select p From Professor p", Professor.class);
		professores = q.getResultList();
		em.close();
		return professores;
	}
	
	public void saveProfessor(Professor professor) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(professor);
		em.getTransaction().commit();
		em.close();
	}

	public void upDateProfessor(Professor p) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(p);
		em.getTransaction().commit();
		em.close();

	}
	
	public Professor upDateProf(Professor p){
		EntityManager em = emf.createEntityManager();
		Professor prof = em.find(Professor.class, p.getNumero());
	    em.refresh(prof);
	    em.close();
	    
	    return prof;
	}

	public boolean removeProfessor(Professor professor) {

		boolean sucesso = false;
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Professor p = em.find(Professor.class, professor.getNumero());
			em.remove(p);
			em.getTransaction().commit();
			em.close();
			sucesso = true;
		} catch (Exception e) {

		}

		return sucesso;

	}
	public List<Curso> getCursosProfessor(Professor professorSelecionado){
		List<Curso> cursos = null;
		EntityManager em = emf.createEntityManager();
		Professor p = em.find(Professor.class, professorSelecionado.getNumero());
		em.refresh(p);
		cursos = p.getCursos();
		em.close();
		
		return cursos;
	}
}
