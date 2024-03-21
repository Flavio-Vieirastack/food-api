package com.foodapi.foodapi.Services;

import com.foodapi.foodapi.DTO.PaymentTypeDTO;
import com.foodapi.foodapi.core.utils.ApiObjectMapper;
import com.foodapi.foodapi.core.utils.ServiceCallsExceptionHandler;
import com.foodapi.foodapi.exceptions.EntityNotFoundException;
import com.foodapi.foodapi.model.PaymentType;
import com.foodapi.foodapi.repository.PaymentTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentTypeService {
    @Autowired
    PaymentTypeRepository paymentTypeRepository;
    @Autowired
    ServiceCallsExceptionHandler serviceCallsExceptionHandler;

    @Autowired
    ApiObjectMapper<PaymentType> apiObjectMapper;

    public List<PaymentType> getAll() {
        return paymentTypeRepository.findAll();
    }

    public PaymentType getOne(Long id) {
        return findOrFail(id);
    }

    @Transactional
    public void create(PaymentTypeDTO paymentType) {
        var newPaymentType = apiObjectMapper.dtoToModel(paymentType, PaymentType.class);
        paymentTypeRepository.save(newPaymentType);
    }

    @Transactional
    public PaymentType update(PaymentTypeDTO paymentTypeDTO, Long id) {
        var paymentTypeInDB = findOrFail(id);
        var newPaymentType = apiObjectMapper.dtoToModel(
                paymentTypeDTO, PaymentType.class);
        var updatedPaymentType = apiObjectMapper.modelToUpdatedModel(
                newPaymentType, paymentTypeInDB);
        return paymentTypeRepository.save(updatedPaymentType);
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
