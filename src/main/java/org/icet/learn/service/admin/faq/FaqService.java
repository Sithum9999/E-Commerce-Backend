package org.icet.learn.service.admin.faq;

import org.icet.learn.dto.Faq;

public interface FaqService {

    Faq postFAQ(Long productId, Faq faqDto);

}
