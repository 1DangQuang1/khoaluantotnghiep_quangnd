package com.example.restapi.model;

import java.math.BigDecimal;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(
    name = "InvoiceItemResponseMapping",
    classes = @ConstructorResult(
        targetClass = com.example.restapi.dto.InvoiceItemResponse.class,
        columns = {
            @ColumnResult(name = "serviceName", type = String.class),
            @ColumnResult(name = "quantity", type = Integer.class),
            @ColumnResult(name = "unitPrice", type = String.class), // <-- fix
            @ColumnResult(name = "eachUnitPrice", type = BigDecimal.class),
            @ColumnResult(name = "totalPrice", type = BigDecimal.class),
            @ColumnResult(name = "insurancePaid", type = BigDecimal.class),
            @ColumnResult(name = "patientPaid", type = BigDecimal.class)
        }
    )
)
@NamedNativeQuery(
    name = "InvoiceItemResponse.findByVisitId",
    query = """
        with base as (
            select 
                   'Khám lân sàng' as serviceName,
                   count(*) as quantity,
                   'Dịch vụ' as unitPrice,
                   250000 as eachUnitPrice,
                   case when es.id is not null then 250000 * count(*) else 0 end as totalPrice,
                   case when es.id is not null then 75000 * count(*) else 0 end as insurancePaid,
                   case when es.id is not null then 175000 * count(*) else 0 end as patientPaid
            from ehrm.emr_visits es
            left join ehrm.emr_clinical_records ecr on es.id = ecr.visit_id
            where es.id = :visitId
              and es.cancle_reason is null
            group by es.id

            union all

            select 
                   elr.type as serviceName,
                   count(*) as quantity,
                   'Dịch vụ' as unitPrice,
                   elsm.price as eachUnitPrice,
                   count(*) * elsm.price as totalPrice,
                   count(*) * elsm.price * 30 /100 as insurancePaid,
                   count(*) * elsm.price * 70 /100 as patientPaid
            from ehrm.emr_visits es
            join ehrm.emr_lab_records elr on es.id = elr.visit_id
            join ehrm.emr_lab_service_mapping elsm on elr.type = elsm.service_name
            where es.id = :visitId
              and es.cancle_reason is null
            group by es.id, elr.type, elsm.price 

            union all

            select
                   'Nội trú' as serviceName,
                   (ev.updated_at::date - ev.created_at::date) as quantity,
                   'Ngày' as unitPrice,
                   100000 as eachUnitPrice,
                   case when ev.service_id = 1 
                        then (ev.updated_at::date - ev.created_at::date) * 100000
                        else 0 end as totalPrice,
                   case when ev.service_id = 1 
                        then (ev.updated_at::date - ev.created_at::date) * 30000
                        else 0 end as insurancePaid,
                   case when ev.service_id = 1 
                        then (ev.updated_at::date - ev.created_at::date) * 70000
                        else 0 end as patientPaid
            from ehrm.emr_visits ev
            where ev.id = :visitId
              and ev.cancle_reason is null

            union all

            select 
                   ed.name as serviceName,
                   epi.total_quantity as quantity,
                   'VND' as unitPrice,
                   ed.price as eachUnitPrice,
                   epi.total_quantity * ed.price as totalPrice,
                   0 as insurancePaid,
                   epi.total_quantity * ed.price as patientPaid
            from ehrm.emr_visits es
            join ehrm.emr_prescriptions ep on es.id = ep.visit_id
            join ehrm.emr_prescription_items epi on ep.id = epi.prescription_id
            join ehrm.emr_drugs ed on epi.drug_code = ed.code
            where es.id = :visitId
        )
        select serviceName, quantity, unitPrice, eachUnitPrice,
               totalPrice, insurancePaid, patientPaid, now()::date as invoiceDay
        from base
        where totalPrice != 0
        """,
    resultSetMapping = "InvoiceItemResponseMapping"
)
public class InvoiceItemResultMappingEntity {
    @Id
    private Long id; // dummy @Id
}
