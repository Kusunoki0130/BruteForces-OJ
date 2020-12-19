package com.bruteforces.demo.dao;

import com.bruteforces.demo.entity.ConPro;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConProMapper {

    List<ConPro> findProbbyconId(int conId);

    int insertNewConPro(ConPro cp);

    int deleteConPro(ConPro cp);

}
