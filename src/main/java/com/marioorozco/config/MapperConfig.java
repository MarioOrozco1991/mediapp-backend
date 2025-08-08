package com.marioorozco.config;

import com.marioorozco.dto.MedicDTO;
import com.marioorozco.model.Medic;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean(name = "defaultMapper")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean(name = "medicMapper")
    public ModelMapper medicMapper() {
        ModelMapper modelMapper = new ModelMapper();

        //Escritura POST PUT
        //Se usa para mapear los datos de entrada del cliente a la entidad Medic, cuando estos no coinciden (DTO, con la clase Medic)
        modelMapper.createTypeMap(MedicDTO.class, Medic.class)
                .addMapping(MedicDTO::getPrimaryName, (dest, v) -> dest.setFirstName((String) v))
                .addMapping(MedicDTO::getSurname, (dest, v) -> dest.setLastName((String) v))
                .addMapping(MedicDTO::getPhoto, (dest, v) -> dest.setPhotoUrl((String) v));

        //Lectura GET
        modelMapper.createTypeMap(Medic.class, MedicDTO.class)
                .addMapping(Medic::getFirstName, (dest, v) -> dest.setPrimaryName((String) v))
                .addMapping(Medic::getLastName, (dest, v) -> dest.setSurname((String) v));

        return modelMapper;
    }
}
