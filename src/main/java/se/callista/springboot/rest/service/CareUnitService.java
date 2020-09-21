package se.callista.springboot.rest.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import se.callista.springboot.rest.api.v1.Bed;
import se.callista.springboot.rest.api.v1.CareUnit;
import se.callista.springboot.rest.api.v1.Hospital;
import se.callista.springboot.rest.domain.BedJPA;
import se.callista.springboot.rest.domain.CareUnitCriteria;
import se.callista.springboot.rest.domain.CareUnitJPA;
import se.callista.springboot.rest.domain.CareUnitRepository;
import se.callista.springboot.rest.domain.HospitalJPA;
import se.callista.springboot.rest.domain.HospitalRepository;
import se.callista.springboot.rest.domain.QBedJPA;
import se.callista.springboot.rest.domain.QCareUnitJPA;
import se.callista.springboot.rest.exception.NotFoundException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CareUnitService {

    @Autowired
    CareUnitRepository careUnitRepository;

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    CareUnitMapper careUnitMapper;

    public Page<CareUnit> findAll(CareUnitCriteria criteria, Pageable pageable) {
        Predicate predicate = buildPredicate(criteria);
        Page<CareUnitJPA> careunits = null;
        if (predicate == null) {
            careunits = careUnitRepository.findAll(pageable);
        } else {
            careunits = careUnitRepository.findAll(predicate, pageable);
        }

        //Transform to DTO's
        return new PageImpl<CareUnit>(careUnitMapper.toListDTOs(careunits.getContent()), pageable, careunits.getTotalElements());
    }

    public CareUnit findOne(long id) {
        CareUnitJPA careunit = careUnitRepository.findById(id).orElseThrow(() -> new NotFoundException("Careunit", Long.toString(id)));
        return careUnitMapper.toDTO(careunit);
    }

    public CareUnit save(Long hospitalId, CareUnit careunit) {
        // Transform from DTO
        CareUnitJPA careunitJPA = careUnitMapper.fromDTO(careunit);

        HospitalJPA hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new NotFoundException("Hospital", Long.toString(hospitalId)));
        careunitJPA.setHospital(hospital);

        CareUnitJPA savedCareunitJPA = careUnitRepository.save(careunitJPA);

        //Transform to DTO
        return careUnitMapper.toDTO(savedCareunitJPA);
    }

    public void update(CareUnit careunit) {
        // Transform from DTO
        CareUnitJPA careunitJPA = careUnitMapper.fromDTO(careunit);

        careUnitRepository.save(careunitJPA);
    }

    public void delete(Long id) {
        careUnitRepository.deleteById(id);
    }

    private Predicate buildPredicate(CareUnitCriteria criteria) {
        BooleanBuilder builder = new BooleanBuilder();
        QCareUnitJPA careUnit = QCareUnitJPA.careUnitJPA;

        if (criteria != null) {
            if (criteria.getName() != null) {
                builder.and(careUnit.name.containsIgnoreCase(criteria.getName()));
            }
            if (criteria.getPhoneNumber() != null) {
                builder.and(careUnit.phoneNumber.containsIgnoreCase(criteria.getPhoneNumber()));
            }
            if (criteria.getEmail() != null) {
                builder.and(careUnit.email.containsIgnoreCase(criteria.getEmail()));
            }
        }
        return builder.getValue();
    }
}
