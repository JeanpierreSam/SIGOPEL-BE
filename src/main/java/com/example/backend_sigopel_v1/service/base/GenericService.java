package com.example.backend_sigopel_v1.service.base;

import org.hibernate.service.spi.ServiceException;

import java.util.List;

public interface GenericService<E, DTO, ID> {
    DTO create(DTO dto) throws ServiceException;
    DTO read(ID id) throws ServiceException;
    DTO update(ID id, DTO dto) throws ServiceException;
    void delete(ID id) throws ServiceException;
    List<DTO> listAll() throws ServiceException;
}
