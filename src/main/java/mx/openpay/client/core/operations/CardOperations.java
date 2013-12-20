/*
 * Copyright 2013 Opencard Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mx.openpay.client.core.operations;

import static mx.openpay.client.utils.OpenpayPathComponents.CARDS;
import static mx.openpay.client.utils.OpenpayPathComponents.CUSTOMERS;
import static mx.openpay.client.utils.OpenpayPathComponents.ID;
import static mx.openpay.client.utils.OpenpayPathComponents.MERCHANT_ID;

import java.util.List;
import java.util.Map;

import mx.openpay.client.Address;
import mx.openpay.client.Card;
import mx.openpay.client.core.JsonServiceClient;
import mx.openpay.client.core.requests.card.CreateCardParams;
import mx.openpay.client.exceptions.OpenpayServiceException;
import mx.openpay.client.exceptions.ServiceUnavailableException;
import mx.openpay.client.utils.ListTypes;
import mx.openpay.client.utils.SearchParams;

/**
 * @author elopez
 */
public class CardOperations extends ServiceOperations {

    private static final String MERCHANT_CARDS_PATH = MERCHANT_ID + CARDS;

    private static final String CUSTOMER_CARDS_PATH = MERCHANT_ID + CUSTOMERS + ID + CARDS;

    private static final String GET_CARD_PATH = CUSTOMER_CARDS_PATH + ID;

    public CardOperations(final JsonServiceClient client, final String merchantId) {
        super(client, merchantId);
    }

    public Card create(final CreateCardParams params) throws OpenpayServiceException, ServiceUnavailableException {
        String path;
        if (params.getCustomerId() == null) {
            path = String.format(MERCHANT_CARDS_PATH, this.getMerchantId());
        } else {
            path = String.format(CUSTOMER_CARDS_PATH, this.getMerchantId(), params.getCustomerId());
        }
        return this.getJsonClient().post(path, params.asMap(), Card.class);
    }

    public List<Card> list(final String customerId, final SearchParams params)
            throws ServiceUnavailableException, OpenpayServiceException {
        String path = String.format(CUSTOMER_CARDS_PATH, this.getMerchantId(), customerId);
        Map<String, String> map = params == null ? null : params.asMap();
        return this.getJsonClient().list(path, map, ListTypes.CARD);
    }

    public Card get(final String customerId, final String cardId) throws ServiceUnavailableException,
            OpenpayServiceException {
        String path = String.format(GET_CARD_PATH, this.getMerchantId(), customerId, cardId);
        return this.getJsonClient().get(path, Card.class);
    }

    public void delete(final String customerId, final String cardId) throws ServiceUnavailableException,
            OpenpayServiceException {
        String path = String.format(GET_CARD_PATH, this.getMerchantId(), customerId, cardId);
        this.getJsonClient().delete(path);
    }

    @Deprecated
    public Card create(final String customerId, final String cardNumber, final String holderName,
            final String cvv2, final String expMonth, final String expYear, final Address address)
            throws ServiceUnavailableException, OpenpayServiceException {
        return this.create(new CreateCardParams()
                .customerId(customerId)
                .cardNumber(cardNumber)
                .cvv2(cvv2)
                .holderName(holderName)
                .address(address)
                .expirationMonth(expMonth)
                .expirationYear(expYear));
    }

}
