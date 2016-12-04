package managedBeans;


import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import modelos.Professor;
import servico.Servico;

public class ProfessorDataModel extends ListDataModel<Professor> implements SelectableDataModel<Professor>{
	private static Servico servico = new Servico();

	public ProfessorDataModel() {}
	
	public ProfessorDataModel(List <Professor> list) {
		super(list);
	}

	@Override
	public Professor getRowData(String rowKey) {
		for(Professor p: servico.getProfessores()){
			if(Integer.parseInt(rowKey) == p.getNumero())
				return p;
		}
		return null;
	}

	@Override
	public Object getRowKey(Professor professor) {
		return professor.getNumero();
	}
	
	
}
