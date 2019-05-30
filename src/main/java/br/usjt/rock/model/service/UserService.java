package br.usjt.rock.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.rock.model.bean.Users;
import br.usjt.rock.model.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;

	public boolean findUserByLoginAndSenha(Users usr) {
		Users user = userRepo.findByEmailandSenha(usr.getEmail(), usr.getSenha());
		return user != null;
	}
}
