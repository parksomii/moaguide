package com.moaguide.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moaguide.domain.card.Card;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.BillingService;
import com.moaguide.service.CardService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/card/")
public class CardRestController {
    private final CardService cardService;
    private final JWTUtil jwtUtil;
    private final BillingService billingService;

    @Value("${toss.secretkey}")
    private String secretkey;

    public CardRestController(CardService cardService, JWTUtil jwtUtil, BillingService billingService) {
        this.cardService = cardService;
        this.jwtUtil = jwtUtil;
        this.billingService = billingService;
    }

    @GetMapping("/mycard")
    public ResponseEntity<?> myCard(@RequestHeader(value = "Authorization") String jwt) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String nickname = jwtUtil.getNickname(jwt.substring(7));
            Card card = cardService.findByNickanme(nickname);
            Map<String,Object> map = new HashMap<>();
            if (card == null) {
                map.put("cardName",null);
                map.put("cardNumber",null);
            }else {
                map.put("cardName",card.getCardname());
                map.put("cardNumber",card.getCardNumber());
            }
            return ResponseEntity.ok(map);
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/create/card")
    public ResponseEntity<?> createCard(@RequestHeader(value = "Authorization") String jwt,String customerKey,String authKey) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String nickname = jwtUtil.getNickname(jwt.substring(7));
            String base64SecretKey = Base64.getEncoder().encodeToString((secretkey + ":").getBytes());
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.tosspayments.com/v1/billing/authorizations/issue"))
                    .header("Authorization", "Basic "+base64SecretKey)
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString("{\"authKey\":\""+authKey+"\",\"customerKey\":\""+customerKey+"\"}"))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            cardService.save(nickname,rootNode.get("cardCompany").asText(),Integer.valueOf(rootNode.get("card").get("number").asText().substring(0,2)));
            billingService.save(nickname,customerKey,rootNode.get("billingKey").asText());
            Map<String,Object> map = new HashMap<>();
            map.put("cardName",rootNode.get("cardCompany").asText());
            map.put("cardNumber",rootNode.get("card").asText());
            return ResponseEntity.ok().body(map);
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (DuplicateKeyException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate key");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/card")
    public ResponseEntity<?> deleteCard(@RequestHeader(value = "Authorization") String jwt) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String nickname = jwtUtil.getNickname(jwt.substring(7));
            cardService.delete(nickname);
            billingService.delete(nickname);
            return ResponseEntity.ok().build();
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
