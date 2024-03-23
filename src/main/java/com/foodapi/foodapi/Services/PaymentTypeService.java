package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.DTO.PaymentTypeDTO;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.ServiceCallsExceptionHandler;
import com.foodapi.foodapi.core.utils.CreateAndUpdateEntityHelper;
import com.foodapi.foodapi.exceptions.EntityNotFoundException;
import com.foodapi.foodapi.model.PaymentType;
import com.foodapi.foodapi.repository.PaymentTypeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentTypeService {
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;
    @Autowired
    private ServiceCallsExceptionHandler serviceCallsExceptionHandler;

    @Autowired
    CreateAndUpdateEntityHelper<PaymentTypeDTO, PaymentType, ?, PaymentTypeDTO> createAndUpdateEntityHelper;

    public List<PaymentType> getAll() {
        return paymentTypeRepository.findAll();
    }

    public PaymentType getOne(Long id) {
        return findOrFail(id);
    }

    @PostConstruct
    private void init() {
        createAndUpdateEntityHelper.setRepository(paymentTypeRepository);
    }

    @Transactional
    public void create(PaymentTypeDTO paymentType) {
        createAndUpdateEntityHelper.create(paymentType, PaymentType.class);
    }

    @Transactional
    public PaymentType update(PaymentTypeDTO paymentTypeDTO, Long id) {
//        var paymentTypeInDB = findOrFail(id);
//        var newPaymentType = apiObjectMapper.dtoToModel(
//                paymentTypeDTO, PaymentType.class);
//        var updatedPaymentType = apiObjectMapper.modelToUpdatedModel(
//                newPaymentType, paymentTypeInDB);
//        return paymentTypeRepository.save(updatedPaymentType);
        return createAndUpdateEntityHelper.update(paymentTypeDTO,id,PaymentType.class);
    }

    @Transactional
    public void delete(Long id) {
        findOrFail(id);
        serviceCallsExceptionHandler.executeOrThrowErrors(
                () -> {
                    paymentTypeRepository.deleteById(id);
                    paymentTypeRepository.flush();
                });
    }

    private PaymentType findOrFail(Long id) {
        return paymentTypeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Payment with id: " + id + " not found"));
    }
}
