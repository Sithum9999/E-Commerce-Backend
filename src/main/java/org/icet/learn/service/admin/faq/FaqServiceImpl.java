package org.icet.learn.service.admin.faq;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.Faq;
import org.icet.learn.entity.FaqEntity;
import org.icet.learn.entity.ProductEntity;
import org.icet.learn.repository.FaqDao;
import org.icet.learn.repository.ProductDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService{

    private final FaqDao faqDao;
    private final ProductDao productDao;

    public Faq postFAQ(Long productId, Faq faqDto) {
        Optional<ProductEntity> optionalProduct = productDao.findById(productId);
        if (optionalProduct.isPresent()) {
            FaqEntity faq = new FaqEntity();

            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(optionalProduct.get());
            return faqDao.save(faq).getFaqDto();
        }
        return null;
    }

}
