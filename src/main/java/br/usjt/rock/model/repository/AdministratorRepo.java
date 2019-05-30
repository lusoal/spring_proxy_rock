package br.usjt.rock.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.usjt.rock.model.bean.Administrator;

@Repository
public interface AdministratorRepo extends JpaRepository<Administrator, Long>{
	
	public static final String validaAdmin = "SELECT * FROM tb_administrador WHERE email = ? AND senha = ?";
	@Query(value = validaAdmin, nativeQuery = true)
	Administrator findByEmailandSenha(String email, String senha);
	
	Administrator findUserByEmail(String email);
}
