package com.reloaded.sales.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseCriteriaReportRepository<E, F, D> {

  @PersistenceContext
  protected EntityManager em;

  protected abstract Class<E> entityClass();
  protected abstract Class<D> dtoClass();

  protected abstract void applyJoins(Root<E> root, Map<String, Join<?, ?>> joins);

  protected abstract List<Predicate> buildPredicates(
    CriteriaBuilder cb,
    Root<E> root,
    Map<String, Join<?, ?>> joins,
    F filter
  );

  protected abstract List<SelectionMapping> buildSelectionMapping(
    Root<E> orderForm,
    Map<String, Join<?, ?>> joins);

  protected SelectionMapping select(
    From<?, ?> from,
    String entityField,
    String dtoField
  ) {
    Path<?> path = from.get(entityField);
    return new SelectionMapping(
      dtoField,
      path,
      path.alias(dtoField)
    );
  }

  @SuppressWarnings("unchecked")
  protected <Z, X> Join<Z, X> resolveJoin(Map<String, Join<?, ?>> joins, String key) {
    return (Join<Z, X>) joins.get(key);
  }

  protected Path<?> resolvePath(Root<E> root, Map<String, Join<?, ?>> joins, String property) {
    if (property.contains(".")) {
      String[] parts = property.split("\\.");
      Join<?, ?> join = joins.get(parts[0]);
      if (join != null) {
        Path<?> path = join;
        for (int i = 1; i < parts.length; i++) {
          path = path.get(parts[i]);
        }
        return path;
      } else {
        Path<?> path = root.get(parts[0]);
        for (int i = 1; i < parts.length; i++) {
          path = path.get(parts[i]);
        }
        return path;
      }
    }

    Join<?, ?> join = joins.get(property);
    if (join != null) return join;
    return root.get(property);
  }

  protected void applySorting(
    CriteriaBuilder cb,
    CriteriaQuery<?> cq,
    Pageable pageable,
    Map<String, Path<?>> pathByDtoField
  ) {
    if (!pageable.getSort().isSorted()) return;

    List<Order> orders = new ArrayList<>();

    for (Sort.Order s : pageable.getSort()) {
      Path<?> path = pathByDtoField.get(s.getProperty());
      if (path == null) {
        throw new IllegalArgumentException(
          "Sorting not supported for: " + s.getProperty()
        );
      }

      orders.add(s.isAscending()
        ? cb.asc(path)
        : cb.desc(path));
    }

    cq.orderBy(orders);
  }

  public Page<D> findAll(F filter, Pageable pageable) {
    CriteriaBuilder cb = em.getCriteriaBuilder();

    // ===== MAIN QUERY =====
    CriteriaQuery<Tuple> cq = cb.createTupleQuery();
    Root<E> root = cq.from(entityClass());
    Map<String, Join<?, ?>> joins = new HashMap<>();
    applyJoins(root, joins);

    List<SelectionMapping> mappings = buildSelectionMapping(root, joins);
    cq.multiselect(
      mappings.stream()
        .map(SelectionMapping::getSelection)
        .toArray(Selection[]::new)
    );
    cq.where(buildPredicates(cb, root, joins, filter).toArray(Predicate[]::new));
    cq.distinct(true);

    Map<String, Path<?>> pathByDtoField =
      mappings.stream().collect(Collectors.toMap(
        SelectionMapping::getDtoField,
        SelectionMapping::getPath
      ));
    applySorting(cb, cq, pageable, pathByDtoField);

    TypedQuery<Tuple> query = em.createQuery(cq);
    query.setFirstResult((int) pageable.getOffset());
    query.setMaxResults(pageable.getPageSize());

    List<Tuple> tuples = query.getResultList();
    List<D> content = TupleMapper.mapToDto(tuples, dtoClass());

    // ===== COUNT QUERY =====
    CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
    Root<E> countRoot = countCq.from(entityClass());
    Map<String, Join<?, ?>> countJoins = new HashMap<>();
    applyJoins(countRoot, countJoins);

    countCq.select(cb.countDistinct(countRoot))
      .where(buildPredicates(cb, countRoot, countJoins, filter).toArray(Predicate[]::new));

    long total = em.createQuery(countCq).getSingleResult();
    return new PageImpl<>(content, pageable, total);
  }
}
