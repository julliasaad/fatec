package managedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;

import modelos.Curso;
import modelos.Professor;
import servico.Servico;

@ManagedBean(name = "professorManagedBean")
@SessionScoped
public class ProfessorManagedBean {
	private Professor professor = new Professor();
	private Professor professorSelecionado;
	private Servico servico = new Servico();
	private DualListModel<Curso> dualList = new DualListModel<Curso>();
	private int flag = 0;
	
	public void onRowEdit(RowEditEvent event) {
		Professor p = (Professor) event.getObject();
		servico.upDateProfessor(p);
	}

	public void salvar() {
		servico.saveProfessor(professor);
		professor = new Professor();
	}
	
	public void addCursoToProfessor(Professor professor) {
		professorSelecionado = professor;

	}
	
	public DualListModel<Curso> getCursos() {
		if(flag == 0){
			List<Curso> source = new ArrayList<Curso>();
			List<Curso> target = new ArrayList<Curso>();
	
			source.addAll(servico.getCursos());
	
			if (professorSelecionado != null) {
				
				target.addAll(professorSelecionado.getCursos());
				source.removeAll(professorSelecionado.getCursos());
			}
	
			dualList.setSource(source);
			dualList.setTarget(target);
			
			
			return dualList;
			}else{
				return dualList;
			}		
	}

	public void setCursos(DualListModel<Curso> cursos) {
		this.dualList = cursos;	
		flag = 1;
	}

	public void remove(Professor professor) {
		servico.removeProfessor(professor);

	}

	public Professor getProfessorSelecionado() {
		return professorSelecionado;
	}

	public void associar() {
		for (Curso c : dualList.getSource()) {
			c.getProfessores().remove(professorSelecionado);
			c.removeProfessor(professorSelecionado);
		}

		for (Curso c : dualList.getTarget()) {
			c.getProfessores().add(professorSelecionado);
			c.addProfessor(professorSelecionado);
		}
		
		professorSelecionado.getCursos().clear();
		professorSelecionado.getCursos().addAll(dualList.getTarget());

		servico.upDateProfessor(professorSelecionado);
		flag = 0;

	}

	public void setProfessorSelecionado(Professor professorSelecionado) {
		if(professorSelecionado != null)
			this.professorSelecionado = servico.upDateProf(professorSelecionado);
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public ProfessorDataModel getProfessores() {
		return new ProfessorDataModel(servico.getProfessores());
	}
	
	public List <Curso> getCursosProfessor()
	{
		if(professorSelecionado != null)
			return servico.getCursosProfessor(professorSelecionado);
		else
			return null;
	}
	
	public void onRowSelect(SelectEvent event) {
     	
	}
}
