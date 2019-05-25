package br.usjt.rock.model.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Jazz {

	private List<String> list = new ArrayList<>();

	public Jazz() {
		list.add("partidas");
		list.add("campeonato");
	}

	public boolean validatePath(String path) {
		path = path.replaceAll("/jazz", "");
		return list.contains(path);
	}
}
