package br.usjt.rock.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.usjt.rock.model.bean.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
	
	public static final String validaUser = "SELECT * FROM tb_torcedores WHERE email = ? AND senha = ?";
	@Query(value = validaUser, nativeQuery = true)
	Users findByEmailandSenha(String email, String senha);
	
	Users findUserByEmail(String email);
}
