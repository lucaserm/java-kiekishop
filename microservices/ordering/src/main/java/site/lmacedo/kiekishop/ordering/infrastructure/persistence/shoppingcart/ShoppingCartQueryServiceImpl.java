package site.lmacedo.kiekishop.ordering.infrastructure.persistence.shoppingcart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.lmacedo.kiekishop.ordering.application.shoppingcart.query.ShoppingCartOutput;
import site.lmacedo.kiekishop.ordering.application.shoppingcart.query.ShoppingCartQueryService;
import site.lmacedo.kiekishop.ordering.application.utility.Mapper;
import site.lmacedo.kiekishop.ordering.domain.model.shoppingcart.ShoppingCartNotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartQueryServiceImpl implements ShoppingCartQueryService {

    private final ShoppingCartPersistenceEntityRepository persistenceRepository;
    private final Mapper mapper;

    @Override
    public ShoppingCartOutput findById(UUID shoppingCartId) {
        return persistenceRepository.findById(shoppingCartId)
                .map(s -> mapper.convert(s, ShoppingCartOutput.class))
                .orElseThrow(ShoppingCartNotFoundException::new);
    }

    @Override
    public ShoppingCartOutput findByCustomerId(UUID customerId) {
        return persistenceRepository.findByCustomer_Id(customerId)
                .map(s -> mapper.convert(s, ShoppingCartOutput.class))
                .orElseThrow(ShoppingCartNotFoundException::new);
    }
}
