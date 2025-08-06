package com.marioorozco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean //para que no cree una instancia ya que daba error java.lang.object por no saber a que identidad hace referencia
public interface IGenericRepo<T, ID>  extends JpaRepository<T, ID> {
}
