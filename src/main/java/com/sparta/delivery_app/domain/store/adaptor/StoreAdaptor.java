package com.sparta.delivery_app.domain.store.adaptor;

import com.sparta.delivery_app.common.exception.errorcode.StoreErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.StoreHistoryException;
import com.sparta.delivery_app.common.globalcustomexception.StoreNotFoundException;
import com.sparta.delivery_app.domain.store.dto.request.ModifySotoreRequestDto;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.repository.StoreRepository;
import com.sparta.delivery_app.common.globalcustomexception.StoreDuplicatedException;
import com.sparta.delivery_app.common.globalcustomexception.StoreNotFoundException;
import com.sparta.delivery_app.domain.store.dto.request.RegisterStoreRequestDto;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.repository.StoreRepository;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.delivery_app.common.exception.errorcode.StoreErrorCode.DUPLICATED_STORE;

@Component
@RequiredArgsConstructor
public class StoreAdaptor {

    private final StoreRepository storeRepository;

    public Store queryStoreById(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(() ->
                new StoreNotFoundException(StoreErrorCode.INVALID_STORE));
    }

    public void checkStoreHistory(User user) {
        storeRepository.findStoreByUser(user)
                .ifPresent(s -> {
                    throw new StoreHistoryException(StoreErrorCode.STORE_HISTORY);
                });
    }

    public void validStoreRegistrationNumber(User user, String storeRegistrationNumber) {
        storeRepository.findByUserOrStoreRegistrationNumber(user, storeRegistrationNumber)
                .ifPresent(s -> {
                            throw new StoreDuplicatedException(DUPLICATED_STORE_BUSINESS_NUMBER);
                        }
                );
    }

    public Store checkStoreId(User user) {
        return storeRepository.findStoreByUser(user).orElseThrow(() ->
                new StoreNotFoundException(StoreErrorCode.INVALID_STORE)
        );
    }

    @Transactional
    public Store saveStore(RegisterStoreRequestDto requestDto, User user) {

        Store newStore = new Store(requestDto, user);
        storeRepository.save(newStore);

        return newStore;
    }


    public Store findById(Long storeId) {
        Optional<Store> store = storeRepository.findById(storeId);

        if (!store.isEmpty()) {
            throw new StoreNotFoundException(StoreErrorCode.INVALID_STORE);
        }

        return store.get();
    }
    @Transactional
    public Store modifyStore(ModifySotoreRequestDto requestDto, Store ownedStore) {
        ownedStore.modifyStore(requestDto);
        storeRepository.save(ownedStore);
        return ownedStore;
    }
}