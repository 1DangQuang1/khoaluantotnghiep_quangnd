package com.example.restapi.repository;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.restapi.model.InvoiceItem;
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
    @Query(value = """
        with base as (
            select es.id as id,
                   'Khám lân sàng' as service_name,
                   1 as quantity,
                   'VND' as unit_price,
                   case when ecr.visit_id is not null then 250000 else 0 end as totalPrice,
                   case when ecr.visit_id is not null then 75000 else 0 end as insurance_paid,
                   case when ecr.visit_id is not null then 175000 else 0 end as patient_paid
            from emr_visits es 
            left join emr_clinical_records ecr on es.id = ecr.visit_id 
            where es.id = :visitId
              and es.cancle_reason is null
              and es.status = 'DONE'
            
            union all
            
            select ev.id,
                   'Nội trú' as service_name,
                   (ev.updated_at::date - ev.created_at::date) as quantity,
                   'VND' as unit_price,
                   case when ev.service_id = 1 
                        then (ev.updated_at::date - ev.created_at::date) * 100000 
                        else 0 end as totalPrice,
                   case when ev.service_id = 1 
                        then (ev.updated_at::date - ev.created_at::date) * 30000 
                        else 0 end as insurance_paid,
                   case when ev.service_id = 1 
                        then (ev.updated_at::date - ev.created_at::date) * 70000 
                        else 0 end as patient_paid
            from emr_visits ev 
            where ev.id = :visitId
              and ev.cancle_reason is null
              and ev.status = 'DONE'
        )
        select * from base where totalPrice != 0
        """, nativeQuery = true)
        List<Map<String, Object>> findInvoiceItemsByVisitId(@Param("visitId") Long visitId);
}
