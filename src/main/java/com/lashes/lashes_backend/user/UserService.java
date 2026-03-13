package com.lashes.lashes_backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> listarTodos() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::from)
                .toList();
    }

    public List<UserDTO> listarPorRol(Rol rol) {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getRol() == rol)
                .map(UserDTO::from)
                .toList();
    }

    public UserDTO cambiarRol(Long id, Rol nuevoRol) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setRol(nuevoRol);
        return UserDTO.from(userRepository.save(user));
    }

    public UserDTO toggleActivo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setActivo(!user.getActivo());
        return UserDTO.from(userRepository.save(user));
    }
}
