package com.perfums.transactions.infraestructure.adapters.postgresql.repositories.panache;

import com.perfums.transactions.infraestructure.adapters.postgresql.entitys.Fragrance;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class FragancePanacheRepository implements PanacheRepositoryBase<Fragrance, Long> {

    public Uni<List<Fragrance>> findByFilters(Set<Long> filterIds, String searchText, int page, int size) {
        StringBuilder queryBuilder = new StringBuilder("""
                    SELECT DISTINCT f FROM Fragrance f
                    LEFT JOIN FETCH f.sizes fs
                    LEFT JOIN FETCH fs.size s
                    LEFT JOIN FETCH f.catalogParameters cp
                """);

        boolean hasFilters = filterIds != null && !filterIds.isEmpty();
        boolean hasSearch = searchText != null && !searchText.isBlank();

        if (hasFilters || hasSearch) {
            queryBuilder.append(" WHERE ");
        }

        if (hasFilters) {
            queryBuilder.append(" cp.id IN :filterIds ");
        }

        if (hasFilters && hasSearch) {
            queryBuilder.append(" AND ");
        }

        if (hasSearch) {
            queryBuilder.append(" (LOWER(f.name) LIKE :text OR LOWER(f.description) LIKE :text) ");
        }

        int offset = page * size;

        return getSession().flatMap(session -> {
            var query = session.createQuery(queryBuilder.toString(), Fragrance.class);
            if (hasFilters) query.setParameter("filterIds", filterIds);
            if (hasSearch) query.setParameter("text", "%" + searchText.toLowerCase() + "%");

            query.setFirstResult(offset);
            query.setMaxResults(size);

            return query.getResultList();
        });
    }


    public Uni<Long> countFiltered(Set<Long> filterIds, String searchText) {
        StringBuilder queryBuilder = new StringBuilder("""
                    SELECT COUNT(DISTINCT f) FROM Fragrance f
                    LEFT JOIN f.catalogParameters cp
                """);

        boolean hasFilters = filterIds != null && !filterIds.isEmpty();
        boolean hasSearch = searchText != null && !searchText.isBlank();

        if (hasFilters || hasSearch) {
            queryBuilder.append(" WHERE ");
        }

        if (hasFilters) {
            queryBuilder.append(" cp.id IN :filterIds ");
        }

        if (hasFilters && hasSearch) {
            queryBuilder.append(" AND ");
        }

        if (hasSearch) {
            queryBuilder.append(" (LOWER(f.name) LIKE :text OR LOWER(f.description) LIKE :text) ");
        }

        return getSession().flatMap(session -> {
            var query = session.createQuery(queryBuilder.toString(), Long.class);
            if (hasFilters) query.setParameter("filterIds", filterIds);
            if (hasSearch) query.setParameter("text", "%" + searchText.toLowerCase() + "%");
            return query.getSingleResult();
        });
    }
}
