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
               @ColumnResult(name = "visit_id", type = Long.class),
               @ColumnResult(name = "service_name", type = String.class),
               @ColumnResult(name = "quantity", type = Integer.class),
               @ColumnResult(name = "unit_price", type = BigDecimal.class),
               @ColumnResult(name = "each_unit_price", type = BigDecimal.class),
               @ColumnResult(name = "total_price", type = BigDecimal.class),
               @ColumnResult(name = "insurance_paid", type = BigDecimal.class),
               @ColumnResult(name = "patient_paid", type = BigDecimal.class)
          }
     )
)
@NamedNativeQuery(
     name = "InvoiceItemResponse.findByVisitId",
     query = """
            with base as (
			      select es.id visit_id,
                   'Khám lân sàng' as service_name,
                   count(*) as quantity,
                   'Dịch vụ' as unit_price,
                   250000 as each_unit_price,
                   case when es.id is not null then 250000 * count(*)  else 0 end as total_price,
                   case when es.id is not null then 75000 * count(*)  else 0 end as insurance_paid,
                   case when es.id is not null then 175000 * count(*) else 0 end as patient_paid
            from ehrm.emr_visits es 
            left join ehrm.emr_clinical_records ecr on es.id = ecr.visit_id 
            where es.id = :visitId
              and es.cancle_reason is null
            group by es.id
        union all
            			select es.id as visit_id,
                   elr.type as service_name,
                   count(*) as quantity,
                   'Dịch vụ' as unit_price,
                   elsm.price as each_unit_price,
                   count(*) * elsm.price as total_price,
                   count(*) * elsm.price * 30 /100 as insurance_paid,
                    count(*) * elsm.price * 70 /100 as patient_paid
            from ehrm.emr_visits es 
            join ehrm.emr_lab_records elr on es.id = elr.visit_id 
            join ehrm.emr_lab_service_mapping elsm on elr.type = elsm.service_name
            where es.id = :visitId
              and es.cancle_reason is null
            group by es.id, elr.type, elsm.price 
			union all
            select ev.id as visit_id,
                   'Nội trú' as service_name,
                   (ev.updated_at::date - ev.created_at::date) as quantity,
                   'Ngày' as unit_price,
                   100000 as each_unit_price,
                   case when ev.service_id = 1 
                        then (ev.updated_at::date - ev.created_at::date) * 100000 
                        else 0 end as total_price,
                   case when ev.service_id = 1 
                        then (ev.updated_at::date - ev.created_at::date) * 30000 
                        else 0 end as insurance_paid,
                   case when ev.service_id = 1 
                        then (ev.updated_at::date - ev.created_at::date) * 70000 
                        else 0 end as patient_paid
            from ehrm.emr_visits ev 
            where ev.id = 1
              and ev.cancle_reason is null
      union all 
           select es.id as visit_id,
				ed.name as service_name,
				epi.total_quantity as quantiy,
				'VND' as unit_price,
				ed.price as each_unit_price,
				epi.total_quantity * ed.price as total_price,
				0 as insurance_paid,
				epi.total_quantity * ed.price as patient_paid
			from ehrm.emr_visits es
			join ehrm.emr_prescriptions ep on es.id = ep.visit_id 
			join ehrm.emr_prescription_items epi on ep.id = epi.prescription_id 
			join ehrm.emr_drugs ed on epi.drug_code = ed.code 
			where es.id = :visitId
        )
        select *, now()::date as invoice_day from base where total_price != 0
        union all
        select visit_id, 'Tổng chi phí' as service_name, null as quantity, 'VND' as unit_price, 
        null as each_unit_price, sum(total_price) as total_price, sum(insurance_paid) as insurance_paid, 
        sum(patient_paid) as patient_paid, now()::date as invoice_day
        from base group by visit_id
        """,
        resultSetMapping = "InvoiceItemResponseMapping"

    )
public class InvoiceItemResultMappingEntity {
    @Id
    private Long id; // just a dummy @Id field to satisfy JPA
}