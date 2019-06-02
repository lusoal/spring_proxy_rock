package br.usjt.rock.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.usjt.rock.model.bean.Torcedor;
import br.usjt.rock.model.repository.TorcedorRepository;

@Service
public class TorcedorService {

	@Autowired
	TorcedorRepository userRepo;

	public Torcedor findUserByLoginAndSenha(Torcedor torcedorParam) {
		return userRepo.findByEmailandSenha(torcedorParam.getEmail(), torcedorParam.getSenha());
	}
}
