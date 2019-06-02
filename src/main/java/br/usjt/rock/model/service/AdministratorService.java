package br.usjt.rock.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.rock.model.bean.Administrator;
import br.usjt.rock.model.repository.AdministratorRepo;

@Service
public class AdministratorService {

	@Autowired
	AdministratorRepo adminRepo;

	public Administrator findUserByLoginAndSenha(Administrator adm) {
		return adminRepo.findByEmailandSenha(adm.getEmail(), adm.getSenha());
	}

	public boolean addUser(Administrator admin) {
		try {
			adminRepo.save(admin);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public Administrator findUserByEmail(String email) {
		return adminRepo.findUserByEmail(email);
	}
}
