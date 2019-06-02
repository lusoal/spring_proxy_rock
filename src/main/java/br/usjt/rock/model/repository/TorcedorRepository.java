package br.usjt.rock.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.usjt.rock.model.bean.Torcedor;

@Repository
public interface TorcedorRepository extends JpaRepository<Torcedor, Long> {
	
	public static final String validaUser = "SELECT * FROM tb_torcedores WHERE email = ? AND senha = ?";
	@Query(value = validaUser, nativeQuery = true)
	Torcedor findByEmailandSenha(String email, String senha);
	
	Torcedor findUserByEmail(String email);
}
