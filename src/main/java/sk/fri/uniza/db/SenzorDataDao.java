package sk.fri.uniza.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import sk.fri.uniza.api.Paged;
import sk.fri.uniza.api.Senzor_data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class SenzorDataDao extends AbstractDAO<Senzor_data> implements BasicDao<Senzor_data, Long> {
    public SenzorDataDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Senzor_data> findById(Long id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(get(id));
    }

    @Override
    public List<Senzor_data> getAll() {
        CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Senzor_data> criteriaQuery = builder.createQuery(Senzor_data.class);
        Root<Senzor_data> root = criteriaQuery.from(Senzor_data.class);
        criteriaQuery.select(root);
        List<Senzor_data> list = list(criteriaQuery);
        return list;
    }

    @Override
    public Paged<List<Senzor_data>> getAll(int limit, int page) {
        CriteriaBuilder builderS = currentSession().getCriteriaBuilder();
        CriteriaQuery<Long> cqS = builderS.createQuery(Long.class);
        Root<Senzor_data> root = cqS.from(Senzor_data.class);
        cqS.select(builderS.count(root));
        Long countResults = currentSession().createQuery(cqS).getSingleResult();

        if (countResults == 0) return null;

        CriteriaQuery<Senzor_data> criteriaQuery = builderS.createQuery(Senzor_data.class);
        criteriaQuery.select(criteriaQuery.from(Senzor_data.class));
        List<Senzor_data> list = currentSession().createQuery(criteriaQuery)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .list();

        return new Paged<>(page, limit, countResults, list);
    }

    @Override
    public Long save(Senzor_data senzor_data) {
        persist(senzor_data);
        return senzor_data.getId();
    }

    @Override
    public Long update(Senzor_data senzor_data, String[] params) {
        Optional<Senzor_data> senzor_dataOptional = findById(senzor_data.getId());
        senzor_dataOptional.ifPresent(senzor_data1 -> {
            //senzor_data.setKto(senzor_data1.getKto()); // upravene
            currentSession().detach(senzor_data1);
        });
        persist(senzor_data);
        return senzor_data.getId();
    }

    @Override
    public void delete(Senzor_data senzor_data) {
        currentSession().delete(senzor_data);
    }
}
