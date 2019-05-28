package br.usjt.rock.model.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Maestro {

	private List<String> list = new ArrayList<>();

	public Maestro() {
		list.add("torcedor");
		list.add("convenio");
		list.add("experiencia");
	}

	public boolean validatePath(String path) {
		path = path.replaceAll("/maestro", "");
		return list.contains(path);
	}	
}