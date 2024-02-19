package com.GrowWithMe.GrowWithMe.utils;

import com.GrowWithMe.GrowWithMe.model.Role;
import com.GrowWithMe.GrowWithMe.repository.IRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialRoleLoader implements CommandLineRunner {
    private final IRoleRepository roleRepository;

    public InitialRoleLoader(IRoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(!roleRepository.existsByAuthority("TRAINER")){
            Role trainerRole=new Role();
            trainerRole.setAuthority("TRAINER");
            roleRepository.save(trainerRole);
        }
        if(!roleRepository.existsByAuthority("CLIENT")){
            Role clientRole = new Role();
            clientRole.setAuthority("CLIENT");
            roleRepository.save(clientRole);
        }
    }
}
