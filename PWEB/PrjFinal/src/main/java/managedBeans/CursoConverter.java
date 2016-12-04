package managedBeans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modelos.Curso;
import servico.Servico;

@FacesConverter("converterCurso")
public class CursoConverter implements Converter {
	Servico servico = new Servico();

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {

		if (value != null && !value.isEmpty()) {
			for (Curso c : servico.getCursos())
				if (c.getNome().equals(value))
					return c;

		}

		return null;

	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object curso) {
		if (curso == null || curso.equals("")) {
			return null;
		} else {
			return ((Curso) curso).getNome();

		}
	}
}
