package managedBeans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import modelos.Curso;
import servico.Servico;

@ManagedBean(name = "cursoManagedBean")
@RequestScoped
public class CursoManagedBean {
	private Curso curso = new Curso();
	private Servico servico = new Servico();
	private List<Curso> cursos;

	public void onRowEdit(RowEditEvent event) {
		Curso c = (Curso) event.getObject();
		servico.upDateCurso(c);
	}

	public void salvar() {
		servico.saveCurso(curso);
		curso = new Curso();
	}
	
	public List <Curso> getCursos() {
		if(cursos == null)
		  cursos =  servico.getCursos();
		
		return cursos;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public void remove(Curso curso) {	
		if(servico.removeCurso(curso))
			cursos.remove(curso);
		else {
			 FacesMessage msg = new FacesMessage("Impossível remover curso com alunos", "Nome: " + curso.getNome());
		     FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
}
