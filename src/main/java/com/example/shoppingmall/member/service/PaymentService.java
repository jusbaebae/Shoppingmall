package com.example.shoppingmall.member.service;

import com.example.shoppingmall.member.dto.PaymentVerifyRequest;
import com.example.shoppingmall.member.entity.Order;
import com.example.shoppingmall.member.entity.OrderItem;
import com.example.shoppingmall.member.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

        //DB에서 주문을 찾기위한 Repository
        private final OrderRepository orderRepository;

        //application.yml에서 PortOne API Secret 가져오기
        @Value("${portone.api-secret}")
        private String portoneApiSecret;

        //PortOne서버와 HTTP 통신하기 위한 객체
        private final RestClient restClient = RestClient.builder()
                .baseUrl("https://api.portone.io")
                .build();

        //주문 확인하기
        public void verifyPayment(PaymentVerifyRequest request){
            //DB에서 주문 조회
            Order order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

            //PortOne에서 실제 결제 정보 조회
            Map<String, Object> payment = restClient.get()
                    .uri("/payments/{paymentId}", request.getPaymentId())
                    .header(
                            "Authorization",
                            "PortOne " + portoneApiSecret
                    )
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {});

            //결제 상태 확인
            String status = (String) payment.get("status");

            if(!"PAID".equals(status)){
                throw new RuntimeException(
                        "결제가 완료되지 않았습니다. 현재 상태: " + status
                );
            }

            //실제 결제 금액 가져오기
            Map<String, Object> amount = (Map<String, Object>) payment.get("amount");

            Number paidAmount = (Number) amount.get("total");

            //서버에서 주문 금액 계산
            int totalPrice = 0;
            for(OrderItem orderItem : order.getOrderItems()){
                totalPrice += orderItem.getOrderPrice() * orderItem.getQuantity();
            }

            //주문 금액이랑 비교
            if(paidAmount.longValue() != totalPrice){
                throw new RuntimeException("결제 금액이 일치하지 않습니다.");
            }

            //결제 성공 -> 주문 상태 변경
            order.setPaymentId(request.getPaymentId());
            order.pay();
            orderRepository.save(order);
        }

        public void refundPayment(String paymentId){
            restClient.post()
                    .uri("/payments/{paymentId}/cancel", paymentId)
                    .header(
                            "Authorization",
                            "PortOne " + portoneApiSecret
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(
                            "reason", "고객 주문 취소"
                    ))
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {});
        }
}
