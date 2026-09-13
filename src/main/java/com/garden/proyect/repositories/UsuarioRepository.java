package com.garden.proyect.repositories;

import com.garden.proyect.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.email = :email and u.password = :password")
    Usuario findUsuarioByNombreAndPassword(@Param("email") String email, @Param("password") String password);

    Optional<Usuario> findByEmail(String email);

}
