package br.com.repositorio.repo.config.validacao;

import com.sun.xml.bind.v2.schemagen.episode.Klass;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {


    private String domainAtribute;
    private Class<?> klass;
    @PersistenceContext
    private EntityManager manager;


    @Override
    public void initialize(UniqueValue uv) {
        domainAtribute = uv.fieldName();
        klass = uv.domainClass();
    }

    @Override
    public boolean isValid(Object criterio, ConstraintValidatorContext context) {
        Query query = manager.createQuery("SELECT 1 FROM " +
                klass.getName() +" WHERE " + domainAtribute + " = :criterio ");
        query.setParameter("criterio", criterio);
        List<?> existentes = query.getResultList();
        return existentes.isEmpty();
    }
}
