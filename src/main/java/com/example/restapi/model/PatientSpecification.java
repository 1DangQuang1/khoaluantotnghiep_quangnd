package com.example.restapi.model;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Subquery;

public class PatientSpecification {

    public static Specification<Patient> hasName(String name) {
        return (root, query, cb) ->
                (name == null || name.isEmpty()) ? cb.conjunction() :
                        cb.like(root.get("fullName"), "%" + name + "%");
    }

    public static Specification<Patient> hasCccd(String cccd) {
        return (root, query, cb) ->
                (cccd == null || cccd.isEmpty()) ? cb.conjunction() :
                        cb.equal(root.get("cccd"), cccd);
    }

    public static Specification<Patient> hasGender(String gender) {
        return (root, query, cb) ->
                (gender == null || gender.isEmpty()) ? cb.conjunction() :
                        cb.equal(root.get("gender"), gender);
    }

    public static Specification<Patient> hasBhyt(Boolean bhyt) {
        return (root, query, cb) ->
                bhyt == null ? cb.conjunction() : cb.equal(root.get("bhyt"), bhyt);
    }

    public static Specification<Patient> hasStatus(Visit.VisitStatus status) {
        return (root, query, cb) -> {
            if (status == null) return cb.conjunction();

            // subquery select 1 from Visit where patient_id = patient.id and status = ?
            Subquery<Long> sub = query.subquery(Long.class);
            var visitRoot = sub.from(Visit.class);
            sub.select(visitRoot.get("patientId"))
               .where(
                   cb.equal(visitRoot.get("patientId"), root.get("id")),
                   cb.equal(visitRoot.get("status"), status)
               );

            return cb.exists(sub);
        };
    }

    public static Specification<Patient> hasDepartment(Long departmentId) {
        return (root, query, cb) -> {
            if (departmentId == null) return cb.conjunction();

            Subquery<Long> sub = query.subquery(Long.class);
            var visitRoot = sub.from(Visit.class);
            sub.select(visitRoot.get("patientId"))
               .where(
                   cb.equal(visitRoot.get("patientId"), root.get("id")),
                   cb.equal(visitRoot.get("departmentId"), departmentId)
               );

            return cb.exists(sub);
        };
    }
}
