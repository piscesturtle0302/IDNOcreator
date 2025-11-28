package com.example.IDNOcreator.common.database.dao;

import com.example.IDNOcreator.common.database.entity.IdnoCreator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdnoCreatorDao extends JpaRepository<IdnoCreator, Long> {
}
