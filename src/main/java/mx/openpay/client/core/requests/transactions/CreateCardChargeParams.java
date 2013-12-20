/*
 * COPYRIGHT © 2012-2013. OPENPAY.
 * PATENT PENDING. ALL RIGHTS RESERVED.
 * OPENPAY & OPENCARD IS A REGISTERED TRADEMARK OF OPENCARD INC.
 *
 * This software is confidential and proprietary information of OPENCARD INC.
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the company policy.
 */
package mx.openpay.client.core.requests.transactions;

import java.math.BigDecimal;

import lombok.Getter;
import mx.openpay.client.core.requests.RequestBuilder;
import mx.openpay.client.core.requests.card.CreateCardParams;
import mx.openpay.client.enums.ChargeMethods;

/**
 * @author elopez
 */
public class CreateCardChargeParams extends RequestBuilder {

    @Getter
    private String customerId;

    public CreateCardChargeParams() {
        this.with("method", ChargeMethods.CARD.name().toLowerCase());
    }

    public CreateCardChargeParams customerId(final String customerId) {
        this.customerId = customerId;
        return this;
    }

    public CreateCardChargeParams card(final CreateCardParams card) {
        if (card == null) {
            return this.with("card", null);
        } else {
            return this.with("card", card.asMap());
        }
    }

    public CreateCardChargeParams cardId(final String cardId) {
        return this.with("source_id", cardId);
    }

    public CreateCardChargeParams amount(final BigDecimal amount) {
        return this.with("amount", amount);
    }

    public CreateCardChargeParams description(final String description) {
        return this.with("description", description);
    }

    public CreateCardChargeParams orderId(final String orderId) {
        return this.with("order_id", orderId);
    }

}
