package com.owned.examples.patterns.structural.proxy;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class Demo {

    /**
     * Pattern used when you need to control access to objects either local or remote for any purpose i.e:
     * including security restrictions, auditing method calls and parameters, etc.
     *
     * @param args Entry point arguments
     */
    public static void main(String[] args) {
        final QuoteFactory quoteFactory = new QuoteFactory();
//
//         Decorator pattern vs proxy pattern:
//
//         Proxy is primarily about mediating access, whereas Decorator is about adding functionality
//         Proxy is hidden from the client (through some creational method), while decorator should be explicitly declared.
//
        QuoteService proxy = quoteFactory.getSupplier("proxyQuoteService");
        proxy.popularQuotes();

    }
}

interface QuoteService {

    List<QuoteService> popularQuotes();

    Optional<QuoteService> getQuote(String quoteId);

}

class QuoteServiceServiceImpl implements QuoteService {

    @Override
    public List<QuoteService> popularQuotes() {
        System.out.println("it should retrieve popular quotes");
        return Collections.emptyList();
    }

    @Override
    public Optional<QuoteService> getQuote(String quoteId) {
        System.out.println("Retrieve quote for " + quoteId); //LOGGER.debug("Retrieve quote for {}", quoteId);
        return Optional.empty();
    }
}

class ProxyQuoteService implements QuoteService {

    private QuoteService quoteService;

    private Map<String, QuoteService> cachedQuotes = new HashMap<>();

    ProxyQuoteService() {
        this.quoteService = new QuoteServiceServiceImpl();
    }

    @Override
    public List<QuoteService> popularQuotes() {
        System.out.println("Includes security restrictions by third parties");
        System.out.println("or");
        System.out.println("audits methods calls");

        return quoteService.popularQuotes();
    }

    @Override
    public Optional<QuoteService> getQuote(String quoteId) {
        if (cachedQuotes.containsKey(quoteId)) {
            return Optional.of(cachedQuotes.get(quoteId));
        } else {
            return quoteService.getQuote(quoteId);
        }
    }
}

class DefaultQuoteService implements QuoteService {

    @Override
    public List<QuoteService> popularQuotes() {
        return Collections.emptyList();
    }

    @Override
    public Optional<QuoteService> getQuote(String quoteId) {
        return Optional.empty();
    }
}

class QuoteFactory {

    private final static Map<String, Supplier<QuoteService>> suppliers = new HashMap<>();

    static {
        suppliers.put("targetQuoteService", QuoteServiceServiceImpl::new);
        suppliers.put("proxyQuoteService", ProxyQuoteService::new);
    }

    QuoteService getSupplier(String supplier) {
        return suppliers.getOrDefault(supplier, DefaultQuoteService::new).get();
    }
}


