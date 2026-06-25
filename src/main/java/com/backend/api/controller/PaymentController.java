package com.backend.api.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final String razorpayKeyId;
    private final String razorpayKeySecret;

    public PaymentController(
            @Value("${razorpay.key.id}") String razorpayKeyId,
            @Value("${razorpay.key.secret}") String razorpayKeySecret) {
        this.razorpayKeyId = razorpayKeyId;
        this.razorpayKeySecret = razorpayKeySecret;
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, Object>> createPaymentIntent(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        long amount = ((Number) data.get("amount")).longValue();
        String currency = (String) data.getOrDefault("currency", "INR");

        // Graceful fallback for dummy Razorpay keys to allow offline/local testing
        if (razorpayKeyId == null || razorpayKeyId.contains("dummy") || razorpayKeyId.trim().isEmpty() ||
            razorpayKeySecret == null || razorpayKeySecret.contains("dummy") || razorpayKeySecret.trim().isEmpty()) {
            response.put("orderId", "order_mock_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16));
            response.put("paymentIntentId", "ch_mock_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16));
            response.put("amount", amount);
            response.put("currency", currency);
            response.put("isMock", true);
            return ResponseEntity.ok(response);
        }

        try {
            RazorpayClient client = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount);
            orderRequest.put("currency", currency.toUpperCase());
            orderRequest.put("receipt", "txn_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16));

            Order order = client.orders.create(orderRequest);

            response.put("orderId", order.get("id"));
            response.put("keyId", razorpayKeyId);
            response.put("amount", amount);
            response.put("currency", currency);
            response.put("isMock", false);
            return ResponseEntity.ok(response);
        } catch (RazorpayException e) {
            System.err.println("Razorpay error: " + e.getMessage() + ". Falling back to mock transaction.");
            response.put("orderId", "order_mock_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16));
            response.put("paymentIntentId", "ch_mock_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16));
            response.put("amount", amount);
            response.put("currency", currency);
            response.put("isMock", true);
            return ResponseEntity.ok(response);
        }
    }
}
