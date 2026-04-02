package com.senai.SA.service;

import com.senai.SA.dto.UsuarioRequisicaoDto;
import com.senai.SA.dto.UsuarioRespostaDto;
import com.senai.SA.exceptions.DataNascimentoInvalidaException;
import com.senai.SA.exceptions.ExistEmailException;
import com.senai.SA.exceptions.NotFoundException;
import com.senai.SA.model.Role;
import com.senai.SA.model.UsuarioModel;
import com.senai.SA.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public List<UsuarioRespostaDto> listarUsuarios() {
        List<UsuarioModel> usuarios = usuarioRepository.findAll();

        List<UsuarioRespostaDto> usuarioRespostaDto = new ArrayList<>();
        for (UsuarioModel usuario : usuarios) {
            usuarioRespostaDto.add(new UsuarioRespostaDto(usuario));
        }
        return usuarioRespostaDto;
    }

    public boolean cadastrarUsuario(UsuarioRequisicaoDto dados) {

        Optional<UsuarioModel> usuario = usuarioRepository.findByEmail(dados.getEmail());

        if (usuario.isPresent()) {
            throw new ExistEmailException("Já existe um usuario com esse email!!");
        } else if (temMaisDe500Anos(dados.getDataNascimento())) {
            throw new DataNascimentoInvalidaException("Data de nascimento invalida!!");
        }

        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setEmail(dados.getEmail());
        usuarioModel.setSenha(encoder.encode(dados.getSenha()));
        usuarioModel.setDataNascimento(dados.getDataNascimento());
        usuarioModel.setNome(dados.getNome());
        usuarioModel.setRole(dados.getRole());
        usuarioRepository.save(usuarioModel);
        return true;
    }

    private boolean temMaisDe500Anos(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            return false;
        }

        long idade = ChronoUnit.YEARS.between(dataNascimento, LocalDate.now());
        return idade > 500;
    }

    public boolean deletarUsuario(long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean atualizarUsuario(UsuarioRequisicaoDto dados, long id) {


        Optional<UsuarioModel> usuarioAntigoOP = usuarioRepository.findById(id);
        if (usuarioAntigoOP.isEmpty()) {
            throw new NotFoundException("Usuario não encontrado!!");
        }


        Optional<UsuarioModel> usuarioAtualizarEmail = usuarioRepository.findByEmail(dados.getEmail());
        if (usuarioAtualizarEmail.isPresent() && usuarioAtualizarEmail.get().getId() != id) {
            throw new ExistEmailException("Já existe um usuario com esse email!!");
        }


        if (temMaisDe500Anos(dados.getDataNascimento())) {
            throw new DataNascimentoInvalidaException("Data de nascimento invalida!!");
        }


        UsuarioModel usuarioAntigo = usuarioAntigoOP.get();
        usuarioAntigo.setNome(dados.getNome());
        usuarioAntigo.setEmail(dados.getEmail());
        usuarioAntigo.setDataNascimento(dados.getDataNascimento());


        if (dados.getSenha() != null && !dados.getSenha().isBlank()) {
            usuarioAntigo.setSenha(encoder.encode(dados.getSenha()));
        }
        usuarioRepository.save(usuarioAntigo);
        return true;
    }

    public UsuarioRespostaDto buscarUsuarioById(long id) {
        Optional<UsuarioModel> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return new UsuarioRespostaDto(usuario.get());
        }
        throw new NotFoundException("Usuario não encontrado!!");
    }

    public long qtdUsuarios() {
        return usuarioRepository.count();
    }


}
